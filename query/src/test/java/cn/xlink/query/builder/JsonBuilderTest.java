package cn.xlink.query.builder;

import cn.xlink.query.builder.json.*;
import cn.xlink.query.domain.RequestBody;
import cn.xlink.query.utils.DataType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.druid.query.ordering.StringComparators;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static cn.xlink.query.domain.RequestBody.QUERY_TYPE_AGGREGATE;
import static cn.xlink.query.domain.RequestBody.QUERY_ENGINE_DRUID;
import static org.junit.Assert.*;

/**
 * Created by Ghold on 2017/8/21.
 */
public class JsonBuilderTest {

    @Test
    public void testBuild() throws Exception {

        String str = "{\n" +
                "    \"dataset\":\"d\",\n" +
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
                "    \"options\": {\n" +
                "        \"threshold\": 200 " +
                "    }\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody body = objectMapper.readValue(str, RequestBody.class);

        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put("a", "a");
        fieldMap.put("b", "b");
        fieldMap.put("c", "c");
        body.setFieldMap(fieldMap);
        Map<String, DataType> typeMap = new HashMap<>();
        typeMap.put("a", DataType.String);
        typeMap.put("b", DataType.Int);
        typeMap.put("c", DataType.Float);
        body.setTypeMap(typeMap);

        body.setCorpId("cid");
        body.setEngine(QUERY_ENGINE_DRUID);
        body.setQueryType(QUERY_TYPE_AGGREGATE);
        body.init();

        JsonBuilder builder = new JsonBuilder(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
        String actual = builder.build();

        String expected = "{\"queryType\":\"groupBy\",\"dataSource\":\"d\"," +
                "\"intervals\":[\"2017-04-09T16:00:00.000+08:00/2017-08-10T00:00:00.000+08:00\"],\"granularity\":\"ALL\"," +
                "\"dimensions\":[{\"type\":\"default\",\"dimension\":\"a\",\"outputName\":\"a\",\"outputType\":\"STRING\"}]," +
                "\"filter\":{\"type\":\"bound\",\"dimension\":\"b\",\"lower\":\"10\",\"lowerStrict\":true,\"upperStrict\":false,\"ordering\":\"numeric\"}," +
                "\"aggregations\":[{\"type\":\"count\",\"name\":\"total\"},{\"type\":\"doubleSum\",\"name\":\"sum_c\",\"fieldName\":\"sum_c\"},{\"type\":\"longSum\",\"name\":\"sum_b\",\"fieldName\":\"sum_b\"}]," +
                "\"postAggregations\":[{\"type\":\"arithmetic\",\"name\":\"avg_c\",\"fn\":\"/\",\"fields\":[{\"type\":\"fieldAccess\",\"name\":\"sum_c\",\"fieldName\":\"sum_c\"},{\"type\":\"fieldAccess\",\"name\":\"count\",\"fieldName\":\"total\"}]},{\"type\":\"arithmetic\",\"name\":\"avg_b\",\"fn\":\"/\",\"fields\":[{\"type\":\"fieldAccess\",\"name\":\"sum_b\",\"fieldName\":\"sum_b\"},{\"type\":\"fieldAccess\",\"name\":\"count\",\"fieldName\":\"total\"}]}]," +
                "\"limitSpec\":{\"type\":\"default\",\"limit\":200,\"columns\":[{\"dimension\":\"a\",\"direction\":\"descending\"}]},\"having\":{\"type\":\"filter\",\"filter\":{\"type\":\"bound\",\"dimension\":\"sum_c\",\"lower\":\"20\",\"lowerStrict\":true,\"upperStrict\":false,\"ordering\":\"numeric\"}}}";

        assertEquals(expected, actual);
    }

    @Test
    public void testFilterMap() throws Exception {
        JsonBuilder builder = new JsonBuilder();
        builder.setBody(new RequestBody());

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

        DimFilter actual = builder.filterMap(object.toMap(), null, true);

        DimFilter expected = new AndDimFilter(
                new ArrayList<>(
                        Arrays.asList(
                                new SelectorDimFilter("a", "1"),
                                new SelectorDimFilter("b", "1"),
                                new NotDimFilter(new SelectorDimFilter("c", "2")),
                                new BoundDimFilter("d", "10", "20", true, true, StringComparators.NUMERIC_NAME),
                                new BoundDimFilter("e", "5", "30", false, false, StringComparators.NUMERIC_NAME),
                                new InDimFilter("f", new ArrayList<>(Arrays.asList("1", new Integer("2"), new Integer("3")))),
                                new NotDimFilter(new InDimFilter("g", new ArrayList<>(Arrays.asList("4", new Integer("5"), new Integer("6"))))),
                                new OrDimFilter(new ArrayList<>(Arrays.asList(
                                        new SelectorDimFilter("h", "a"),
                                        new SelectorDimFilter("i", "3")
                                ))),
                                new AndDimFilter(new ArrayList<>(Arrays.asList(
                                        new SelectorDimFilter("j", "b"),
                                        new SelectorDimFilter("k", "4")
                                ))),
                                new AndDimFilter(new ArrayList<>(Arrays.asList(
                                        new SelectorDimFilter("l", "c"),
                                        new SelectorDimFilter("m", "5")
                                ))),
                                new LikeDimFilter("n", "%o")
                        )));

        assertTrue(expected.equals(actual));
    }
}
