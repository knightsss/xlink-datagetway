package cn.xlink.query.controller;

import cn.xlink.metadata.datasetMetadata.*;
import cn.xlink.query.builder.JsonBuilder;
import cn.xlink.query.builder.SqlBuilder;
import cn.xlink.query.domain.RequestBody;
import cn.xlink.query.domain.TimeField;
import cn.xlink.query.proxy.Proxy;
import cn.xlink.query.service.Service;
import cn.xlink.query.utils.DataType;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Ghold on 2017/6/12.
 */
public class AbstractController {
    private Properties p;
    private Map<String, Proxy> proxyMap;
    private Map<String, Service> serviceMap;
    private DatasetMetadataService datasetService;

    private static final String DRUID_BROKER_QUERY_PATH = "druid.broker.query.path";
    private static final String DRUID_BROKER_SQL_PATH = "druid.broker.sql.path";
    private static final String XLINK_AUTH_CHECK_PATH = "xlink.auth.check.path";

    static final String SERVICE_AUTH = "auth";
    static final String SERVICE_DATAPOINT = "datapoint";

    public AbstractController() {}
    AbstractController(Properties p,
                                 Map<String, Proxy> proxyMap,
                                 Map<String, Service> serviceMap,
                                 DatasetMetadataService datasetService
    ){
        this.p = p;
        this.proxyMap = proxyMap;
        this.serviceMap = serviceMap;
        this.datasetService = datasetService;
    }

    DatasetMetadataService getDatasetService() {
        return datasetService;
    }

    Map<String, Service> getServiceMap() {
        return serviceMap;
    }

    /**
     * 请求体初始化，分别完成以下事情
     * 1. 用户权限检验及获取企业Id
     * 2. 获取数据集元数据并强化请求体
     * 3. 通过元数据中的字段名称和类型，建立字段映射
     * @param request
     * @param response
     * @return
     */
    final RequestBody init(Request request, Response response) {
        RequestBody body = request.getBodyAs(RequestBody.class, "Body details not provided");
        if (body.invalid()) {
            response.setException(new Exception("Requied params lose"));
            response.setResponseCode(400);
            return null;
        }
        try{
            // 用户权限检验及获取企业Id
            String corpId;
            if ((corpId = authCheck(request, response)) == null) return null;
            body.setCorpId(corpId);

            // 获取数据集元数据，根据元数据可以拿到字段所在数据库、对应表、字段数据的类型、表连接方式及一些表关联信息
            DatasetMetadata metadata;
            if ((metadata = datasetService
                    .findByEntity(new DatasetMetadata(body.getDataset()))) != null) { // 根据数据集的名称获取元数据
                body.setEngine(metadata.getEngine());

                switch (body.getEngine().toLowerCase()) { // 根据不同的查询引擎组合信息
                    case RequestBody.QUERY_ENGINE_PRESTO:

                        // Presto允许多表关联，因此先检查数据关联表情况
                        if (metadata.getJoins() != null && metadata.getJoins().size() > 0) {
                            List<Map<String, String>> joins = new ArrayList<>();
                            for (DatasetMetadataJoinEntity join : metadata.getJoins()) {
                                Map<String, String> ret = new HashMap<>();
                                ret.put("left", join.getLeft());
                                ret.put("right", join.getRight());
                                joins.add(ret);
                            }
                            body.setJoins(joins);
                        }

                        // 由于数据集的定义不基于企业或产品，而且这些分租的信息直接带入查询条件的中fitler并不友好。因此在数据集中预设一些过滤字段
                        if (metadata.getFilters() != null && metadata.getFilters().size() > 0) {
                            Map<String, Object> preFilters = new HashMap<>();
                            for (DatasetMetadataFieldEntity filter : metadata.getFilters()) {
                                switch (filter.getName()) {
                                    case "corp_id":
                                        preFilters.put(filter.getField(), body.getOptions().getOrDefault("corp_id", "").toString());
                                        break;
                                    case "product_id":
                                        preFilters.put(filter.getField(), body.getOptions().getOrDefault("product_id", "").toString());
                                }
                            }
                            body.setPreFilters(preFilters);
                        }

                        // Presto支持多表查询，在from statement中允许以逗号分隔表
                        if (metadata.getSources() != null && metadata.getSources().size() > 0) {
                            List<String> sources = new ArrayList<>();
                            for (DatasetMetadataSourceEntity source : metadata.getSources()) {
                                sources.add(source.getSource() + " " + source.getName());
                            }
                            body.setDataset(String.join(", ", sources));
                        }
                        break;
                    case RequestBody.QUERY_ENGINE_DRUID:
                        if (metadata.getSources() != null && metadata.getSources().size() > 0) {
                            body.setDataset(metadata.getSources().get(0).getSource());
                        }
                        break;
                }

                // 根据元数据建立字段名称映射和字段类型映射
                Map<String, String> fieldMap = new HashMap<>();
                Map<String, DataType> typeMap = new HashMap<>();

                // 时间字段在数据平台是必须的，单独处理的原因是，SQL对于时间的过滤需要特殊的格式，如必须加入Timestamp保留字
                if (metadata.getTimeField() != null) {
                    body.setTimeField(new TimeField(metadata.getTimeField().getName(), DataType.fromType(metadata.getTimeField().getType())));
                    fieldMap.put(metadata.getTimeField().getName(), metadata.getTimeField().getField());
                    typeMap.put(metadata.getTimeField().getName(), DataType.fromType(metadata.getTimeField().getType()));
                }

                for (DatasetMetadataFieldEntity field: metadata.getFields()) {

                    DataType dataType = DataType.fromType(field.getType());
                    switch (dataType) {
                        case Datapoint:
                            // Datapoint类型是为数据端点特定
                            if (body.invalidProductId()) throw new Exception("Invalid product_id");
                            String path = field.getField().replaceAll("\\{.*\\}", body.getOptions().getOrDefault("product_id", "").toString());
                            JSONArray datapointFields = new JSONArray(this.serviceMap.get(SERVICE_DATAPOINT).serve(
                                    request,
                                    response,
                                    path,
                                    Collections.singletonMap("Access-Token", request.getHeader("Access-Token"))));
                            if (datapointFields.length() > 0) {
                                for (int idx = 0; idx < datapointFields.length(); idx ++) {
                                    JSONObject datapoint;
                                    int index;
                                    int type;

                                    // 通过point_加上端点索引的方式唯一确定字段名称，进而与实际含义的名称进行映射，这样使得前端不需要关心抽象的信息
                                    if ((datapoint = datapointFields.getJSONObject(idx)) != null
                                            && (index = datapoint.getInt("index")) >= 0
                                            && (type = datapoint.getInt("type")) > 0) {
                                        dataType = DataType.fromType(type);
                                        if (dataType.sqlDesc().equals("VARCHAR")) {
                                            fieldMap.put("point_" + index, field.getSource() + ".\"" + index + "\"");
                                        } else {
                                            fieldMap.put("point_" + index, "CAST (" + field.getSource() + ".\"" + index + "\" AS " + dataType.sqlDesc() + ")");
                                        }

                                        typeMap.put("point_" + index, dataType);
                                    }
                                }
                            }
                            break;
                        default:
                            fieldMap.put(field.getName(), field.getField());
                            typeMap.put(field.getName(), DataType.fromType(field.getType()));
                    }
                }
                body.setFieldMap(fieldMap);
                body.setTypeMap(typeMap);
            } else {
                body.setEngine(RequestBody.QUERY_ENGINE_DRUID);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            response.setResponseCode(500);
            response.setException(e1);
            return null;
        }
        body.init();
        return body;
    }

    /**
     * 通过请求体、开始时间、结束时间获取数据列表
     * 主要完成以下事情：
     * 1. 根据查询引擎确定请求的方式
     * 2. 通过json生成器或者SQL生成器，把请求信息转为json或sql
     * 3. 查询结果返回JSONArray
     * @param body
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    final JSONArray getList(RequestBody body, Date startTime, Date endTime) throws IOException {
        switch (body.getEngine().toLowerCase()) {
            case RequestBody.QUERY_ENGINE_DRUID:
                if (body.useJson()) {
                    JsonBuilder jsonBuilder = new JsonBuilder(body, startTime, endTime);
                    String query = jsonBuilder.build();
                    return queryFilter(body, new JSONArray((JSONTokener) this.proxyMap.get(RequestBody.QUERY_ENGINE_DRUID).post(query, p.getProperty(DRUID_BROKER_QUERY_PATH))));
                } else {
                    SqlBuilder builder = new SqlBuilder(body, startTime, endTime);
                    String query = druidSqlWrap(builder.build(), body);
                    return new JSONArray((JSONTokener) this.proxyMap.get(RequestBody.QUERY_ENGINE_DRUID).post(query, p.getProperty(DRUID_BROKER_SQL_PATH)));
                }
            case RequestBody.QUERY_ENGINE_PRESTO:
                SqlBuilder builder = new SqlBuilder(body, startTime, endTime);
                String sql = builder.build();
                return (JSONArray) this.proxyMap.get(RequestBody.QUERY_ENGINE_PRESTO).post(sql, null);
            default:
                return null;
        }
    }

    /**
     * 获取计数，根据请求体、开始时间、结束时间获取数据计数
     * @param body
     * @param startTime
     * @param endTime
     * @return
     * @throws IOException
     */
    final int getTotal(RequestBody body, Date startTime, Date endTime) throws IOException {
        SqlBuilder builder = new SqlBuilder(body, startTime, endTime);
        String query = druidSqlWrap(builder.buildForTotal(), body);
        JSONArray ret = null;
        switch (body.getEngine().toLowerCase()) {
            case RequestBody.QUERY_ENGINE_DRUID:
                ret = new JSONArray((JSONTokener) this.proxyMap.get(RequestBody.QUERY_ENGINE_DRUID).post(query, p.getProperty(DRUID_BROKER_SQL_PATH)));
                break;
            case RequestBody.QUERY_ENGINE_PRESTO:
                ret = (JSONArray) this.proxyMap.get(RequestBody.QUERY_ENGINE_PRESTO).post(query, null);
        }
        if (ret != null && ret.length() > 0) {
            return (int)ret.getJSONObject(0).get("total");
        }
        return 0;
    }

    /**
     * 用于处理结果数据分页
     *
     * @param body
     * @param list
     * @return
     */
    final List<Object> getDataList(RequestBody body, JSONArray list) {
        List<Object> splice = new ArrayList<Object>();
        if (!body.invalidPagination()){
            int page = (int) body.getOptions().getOrDefault("page", 1);
            if (page == 0) {
                page = 1;
            }
            int limit = (int) body.getOptions().get("limit");

            if ((page - 1) * limit + 1 <= list.length()) {
                for (int i = 0; i < limit; i++) {
                    if (!list.isNull((page - 1) * limit + i)) {
                        splice.add(list.getJSONObject((page - 1) * limit + i).toMap());
                    }
                }
            }
        } else {
            for (int i = 0; i < list.length(); i++) {
                splice.add(list.getJSONObject(i).toMap());
            }
        }
        return splice;
    }

    /**
     * 工具方法，用于处理Druid的json查询时，补全信息
     *
     * @param body
     * @param origins
     * @return
     */
    private JSONArray queryFilter(RequestBody body, JSONArray origins) {
        JSONArray rets = new JSONArray();
        boolean needInterval = !body.invalidInterval();
        DateFormat ISO8601UTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        ISO8601UTC.setTimeZone(DateTimeZone.UTC.toTimeZone());
        DateFormat ISO8601Local = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        ISO8601Local.setTimeZone(DateTimeZone.forID(body.getTimezone()).toTimeZone());
        for (int idx = 0; idx < origins.length(); idx ++) {
            JSONObject object = origins.getJSONObject(idx);
            JSONObject ret = object.getJSONObject("event");
            if (ret != null && needInterval) {
                try {
                    ret.put("__time", ISO8601Local.format(ISO8601UTC.parse(object.getString("timestamp"))));
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            rets.put(ret);
        }
        return rets;
    }

    /**
     * 工具方法，用于处理Druid的SQL查询时，补全信息
     * @param sql
     * @param body
     * @return
     */
    private String druidSqlWrap(String sql, RequestBody body) {
        StringBuilder query = new StringBuilder();
        return query
                .append("{\"query\" : \"")
                .append(sql)
                .append("\",\"context\" : {\"sqlTimeZone\" : \"")
                .append(body.getTimezone())
                .append("\"}}")
                .toString();
    }

    /**
     * 权限验证，用于用户token验证和返回企业Id
     * @param request
     * @param response
     * @return corp_id
     */
    String authCheck(Request request, Response response) {
        try {
            JSONObject res = new JSONObject(this.serviceMap.get(SERVICE_AUTH).serve(request, response, p.getProperty(XLINK_AUTH_CHECK_PATH), null));
            return res.getString("corp_id");
        } catch (Exception e) {
            e.printStackTrace();
            response.setResponseCode(403);
            response.setException(e);
            return null;
        }
    }
}
