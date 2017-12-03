package cn.xlink.data.core.builder;

import cn.xlink.data.query.builder.SqlBuilder;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.query.domain.TimeField;
import cn.xlink.data.core.utils.DataType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static cn.xlink.data.query.domain.RequestBody.QUERY_ENGINE_DRUID;
import static cn.xlink.data.query.domain.RequestBody.QUERY_ENGINE_PRESTO;
import static cn.xlink.data.query.domain.RequestBody.QUERY_TYPE_AGGREGATE;
import static cn.xlink.data.core.utils.DataType.Timestamp;
import static org.junit.Assert.*;

/**
 * Created by Ghold on 2017/8/23.
 */
public class SqlBuilderTest {

    private RequestBody body;

    private void init (String str) throws Exception{
        if (str == null) {
            str = "{\n" +
                "    \"dataset\":\"x t1,y t2\",\n" +
                "    \"productId\": \"pid\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Shanghai\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"-a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"joins\": [{\"left\": \"t1.e\", \"right\": \"t2.e\"}],\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200," +
                "        \"interval\": \"hour\"" +
                "    }\n" +
                "}";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        body = objectMapper.readValue(str, RequestBody.class);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("a", "t1.a");
        fieldMap.put("b", "t1.b");
        fieldMap.put("c", "t2.c");
        fieldMap.put("d", "t2.d");
        fieldMap.put("e", "t1.e");
//        body.setFieldMap(fieldMap);
        Map<String, DataType> typeMap = new HashMap<>();
        typeMap.put("a", DataType.String);
        typeMap.put("b", DataType.Int);
        typeMap.put("c", DataType.Float);
        typeMap.put("d", DataType.Timestamp);
        typeMap.put("e", DataType.String);
//        body.setTypeMap(typeMap);

        body.setCorpId("cid");
        body.setEngine(QUERY_ENGINE_PRESTO);
        body.setQueryType(QUERY_TYPE_AGGREGATE);
        body.setTimeField(new TimeField("d", Timestamp));
//        body.init();
    }

    @Test
    public void testBuild() throws Exception {
        init(null);
        SqlBuilder builder = new SqlBuilder(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
        String actual = builder.build();

        String expected = "SELECT DATE_TRUNC('hour', t2.d) __time, t1.a a, COUNT(t2.c) count_c, AVG(t2.c) avg_c, AVG(t1.b) avg_b, SUM(t2.c) sum_c FROM x t1,y t2 WHERE t2.d > TIMESTAMP '2017-04-09 08:00:00Z' AND t2.d <= TIMESTAMP '2017-08-09 16:00:00Z' AND t1.b > 10 AND t1.e = t2.e GROUP BY DATE_TRUNC('hour', t2.d), t1.a HAVING SUM(t2.c)  > 20 ORDER BY t1.a DESC LIMIT 200";

        assertEquals(expected, actual);
    }

    @Test
    public void testBuildForTotal() throws Exception {
        init(null);
        SqlBuilder builder = new SqlBuilder(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
        String actual = builder.buildForTotal();
        String expected = "SELECT COUNT(*) total FROM x t1,y t2 WHERE t2.d > TIMESTAMP '2017-04-09 08:00:00Z' AND t2.d <= TIMESTAMP '2017-08-09 16:00:00Z' AND t1.b > 10 AND t1.e = t2.e LIMIT 200";

        assertEquals(expected, actual);
    }

    @Test
    public void testDruidSort() throws Exception {
        String str = "{\n" +
                "    \"dataset\":\"ds\",\n" +
                "    \"productId\": \"pid\",\n" +
                "    \"startTime\":\"2017-04-09 16:00:00\",\n" +
                "    \"endTime\":\"2017-08-10 00:00:00\",\n" +
                "    \"timezone\":\"Asia/Shanghai\",\n" +
                "    \"fields\": [\"a\", \"$avg(b)\", \"$count(c)\", \"$sum(c)\", \"$avg(c)\"],\n" +
                "    \"filters\": {\n" +
                "    \t\"b\": {\"$gt\": 10}\n" +
                "    },\n" +
                "    \"sorts\": [\"a\"],\n" +
                "    \"havings\": {\n" +
                "        \"$sum(c)\": {\"$gt\": 20}\n" +
                "    },\n" +
                "    \"options\": {\n" +
                "        \"threshold\": 200," +
                "        \"interval\": \"minute\"" +
                "    }\n" +
                "}";

        init(str);
        body.setEngine(QUERY_ENGINE_DRUID);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("a", "a");
        fieldMap.put("b", "b");
        fieldMap.put("c", "c");
        fieldMap.put("d", "d");
        fieldMap.put("e", "e");
//        body.setFieldMap(fieldMap);
        Map<String, DataType> typeMap = new HashMap<>();
        typeMap.put("a", DataType.String);
        typeMap.put("b", DataType.Int);
        typeMap.put("c", DataType.Float);
        typeMap.put("d", DataType.Timestamp);
        typeMap.put("e", DataType.String);
//        body.setTypeMap(typeMap);
        body.setTimeField(null);

        SqlBuilder builder = new SqlBuilder(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));

        String actual = builder.build();
        String expected = "SELECT FLOOR(__time to minute) __time, a a, COUNT(c) count_c, AVG(c) avg_c, AVG(b) avg_b, SUM(c) sum_c FROM ds WHERE __time > TIMESTAMP '2017-04-09 16:00:00' AND __time <= TIMESTAMP '2017-08-10 00:00:00' AND b > 10 GROUP BY FLOOR(__time to minute), a HAVING SUM(c)  > 20 ORDER BY a ASC LIMIT 200";

        assertEquals(expected, actual);

    }

    @Test
    public void testFilterMap() throws Exception {
        SqlBuilder builder = new SqlBuilder();
        RequestBody body = new RequestBody();
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("a", "t1.a");
        fieldMap.put("b", "t1.b");
        fieldMap.put("c", "t1.c");
        fieldMap.put("d", "t1.d");
        fieldMap.put("e", "t1.e");
        fieldMap.put("f", "t1.f");
        fieldMap.put("g", "t1.g");
        fieldMap.put("h", "t2.h");
        fieldMap.put("i", "t2.i");
        fieldMap.put("j", "t2.j");
        fieldMap.put("k", "t2.k");
        fieldMap.put("l", "t2.l");
        fieldMap.put("m", "t2.m");
        fieldMap.put("n", "t2.n");
//        body.setFieldMap(fieldMap);

        builder.setBody(body);

        String str = "{\n" +
                "\t\"a\": 1,\n" +
                "\t\"b\": \"1\",\n" +
                "\t\"c\": {\"$ne\": \"2\"},\n" +
                "\t\"d\": {\"$lt\": 20, \"$gt\": 10},\n" +
                "\t\"e\": {\"$lte\": 30, \"$gte\": 5},\n" +
                "\t\"f\": {\"$in\": [\"1\", 2, 3]},\n" +
                "\t\"g\": {\"$nin\": [\"4\", 5, 6]},\n" +
                "\t\"$or\": [{\"h\": \"a\"}, {\"i\": 3}],\n" +
                "\t\"$and\": [{\"j\": \"b\"}, {\"k\": 4}],\n" +
                "\t\"$not\": {\"$nor\": [{\"l\": \"c\"}, {\"m\": 5}]},\n" +
                "\t\"n\": {\"$like\": \"%o\"}\n" +
                "}";
        JSONObject object = new JSONObject(str);

        String actual = builder.filterMap(object.toMap(), null, true);
        String expected = "t1.a = '1' AND t1.b = '1' AND (t2.j = 'b' AND t2.k = '4') AND t1.c <> '2' AND t1.d > 10 AND t1.d < 20 AND t1.e >= 5 AND t1.e <= 30 AND t1.f IN ('1','2','3') AND t1.g NOT IN ('4','5','6') AND (t2.h = 'a' OR t2.i = '3') AND (t2.l = 'c' AND t2.m = '5') AND t2.n LIKE '%o'";

        assertEquals(actual, expected);
    }
}
