package cn.xlink.data.core.controller;

import cn.xlink.data.query.controller.AbstractController;
import cn.xlink.data.query.serialization.SerializationProvider;
import cn.xlink.data.query.Configuration;
import cn.xlink.data.query.domain.Metric;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.core.utils.CorpAuth;
import cn.xlink.data.core.utils.DataType;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.util.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ghold on 2017/8/24.
 */
public class AbstractControllerTest {
    private CorpAuth corpAuth;
    private String token;
    private AbstractController controller;
    private DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public AbstractControllerTest() throws Exception {
        this.corpAuth = new CorpAuth("http://api-test.xlink.io:1080", "laojiahao@qq.com", "Ljh123456");
        JSONTokener t = this.corpAuth.serve(null, null, "/v2/corp_auth", null);
        JSONObject o = new JSONObject(t);
        if (o.getString("access_token") != null) {
            this.token = o.getString("access_token");
        }

        Configuration config = Environment.load(new String[0], Configuration.class);
        this.controller = new AbstractController(config.getProperties(), config.getProxyMap(), config.getServiceMap());
    }

    @Test
    public void testInitForPrestoDatasetExisted() throws Exception {

        String str = "{\n" +
                "    \"dataset\":\"datapoint_dataset\",\n" +
                "    \"productId\": \"1607d2b1946700011607d2b194675c01\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Tokyo\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"-a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200 " +
                "    }\n" +
                "}";

        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/v2/common/query", Unpooled.wrappedBuffer(
                str.getBytes())
        );
        httpRequest.headers().add("Access-Token", this.token).add("Content-Type", "application/json");
        Request request = new Request(httpRequest, null, new SerializationProvider());
        Response response = new Response();

        RequestBody actual = controller.init(request, response);

        assertEquals("mongodb.xlink.device m1, mongodb.xlink.virtual_device m2", actual.getDataset());
        assertEquals("presto", actual.getEngine());
//        assertEquals("[{left=m1._id, right=m2._id}, {left=m1.corp_id, right=m2.corp_id}, {left=m1.product_id, right=m2.product_id}]",actual.getJoins().toString());
        assertEquals("{m1.corp_id=1007d2ad165a7000, m1.product_id=1607d2b1946700011607d2b194675c01}",actual.getPreFilters().toString());
        assertEquals("Asia/Tokyo", actual.getTimezone());
        assertEquals("last_update", actual.getTimeField().getField());
        assertEquals(DataType.Timestamp, actual.getTimeField().getType());

        Map<String, String> expectedFieldMap = new HashMap<>();
        Map<String, DataType> expectedTypeMap = new HashMap<>();

        expectedFieldMap.put("device_id", "m1._id");
        expectedTypeMap.put("device_id", DataType.UnsignedInt);
        expectedFieldMap.put("last_update", "m2.last_update");
        expectedTypeMap.put("last_update", DataType.Timestamp);
        expectedFieldMap.put("firmware_mod", "m1.firmware_mod");
        expectedTypeMap.put("firmware_mod", DataType.String);
        expectedFieldMap.put("firmware_version", "m1.firmware_version");
        expectedTypeMap.put("firmware_version", DataType.Int);
        expectedFieldMap.put("point_2", "CAST (m2.\"2\" AS INT)");
        expectedTypeMap.put("point_2", DataType.Int);
        expectedFieldMap.put("corp_id", "m1.corp_id");
        expectedTypeMap.put("corp_id", DataType.String);
        expectedFieldMap.put("point_1", "CAST (m2.\"1\" AS BOOLEAN)");
        expectedTypeMap.put("point_1", DataType.Boolean);
        expectedFieldMap.put("mac", "m1.mac");
        expectedTypeMap.put("mac", DataType.String);
        expectedFieldMap.put("point_0", "CAST (m2.\"0\" AS DOUBLE)");
        expectedTypeMap.put("point_0", DataType.Float);
        expectedFieldMap.put("point_4", "m2.\"4\"");
        expectedTypeMap.put("point_4", DataType.String);

        assertEquals(expectedFieldMap, actual.getFieldMap());
//        assertEquals(expectedTypeMap, actual.getTypeMap());

        Map<String, Metric> expectedMetrics = new HashMap<>();
        expectedMetrics.put("$count(c)", new Metric("c", "count"));
        expectedMetrics.put("$avg(c)", new Metric("c", "avg"));
        expectedMetrics.put("$avg(b)", new Metric("b", "avg"));
        expectedMetrics.put("$sum(c)", new Metric("c", "sum"));

        assertEquals(expectedMetrics.toString(), actual.getMetrics().toString());
    }

    @Test
    public void testInitForDruidDatasetExisted() {
        // TODO
    }

    @Test
    public void testInvalidBody() {
        String str = "{\n" +
                "    \"dataset\":\"datapoint_dataset\",\n" +
                "    \"productId\": \"1607d2b1946700011607d2b194675c01\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Tokyo\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"-a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200 " +
                "    }\n" +
                "}";

        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/v2/common/query", Unpooled.wrappedBuffer(
                str.getBytes())
        );
        httpRequest.headers().add("Access-Token", "").add("Content-Type", "application/json");
        Request request = new Request(httpRequest, null, new SerializationProvider());
        Response response = new Response();

        controller.init(request, response);

        assertEquals(HttpResponseStatus.FORBIDDEN, response.getResponseStatus());
    }

    @Test
    public void testNoAuth() {
        String str = "{}";
        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/v2/common/query", Unpooled.wrappedBuffer(
                str.getBytes())
        );
        httpRequest.headers().add("Access-Token", this.token).add("Content-Type", "application/json");
        Request request = new Request(httpRequest, null, new SerializationProvider());
        Response response = new Response();

        controller.init(request, response);

        assertEquals(HttpResponseStatus.BAD_REQUEST, response.getResponseStatus());
    }

    @Test
    public void testInitForDatasetNotExisted() {
        String str = "{\n" +
                "    \"dataset\":\"d\",\n" +
                "    \"productId\": \"pid\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Tokyo\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"-a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200 " +
                "    }\n" +
                "}";

        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/v2/common/query", Unpooled.wrappedBuffer(
                str.getBytes())
        );
        httpRequest.headers().add("Access-Token", this.token).add("Content-Type", "application/json");
        Request request = new Request(httpRequest, null, new SerializationProvider());
        Response response = new Response();

        RequestBody actual = controller.init(request, response);

        assertEquals("druid", actual.getEngine());

        Map<String, Metric> expectedMetrics = new HashMap<>();
        expectedMetrics.put("$count(c)", new Metric("c", "count"));
        expectedMetrics.put("$avg(c)", new Metric("c", "avg"));
        expectedMetrics.put("$avg(b)", new Metric("b", "avg"));
        expectedMetrics.put("$sum(c)", new Metric("c", "sum"));

        assertEquals(expectedMetrics.toString(), actual.getMetrics().toString());
    }

    @Test
    public void testInitProcessError() {
        String str = "{\n" +
                "    \"dataset\":\"datapoint_dataset\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Tokyo\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"-a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200 " +
                "    }\n" +
                "}";

        FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/v2/common/query", Unpooled.wrappedBuffer(
                str.getBytes())
        );
        httpRequest.headers().add("Access-Token", this.token).add("Content-Type", "application/json");
        Request request = new Request(httpRequest, null, new SerializationProvider());
        Response response = new Response();

        controller.init(request, response);

        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR, response.getResponseStatus());
    }
}
