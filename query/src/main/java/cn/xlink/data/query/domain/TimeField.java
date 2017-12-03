package cn.xlink.data.query.domain;

import cn.xlink.data.core.utils.DataType;

/**
 * Created by Ghold on 2017/8/23.
 */
public class TimeField {
    private String field;
    private DataType type;

    public TimeField() {}

    public TimeField(String field, DataType type) {
        this.field = field;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
