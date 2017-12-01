package cn.xlink.cassandra.core.type;


/**
 * 定时任务日志执行状态
 * Created by Zhengzhenxie on 2017/9/13.
 */
public enum TimerTaskLogStatusType {
    Unknown(0),
    /**
     * 成功
     */
    Success(1),
    /**
     * 失败
     */
    Failure(2);

    private final int type;

    public int type() {
        return type;
    }

    TimerTaskLogStatusType(int type) {
        this.type = type;
    }

    public static final TimerTaskLogStatusType fromType(int type) {
        for (TimerTaskLogStatusType status : values()) {
            if (type == status.type()) {
                return status;
            }
        }
        return Unknown;
    }
}
