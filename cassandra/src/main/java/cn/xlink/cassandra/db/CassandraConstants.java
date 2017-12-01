package cn.xlink.cassandra.db;

public class CassandraConstants {

    private CassandraConstants() {
        throw new IllegalStateException("Utility class");
    }

    /******************** table name ********************/
    /******************** table name ********************/
    /******************** table name ********************/
    /******************** table name ********************/
    /******************** table name ********************/

    //用户认证 - 用户id
    public static final String T_USER_AUTH = "user_auth";
    //用户画像 - 用户id
    public static final String T_USER_FIGURE = "user_figure";
    //用户快照 - 用户id
    public static final String T_USER_INFO_SNAPSHOT = "user_info_snapshot";
    //设备快照 - 设备id
    public static final String T_DEVICE_INFO_SNAPSHOT = "device_info_snapshot";
    //设备激活 - 设备id
    public static final String T_DEVICE_ACTIVE = "device_active";
    //设备上线 - 设备id
    public static final String T_DEVICE_ONLINE = "device_online";
    //设备下线 - 设备id
    public static final String T_DEVICE_OFFLINE = "device_offline";
    //远程控制日志 - 设备id
    public static final String T_REMOTE_CONTROL_LOG_DEVICE = "message_flow_device";
    //远程控制日志 - 用户id
    public static final String T_REMOTE_CONTROL_LOG_USER = "message_flow_user";
    //avs日志 - 设备id
    public static final String T_AVS_LOG = "avs_log";
    //定时任务执行日志
    public static final String T_TIMER_TASK_LOG = "timer_task_log";

    /***************** materialized view ****************/
    /***************** materialized view ****************/
    /***************** materialized view ****************/
    /***************** materialized view ****************/
    /***************** materialized view ****************/

    //用户认证 - 企业id
    public static final String M_USER_AUTH_CORP_ID = "m_user_auth_corp_id";

    //用户画像- 企业id
    public static final String M_USER_FIGURE_CORP_ID = "m_user_figure_corp_id";

    //用户快照 - 企业id
    public static final String M_USER_INFO_SNAPSHOT_CORP_ID = "m_user_info_snapshot_corp_id";

    //设备快照 - 企业id
    public static final String M_DEVICE_INFO_SNAPSHOT_CORP_ID = "m_device_info_snapshot_corp_id";
    //设备快照 - 产品id
    public static final String M_DEVICE_INFO_SNAPSHOT_PRODUCT_ID = "m_device_info_snapshot_product_id";

    //设备激活 - 企业id
    public static final String M_DEVICE_ACTIVE_CORP_ID = "m_device_active_corp_id";
    //设备激活 - 产品id
    public static final String M_DEVICE_ACTIVE_PRODUCT_ID = "m_device_active_product_id";

    //设备上线 - 企业id
    public static final String M_DEVICE_ONLINE_CORP_ID = "m_device_online_corp_id";
    //设备上线 - 产品id
    public static final String M_DEVICE_ONLINE_PRODUCT_ID = "m_device_online_product_id";

    //设备下线 - 企业id
    public static final String M_DEVICE_OFFLINE_CORP_ID = "m_device_offline_corp_id";
    //设备下线 - 产品id
    public static final String M_DEVICE_OFFLINE_PRODUCT_ID = "m_device_offline_product_id";

    //avs日志 - 企业id
    public static final String M_AVS_LOG_CORP_ID = "m_avs_log_corp_id";
    //avs日志 - 产品id
    public static final String M_AVS_LOG_PRODUCT_ID = "m_avs_log_product_id";


}
