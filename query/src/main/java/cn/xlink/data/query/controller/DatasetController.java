package cn.xlink.data.query.controller;

import cn.xlink.data.core.service.Service;
import cn.xlink.data.core.utils.DataType;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadata;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataFieldEntity;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.data.core.proxy.Proxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

import java.util.*;

/**
 * Created by Ghold on 2017/8/8.
 */
public class DatasetController extends AbstractController{
    private DatasetMetadataService datasetService;
    public DatasetController(Properties p,
                             Map<String, Proxy> proxyMap,
                             Map<String, Service> serviceMap,
                             DatasetMetadataService datasetService){
       super(p, proxyMap, serviceMap);
       this.datasetService = datasetService;
    }

    public void list(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);

        // check auth
        if (authCheck(request, response) == null) return;
//        filter.addCriteria("corpId",  FilterOperator.EQUALS, corpId);
        List<DatasetMetadata> list = datasetService.readAll(filter, range, order);

        List<Map<String, String>> rets = new ArrayList<>();
        for (DatasetMetadata dataset: list) {
            rets.add(dataset.toMap());
        }
        Map<String, List> data = new HashMap<>();
        data.put("list", rets);
        response.setBody(data);
    }

    public void fields(Request request, Response response) {
        DatasetMetadata dataset = request.getBodyAs(DatasetMetadata.class, "Body details not provided");
        // check auth
        if (authCheck(request, response) == null) return;

        Map<String, List> data = new HashMap<>();

        DatasetMetadata metadata;
        if ((metadata = datasetService.findByEntity(dataset)) != null) {
            // 需要检查字段的来源
            List<Map<String, Object>> fields = new ArrayList<>();
            for (DatasetMetadataFieldEntity field: metadata.getFields()) {
                switch (DataType.fromType(field.getType())) {
                    case Datapoint:
                        try {
                            String path = field.getField().replaceAll("\\{.*\\}", dataset.getProductId());
                            JSONArray datapointFields = new JSONArray(this.getServiceMap().get(SERVICE_DATAPOINT).serve(
                                    request,
                                    response,
                                    path,
                                    Collections.singletonMap("Access-Token", request.getHeader("Access-Token"))));
                            if (datapointFields.length() > 0) {
                                for (int idx = 0; idx < datapointFields.length(); idx++) {
                                    JSONObject datapoint;
                                    if ((datapoint = datapointFields.getJSONObject(idx)) != null
                                            && datapoint.getString("name") != null
                                            && datapoint.getInt("index") >= 0
                                            && datapoint.getInt("type") != 0) {
                                        Map<String, Object> ret = new HashMap<>();
                                        ret.put("name", "point_" + datapoint.getInt("index"));
                                        ret.put("description", datapoint.getString("name"));
                                        ret.put("type", datapoint.getInt("type"));
                                        fields.add(ret);
                                    }
                                }
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                            response.setResponseCode(500);
                            response.setException(e);
                        }
                        break;
                    default:
                        Map<String, Object> object = new HashMap<>();
                        object.put("name", field.getName());
                        object.put("type", field.getType());
                        object.put("description", field.getDescription());
                        fields.add(object);
                }
            }
            data.put("fields", fields);

            List<Map<String, Object>> times = new ArrayList<>();
            for (DatasetMetadataFieldEntity field: metadata.getTimes()) {
                Map<String, Object> object = new HashMap<>();
                object.put("name", field.getName());
                object.put("type", field.getType());
                object.put("description", field.getDescription());
                times.add(object);
            }
            data.put("times", times);
        }
        response.setBody(data);
    }
}
