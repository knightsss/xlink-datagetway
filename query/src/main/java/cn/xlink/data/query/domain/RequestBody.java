package cn.xlink.data.query.domain;

import cn.xlink.data.core.utils.DataType;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataFieldEntity;
import cn.xlink.data.query.cache.DatasetCacheStore;
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
    private Map<String, Object> filters;

    @JsonProperty
    private List<String> fields;

    @JsonProperty
    private List<String> metrics;

    @JsonProperty
    private List<String> sorts;

    @JsonProperty
    private Map<String, Object> havings;

    @JsonProperty
    private Map<String, Object> options;

    private String timezone;
    private String engine;
    private int queryType;
    private DatasetCacheStore cache;
    private Map<String, DatasetMetadataFieldEntity> fieldMap;
    private Map<String, String> sourceMap = new HashMap<>();
    private TimeField timeField;
    private Map<String, Metric> metricMap;
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

    /**
     * 初始化body
     * @param cache
     * @throws Exception
     */
    public void init(DatasetCacheStore cache) throws Exception{
        if (invalidMetrics() && invalidFields()) throw new Exception("field or metric must have at least one");
        this.cache = cache;
        this.engine = cache.getEngine();

        // 主体源不可缺少
        this.sourceMap.put(cache.getDefaultSource().getName(), cache.getDefaultSource().getSource());

        // 预置过滤条件
        if (cache.getPreFilters() != null && cache.getPreFilters().size() > 0) {
            Map<String, Object> preFilters = new HashMap<>();
            for (String name : cache.getPreFilters()) {
                String value = options.getOrDefault(name, "").toString();
                if (!value.equals("")) preFilters.put(name, value);
            }
            this.preFilters = preFilters;
        }

        // 根据元数据建立字段名称映射和字段类型映射
        Map<String, DatasetMetadataFieldEntity> fieldMap = new HashMap<>();
        fieldMap.putAll(cache.getFieldMap());

        // 时间字段在数据平台是必须的，单独处理的原因是，SQL对于时间的过滤需要特殊的格式，如必须加入Timestamp保留字
        if (cache.getTimeMap() != null && cache.getTimeMap().size() > 0) {
            DatasetMetadataFieldEntity entity;
            Object name = options.getOrDefault("time_field", null);
            if (name == null
                    || (entity = cache.getTimeMap().getOrDefault(name.toString(), null)) == null) {
                entity = cache.getDefaultTime();
            }

            this.timeField = new TimeField(entity.getName(), DataType.fromType(entity.getType()));

            fieldMap.putAll(cache.getTimeMap());
        }

        this.fieldMap = fieldMap;

        checkTimezone();
        checkTimeField();
        convertMetricsToMap();
    }

    public boolean invalid(){
        if (options == null) {
            options = new HashMap<>();
        }
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

    public boolean invalidMetrics() {
        return (metrics == null || metrics.size() == 0);
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

    public boolean invalidPreFilter() {
        return (preFilters == null
                || preFilters.size() == 0);
    }

    public boolean invalidMetricMap() {
        return (metricMap == null || metricMap.size() == 0);
    }

    public boolean useJson() {
        if (!invalidMetricMap()) {
            for (Metric metric: metricMap.values()) {
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
        String timezone = options.getOrDefault("timezone", DEFAULT_TIMEZONE).toString();
        if (timezone.equals("") || DateTimeZone.forID(timezone) == null){
            this.setTimezone(DEFAULT_TIMEZONE);
        } else {
            this.setTimezone(timezone);
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
     * field to metricMap
     * @return
     */
    private void convertMetricsToMap() {
        if (!invalidMetrics()) {
            for (String field: metrics) {
                Matcher m = pattern.matcher(field);
                if (m.find()){
                    if (metricMap == null) {
                        this.setMetricMap(new HashMap<>());
                    }
                    metricMap.put(field, new Metric(m.group(2), m.group(1)));
                }
            }
            if (metricMap != null && metricMap.size() > 0) {
                this.setQueryType(QUERY_TYPE_AGGREGATE);
            } else {
                this.setQueryType(QUERY_TYPE_SELECT);
            }
        }
    }

    /**
     * field change to dataset format
     * @return
     */
    public String fieldExchange(String field) {
        if (fieldMap != null
                && fieldMap.get(field) != null) {
            String source = fieldMap.get(field).getSource();
            sourceMap.put(source, cache.getSourceMap().get(source));
            return fieldMap.get(field).getField();
        }
        if (!invalidMetricMap()
                && metricMap.get(field) != null) return metricMap.get(field).getDisplayName();
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

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public DatasetCacheStore getCache() {
        return cache;
    }

    public void setCache(DatasetCacheStore cache) {
        this.cache = cache;
    }

    public Map<String, DatasetMetadataFieldEntity> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, DatasetMetadataFieldEntity> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public Map<String, String> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<String, String> sourceMap) {
        this.sourceMap = sourceMap;
    }

    public TimeField getTimeField() {
        return timeField;
    }

    public void setTimeField(TimeField timeField) {
        this.timeField = timeField;
    }

    public Map<String, Metric> getMetricMap() {
        return metricMap;
    }

    public void setMetricMap(Map<String, Metric> metricMap) {
        this.metricMap = metricMap;
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
                ", filters=" + filters +
                ", fields=" + fields +
                ", options=" + options +
                '}';
    }
}
