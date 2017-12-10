package cn.xlink.data.query.controller;

import cn.xlink.data.core.proxy.Proxy;
import cn.xlink.data.core.service.Service;
import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadata;
import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadataController;
//import cn.xlink.data.metadata.pageMetadata.PageMetadataController;
//import cn.xlink.data.metadata.pageMetadata.PageMetadataService;
import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadataService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.FilterComponent;
import org.restexpress.common.query.FilterOperator;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryRange;

import java.util.*;

/**
 * Created by shifeixiang on 2017/11/7.
 */
public class JdbcController extends JdbcMetadataController {
    private ObjectMapper mapper;
    private JdbcMetadataService jdbcService;

    public JdbcController(Properties p,
                          Map<String, Proxy> proxyMap,
                          Map<String, Service> serviceMap,
                          JdbcMetadataService jdbcService){
        super(p, proxyMap, serviceMap, jdbcService);
        this.jdbcService = jdbcService;
        this.mapper = new ObjectMapper();
        this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /*所有数据库配置列表*/
    public Object readAll(Request request, Response response) {

        Map<String, String> queryString = request.getQueryStringMap();
        String corpId;
        if ((corpId = authCheck(request, response)) == null) return null;
        System.out.println(corpId);

        try {
            int page = Integer.valueOf(queryString.getOrDefault("page", "1"));
            int limit = Integer.valueOf(queryString.getOrDefault("limit", "10"));
            int offset = (page - 1) * limit;

            List<FilterComponent> filters = new ArrayList<>();
            filters.add(new FilterComponent("corp_id", FilterOperator.EQUALS, corpId));

            List<JdbcMetadata> list = jdbcService.readAll(new QueryFilter(filters), new QueryRange(offset, limit), null);
            System.out.println("list is " + list);
            ArrayList medataList = new ArrayList();
            for(JdbcMetadata item : list){
                medataList.add(item.toMap());
            }
            Map<String, Object> map = new HashMap<>();
            map.put("list", medataList);
            System.out.println(map);
            return map;
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setException(e);
        }
        return null;
    }

//    public List<Object> connectAndGetTables(Request request, Response response) {
//
//    }
}
