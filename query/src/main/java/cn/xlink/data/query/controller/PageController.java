package cn.xlink.data.query.controller;

import cn.xlink.data.core.proxy.Proxy;
import cn.xlink.data.core.service.Service;
//import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadata;
import cn.xlink.data.metadata.Constants;
import cn.xlink.data.metadata.pageMetadata.PageMetadata;
import cn.xlink.data.metadata.pageMetadata.PageMetadataController;
import cn.xlink.data.metadata.pageMetadata.PageMetadataService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by shifeixiang on 2017/11/7.
 */
public class PageController extends PageMetadataController {
    private ObjectMapper mapper;
    private PageMetadataService pageService;

    public PageController(Properties p,
                          Map<String, Proxy> proxyMap,
                          Map<String, Service> serviceMap,
                          PageMetadataService pageService){
        super(p, proxyMap, serviceMap, pageService);
        this.pageService = pageService;
        this.mapper = new ObjectMapper();
        this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /*所有战图列表*/
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

            List<PageMetadata> list = pageService.readAll(new QueryFilter(filters), new QueryRange(offset, limit), null);
            System.out.println("list is " + list);
            ArrayList medataList = new ArrayList();
            for(PageMetadata item : list){
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


    /*更新部分字段*/
    public Object updateIndividual(Request request, Response response)
    {
        String id = request.getHeader(Constants.Url.PAGE_ID, "No resource ID supplied");
        try {
            // check auth
            authCheck(request, response);
            PageMetadata baseEntity = pageService.read(Identifiers.MONGOID.parse(id));
            PageMetadata entity = request.getBodyAs(PageMetadata.class, "Resource details not provided");
            /*反射实现*/
            Class cls = entity.getClass();
            Field[] flds = cls.getDeclaredFields();
            if ( flds != null )
            {
                for ( int i = 0; i < flds.length; i++ )
                {
                    String name = flds[i].getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                    String type = flds[i].getGenericType().toString(); // 获取属性的类型
                    if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                        /*查询新的字段信息*/
                        Method m = cls.getMethod("get" + name);
                        String value = (String) m.invoke(entity); // 调用getter方法获取属性值
                        if (value != null){
                            /*更新原始数据字段信息*/
                            m = baseEntity.getClass().getMethod("set"+name,String.class);
                            m.invoke(baseEntity, value);
                        }
                    }
                }
            }
            baseEntity.setId(Identifiers.MONGOID.parse(id));
            pageService.update(baseEntity);
            response.setBody(baseEntity.toMap());
        } catch (ItemNotFoundException e1) {
            response.setResponseCode(400);
            response.setException(e1);
            return null;
        } catch (Exception e) {
            response.setResponseCode(500);
            response.setException(e);
            e.printStackTrace();
        }
        return null;
    }
}
