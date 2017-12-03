package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class BoundDimFilter implements DimFilter,Comparable {
    @JsonProperty("type")
    private String type = "bound";

    @JsonProperty("dimension")
    private String dimension;
    @JsonProperty("lower")
    private String lower;
    @JsonProperty("upper")
    private String upper;
    @JsonProperty("lowerStrict")
    private boolean lowerStrict;
    @JsonProperty("upperStrict")
    private boolean upperStrict;
    @JsonProperty("ordering")
    private String ordering;

    public BoundDimFilter(String dimension, String lower, String upper, boolean lowerStrict, boolean upperStrict, String ordering) {
        this.dimension = dimension;
        this.lower = lower;
        this.upper = upper;
        this.lowerStrict = lowerStrict;
        this.upperStrict = upperStrict;
        this.ordering = ordering;
    }

    public BoundDimFilter(String dimension, String ordering){
        this.dimension =dimension;
        this.ordering =ordering;
    }

    public boolean invalid(){
        return (this.lower == null || this.lower.equals(""))
                && (this.upper == null || this.upper.equals(""));
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public void setLowerStrict(boolean lowerStrict) {
        this.lowerStrict = lowerStrict;
    }

    public void setUpperStrict(boolean upperStrict) {
        this.upperStrict = upperStrict;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoundDimFilter that = (BoundDimFilter) o;

        if (lowerStrict != that.lowerStrict) return false;
        if (upperStrict != that.upperStrict) return false;
        if (!type.equals(that.type)) return false;
        if (!dimension.equals(that.dimension)) return false;
        if (lower != null ? !lower.equals(that.lower) : that.lower != null) return false;
        if (upper != null ? !upper.equals(that.upper) : that.upper != null) return false;
        return ordering != null ? ordering.equals(that.ordering) : that.ordering == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + dimension.hashCode();
        result = 31 * result + (lower != null ? lower.hashCode() : 0);
        result = 31 * result + (upper != null ? upper.hashCode() : 0);
        result = 31 * result + (lowerStrict ? 1 : 0);
        result = 31 * result + (upperStrict ? 1 : 0);
        result = 31 * result + (ordering != null ? ordering.hashCode() : 0);
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
