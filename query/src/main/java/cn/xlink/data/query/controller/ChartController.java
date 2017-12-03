package cn.xlink.data.query.controller;

import cn.xlink.data.core.service.Service;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.data.core.proxy.Proxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.*;

/**
 * Created by zouniandang on 2017/5/25.
 */
public class ChartController extends AbstractController {

    public ChartController(Properties p,
                           Map<String, Proxy> proxyMap,
                           Map<String, Service> serviceMap) {
        super(p, proxyMap, serviceMap);
    }

    /**
     * 趋势
     * @param request
     * @param response
     * @return
     */
    public Object tendency(Request request, Response response) {
        RequestBody body;
        if ((body = this.init(request, response)) == null) return null;

        Map<String, Object> data = new HashMap<String, Object>();

        try {
            JSONArray list = this.getList(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
            data.put("tendency", this.getDataList(body, list));
        } catch (Exception e) {
            response.setException(e);
        }

        return data;
    }

    /**
     * 按位置查询
     * @param request
     * @param response
     * @return
     */
    public Object position(Request request, Response response) {

        RequestBody body;
        if ((body = this.init(request, response)) == null) return null;

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            JSONArray list = this.getList(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
            data.put("position", this.getDataList(body, list));
        } catch (Exception e) {
            response.setException(e);
        }
        return data;
    }

    /**
     * 计数top_n查询
     * @param request
     * @param response
     * @return
     */
    public Object top_n(Request request, Response response) {
        RequestBody body;
        if ((body = this.init(request, response)) == null) return null;

//        if (!body.invalidOrder()) {
//            if (body.getOptions().get("order").toString().equals("DESC")) {
//                body.getOptions().put("sort", new ArrayList(Arrays.asList("-total")));
//            } else {
//                body.getOptions().put("sort", new ArrayList(Arrays.asList("total")));
//            }
//        }

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            JSONArray list = this.getList(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
            data.put("top_n", this.getDataList(body, list));
        } catch (Exception e) {
            response.setException(e);
        }
        return data;
    }

    /**
     * 计数占比查询
     * @param request
     * @param response
     * @return
     */
    public Object ratio(Request request, Response response) {
        RequestBody body;
        if ((body = this.init(request, response)) == null) return null;

        Map<String, Object> data = new HashMap<String, Object>();
        try {
            JSONArray list = this.getList(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
            int total = this.getTotal(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
            if (total <= 0) {
                response.setException(new Exception("No total access, can not calc ratio"));
            }

            for (int i = 0; i < list.length(); i ++) {
                JSONObject item = list.getJSONObject(i);
                item.put("ratio", item.getDouble("total")/ total);
            }
            data.put("total", total);
            data.put("ratios", this.getDataList(body, list));
        } catch (Exception e) {
            response.setException(e);
        }
        return data;
    }
}
