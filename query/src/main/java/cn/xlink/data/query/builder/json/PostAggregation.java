package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Ghold on 2017/7/21.
 */
public class PostAggregation {
    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fn")
    private String fn;

    @JsonProperty("fields")
    private List<Aggregation> fields;

    public static final String POST_AGGREGATION_TYPE_ARITHMETIC = "arithmetic";

    public PostAggregation() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public List<Aggregation> getFields() {
        return fields;
    }

    public void setFields(List<Aggregation> fields) {
        this.fields = fields;
    }
}
