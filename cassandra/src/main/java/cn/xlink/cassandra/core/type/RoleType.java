package cn.xlink.cassandra.core.type;

/**
 * 角色类型
 * Created by Zhengzhenxie on 2017/9/13.
 */
public enum RoleType {
    /**
     * 未知
     */
    Unknown(0),
    /**
     *
     */
    Corp(1),
    /**
     *
     */
    User(2),
    /**
     *
     */
    Device(3),
    /**
     *
     */
    Dealer(4),
    /**
     *
     */
    HeavyBuyer(5),
    /**
     *
     */
    EMPOWER(6);

    private final int type;

    RoleType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }

    public static final RoleType fromType(int type) {
        for (RoleType roleType : values()) {
            if (roleType.type() == type) {
                return roleType;
            }
        }
        return Unknown;
    }

}
