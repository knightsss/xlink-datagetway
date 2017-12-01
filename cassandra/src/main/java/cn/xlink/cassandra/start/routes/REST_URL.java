package cn.xlink.cassandra.start.routes;

/**
 * Created by Zhengzhenxie on 2017/10/16.
 */
public class REST_URL {
    /**
     * 用户认证
     */
    public static final String USER_AUTH = "/v2/analysis/user/auth";
    /**
     * 用户画像
     */
    public static final String USER_FIGURE = "/v2/analysis/user/figure";
    /**
     * 用户快照
     */
    public static final String USER_INFO_SNAPSHOT = "/v2/analysis/user/snapshot";
    /**
     * 设备快照
     */
    public static final String DEVICE_INFO_SNAPSHOT = "/v2/analysis/device/snapshot";
    /**
     * 设备激活
     */
    public static final String DEVICE_ACTIVE = "/v2/analysis/device/active";
    /**
     * 设备在线
     */
    public static final String DEVICE_ONLINE = "/v2/analysis/device/online";
    /**
     * 设备离线
     */
    public static final String DEVICE_OFFLINE = "/v2/analysis/device/offline";
    /**
     * 远程控制日志
     */
    public static final String REMOTE_CONTROL_LOG = "/v2/analysis/remote_control_log";
    /**
     * avs日志
     */
    public static final String AVS_LOG = "/v2/analysis/avs_log";
    /**
     * 定时任务
     */
    public static final String TIMER_TASK_LOG = "/v2/analysis/timer/execute_log";
}
