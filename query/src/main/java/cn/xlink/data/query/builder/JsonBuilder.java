package cn.xlink.data.query.builder;

import cn.xlink.data.query.builder.json.*;
import cn.xlink.data.query.domain.Metric;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.query.domain.DruidJsonQueryBody;
import cn.xlink.data.core.utils.DataType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.druid.query.groupby.orderby.OrderByColumnSpec;
import io.druid.query.ordering.StringComparators;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.util.*;
import java.util.regex.Matcher;


/**
 * Created by Ghold on 2017/7/18.
 */
public class JsonBuilder implements Builder{
    private RequestBody body;
    private Date startTime;
    private Date endTime;

    private static final String DEFALUT_GRANULARITY = "ALL";

    public JsonBuilder(){}

    public JsonBuilder(RequestBody body, Date startTime, Date endTime) {
        this.body = body;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setBody(RequestBody body) {
        this.body = body;
    }

    /**
     * Druid查询用Json生成器
     * 如果希望使用Json的方式查询Druid的数据，需要按照Druid提供的查询条件文档产生标准的查询json
     *
     * @return
     */
    @Override
    public String build() {
        DruidJsonQueryBody queryBody = new DruidJsonQueryBody();

        // 对应dataSource
        queryBody.setDataSource(body.getDataset());

        // 对应intervals
        Interval interval = new Interval(startTime.getTime(),
                endTime.getTime(), DateTimeZone.forID(body.getTimezone()));
        queryBody.setIntervals(Collections.singletonList(interval.toString()));

        // 对应granularity
        queryBody.setGranularity(body.invalidInterval()?DEFALUT_GRANULARITY:body.getOptions().get("interval").toString().toUpperCase());

        // 对应filters
        DimFilter filter = this.filterMap(body.getFilters(), null,true);
        if (filter != null) {
            queryBody.setFilter(filter);
        }

        // 对应dimensions use field
        List<Dimension> dimensions = new ArrayList<>();

        if (!body.invalidFields()) {
            for (String field: body.getFields()) {
                dimensions.add(new Dimension("default", body.fieldExchange(field), field, Aggregation.FIELD_TYPE_STRING));
            }
        }
        queryBody.setDimensions(dimensions);

        // 对应queryType
        if (body.getQueryType() == RequestBody.QUERY_TYPE_SELECT) {
            queryBody.setQueryType("select");
            queryBody.setPagingSpec(new PagingSpec(new HashMap<>(), 5000));
        } else if (body.getQueryType() == RequestBody.QUERY_TYPE_AGGREGATE) {
            queryBody.setQueryType("groupBy");

            // according to metrics, make up a Aggregation PostAggregation and limitSpec
            List<Aggregation> aggregations = new ArrayList<>();
            List<PostAggregation> postAggregations = new ArrayList<>();
//            Map<String, Set<String>> aggregationMap = new HashMap<>();

            // aggregation count set
            Set<String> aggregationSet = new HashSet<>();
            Set<String> postAggregationSet = new HashSet<>();

            if (!body.invalidMetricMap()) {
                for (Metric metric: body.getMetricMap().values()) {
                    if (!metric.invalid()) {
                        String key = metric.getName();
//                        if (aggregationMap.getOrDefault(key, null) == null) {
//                            aggregationMap.put(key, new HashSet<String>());
//                        }
                        switch (metric.getType().toLowerCase()) {
                            case Metric.METRIC_COUNT:
                                if (aggregationSet.add(Metric.METRIC_COUNT)) {
                                    aggregations.add(new Aggregation(Aggregation.AGGREGATION_TYPE_COUNT, Aggregation.AGGREGATION_NAME_COUNT));
                                }
                                break;
                            case Metric.METRIC_AVG:
                                if (aggregationSet.add(Metric.METRIC_COUNT)) {
                                    aggregations.add(new Aggregation(Aggregation.AGGREGATION_TYPE_COUNT, Aggregation.AGGREGATION_NAME_COUNT));
                                }

                                if (postAggregationSet.add(Metric.METRIC_AVG + "_" + key)) {
                                    PostAggregation postAggregation = new PostAggregation();
                                    postAggregation.setType(PostAggregation.POST_AGGREGATION_TYPE_ARITHMETIC);
                                    postAggregation.setName(Metric.METRIC_AVG + "_" + key);
                                    postAggregation.setFn("/");
                                    List<Aggregation> fields = new ArrayList<>();
                                    fields.add(new Aggregation(Aggregation.AGGREGATION_TYPE_FIELD_ACCESS, Metric.METRIC_SUM + "_" + key, Metric.METRIC_SUM + "_" + key));
                                    fields.add(new Aggregation(Aggregation.AGGREGATION_TYPE_FIELD_ACCESS, Aggregation.AGGREGATION_TYPE_COUNT, Aggregation.AGGREGATION_NAME_COUNT));
                                    postAggregation.setFields(fields);
                                    postAggregations.add(postAggregation);
                                }
                            case Metric.METRIC_SUM:
                                if (aggregationSet.add(Metric.METRIC_SUM + "_" + key)) {
                                    String type;
                                    // TODO check field type decide aggregation type
                                    if (DataType.checkMetricType(DataType.fromType(body.getFieldMap().get(key).getType()))){
                                        type = Aggregation.AGGREGATION_TYPE_DOUBLE_SUM;
                                    } else {
                                        type = Aggregation.AGGREGATION_TYPE_LONG_SUM;
                                    }
                                    Aggregation aggregation = new Aggregation(type, Metric.METRIC_SUM + "_" + key, Metric.METRIC_SUM + "_" + key );
                                    aggregation.setTypeByFieldType(DataType.fromType(body.getFieldMap().get(key).getType()).druidDesc());
                                    aggregations.add(aggregation);
                                }
                                break;
                        }
                    }
                }
            }

            queryBody.setAggregations(aggregations);
            queryBody.setPostAggregations(postAggregations);

            // 对应having
            DimFilter having = this.filterMap(body.getHavings(), null,true);
            if (having != null) {
                queryBody.setHaving(new DimFilterHavingSpec(having));
            }

            // 对应limitSpec
            if (!body.invalidSort()) {
                List<OrderColumn> orderColumns = new ArrayList<>();
                for (String sort : body.getSorts()) {
                    if (sort != null) {
                        boolean order = true;
                        if (sort.startsWith("-")) {
                            sort = sort.substring(1, sort.length());
                            order = false;
                        }

                        Matcher m = body.getPattern().matcher(sort);
                        if (m.find()) {
                            orderColumns.add(new OrderColumn(body.fieldExchange(sort),
                                    order? OrderByColumnSpec.Direction.ASCENDING.toString():OrderByColumnSpec.Direction.DESCENDING.toString(),
                                    StringComparators.NUMERIC_NAME));
                        } else {
                            if (!DataType.checkMetricType(DataType.fromType(body.getFieldMap().get(sort).getType()))) {
                                orderColumns.add(new OrderColumn(sort,
                                        order?OrderByColumnSpec.Direction.ASCENDING.toString():OrderByColumnSpec.Direction.DESCENDING.toString()));
                            } else {
                                orderColumns.add(new OrderColumn(sort,
                                        order?OrderByColumnSpec.Direction.ASCENDING.toString():OrderByColumnSpec.Direction.DESCENDING.toString(),
                                        StringComparators.NUMERIC_NAME));
                            }
                        }
                    }
                }
                queryBody.setLimitSpec(
                        new Limit(
                                "default",
                                (body.invalidThreshold() ? RequestBody.DEFAULT_THRESHOLD : (int) body.getOptions().get("threshold")),
                                orderColumns));
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        try {
            return mapper.writeValueAsString(queryBody);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用于递归对象格式的过滤器
     *
     * @param filters
     * @param parentKey
     * @param flag
     * @return
     */
    public DimFilter filterMap(Map<String, Object> filters, String parentKey, boolean flag) {
        List<DimFilter> lists = new ArrayList<DimFilter>();
        if (filters == null) {
            return null;
        } else {
            boolean thanFlag = true;
            for (String key : filters.keySet()) {
                Object value;
                if ((value = filters.get(key)) != null) {
                    DimFilter filter = null;
                    switch (key) {
                        case "$in":
                            filter = this.filterObject(value, parentKey, flag);
                            break;
                        case "$ne":
                        case "$nin":
                        case "$not":
                            filter = this.filterObject(value, parentKey, !flag);
                            break;
                        case "$lt":
                        case "$lte":
                        case "$gt":
                        case "$gte":
                            if (thanFlag) {
                                filter = this.filterThan(filters, parentKey, flag);
                            }
                            thanFlag = false;
                            break;
                        case "$or":
                            filter = this.filterObjectArray(value, "OR", flag);
                            break;
                        case "$and":
                            filter = this.filterObjectArray(value, "AND", flag);
                            break;
                        case "$nor":
                            filter = this.filterObjectArray(value, "NOR", flag);
                            break;
                        case "$like":
                            filter = this.filterLike(value, parentKey, flag);
                            break;
                        default:
                            filter = this.filterObject(value, key, flag);
                    }
                    if (filter != null) lists.add(filter);
                }
            }
            return lists.size() > 0 ? (lists.size() == 1? lists.get(0): new AndDimFilter(lists)): null;
        }
    }

    /**
     * 如果过滤条件值为对象，则需要对对象进行分类处理
     *
     * @param object
     * @param key
     * @param flag
     * @return
     */
    private DimFilter filterObject(Object object, String key, boolean flag) {
        key = body.fieldExchange(key);
        if(object instanceof java.util.ArrayList) {
            List<Object> values = (ArrayList<Object>) object;
            DimFilter filter = new InDimFilter(key, values);
            if (!flag) {
                filter = new NotDimFilter(filter);
            }
            return filter;
        } else if (object instanceof java.util.HashMap) {
            Map<String, Object> filters = (HashMap<String, Object>) object;
            return filterMap(filters, key, flag);
        } else {
            DimFilter filter = new SelectorDimFilter(key, object.toString());
            if (!flag) {
                filter = new NotDimFilter(filter);
            }
            return filter;
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
    private DimFilter filterThan(Map<String, Object> filters, String parentKey, boolean flag) {
        BoundDimFilter filter = new BoundDimFilter(body.fieldExchange(parentKey), StringComparators.NUMERIC_NAME);
        for (String key : filters.keySet()) {
            Object value;
            boolean inFlag = flag;
            if ((value = filters.get(key)) != null) {
                switch (key) {
                    case "$lt":
                        inFlag = !inFlag;
                    case "$gte":
                        if (inFlag) {
                            filter.setLower(value.toString());
                            filter.setLowerStrict(false);
                        } else {
                            filter.setUpper(value.toString());
                            filter.setUpperStrict(true);
                        }
                        break;
                    case "$lte":
                        inFlag = !inFlag;
                    case "$gt":
                        if (inFlag) {
                            filter.setLower(value.toString());
                            filter.setLowerStrict(true);
                        } else {
                            filter.setUpper(value.toString());
                            filter.setUpperStrict(false);
                        }
                        break;
                }
            }
        }

        if (filter.invalid()) {
            return null;
        }
        return filter;
    }

    /**
     * 处理过滤条件中的逻辑对象列表
     *
     * @param object
     * @param logic
     * @param flag
     * @return
     */
    private DimFilter filterObjectArray(Object object, String logic, boolean flag) {
        if(object instanceof java.util.ArrayList) {
            List<HashMap<String, Object>> values = (ArrayList<HashMap<String, Object>>) object;

            if (values.size() == 0) {
                return null;
            }

            List<DimFilter> lists = new ArrayList<DimFilter>();
            for (HashMap<String, Object> value: values) {
                lists.add(this.filterMap(value, null, true));
            }

            if (lists.size() > 0) {
                DimFilter filter;
                switch (logic) {
                    case "AND":
                        filter = new AndDimFilter(lists);
                        if (!flag) {
                            filter = new NotDimFilter(filter);
                        }
                        return filter;
                    case "OR":
                        filter = new OrDimFilter(lists);
                        if (!flag) {
                            filter = new NotDimFilter(filter);
                        }
                        return filter;
                    case "NOR":
                        if (!flag) {
                            filter = new AndDimFilter(lists);
                        } else {
                            filter = new NotDimFilter(new AndDimFilter(lists));
                        }
                        return filter;
                }
            }
        }
        return null;
    }

    /**
     * 处理过滤条件中的Like语法
     * 
     * @param object
     * @param key
     * @param flag
     * @return
     */
    private DimFilter filterLike(Object object, String key, boolean flag) {
        DimFilter filter = new LikeDimFilter(body.fieldExchange(key), object.toString());
        if (!flag) {
            filter = new NotDimFilter(filter);
        }
        return filter;
    }
}
