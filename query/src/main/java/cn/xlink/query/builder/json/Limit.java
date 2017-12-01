package cn.xlink.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Ghold on 2017/7/21.
 */
public class Limit {
    @JsonProperty("type")
    private String type;

    @JsonProperty("limit")
    private int limit;

    @JsonProperty("columns")
    private List<OrderColumn> columns;

    public Limit(String type, int limit, List<OrderColumn> columns) {
        this.type = type;
        this.limit = limit;
        this.columns = columns;
    }
}
