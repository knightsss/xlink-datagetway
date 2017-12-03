package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class DimFilterHavingSpec implements HavingSpec {
    @JsonProperty("type")
    private String type = "filter";

    @JsonProperty("filter")
    private DimFilter filter;

    public DimFilterHavingSpec(DimFilter filter) {
        this.filter = filter;
    }
}
