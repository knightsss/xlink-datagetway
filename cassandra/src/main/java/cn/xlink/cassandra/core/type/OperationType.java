package cn.xlink.cassandra.core.type;


/**
 * 运算符类型
 * Created by Zhengzhenxie on 2017/9/20.
 */
public enum OperationType {
    /**
     * 大于
     */
    Gt(">"),
    /**
     * 小于
     */
    Lt("<"),
    /**
     * 等于
     */
    Eq("=");


    private final String type;

    public String type() {
        return type;
    }

    OperationType(String type) {
        this.type = type;
    }

    public static final OperationType fromType(String type) {
        for (OperationType scope : values()) {
            if (type.equals(scope.type())) {
                return scope;
            }
        }
        return Eq;
    }
}
