package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class NotDimFilter implements DimFilter, Comparable{
    @JsonProperty("type")
    private String type = "not";

    @JsonProperty("field")
    private DimFilter field;

    public NotDimFilter(DimFilter field) {
        this.field = field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NotDimFilter that = (NotDimFilter) o;

        if (!type.equals(that.type)) return false;
        return field.equals(that.field);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + field.hashCode();
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
