package cn.xlink.query.builder;

import cn.xlink.query.domain.Metric;
import cn.xlink.query.domain.RequestBody;
import cn.xlink.query.utils.DataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.xlink.query.domain.Metric.*;
import static cn.xlink.query.domain.RequestBody.DEFAULT_THRESHOLD;

/**
 * Created by Ghold on 2017/6/1.
 * // todo 日志打印
 */
public class SqlBuilder implements Builder{
    private RequestBody body;
    private Date startTime;
    private Date endTime;

    private DateFormat utcFmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SqlBuilder() {}

    public SqlBuilder(RequestBody body, Date startTime, Date endTime) {
        this.body = body;
        this.startTime = startTime;
        this.endTime = endTime;
        utcFmt.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    /**
     * 根据请求的信息，组装SQL语句。注意这里会存在不同引擎的特殊处理
     * @return sql
     */
    @Override
    public String build() {
        StringBuilder selectStatement = new StringBuilder();
        StringBuilder groupByStatement = new StringBuilder();
        StringBuilder whereStatement = new StringBuilder();
        StringBuilder orderByStement = new StringBuilder();
        StringBuilder havingStatement = new StringBuilder();
        StringBuilder sql = new StringBuilder();

        List<String> selectList = new ArrayList<String>();
        List<String> groupByList = new ArrayList<String>();
        List<String> orderByList = new ArrayList<String>();
        List<String> havingList = new ArrayList<>();

        selectStatement.append("SELECT ");
        groupByStatement.append(" GROUP BY ");
        whereStatement.append(" WHERE ");
        orderByStement.append(" ORDER BY ");
        havingStatement.append(" HAVING ");

        // 是否显示时间列，select Timeseries
        if (!body.invalidInterval()) {
            switch (body.getEngine()) {
                case RequestBody.QUERY_ENGINE_DRUID:
                    selectList.add("FLOOR(__time to " + body.getOptions().get("interval") + ") __time");
                    groupByList.add("FLOOR(__time to " + body.getOptions().get("interval") + ")");
                    break;
                case RequestBody.QUERY_ENGINE_PRESTO:
                    if (body.getTimeField() == null || !body.getTimeField().getType().equals(DataType.Timestamp)) {
                        return null;
                    }
                    selectList.add("DATE_TRUNC('" + body.getOptions().get("interval") +  "', "+ body.fieldExchange(body.getTimeField().getField()) + ") __time");
                    groupByList.add("DATE_TRUNC('" + body.getOptions().get("interval") +  "', "+ body.fieldExchange(body.getTimeField().getField()) + ")");
                    break;
                default:
            }
        }

        // 处理非统计的字段，如果是聚合查询，需要把这些字段也放入groupby statement
        if (!body.invalidFields()) {
            for (String field : body.getFields()) {
                String selectField = body.fieldExchange(transactSQLInjection(field));
                selectList.add(selectField + " " + field);
                if (body.getQueryType() == RequestBody.QUERY_TYPE_AGGREGATE) {
                    groupByList.add(selectField);
                }
            }
        } else if (body.getQueryType() == RequestBody.QUERY_TYPE_SELECT) { // 如果查询类型是源数据，不是聚合，
            selectList.add("*");
        }

        // 处理统计的字段，放入select statement
        switch (body.getQueryType()) {
            case RequestBody.QUERY_TYPE_AGGREGATE:
                for (Metric metric: body.getMetrics().values()) {
                    String changed;
                    if ((changed = metricChangeToSql(metric, metric.getDisplayName())) != null) {
                        selectList.add(changed);
                    }
                }
                break;
            default:
        }

        // 组装select statement
        selectStatement
                .append(String.join(", ", selectList))
                .append(" FROM ")
                .append(transactSQLInjection(body.getDataset()));

        // 组装groupBy statement
        if (groupByList.size() > 0) {
            groupByStatement.append(String.join(", ", groupByList));
        }

        // 组装where statement
        whereState(whereStatement);

        // 组装having statement
        String havings = this.filterMap(body.getHavings(), null, true);

        if (!havings.equals("")){
            havingList.add(havings);
        }
        havingStatement.append(String.join(" AND ", havingList));

        // 根据提供的排序信息，组装sql排序
        if (!body.invalidSort()) {
            for (String o : body.getSorts()) {
                if (o != null) {
                    if (o.startsWith("-")) {
                        o = o.substring(1, o.length());
                        orderByList.add(body.fieldExchange(o) + " DESC");
                    } else {
                        orderByList.add(body.fieldExchange(o) + " ASC");
                    }
                }
            }
        }

        // orderBy statement
        orderByStement.append(String.join(", ", orderByList));

        sql.append(selectStatement)
                .append(whereStatement);

        if (groupByList.size() > 0) {
            sql.append(groupByStatement);
        }

        if (havingList.size() > 0) {
            sql.append(havingStatement);
        }

        if (orderByList.size() > 0) {
            sql.append(orderByStement);
        }

        sql.append(" LIMIT ")
                .append((body.invalidThreshold() ? DEFAULT_THRESHOLD : (int) body.getOptions().get("threshold")));

        return sql.toString();
    }

    /**
     * 用于查询总数量，不支持聚合条件
     * @return
     */
    public String buildForTotal() {
        StringBuilder selectStatement = new StringBuilder();
        StringBuilder whereStatement = new StringBuilder();
        StringBuilder sql = new StringBuilder();

        List<String> selectList = new ArrayList<String>();

        selectStatement.append("SELECT ");
        whereStatement.append(" WHERE ");

        selectList.add("COUNT(*) total");

        // select statement
        selectStatement
                .append(String.join(", ", selectList))
                .append(" FROM ")
                .append(transactSQLInjection(body.getDataset()));

        // where statement
        whereState(whereStatement);

        sql.append(selectStatement)
                .append(whereStatement)
                .append(" LIMIT ")
                .append((body.invalidThreshold() ? DEFAULT_THRESHOLD : (int) body.getOptions().get("threshold")));

        return sql.toString();
    }


    /**
     * 用于产生SQL中的where statement
     * @param whereStatement
     */
    private void whereState(StringBuilder whereStatement) {
        List<String> whereList = new ArrayList<String>();
        // where statement
        switch (body.getEngine()) {
            case RequestBody.QUERY_ENGINE_DRUID:
                whereList.add("__time > TIMESTAMP '" + body.getFmt().format(startTime) + "'");
                whereList.add("__time <= TIMESTAMP '" + body.getFmt().format(endTime) + "'");
                break;
            case RequestBody.QUERY_ENGINE_PRESTO:
                boolean flag = body.getTimeField().getType().equals(DataType.Timestamp);
                whereList.add(body.fieldExchange(body.getTimeField().getField()) + " > " + (flag?"TIMESTAMP '": "'") + utcFmt.format(startTime) + (flag?"Z'":"'"));
                whereList.add(body.fieldExchange(body.getTimeField().getField()) + " <= " + (flag?"TIMESTAMP '": "'") + utcFmt.format(endTime) + (flag?"Z'":"'"));
                break;
            default:
        }

        String filters = this.filterMap(body.getFilters(), null, true);

        if (!filters.equals("")){
            whereList.add(filters);
        }

        // 之前提到数据集预置的过滤器，如产品Id、企业Id
        if (!body.invalidPreFilter()) {
            for (String key: body.getPreFilters().keySet()) {
                whereList.add(key + " = '" + body.getPreFilters().get(key).toString() + "'");
            }
        }

        // 内关联表时需要用到的关联条件
        if (!body.invalidJoin()) {
            for (Map<String, String> join : body.getJoins()) {
                if (join != null
                        && !join.get("left").equals("")
                        && !join.get("right").equals("")) {
                    whereList.add(join.get("left") + " = " + join.get("right"));
                }
            }
        }

        whereStatement.append(String.join(" AND ", whereList));
    }

    /**
     * 工具方法，防注入
     * // todo 加强
     * @param str
     * @return
     */
    public static String transactSQLInjection(String str) {
        return str.replaceAll("[';]+|(--)+", "");
    }

    /**
     * 用于递归过滤对象，产生where statement
     *
     * @param filters
     * @param parentKey
     * @param flag
     * @return
     */
    public String filterMap(Map<String, Object> filters, String parentKey, boolean flag) {
        List<String> lists = new ArrayList<String>();
        if (filters == null) {
            return "";
        } else {
            boolean thanFlag = true;
            for (String key : filters.keySet()) {
                String sql = null;
                Object value;
                if ((value = filters.get(key)) != null) {
                    switch (key) {
                        case "$in":
                            sql = this.filterObject(value, null, flag);
                            break;
                        case "$ne":
                        case "$nin":
                        case "$not":
                            sql = this.filterObject(value, null, !flag);
                            break;
                        case "$lt":
                        case "$gt":
                        case "$lte":
                        case "$gte":
                            if (thanFlag) {
                                sql = this.filterThan(filters, parentKey, flag);
                            }
                            thanFlag = false;
                            break;
                        case "$or":
                            sql = flag?this.filterObjectArray(value, "OR"):this.filterObjectArray(value, "NAND");
                            break;
                        case "$and":
                            sql = flag?this.filterObjectArray(value, "AND"):this.filterObjectArray(value, "NOR");
                            break;
                        case "$nor":
                            sql = flag?this.filterObjectArray(value, "NOR"):this.filterObjectArray(value, "AND");
                            break;
                        case "$like":
                            sql = this.filterLike(value, flag);
                            break;
                        default:
                            sql = filterFieldToSql(key) + this.filterObject(value, key, flag);
                    }
                    if (sql != null) lists.add(sql);
                }
            }
        }
        return String.join(" AND ", lists);
    }

    /**
     * 如果过滤条件值为对象，则需要对对象进行分类处理
     *
     * @param object
     * @param parentKey
     * @param flag
     * @return
     */
    private String filterObject(Object object, String parentKey, boolean flag) {
        String sql;
        if(object instanceof java.util.ArrayList) {
            List<Object> values = (ArrayList<Object>) object;
            sql = (flag? " IN ":" NOT IN ") + "(";
            for (Object v : values) {
                sql = sql + "\'" + transactSQLInjection(v.toString()) + "\',";
            }
            return sql.replaceAll(",$", ")");
        } else if (object instanceof java.util.HashMap) {
            Map<String, Object> filters = (HashMap<String, Object>) object;
            return filterMap(filters, parentKey, flag);
        } else {
            sql = flag?" = ":" <> ";
            return sql + "\'" + transactSQLInjection(object.toString()) + "\'";
        }
    }

    /**
     * 处理过滤条件中的大小比较情况
     *
     * @param filters
     * @param parentKey
     * @param flag
     * @return
     */
    private String filterThan(Map<String,Object> filters, String parentKey, boolean flag) {
        List<String> lists = new ArrayList<>();
        for (String key : filters.keySet()) {
            Object value;
            if ((value = filters.get(key)) != null) {
                switch (key) {
                    case "$lt":
                        if (flag){
                            lists.add(" < " + transactSQLInjection(value.toString()));
                        } else {
                            lists.add(" >= " + transactSQLInjection(value.toString()));
                        }
                        break;
                    case "$gte":
                        if (flag){
                            lists.add(" >= " + transactSQLInjection(value.toString()));
                        } else {
                            lists.add(" < " + transactSQLInjection(value.toString()));
                        }
                        break;
                    case "$lte":
                        if (flag){
                            lists.add(" <= " + transactSQLInjection(value.toString()));
                        } else {
                            lists.add(" > " + transactSQLInjection(value.toString()));
                        }
                        break;
                    case "$gt":
                        if (flag){
                            lists.add(" > " + transactSQLInjection(value.toString()));
                        } else {
                            lists.add(" <= " + transactSQLInjection(value.toString()));
                        }
                        break;
                }
            }
        }
        return String.join(" AND " + filterFieldToSql(parentKey), lists);
    }

    /**
     * 处理过滤条件中的逻辑对象列表
     *
     * @param object
     * @param logic
     * @return
     */
    private String filterObjectArray(Object object, String logic) {
        String sql = "";
        boolean flag = true;
        if(object instanceof java.util.ArrayList) {
            List<HashMap<String, Object>> values = (ArrayList<HashMap<String, Object>>) object;

            if (values.size() == 0) {
                return "";
            }

            switch (logic) {
                case "NOR":
                    logic = "OR";
                    flag = false;
                    break;
                case "NAND":
                    logic = "AND";
                    flag = false;
                    break;
            }

            sql = "(";

            List<String> lists = new ArrayList<String>();
            for (HashMap<String, Object> value: values) {
                lists.add(this.filterMap(value, null, flag));
            }

            sql = sql + String.join(" " + logic + " ", lists) + ")";
        }
        return sql;
    }

    /**
     * 处理过滤条件中的Like语法
     *
     * @param object
     * @param flag
     * @return
     */
    private String filterLike(Object object, boolean flag) {
        String sql;
        sql = flag?" LIKE ":" NOT LIKE ";
        return sql + "\'" + transactSQLInjection(object.toString()) + "\'";
    }

    /**
     * 工具方法，确认数据集配置中是否包含此字段，如果包含，则使用特定sql语法转换
     *
     * @param key
     * @return
     */
    private String filterFieldToSql(String key) {
        if (body.getFieldMap().get(key) != null) {
            return body.fieldExchange(key);
        } else if (!body.invalidMetrics() && body.getMetrics().get(key) != null) {
            return metricChangeToSql(body.getMetrics().get(key), "");
        }
        return key;
    }

    /**
     * 工具方法，度量字段的信息转换为SQL
     *
     * @param metric
     * @param suffix
     * @return
     */
    private String metricChangeToSql(Metric metric, String suffix) {
        switch (metric.getType().toLowerCase()) {
            case METRIC_COUNT:
                if (metric.getName().equals("*")) {
                    return "COUNT(*) " + suffix;
                } else {
                    return "COUNT(" + body.fieldExchange(metric.getName().trim()) + ") " + suffix;
                }
            case METRIC_SUM:
            case METRIC_AVG:
                return metric.getType().toUpperCase() + "(" + body.fieldExchange(metric.getName().trim()) + ") " + suffix;
            case METRIC_DISTINCT:
                String[] list = metric.getName().split(",");
                if (list.length ==0) {
                    return "COUNT(DISTINCT " + body.fieldExchange(list[0].trim()) + ") " + suffix;
                } else if (list.length > 1) {
                    List<String> fields = new ArrayList<>();
                    for (String item: list) {
                        fields.add(body.fieldExchange(item.trim()));
                    }
                    return "COUNT(DISTINCT " + String.join(", ", fields) + ") " + suffix;
                }
                break;
            default:
        }
        return null;
    }
}
