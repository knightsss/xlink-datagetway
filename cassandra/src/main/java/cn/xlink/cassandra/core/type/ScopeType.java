package cn.xlink.cassandra.core.type;


/**
 * 校验维度
 * Created by Zhengzhenxie on 2017/9/13.
 */
public enum ScopeType {
    Unknown("unknown"),
    /**
     * 企业id
     */
    Corp("corp"),
    /**
     * 设备id
     */
    Device("device"),
    /**
     * 产品id
     */
    Product("product"),
    /**
     * 用户id
     */
    User("user");

    private final String type;

    public String type() {
        return type;
    }

    ScopeType(String type) {
        this.type = type;
    }

    public static final ScopeType fromType(String type) {
        for (ScopeType scope : values()) {
            if (type.equals(scope.type())) {
                return scope;
            }
        }
        return Unknown;
    }
}
