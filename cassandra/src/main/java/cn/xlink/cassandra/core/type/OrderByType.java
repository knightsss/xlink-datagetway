package cn.xlink.cassandra.core.type;


/**
 * 排序类型
 * Created by Zhengzhenxie on 2017/9/20.
 */
public enum OrderByType {
    /**
     * 升序
     */
    Asc("asc"),
    /**
     * 降序
     */
    Desc("desc");


    private final String type;

    public String type() {
        return type;
    }

    OrderByType(String type) {
        this.type = type;
    }

    public static final OrderByType fromType(String type) {
        for (OrderByType scope : values()) {
            if (type.equals(scope.type())) {
                return scope;
            }
        }
        return Asc;
    }
}
