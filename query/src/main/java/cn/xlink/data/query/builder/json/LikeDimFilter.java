package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class LikeDimFilter implements DimFilter, Comparable{

    @JsonProperty("type")
    private String type = "like";

    @JsonProperty("dimension")
    private String dimension;

    @JsonProperty("pattern")
    private String pattern;

    public LikeDimFilter(String dimension, String pattern) {
        this.dimension = dimension;
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikeDimFilter that = (LikeDimFilter) o;

        if (!type.equals(that.type)) return false;
        if (!dimension.equals(that.dimension)) return false;
        return pattern.equals(that.pattern);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + dimension.hashCode();
        result = 31 * result + pattern.hashCode();
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
