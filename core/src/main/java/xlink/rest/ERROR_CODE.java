package xlink.rest;

public class ERROR_CODE {

	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/**
	 * HTTP 400 下返回的错误码
	 */
	/**
	 * 请求数据字段验证不通过
	 */
	public static final int PARAM_VALID_ERROR = 4001001;

	/**
	 * 请求数据必须字段不可为空
	 */
	public static final int PARAM_MUST_NOT_NULL = 4001002;
	/**
	 * 手机验证码不存在
	 */
	public static final int PHONE_VERIFYCODE_NOT_EXISTS = 4001003;
	/**
	 * 手机验证码错误
	 */
	public static final int PHONE_VERIFYCODE_ERROR = 4001004;
	/**
	 * 注册的手机号已存在
	 */
	public static final int REGISTER_PHONE_EXISTS = 4001005;
	/**
	 * 注册的邮箱已存在
	 */
	public static final int REGISTER_EMAIL_EXISTS = 4001006;
	/**
	 * 密码错误
	 */
	public static final int ACCOUNT_PASSWORD_ERROR = 4001007;
	/**
	 * 帐号不合法
	 */
	public static final int ACCOUNT_VALID_ERROR = 4001008;
	/**
	 * 企业成员状态不合法
	 */
	public static final int MEMBER_STATUS_ERROR = 4001009;
	/**
	 * 刷新token不合法
	 */
	public static final int REFRESH_TOKEN_ERROR = 4001010;
	/**
	 * 未知成员角色类型
	 */
	public static final int MEMBER_ROLE_TYPE_UNKOWN = 4001011;
	/**
	 * 只有管理员才能邀请
	 */
	public static final int MEMBER_INVITE_NOT_ADMIN = 4001012;
	/**
	 * 不可修改其他成员信息
	 */
	public static final int CAN_NOT_MODIFY_OTHER_MEMBER_INFO = 4001013;
	/**
	 * 不能删除本人
	 */
	public static final int CAN_NOT_DELETE_YOURSELF = 4001014;
	/**
	 * 未知的产品连接类型
	 */
	public static final int PRODUCT_LINK_TYPE_UNKOWN = 4001015;
	/**
	 * 已发布的产品不可删除
	 */
	public static final int CAN_NOT_DELETE_RELEASE_PRODUCT = 4001016;
	/**
	 * 固件版本已存在
	 */
	public static final int FIRMWARE_VERSION_EXISTS = 4001017;
	/**
	 * 数据端点未知数据类型
	 */
	public static final int DATAPOINT_TYPE_UNKOWN = 4001018;
	/**
	 * 数据端点索引已存在
	 */
	public static final int DATAPOINT_INDEX_EXISTS = 4001019;
	/**
	 * 已发布的数据端点不可删除
	 */
	public static final int CANT_NOT_DELETE_RELEASED_DATAPOINT = 4001020;
	/**
	 * 该产品下设备MAC地址已存在
	 */
	public static final int DEVICE_MAC_ADDRESS_EXISTS = 4001021;
	/**
	 * 不能删除已激活的设备
	 */
	public static final int CAN_NOT_DELETE_ACTIVATED_DEVICE = 4001022;
	/**
	 * 扩展属性Key为预留字段
	 */
	public static final int PROPERTY_KEY_PROTECT = 4001023;
	/**
	 * 设备扩展属性超过上限
	 */
	public static final int PROPERTY_LIMIT = 4001024;
	/**
	 * 新增已存在的扩展属性
	 */
	public static final int PROPERTY_ADD_EXISTS = 4001025;
	/**
	 * 更新不存在的扩展属性
	 */
	public static final int PROPERTY_UPDATE_NOT_EXISTS = 4001026;
	/**
	 * 属性字段名不合法
	 */
	public static final int PROPERTY_KEY_ERROR = 4001027;
	/**
	 * 邮件验证码不存在
	 */
	public static final int EMAIL_VERIFYCODE_NOT_EXISTS = 4001028;
	/**
	 * 邮件验证码错误
	 */
	public static final int EMAIL_VERIFYCODE_ERROR = 4001029;
	/**
	 * 用户状态不合法
	 */
	public static final int USER_STATUS_ERROR = 4001030;
	/**
	 * 用户手机尚未认证
	 */
	public static final int USER_PHONE_NOT_VALID = 4001031;
	/**
	 * 用户邮箱尚未认证
	 */
	public static final int USER_EMAIL_NOT_VALID = 4001032;
	/**
	 * 用户已经订阅设备
	 */
	public static final int USER_HAS_SUBSCRIBE_DEVICE = 4001033;
	/**
	 * 用户没有订阅该设备
	 */
	public static final int USER_HAVE_NO_SUBSCRIBE_DEVICE = 4001034;
	/**
	 * 自动升级任务名称已存在
	 */
	public static final int UPGRADE_TASK_NAME_EXISTS = 4001035;
	/**
	 * 升级任务状态未知
	 */
	public static final int UPGRADE_TASK_STATUS_UNKOWN = 4001036;
	/**
	 * 已有相同的起始版本升级任务
	 */
	public static final int UPGRADE_TASK_HAVE_STARTING_VERSION = 4001037;
	/**
	 * 设备激活失败
	 */
	public static final int DEVICE_ACTIVE_FAIL = 4001038;
	/**
	 * 设备认证失败
	 */
	public static final int DEVICE_AUTH_FAIL = 4001039;
	/**
	 * 订阅设备认证码错误
	 */
	public static final int SUBSCRIBE_AUTHORIZE_CODE_ERROR = 4001041;
	/**
	 * 授权名称已存在
	 */
	public static final int EMPOWER_NAME_EXISTS = 4001042;
	/**
	 * 该告警规则名称已存在
	 */
	public static final int ALARM_RULE_NAME_EXISTS = 4001043;
	/**
	 * 数据表名称已存在
	 */
	public static final int DATA_TABLE_NAME_EXISTS = 4001045;
	/**
	 * 产品固件文件超过大小限制
	 */
	public static final int PRODUCT_FIRMWARE_FILE_SIZE_LIMIT = 4001046;
	/**
	 * apn密钥文件超过大小限制
	 */
	public static final int APP_APN_LICENSE_FILE_SIZE_LIMIT = 4001047;
	/**
	 * APP的APN功能未启用
	 */
	public static final int APP_APN_IS_NOT_ENABLE = 4001048;
	/**
	 * 产品未允许用户注册设备
	 */
	public static final int PRODUCT_CAN_NOT_REGISTER_DEVICE = 4001049;
	/**
	 * 该类型的邮件模板已存在
	 */
	public static final int EMAIL_TEMPLATE_TYPE_EXISTS = 4001050;
	/**
	 * 邮件模板正文内容参数缺失
	 */
	public static final int EMAIL_TEMPLATE_CONTENT_PARAM_LOSE = 4001051;
	/**
	 * 该手机今日发送短信的次数已达上限
	 */
	public static final int SMS_SEND_LIMIT = 4001052;
	/**
	 * 设备已经是最新版
	 */
	public static final int DEVICE_VERSION_NEWEST = 4001053;
	/**
	 * 设备不在线
	 */
	public static final int DEVICE_IS_OFFLINE = 4001054;
	/**
	 * 设备升级失败
	 */
	public static final int DEVICE_UPGRADE_FAILED = 4001055;
	/**
	 * 模板审核中
	 */
	public static final int EMAIL_TEMPLATE_STATUS_WAITING = 4001056;
	/**
	 * 应用类型错误
	 */
	public static final int APP_TYPE_ERROR = 4001057;
	/**
	 * 数据表类型错误
	 */
	public static final int DATA_TABLE_TYPE_ERROR = 4001058;
	/**
	 * 第三方用户验证失败
	 */
	public static final int USER_AUTH_THIRD_INVALID = 4001059;
	/**
	 * 图片文件大小超过上限
	 */
	public static final int IMAGE_FILE_SIZE_LIMIT = 4001060;

	/**
	 * 用户锁定错误
	 */
	public static final int ACCOUNT_UNDER_LOCK_ERROR = 4001061;

	/**
	 * 企业成员帐号邮箱已经激活
	 */
	public static final int MEMBER_EMAIL_HAS_ACTIVED = 4001062;

	/**
	 * 用户邮箱已经激活
	 */
	public static final int USER_EMAIL_HAS_ACTIVED = 4001063;
	/**
	 * 访问设备超时
	 */
	public static final int ACCESS_DEVICE_TIMEOUT = 4001064;

	/**
	 * 授权登录wechat认证失败
	 */
	public static final int USER_AUTH_WECHAT_INVALID = 4001065;
	/**
	 * 授权登录微博认证失败
	 */
	public static final int USER_AUTH_WEIBO_INVALID = 4001066;

	/**
	 * qq获取授权用户nickname失败
	 */
	public static final int USER_AUTH_QQ_GET_NICKNAME_ERROR = 4001067;
	/**
	 * weichat获取授权用户nickname失败
	 */
	public static final int USER_AUTH_WECHAT_GET_NICKNAME_ERROR = 4001068;
	/**
	 * 微博获取授权用户nickname失败
	 */
	public static final int USER_AUTH_WEIBO_GET_NICKNAME_ERROR = 4001069;
	/**
	 * 其他平台获取授权用户nickname失败
	 */
	public static final int USER_AUTH_OTHER_GET_NICKNAME_ERROR = 4001070;
	/**
	 * accesskey不可用
	 */
	public static final int ACCESSKEY_STATUS_DISABLE = 4001071;
	/**
	 * accesskey错误
	 */
	public static final int ACCESSKEY_ERROR = 4001072;

	/**
	 * 该mac地址的设备已经授权
	 */
	public static final int WECHAT_AUTH_DEVICE_MAC_HAD_AUTH = 4001073;

	/**
	 * 再次授权时,mac地址未授权
	 */
	public static final int WECHAT_AUTH_DEVICE_MAC_NOT_AUTH = 4001074;

	/**
	 * DDS数据转发规则中的url对应服务器不相应
	 */
	public static final int DDS_RULE_SERVER_FORBIDDEN = 4001075;
	/**
	 * 用户不是设备管理员
	 */
	public static final int USER_IS_NOT_DEVICE_ADMIN = 4001076;
	/**
	 * 微信配置相关参数类型不明确
	 */
	public static final int WECHAT_AUTH_CONFIG_TYPE_UNKOWN = 4001077;

	/**
	 * 产品正在授权中
	 */
	public static final int WECHAT_AUTH_PROCESS = 4001078;
	/**
	 * 管理员帐号密码错误
	 */
	public static final int ADMIN_PASSWORD_ERROR = 4001079;

	/**
	 * 企业配额超出限制
	 */
	public static final int OVER_CORP_LIMIT_ERROR = 4001080;

	/**
	 * 访问sendcloud异常
	 */
	public static final int SENDCLOUD_ERROR = 4001081;

	/**
	 * 云迁移任务不存在
	 */
	public static final int MIGRATION_NOT_EXISTS = 4001082;

	/**
	 * 云迁移任务存在
	 */
	public static final int MIGRATION_EXISTS = 4001083;

	/**
	 * 云迁移任务取消了
	 */
	public static final int MIGRATION_CANCEL = 4001084;

	/**
	 * 云迁移任务完成了
	 */
	public static final int MIGRATION_CONFIRMED = 4001085;

	/**
	 * 产品连接类型不是PC类型
	 */
	public static final int PRODUCT_LINKTYPE_NOT_PC = 4001086;

	/**
	 * 不允许通过激活添加设备
	 */
	public static final int PRODUCT_IS_ACTIVE_REGISTER = 4001087;

	/**
	 * sendcloud 域名超过限制
	 */
	public static final int SENDCLOUD_DOMAIN_OVER_LIMIT = 4001088;

	/**
	 * 快照规则未知类型
	 */
	public static final int SNAPSHOT_TYPE_UNKOWN = 4001089;

	/**
	 * APP类型已存在
	 */
	public static final int APP_TYPE_EXIST = 4001090;
	/**
	 * 产品已发布
	 */
	public static final int PRODUCT_IS_RELEASED = 4001091;
	/**
	 * 插件已存在
	 */
	public static final int PLUGIN_IS_EXISTS = 4001092;

	/**
	 * 搜索运算符未知
	 */
	public static final int TAG_SEARCH_TYPE_UNKOWN = 4001093;
	/**
	 * 手机号已被使用
	 */
	public static final int PHONE_EXISTS = 4001094;
	/**
	 * 非法url
	 */
	public static final int UNVALID_URL_FORMAT = 4001095;
	/**
	 * 邮箱已被使用
	 */
	public static final int EMAIL_EXISTS = 4001096;

	/**
	 * 设备授权码验证错误
	 */
	public static final int DEVICE_AUTHORIZE_CODE_ERROR = 4001097;
	/**
	 * 访问设备probe失败
	 */
	public static final int ACCESS_PROBE_DEVICE_FAIL = 4001098;
	/**
	 * 成员角色使用中
	 */
	public static final int MEMBER_ROLE_IS_USED = 4001099;
	/**
	 * splashWnd图片的最大数量
	 */
	public static final int APP_SPLASHWND_NUMBER_LIMIT = 4001100;
	/**
	 * xfile最大数量
	 */
	public static final int XFILE_SIZE_LIMIT = 4001101;
	/**
	 * 密码过于简单
	 */
	public static final int SIMPLE_PASSWORD = 4001102;
	/**
	 * 生成二维码信息数量超过上限
	 */
	public static final int QRCODE_TICKET_GENERATE_LIMIT = 4001103;
	/**
	 * 广播消息类型未知
	 */
	public static final int MESSAGE_TYPE_UNKNOWN = 4001104;
	/**
	 * 广播消息动作类型未知
	 */
	public static final int MESSAGE_ACTION_TYPE_UNKNOWN = 4001105;
	/**
	 * 广播消息目标地址类型未知
	 */
	public static final int MESSAGE_DEST_TYPE_UNKNOWN = 4001106;
	/**
	 * 用户不是第三方用户
	 */
	public static final int USER_IS_NOT_THIRD = 4001107;
	/**
	 * 用户已初始化登录密码
	 */
	public static final int USER_IS_ALREADY_INIT_PASSWORD = 4001108;
	/**
	 * 用户已绑定第三方帐号
	 */
	public static final int USER_HAS_BIND_THIRD_ACCOUNT = 4001109;
	/**
	 * 解除绑定第三方帐号失败,请保证至少保留一个第三方帐号信息或者Xlink帐号信息
	 */
	public static final int USER_UNBIND_THIRD_ACCOUNT_FAIL = 4001110;
	/**
	 * 设备地理位置信息不存在
	 */
	public static final int DEVICE_GEOGRAPHY_NOT_EXIST = 4001111;
	/**
	 * 设备固件类型不存在
	 */
	public static final int FIRMWARE_TYPE_UNKNOWN = 4001112;
	/**
	 * 固件升级任务类型不存在
	 */
	public static final int FIRMWARE_UPGRADE_TASK_TYPE_UNKNOWN = 4001113;
	/**
	 * 设备的accessToken与需要操作的设备id不一致
	 */
	public static final int DEVICE_ID_VALID_FAILED = 4001114;
	/**
	 * 用户性别类型未知
	 */
	public static final int USER_GENDER_TYPE_UNKNOWN = 4001115;
	/**
	 * 用户区域设置错误
	 */
	public static final int USER_REGION_ERROR = 4001116;
	/**
	 * 经销商已经被停用
	 */
	public static final int DEALER_DISABLED = 4001117;
	/**
	 * 大客户账号已经被停用
	 */
	public static final int HEAVY_BUYER_DISABLED = 4001118;
	/**
	 * 推送任务不存在
	 */
	public static final int BROADCAST_TASK_NOT_EXIST = 4001119;
	/**
	 * 企业云迁移任务不存在
	 */
	public static final int CORP_MIGRATION_NOT_EXIST = 4001120;
	/**
	 * 设备未激活
	 */
	public static final int DEVICE_NOT_ACTIVE = 4001121;
	/**
	 * 设备区域设置错误
	 */
	public static final int DEVICE_REGION_ERROR = 4001122;
	/**
	 * 推送任务已经开始
	 */
	public static final int BROADCAST_TASK_STARTED = 4001123;
	/**
	 * 用户未初始化密码
	 */
	public static final int USER_IS_NOT_INIT_PASSWORD = 4001124;
	/**
	 * 用户自定义第三方OpenIDD已存在
	 */
	public static final int USER_OTHER_OPEN_ID_IS_EXISTS = 4001125;
	/**
	 * 用户第三方QQ OpenID已存在
	 */
	public static final int USER_QQ_OPEN_ID_IS_EXISTS = 4001126;
	/**
	 * 用户第三方微信 OpenID已存在
	 */
	public static final int USER_WX_OPEN_ID_IS_EXISTS = 4001127;
	/**
	 * 用户第三方微博OpenID已存在
	 */
	public static final int USER_WB_OPEN_ID_IS_EXISTS = 4001128;
	/**
	 * 无效的设备二维码
	 */
	public static final int DEVICE_QRCODE_UNVALID = 4001129;
	/**
	 * home邀请ID无效
	 */
	public static final int HOME_INVITEID_INVALID = 4001130;
	/**
	 * home邀请的状态错误
	 */
	public static final int HOME_INVITEID_STATUS_ERROR = 4001131;
	/**
	 * home的超级管理员不能删除
	 */
	public static final int HOME_SUPERADMIN_NOT_DELETE = 4001132;
	/**
	 * home的创建者不能自行退出home
	 */
	public static final int HOME_CREATOR_DISABLE_QUIT = 4001133;
	/**
	 * 用户不是home邀请者
	 */
	public static final int NOT_HOME_INVITEE = 4001134;

	/**
	 * 用户不同在一个Home家庭中
	 */
	public static final int USERS_NOT_IN_HOME = 4001135;
	/**
	 * home成员数量超过限制
	 */
	public static final int HOME_MEMBER_AMOUNT_OVERFLOW = 4001136;
	/**
	 * APP应用未启用
	 */
	public static final int APP_IS_NOT_ENABLE = 4001137;
	/**
	 * 升级任务类型未知错误
	 */
	public static final int UPGRADE_TASK_TYPE_UNKNOWN = 4001138;
	/**
	 * 不允许多个设备管理员
	 */
	public static final int DEVICE_NOT_ALLOW_MULTI_ADMIN = 4001139;
	/**
	 * 设备不属于home
	 */
	public static final int DEVICE_NOT_BELONG_HOME = 4001140;
	/**
	 * 设备因扫描模式原因订阅失败
	 */
	public static final int DEVICE_SUBSCRIBE_FAIL_BY_SCAN_MODE = 4001141;
	/**
	 * 固件在启动的升级任务中不能被修改
	 */
	public static final int FIRMWARE_CAN_NOT_MODIFIED = 4001142;
	/**
	 * 经销商销售记录中客户类型不明确
	 */
	public static final int DEALER_CLIENT_TYPE_UNKOWN = 4001143;
	/**
	 * app未经大管理台审核
	 */
	public static final int APP_PLATFORM_STATUS_DISABLE = 4001144;
	/**
	 * app平台状态类型未知
	 */
	public static final int APP_PLATFORM_STATUS_UNKNOWN = 4001145;
	/**
	 * 微信设备授权任务不是正在进行
	 */
	public static final int WECHAT_AUTH_NOT_PROCESS = 4001146;
	/**
	 * 导入的设备mac地址或者sn重复
	 */
	public static final int DEVICE_IMPORT_DUPLICATE = 4001147;
	/**
	 * 设备的sn已存在
	 */
	public static final int DEVICE_SN_EXISTS = 4001148;
	/**
	 * 快照统计规则统计粒度未知
	 */
	public static final int SNAPSHOT_STATISTICS_FINENESS_TYPE_UNKNOWN = 4001149;
	/**
	 * 快照规则的统计规则超过限额
	 */
	public static final int SNAPSHOT_STATISTIC_OVER_LIMIT = 4001150;
	/**
	 * 快照规则不包含数据端点
	 */
	public static final int DATAPOINT_NOT_BEYONG_TO_SNAPSHOT_RULE = 4001151;
	/**
	 * 快照统计规则统计方式未知
	 */
	public static final int SNAPSHOT_STATISTICS_MODE_TYPE_UNKNOWN = 4001152;
	/**
	 * 智能互联的指令不合法
	 */
	public static final int INTERCONNECT_ACTION_INVALID = 4001153;
	/**
	 * 需要图片验证码
	 */
	public static final int NEED_CAPTCHA = 4001154;
	/**
	 * 图片验证码不存在
	 */
	public static final int CAPTCHA_NOT_EXISTS = 4001155;
	/**
	 * 图片验证吗错误
	 */
	public static final int CAPTCHA_ERROR = 4001156;
	/**
	 * 快照规则不支持有统计规则
	 */
	public static final int SNAPSHOT_RULE_TYPE_UNSUPPORT = 4001157;

	/**
	 * 排序方式类型未知
	 */
	public static final int SORT_TYPE_UNKNOWN = 4001158;

	/**
	 * 快照统计规则未知类型
	 */
	public static final int SNAPSHOT_STATISTICS_TYPE_UNKOWN = 4001159;

	/**
	 * 快照规则有统计规则
	 */
	public static final int SNAPSHOT_RULE_HAVE_STATISTICS_RULE = 4001160;
	/**
	 * 无效的oauth授权应用
	 */
	public static final int OAUTH2_INVALID_CLIENT = 4001161;
	/**
	 * 无效的oauth回调URI
	 */
	public static final int OAUTH2_INVALID_REDIRECTURI = 4001162;
	/**
	 * 无效的oauth的scope列表
	 */
	public static final int OAUTH2_INVALID_SCOPE = 4001163;
	/**
	 * 无效的oauth授权码
	 */
	public static final int OAUTH2_INVALID_CODE = 4001164;
	/**
	 * 无效的请求类型
	 */
	public static final int OAUTH2_INVALID_REQUEST_TYPE = 4001165;
	/**
	 * oauth2授权失败
	 */
	public static final int OAUTH2_AUTHORIZATION_FAILED = 4001166;

	/**
	 * 快照统计规则未知状态
	 */
	public static final int SNAPSHOT_STATISTICS_STATUS_UNKOWN = 4001167;
	/**
	 * oauth access token无效
	 */
	public static final int OAUTH2_ACCESS_TOKEN_INVALID = 4001168;
	/**
	 * twitter用户已经存在
	 */
	public static final int USER_TWITTER_OPEN_ID_IS_EXISTS = 4001169;
	/**
	 * facebook用户已经存在
	 */
	public static final int USER_FACEBOOK_OPEN_ID_IS_EXISTS = 4001170;
	/**
	 * oauth版本未知
	 */
	public static final int OAUTH_VERSION_UNKNOWN = 4001171;
	/**
	 * 导出任务未知状态
	 */
	public static final int EXPORT_TASK_TYPE_UNKOWN = 4001172;
	/**
	 * 导出任务不是处于已完成导出状态
	 */
	public static final int EXPORT_TASK_NOT_EXPORTED = 4001173;
	/**
	 * 导出任务已过期
	 */
	public static final int EXPORT_TASK_EXPIRED = 4001174;
	/**
	 * 未知的扩展属性
	 */
	public static final int EXTEND_PROPERTY_UNKNOWN = 4001175;
	/**
	 * 销售信息不存在
	 */
	public static final int DEALER_CLIENT_INFO_NOT_EXISTS = 4001176;
	/**
	 * 大客户用户名已存在
	 */
	public static final int HEAVY_BUYER_USERNAME_EXISTS = 4001177;
	/**
	 * 数据端点来源类型未知
	 */
	public static final int DATAPOINT_SOURCE_TYPE_UNKOWN = 4001178;
	/**
	 * 注册账号已存在
	 */
	public static final int REGISTER_ACCOUNT_EXISTS = 4001179;
	/**
	 * 逻辑类型
	 */
	public static final int LOGICAL_TYPE_UNKOWN = 4001180;
	/**
	 * 用户手机区号错误
	 */
	public static final int USER_PHONE_ZONE_ERROR = 4001181;
	/**
	 * 设备日志类型未知
	 */
	public static final int DEVICE_LOG_TYPE_UNKNOWN = 4001182;
	/**
	 * 数据端点不是应用类型
	 */
	public static final int DATAPOINT_SOURCE_NOT_APPLICATION = 4001183;
	/**
	 * 产品连接类型不是蓝牙类型
	 */
	public static final int PRODUCT_LINKTYPE_NOT_BLUETOOTH = 4001184;

	/**
	 * 第三方地址验证失败
	 */
	public static final int THIRD_URL_VALIDATE_ERROR = 4001185;
	/**
	 * 子设备能力的配置缺失
	 */
	public static final int SUB_DEVICE_CAPABILITY_CONFIG_MISSING = 4001186;
	/**
	 * 子设备网关的能力的配置项错误
	 */
	public static final int SUB_DEVICE_G_C_CONFIG_ERROR = 4001187;
	/**
	 * 网关能力类型未知
	 */
	public static final int GATEWAY_CAPABILITY_CONFIG_TYPE_UNKNOWN = 4001188;
	/**
	 * 耗材数据类型未知
	 */
	public static final int SUPLLIES_DATA_TYPE_UNKNOWN = 4001189;
	/**
	 * 设备授权的最大数量
	 */
	public static final int DEVICE_IMPORT_LIMIT = 4001190;
	/**
	 * 设备已经设置了home属性
	 */
	public static final int DEVICE_ALREADY_SET_HOMEID = 4001191;
	/**
	 * 企业别名已存在
	 */
	public static final int CORP_ALIAS_EXISTS = 4001192;

	/**
	 * 语音模板的数据端点可设置个数超过5个
	 */
	public static final int SPEECH_TEMPLATE_PRODUCT_LIMIT = 4001193;
	/**
	 * 一个数据端点的语音模板超过3个
	 */
	public static final int SPEECH_TEMPLATE_DATAPOINT_LIMIT = 4001194;

	/**
	 * 语音模板已经存在
	 */
	public static final int SPEECH_TEMPLATE_EXIST = 4001195;
	/**
	 * 语音模板不存在
	 */
	public static final int SPEECH_TEMPLATE_NOT_EXIST = 4001196;
	/**
	 * 语音模板不合法
	 */
	public static final int SPEECH_TEMPLATE_ILLEGAL = 4001197;
	/**
	 * home的房间名称重复
	 */
	public static final int HOME_ROOM_NAME_DUPLICATE = 4001198;
	/**
	 * home的房间zone名称重复
	 */
	public static final int HOME_ZONE_NAME_DUPLICATE = 4001199;
	/**
	 * home的房间数超出限制
	 */
	public static final int HOME_ROOM_SIZE_LIMIT = 4001200;
	/**
	 * home的zone数超出限制
	 */
	public static final int HOME_ZONE_SIZE_LIMIT = 4001201;
	/**
	 * 设备不能被解除订阅
	 */
	public static final int DEVICE_CAN_NOT_UNSUBSCRIBE = 4001202;
	/**
	 * home的设备不属于房间
	 */
	public static final int HOME_DEVICE_NOT_IN_ROOM = 4001203;
	/**
	 * 规则策略状态未知
	 */
	public static final int RULE_POLICY_STATUS_UNKNOWN = 4001204;
	/**
	 * 规则策略数据接收者未知
	 */
	public static final int RULE_POLICY_PUBLISHER_UNKNOWN = 4001205;
	/**
	 * 规则策略kafka主题未知
	 */
	public static final int RULE_POLICY_TOPIC_UNKNOWN = 4001206;
	/**
	 * 规则策略执行计划非法
	 */
	public static final int RULE_POLICY_EXECUTE_PLAN_UNVALID = 4001207;
	/**
	 * 规则策略INPUT_FIELD非法
	 */
	public static final int RULE_POLICY_INPUT_FIELD_UNVALID = 4001208;
	/**
	 * 规则策略数据接收者无效
	 */
	public static final int RULE_POLICY_PUBLISHER_UNVALID = 4001209;

	/**
	 * 拥有子组织
	 */
	public static final int ORGANIZATION_HAVE_SUB = 4001210;
	/**
	 * 组织类型未知
	 */
	public static final int ORGANIZATION_TYPE_UNKNOWN = 4001211;
	/**
	 * 组织类型不匹配
	 */
	public static final int ORGANIZATION_TYPE_NOT_MATCH = 4001212;
	/**
	 * 操作类型未知
	 */
	public static final int OPT_TYPE_UNKNOWN = 4001213;
	/**
	 * 角色类型未知
	 */
	public static final int ROLE_TYPE_UNKNOWN = 4001214;

	/**
	 * 模块类型未知
	 */
	public static final int MODULE_TYPE_UNKNOWN = 4001215;

	/**
	 * 分享消息不是等待状态
	 */
	public static final int SHARE_NOT_PENDING = 4001216;
	/**
	 * 系统内建的平台应用不能删除
	 */
	public static final int PLATFORM_BUILTIN_NOT_DELETE = 4001217;
	/**
	 * UI权限类型未知
	 */
	public static final int UI_PERMISSION_TYPE_UNKNOWN = 4001218;
	/**
	 * 组织下有成员
	 */
	public static final int ORGANIZATION_HAVE_MEMBER = 4001219;

	/**
	 * 功能模块类型未知
	 */
	public static final int FUNCTION_MOUDLE_TYPE_UNKNOWN = 4001220;

	/**
	 * 设备过滤器类型未知
	 */
	public static final int DEVICE_FILTER_TYPE_UNKNOWN = 4001221;
	/**
	 * 设备过滤器类型未知
	 */
	public static final int DEVICE_FILTER_QUERY_TYPE_UNKNOWN = 4001222;
	/**
	 * 设备过滤器字段域未知
	 */
	public static final int DEVICE_FILTER_DEMAIN_UNKNOWN = 4001223;
	/**
	 * 设备过滤器不允许删除
	 */
	public static final int DEVICE_FILTER_CANNOT_DELETE = 4001224;
	/**
	 * 经度不合法
	 */
	public static final int LONGITUDE_ILLEAGAL = 4001225;
	/**
	 * 纬度不合法
	 */
	public static final int LATITUDE_ILLEAGAL = 4001226;
	/**
	 * 工单过滤器类型未知
	 */
	public static final int WORK_ORDERS_FILTER_TYPE_UNKNOWN = 4001227;
	/**
	 * 工单过滤器字段域未知
	 */
	public static final int WORK_ORDERS_FILTER_DOMAIN_UNKNOWN = 4001228;
	/**
	 * 工单过滤器不允许删除
	 */
	public static final int WORK_ORDERS_FILTER_CANNOT_DELETE = 4001229;

	/**
	 * 设备收藏类型未知
	 */
	public static final int DEVICE_FILTER_MARK_TYPE_UNKNOWN = 4001230;
	/**
	 * 工单收藏类型未知
	 */
	public static final int WORK_ORDERS_FILTER_MARK_TYPE_UNKNOWN = 4001231;
	/**
	 * 大客户用户账号已经被停用
	 */
	public static final int HEAVY_BUYER_USER_DISABLED = 4001232;
	/**
	 * 工单配置类型未知
	 */
	public static final int WORK_ORDERS_CONFIG_TYPE_UNKNOWN = 4001233;
	/**
	 * 工单过滤器类型未知
	 */
	public static final int WORK_ORDERS_FILTER_QUERY_TYPE_UNKNOWN = 4001234;
	/**
	 * 经销商用户名已存在
	 */
	public static final int DEALER_USERNAME_EXISTS = 4001235;
	/**
	 * 经销商用户账号已经被停用
	 */
	public static final int DEALER_USER_DISABLED = 4001236;
	/**
	 * avs日志类型未知
	 */
	public static final int AVS_LOG_TYPE_UNKNOWN = 4001237;
	/**
	 * 工单已被关闭
	 */
	public static final int WORK_ORDERS_CLOSE = 4001238;
	/**
	 * 组织重复挂在网点下
	 */
	public static final int BRANCH_ORGANIZATION_DUPLICATE = 4001239;
	/**
	 * 不是审核中的订单不能删除
	 */
	public static final int ORDER_IN_REVIEW = 4001240;
	/**
	 * 订单状态非法
	 */
	public static final int ORDER_STATUS_INVALID = 4001241;
	/**
	 * 该设备不是网关设备
	 */
	public static final int DEVICE_IS_NOT_GATEWAY = 4001242;
	/**
	 * home成员已经存在
	 */
	public static final int HOME_MEMBER_EXISTS = 4001243;
	/**
	 * 经销商码已存在
	 */
	public static final int DEALER_CODE_EXISTS = 4001244;
	/**
	 * 产品品类已被产品使用，不允许删除
	 */
	public static final int PRODUCT_CATEGORY_EXISTS = 4001245;
	/**
	 * 定时任务类型未知
	 */
	public static final int TIMER_TASK_TYPE_UNKNOWN = 4001246;
	/**
	 * 定时任务动作类型未知
	 */
	public static final int TIMER_TASK_ACTION_TYPE_UNKNOWN = 4001247;
	/**
	 * 定时任务已执行
	 */
	public static final int TIMER_TASK_STATUS_EXECUTE = 4001248;
	/**
	 * 资金池异常
	 */
	public static final int POOL_AMOUNT = 4001249;
	/**
	 * 经销商别名已存在
	 */
	public static final int DEALER_ALIAS_EXIST = 4001250;
	/**
	 * 设备未扣款
	 */
	public static final int DEVICE_NOT_PAY = 4001251;
	/**
	 * 已经申请返利
	 */
	public static final int ALERDT_APPLEY_REBATE = 4001252;
	/**
	 * 数据端点名称已存在
	 */
	public static final int DATAPOINT_NAME_EXISTED = 4001253;
	/**
	 * 拥有子经销商
	 */
	public static final int DEALER_HAS_SUB_DEALER = 4001254;
	/**
	 * 上级经销上必须为null
	 */
	public static final int DEALER_UPPER_MUST_NULL = 4001255;
	/**
	 * 定时任务周期类型未知
	 */
	public static final int TIMER_TASK_UNIT_TYPE_UNKNOWN = 4001256;
	/**
	 * 定时任务星期类型未知
	 */
	public static final int TIMER_TASK_WEEKDAY_TYPE_UNKNOWN = 4001257;
	/**
	 * 产品品类名称已经存在
	 */
	public static final int PRODUCT_CATEGORY_NAME_EXISTS = 4001258;
	/**
	 * 产品品类可见范围未知
	 */
	public static final int PRODUCT_CATEGORY_VISIBILITY_UNKNOWN = 4001259;
	/**
	 * 产品品类扩展属性超过大小限制
	 */
	public static final int PRODUCT_CATEGORY_EXTEND_SIZE_LIMIT = 4001260;
	/**
	 * 产品品类超过大小限制
	 */
	public static final int PRODUCT_CATEGORY_SIZE_LIMIT = 4001261;
	/**
	 * 设备已经安装激活
	 */
	public static final int DEVICE_ALERDY_INSTALLED = 4001258;
	/**
	 * 设备已经删除
	 */
	public static final int DEVICE_IS_DELETED = 4001259;
	/**
	 * 用户已经删除
	 */
	public static final int USER_IS_DELETED = 4001260;
	/**
	 * 返利规则不存在
	 */
	public static final int REBATE_RULE_NOT_EXISTS = 4001262;
	/**
	 * 返利规则已经存在
	 */
	public static final int REBATE_RULE_ALERDY_EXISTS = 4001263;
	/**
	 * 产品库存不足
	 */
	public static final int INSTORE_NOT_ENOUGH = 4001264;
	/**
	 * 产品系列分类名重复
	 */
	public static final int SERIES_NAME_DUPLICATE = 4001265;
	/**
	 * ui可视化类型未知
	 */
	public static final int UI_VISIBLE_TYPE_UNKOWN = 4001266;
	/**
	 * 数据转发状态未知
	 */
	public static final int DDS_RULE_STATUS_UNKNOWN = 4001267;
	/**
	 * 产品导入出错
	 */
	public static final int IMPORT_PRODUCT_ERROR = 4001268;
	/**
	 * 智能互联命令没有关联到数据端点
	 */
	public static final int INTERCONNECT_NO_DATAPOINT = 4001269;
	/**
	 * 智能互联命令没有设置对应的指令值
	 */
	public static final int INTERCONNECT_NO_COMMAND_VALUE= 4001270;
	/**
	 * 不支持的智能互联类型
	 */
	public static final int INTERCONNECT_TYPE_NOT_SUPPORT = 4001271;
	/**
	 * 数据端点权限类型未知
	 */
	public static final int DATAPOINT_PERMISSION_TYPE_UNKNOWN = 4001272;
	/**
	 * 应用类型数据端点权限不足
	 */
	public static final int DATAPOINT_APPLICATION_PERMISSION_DENY = 4001273;
	/**
	 * 设备不全属于产品
	 */
	public static final int DEVICE_NOT_BEYONG_TO_PRODUCT = 4001274;
	/**
	 * 升级任务scope未知
	 */
	public static final int UPGRADE_TASK_SCOPE_UNKNOWN = 4001275;
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/

	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/**
	 * HTTP 403 下返回的错误码
	 */
	/**
	 * 禁止访问
	 */
	public static final int INVALID_ACCESS = 4031001;
	/**
	 * 禁止访问，需要Access-Token
	 */
	public static final int NEED_ACCESS_TOKEN = 4031002;
	/**
	 * 无效的Access-Token
	 */
	public static final int ACCESS_TOKEN_INVALID = 4031003;
	/**
	 * 需要企业的调用权限
	 */
	public static final int NEED_CORP_API = 4031004;
	/**
	 * 需要企业管理员权限
	 */
	public static final int NEED_CORP_ADMIN_MEMBER = 4031005;
	/**
	 * 需要数据操作权限
	 */
	public static final int NEED_DATA_PERMISSION = 4031006;
	/**
	 * 禁止访问私有数据
	 */
	public static final int INVALID_ACCESS_PRIVATE_DATA = 4031007;
	/**
	 * 分享已经被取消
	 */
	public static final int SHARE_CANCELED = 4031008;
	/**
	 * 分享已经接受
	 */
	public static final int SHARE_ACCEPTED = 4031009;
	/**
	 * 用户没有订阅设备，不能执行操作
	 */
	public static final int DEVICE_NOT_SUBSCRIBE = 4031010;
	/**
	 * 插件应用认证不通过
	 */
	public static final int APP_AUTH_INVALID = 4031011;
	/**
	 * 分享已经无效了
	 */
	public static final int SHARE_INVALID = 4031012;
	/**
	 * 设备不能被订阅
	 */
	public static final int DEVICE_CAN_NOT_BE_SUBSCRIBE = 4031013;
	/**
	 * 需要home的管理员及以上的权限
	 */
	public static final int NEED_HOME_ADMIN = 4031014;
	/**
	 * 需要home的创建者权限
	 */
	public static final int NEED_HOME_CREATOR = 4031015;
	/**
	 * home member到期
	 */
	public static final int HOME_MEMBER_EXPIRE = 4031016;
	/**
	 * 需要home的超级管理员权限
	 */
	public static final int NEED_HOME_SUPERADMIN = 4031017;
	/**
	 * 身份提供商签名无效
	 */
	public static final int PROVIDER_SIGN_UNVALID = 4031018;
	/**
	 * Oauth的token没有权限调用接口
	 */
	public static final int OAUTH_HAVE_NO_PERMISSION = 4031019;
	/**
	 * 需要大客户组织用户管理员权限
	 */
	public static final int NEED_HEAVY_BUYER_ADMIN_ROLE_USER = 4031020;
	/**
	 * Access-Token过期
	 */
	public static final int ACCESS_TOKEN_EXPIRED = 4031021;
	/**
	 * Access-Token被刷新
	 */
	public static final int ACCESS_TOKEN_REFRESH = 4031022;
	/**
	 * 企业成员的权限不够
	 */
	public static final int INVALID_CORP_MEMBER_PERMISSION = 4031023;
	/**
	 * 权限不足, 包括成员大客户经销商
	 */
	public static final int PERMISSION_DENIED = 4031024;
	/**
	 * 操作对象是企业管理员
	 */
	public static final int INVALID_CORP_ADMIN_PERMISSION = 4031025;
	/**
	 * 操作对象是企业创建者
	 */
	public static final int INVALID_CORP_CREATOR_PERMISSION = 4031026;

	/**
	 * 无权限访问该品类或该品类下的产品
	 */
	public static final int INVALID_PRODUCT_CATEGORY_PERMISSION = 4031027;

	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/**
	 * HTTP 404 下返回的错误码
	 */
	/**
	 * URL找不到
	 */
	public static final int URL_NOT_FOUND = 4041001;
	/**
	 * 企业成员帐号不存在
	 */
	public static final int MEMBER_ACCOUNT_NO_EXISTS = 4041002;
	/**
	 * 企业成员不存在
	 */
	public static final int MEMBER_NOT_EXISTS = 4041003;
	/**
	 * 激活的成员邮箱不存在
	 */
	public static final int MEMBER_INVITE_EMAIL_NOT_EXISTS = 4041004;
	/**
	 * 产品信息不存在
	 */
	public static final int PRODUCT_NOT_EXISTS = 4041005;
	/**
	 * 产品固件不存在
	 */
	public static final int FIRMWARE_NOT_EXISTS = 4041006;
	/**
	 * 数据端点不存在
	 */
	public static final int DATAPOINT_NOT_EXISTS = 4041007;
	/**
	 * 设备不存在
	 */
	public static final int DEVICE_NOT_EXISTS = 4041008;
	/**
	 * 设备扩展属性不存在
	 */
	public static final int DEVICE_PROPERTY_NOT_EXISTS = 4041009;
	/**
	 * 企业不存在
	 */
	public static final int CORP_NOT_EXISTS = 4041010;
	/**
	 * 用户不存在
	 */
	public static final int USER_NOT_EXISTS = 4041011;
	/**
	 * 用户扩展属性不存在
	 */
	public static final int USER_PROPERTY_NOT_EXISTS = 4041012;
	/**
	 * 升级任务不存在
	 */
	public static final int UPGRADE_TASK_NOT_EXISTS = 4041013;
	/**
	 * 第三方身份授权不存在
	 */
	public static final int EMPOWER_NOT_EXISTS = 4041014;
	/**
	 * 告警规则不存在
	 */
	public static final int ALARM_RULE_NOT_EXISTS = 4041015;
	/**
	 * 数据表不存在
	 */
	public static final int DATA_TABLE_NOT_EXISTS = 4041016;
	/**
	 * 数据不存在
	 */
	public static final int DATA_NOT_EXISTS = 4041017;
	/**
	 * 分享资源不存在
	 */
	public static final int SHARE_NOT_EXISTS = 4041018;
	/**
	 * 企业邮箱不存在
	 */
	public static final int CORP_EMAIL_NOT_EXISTS = 4041019;
	/**
	 * APP不存在
	 */
	public static final int APP_NOT_EXISTS = 4041020;
	/**
	 * 产品转发规则不存在
	 */
	public static final int DDSRULE_NOT_EXISTS = 4041021;
	/**
	 * 邮件模板不存在
	 */
	public static final int EMAIL_TEMPLATE_NOT_EXISTS = 4041022;
	/**
	 * 第三方用户验证URL不存在
	 */
	public static final int USER_AUTH_THIRD_URL_NOT_EXISTS = 4041023;

	/**
	 * accesskey不存在
	 */
	public static final int ACCESSKEY_NOT_EXISTS = 4041024;

	/**
	 * 微信授权配置不存在
	 */
	public static final int WECHAT_AUTH_CONFIG_NOT_EXISTS = 4041025;

	/**
	 * 快照规则不存在
	 */
	public static final int SNAPSHOTRULE_NOT_EXISTS = 4041026;

	/**
	 * splashWnd图片不存在
	 */
	public static final int APP_SPLASHWND_NOT_EXISTS = 4041027;
	/**
	 * splashWnd图片已经存在
	 */
	public static final int APP_SPLASHWND_ALREADY_EXISTS = 4041028;
	/**
	 * 成员角色不存在
	 */
	public static final int MEMBER_ROLE_NOT_EXISTS = 4041029;
	/**
	 * 经销商不存在
	 */
	public static final int DEALER_NOT_EXISTS = 4041030;
	/**
	 * 大客户不存在
	 */
	public static final int HEAVY_BUYER_NOT_EXISTS = 4041031;
	/**
	 * 推送任务不存在
	 */
	public static final int BROADCAST_TASK_NOT_EXISTS = 4041032;
	/**
	 * 超过某个产品的设备配额
	 */
	public static final int OVER_PRODUCT_DEVICE_LIMIT = 4041033;
	/**
	 * 导入授权记录不存在
	 */
	public static final int RECORD_NOT_EXISTS = 4041034;
	/**
	 * home的成员不存在
	 */
	public static final int HOME_MEMBER_NOT_EXISTS = 4041035;
	/**
	 * home不存在
	 */
	public static final int HOME_NOT_EXISTS = 4041036;
	/**
	 * 应用提供商不存在
	 */
	public static final int PROVIDER_CONFIG_NOT_EXISTS = 4041037;
	/**
	 * 推送消息在推送中或者已推送,不允许删除
	 */
	public static final int BROADCAST_TASK_PUSH = 4041038;
	/**
	 * 经销商没有拥有设备
	 */
	public static final int DEALER_NOT_OWN_DEVICE = 4041039;
	/**
	 * snapshot statistic rule不存在
	 */
	public static final int SNAPSHOT_STATISTIC_RULE_NOT_EXISTS = 4041040;
	/**
	 * 授权记录不存在
	 */
	public static final int DEVICE_QUOTA_NOT_EXISTS = 4041041;
	/**
	 * home邀请记录不正确
	 */
	public static final int HOME_INVITATION_NOT_EXISTS = 4041042;
	/**
	 * 导出任务不存在
	 */
	public static final int EXPORT_TASK_NOT_EXISTS = 4041043;
	/**
	 * 虚拟设备不存在
	 */
	public static final int VIRTUAL_DEVICE_NOT_EXISTS = 4041044;
	/**
	 * 大客户用户名不存在
	 */
	public static final int HEAVY_BUYER_USER_NOT_EXISTS = 4041045;
	/**
	 * 指定的组织不存在
	 */
	public static final int HEAVY_BUYER_ORGANIZATION_NOT_EXISTS = 4041046;
	/**
	 * 用户不是邀请者
	 */
	public static final int USER_IS_NOT_HOME_INVITOR = 4041047;
	/**
	 * 产品拓展属性不存在
	 */
	public static final int PRODUCT_PROPERTY_NOT_EXISTS = 4041048;
	/**
	 * 产品不支持子设备
	 */
	public static final int PRODUCT_NOT_SUPPORT_SUB_DEVICES = 4041049;
	/**
	 * 上级经销商代码不存在
	 */
	public static final int UPPER_DEALER_CODE_NOT_EXISTS = 4041050;
	/**
	 * 证书不存在
	 */
	public static final int CERTIFICATE_NOT_EXISTS = 4041051;
	/**
	 * home的房间不存在
	 */
	public static final int HOME_ROOME_NOT_EXISTS = 4041052;
	/**
	 * home的zone不存在
	 */
	public static final int HOME_ZONE_NOT_EXISTS = 4041053;
	/**
	 * 微信图片获取失败
	 */
	public static final int XFILE_WEIXIN_FAILED = 4041054;
	/**
	 * 规则策略不存在
	 */
	public static final int RULE_POLICY_NOT_EXISTS = 4041055;
	/**
	 * 工单不存在
	 */
	public static final int WORK_ORDERS_NOT_EXISTS = 4041056;
	/**
	 * 工单类型不存在
	 */
	public static final int WORK_ORDERS_TYPE_NOT_EXISTS = 4041057;
	/**
	 * 网点不存在
	 */
	public static final int WARRANTY_BRANCH_NOT_EXISTS = 4041058;
	/**
	 * 组织不存在
	 */
	public static final int ORGANIZATION_NOT_EXIST = 4041059;
	/**
	 * 模块不存在
	 */
	public static final int MODULE_NOT_EXIST = 4041060;
	/**
	 * 角色不存在
	 */
	public static final int ROLE_NOT_EXIST = 4041061;

	/**
	 * 模块模版不存在
	 */
	public static final int MODULE_TEMPLATE_NOT_EXIST = 4041062;
	/**
	 * 权限不存在
	 */
	public static final int PERMISSION_NOT_EXIST = 4041063;
	/**
	 * 工单过滤器不存在
	 */
	public static final int WORK_ORDERS_FILTER_NOT_EXIST = 4041064;
	/**
	 * 工单配置不存在
	 */
	public static final int WORK_ORDERS_CONFIG_NOT_EXIST = 4041065;
	/**
	 * 经销商用户不存在
	 */
	public static final int DEALER_USER_NOT_EXISTS = 4041066;
	/**
	 * 请求的经销商ID无效
	 */
	public static final int DEALER_ID_INVALID = 4041067;
	/**
	 * 产测记录不存在
	 */
	public static final int PRODUCTION_TEST_NOT_EXISTS = 4041068;
	/**
	 * 数据已经存在
	 */
	public static final int DATA_ALREADY_EXIST = 4041069;
	/**
	 * 设备过滤器不存在
	 */
	public static final int DEVICE_FILTER_NOT_EXIST = 4041070;
	/**
	 * 维护公告不存在
	 */
	public static final int ANNOUNCEMENT_NOT_EXISTS = 4041071;
	/**
	 * 定时消息不存在
	 */
	public static final int TIMER_TASK_NOT_EXISTS = 4041072;
	/**
	 * 产品品类不存在
	 */
	public static final int PRODUCT_CATEGORY_NOT_EXISTS = 4041073;
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/******************************* 错误码分隔符 ********************************/
	/**
	 * HTTP 503下返回的错误码
	 */
	/**
	 * 服务端发生异常
	 */
	public static final int SERVICE_EXCEPTION = 5031001;

}
