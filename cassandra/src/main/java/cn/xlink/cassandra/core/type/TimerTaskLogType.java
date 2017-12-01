package cn.xlink.cassandra.core.type;


/**
 * 定时任务日志查询维度
 * Created by Zhengzhenxie on 2017/9/25.
 */
public enum TimerTaskLogType {
    Unknown("unknown"),
    /**
     * 默认条件 task_id + execute_time
     */
    Default("default"),
    /**
     * task_id + execute_time + status
     */
    Status("status"),
    /**
     * task_id + execute_time + owner
     */
    Owner("owner");

    private final String type;

    public String type() {
        return type;
    }

    TimerTaskLogType(String type) {
        this.type = type;
    }

    public static final TimerTaskLogType fromType(String type) {
        for (TimerTaskLogType log : values()) {
            if (type.equals(log.type())) {
                return log;
            }
        }
        return Unknown;
    }
}
