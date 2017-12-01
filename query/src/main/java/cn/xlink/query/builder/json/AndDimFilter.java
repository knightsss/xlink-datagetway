package cn.xlink.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

/**
 * Created by Ghold on 2017/7/21.
 */
public class AndDimFilter implements DimFilter, Comparable{

    @JsonProperty("type")
    private String type = "and";
    @JsonProperty("fields")
    private List<DimFilter> fields;

    public AndDimFilter(List<DimFilter> fields) {
        this.fields = fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AndDimFilter that = (AndDimFilter) o;

        if (!type.equals(that.type)) return false;
        Collections.sort(fields);
        Collections.sort(that.fields);

        return fields.equals(that.fields);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + fields.hashCode();
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
