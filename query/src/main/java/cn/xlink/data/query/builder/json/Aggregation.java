package cn.xlink.data.query.builder.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Ghold on 2017/7/21.
 */
public class Aggregation {
    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("fieldName")
    private String fieldName;

    public static final String FIELD_TYPE_STRING = "STRING";
    public static final String FIELD_TYPE_LONG = "LONG";
    public static final String FIELD_TYPE_FLOAT = "FLOAT";

    public static final String AGGREGATION_TYPE_COUNT = "count";
    public static final String AGGREGATION_TYPE_LONG_SUM = "longSum";
    public static final String AGGREGATION_TYPE_DOUBLE_SUM = "doubleSum";
    public static final String AGGREGATION_TYPE_FIELD_ACCESS = "fieldAccess";

    public static final String AGGREGATION_NAME_COUNT = "total";

    public Aggregation(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Aggregation(String type, String name, String fieldName) {
        this.type = type;
        this.name = name;
        this.fieldName = fieldName;
    }


    public void setTypeByFieldType(String fieldType) {
        switch (fieldType) {
            case FIELD_TYPE_LONG:
                this.setType(AGGREGATION_TYPE_LONG_SUM);
                break;
            case FIELD_TYPE_FLOAT:
                this.setType(AGGREGATION_TYPE_DOUBLE_SUM);
        }
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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
