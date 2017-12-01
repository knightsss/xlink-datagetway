package cn.xlink.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.druid.guice.annotations.Json;

import java.util.Map;

/**
 * Created by Ghold on 2017/8/9.
 */
public class PagingSpec {
    @JsonProperty("pagingIdentifiers")
    private Map<String, Integer> pagingIdentifiers;

    @JsonProperty("threshold")
    private int threshold;

    @JsonProperty("fromNext")
    private boolean fromNext;

    public PagingSpec(Map<String, Integer> pagingIdentifiers, int threshold) {
        this.pagingIdentifiers = pagingIdentifiers;
        this.threshold = threshold;
    }
}
