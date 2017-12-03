package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

/**
 * Created by Ghold on 2017/7/21.
 */
public class InDimFilter implements DimFilter, Comparable{
    @JsonProperty("type")
    private String type = "in";

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("values")
    private Collection<Object> values;

    public InDimFilter(String dimension, Collection<Object> values) {
        this.dimension = dimension;
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InDimFilter that = (InDimFilter) o;

        if (!type.equals(that.type)) return false;
        if (!dimension.equals(that.dimension)) return false;
        return values.equals(that.values);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + dimension.hashCode();
        result = 31 * result + values.hashCode();
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
