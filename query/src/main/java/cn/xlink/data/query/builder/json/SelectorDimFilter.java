package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

/**
 * Created by Ghold on 2017/7/21.
 */
public class SelectorDimFilter implements DimFilter, Comparable{
    @JsonProperty("type")
    private String type = "selector";

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("value")
    private String value;

    public SelectorDimFilter(String dimension, String value) {
        this.dimension = dimension;
        this.value = Strings.nullToEmpty(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SelectorDimFilter that = (SelectorDimFilter) o;

        if (!type.equals(that.type)) return false;
        if (!dimension.equals(that.dimension)) return false;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + dimension.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public int compareTo(Object o) {
        if (this.hashCode() >= o.hashCode()) {
            return 1;
        } else {
            return -1;
        }
    }
}
