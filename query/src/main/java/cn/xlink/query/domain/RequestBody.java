package cn.xlink.query.domain;

import cn.xlink.query.utils.DataType;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTimeZone;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ghold on 2017/5/25.
 */
public class RequestBody {

    @JsonProperty
    private String dataset;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty
    private String timezone;

    @JsonProperty
    private Map<String, Object> filters;

    @JsonProperty
    private List<String> fields;

    @JsonProperty
    private List<String> sorts;

    @JsonProperty
    private Map<String, Object> havings;

    @JsonProperty
    private Map<String, Object> options;

    private String engine;
    private int queryType;
    private Map<String, String> fieldMap;
    private Map<String, DataType> typeMap;
    private TimeField timeField;
    private Map<String, Metric> metrics;
    private List<Map<String, String>> joins;
    private Map<String, Object> preFilters;
    private DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Set<String> interval = new HashSet<String>(Arrays.asList("SECOND", "MINUTE", "HOUR", "DAY", "WEEK", "MONTH", "QUARTER", "YEAR"));
    private static final String DEFAULT_TIMEZONE = "Asia/Shanghai";
    private static final String DEFAULT_TIME_FIELD = "__time";
    private static final Pattern pattern = Pattern.compile("\\$(.*)\\((.*)\\)");

    public static final int DEFAULT_THRESHOLD = 5000;

    public static final int BUILD_TYPE_UNKNOWN = 0;
    public static final int QUERY_TYPE_SELECT = 1;
    public static final int QUERY_TYPE_AGGREGATE = 2;

    public static final String QUERY_ENGINE_DRUID = "druid";
    public static final String QUERY_ENGINE_PRESTO = "presto";

    public RequestBody() {
    }

    public void init(){
        if (options == null) {
            options = new HashMap<>();
        }
        checkTimezone();
        checkTimeField();
        changeFieldsToMetrics();
    }

    public boolean invalid(){
        return (dataset == null || dataset.equals("")
                || startTime == null || startTime.equals("")
                || endTime == null || endTime.equals("")
                || invalidTime());
    }

    private boolean invalidTime() {
        try {
            fmt.parse(startTime);
            fmt.parse(endTime);
        } catch (ParseException e) {
            return true;
        }
        return false;
    }

    public boolean invalidFields() {
        return (fields == null || fields.size() == 0);
    }

    public boolean invalidPagination() {
        return ((int) options.getOrDefault("page", 0) < 0
                || options.get("limit") == null || (int) options.get("limit") <= 0
        );
    }

    public boolean invalidInterval() {
        return (options.getOrDefault("interval", null) == null
                || !interval.contains(options.get("interval").toString().toUpperCase())
        );
    }

    public boolean invalidThreshold() {
        return (int) options.getOrDefault("threshold", 0) <= 0;
    }

    public boolean invalidProductId() {
        return (options.getOrDefault("product_id", null) == null
                || options.get("product_id").equals(""));
    }

    public boolean invalidSort() {
        return (sorts == null
                || sorts.size() == 0);
    }

    public boolean invalidJoin() {
        return (joins == null
                || joins.size() == 0);
    }

    public boolean invalidPreFilter() {
        return (preFilters == null
                || preFilters.size() == 0);
    }

    public boolean invalidMetrics() {
        return (metrics == null || metrics.size() == 0);
    }

    public boolean useJson() {
        if (!invalidMetrics()) {
            for (Metric metric: metrics.values()) {
                if (metric.useJson()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查时区
     */
    private void checkTimezone() {
        if (timezone.equals("") || DateTimeZone.forID(timezone) == null){
            this.setTimezone(DEFAULT_TIMEZONE);
        }
        fmt.setTimeZone(TimeZone.getTimeZone(timezone));
    }

    /**
     * 检查时间字段
     */
    private void checkTimeField() {
        if (timeField == null) {
            this.setTimeField(new TimeField(DEFAULT_TIME_FIELD, DataType.Timestamp));
        }
    }

    /**
     * field to metrics
     * @return
     */
    private void changeFieldsToMetrics() {
        if (!invalidFields()) {
            List<String> newFields = new ArrayList<>();
            for (String field: fields) {
                Matcher m = pattern.matcher(field);
                if (m.find()){
                    if (metrics == null) {
                        this.setMetrics(new HashMap<>());
                    }
                    metrics.put(field, new Metric(m.group(2), m.group(1)));
                } else {
                    newFields.add(field);
                }
            }
            fields = newFields;
            if (metrics != null && metrics.size() > 0) {
                this.setQueryType(QUERY_TYPE_AGGREGATE);
            } else {
                this.setQueryType(QUERY_TYPE_SELECT);
            }
        }
    }

    /**
     * field change to sql format
     * @return
     */
    public String fieldExchange(String field) {
        if (fieldMap != null
                && fieldMap.get(field) != null
                && !fieldMap.get(field).equals("")) return fieldMap.get(field);
        if (!invalidMetrics()
                && metrics.get(field) != null) return metrics.get(field).getDisplayName();
        return field;
    }

    public void setCorpId(String corpId) {
        options.put("corp_id", corpId);
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String start_time) {
        this.startTime = start_time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String end_time) {
        this.endTime = end_time;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public Map<String, String> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, DataType> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, DataType> typeMap) {
        this.typeMap = typeMap;
    }

    public TimeField getTimeField() {
        return timeField;
    }

    public void setTimeField(TimeField timeField) {
        this.timeField = timeField;
    }

    public Map<String, Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Metric> metrics) {
        this.metrics = metrics;
    }

    public List<String> getSorts() {
        return sorts;
    }

    public void setSorts(List<String> sorts) {
        this.sorts = sorts;
    }

    public Map<String, Object> getHavings() {
        return havings;
    }

    public void setHavings(Map<String, Object> havings) {
        this.havings = havings;
    }

    public List<Map<String, String>> getJoins() {
        return joins;
    }

    public void setJoins(List<Map<String, String>> joins) {
        this.joins = joins;
    }

    public Map<String, Object> getPreFilters() {
        return preFilters;
    }

    public void setPreFilters(Map<String, Object> preFilters) {
        this.preFilters = preFilters;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public DateFormat getFmt() {
        return fmt;
    }

    public static Pattern getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                ", dataset='" + dataset + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", timezone='" + timezone + '\'' +
                ", filters=" + filters +
                ", fields=" + fields +
                ", options=" + options +
                '}';
    }
}
