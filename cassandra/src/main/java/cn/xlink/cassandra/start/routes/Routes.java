package cn.xlink.cassandra.start.routes;

import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.db.base.BaseEntity;
import cn.xlink.cassandra.rest.*;
import cn.xlink.cassandra.rest.base.BaseController;
import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Routes {
	protected Routes() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * post方法
	 */
	private static final String XLINK_POST = "xlinkPost";
	/**
	 * get方法
	 */
	private static final String XLINK_GET = "xlinkGet";
	/**
	 * delete方法
	 */
	private static final String XLINK_DELETE = "xlinkDelete";
	/**
	 * put方法
	 */
	private static final String XLINK_PUT = "xlinkPut";

	private static Map<String, urlMapping> urlMap = new ConcurrentHashMap<>();

	private static void init() throws Exception {
		// 用户认证
		register(REST_URL.USER_AUTH, UserAuthController.class, RoleType.Corp);
		// 用户画像
		register(REST_URL.USER_FIGURE, UserFigureController.class, RoleType.Corp);
		// 用户快照
		register(REST_URL.USER_INFO_SNAPSHOT, UserInfoSnapshotController.class, RoleType.Corp);
		// 设备快照
		register(REST_URL.DEVICE_INFO_SNAPSHOT, DeviceInfoSnapshotController.class, RoleType.Corp);
		// 设备激活
		register(REST_URL.DEVICE_ACTIVE, DeviceActiveController.class, RoleType.Corp);
		// 设备上线
		register(REST_URL.DEVICE_ONLINE, DeviceOnlineController.class, RoleType.Corp);
		// 设备下线
		register(REST_URL.DEVICE_OFFLINE, DeviceOfflineController.class, RoleType.Corp);
		// 远程控制日志
		register(REST_URL.REMOTE_CONTROL_LOG, RemoteControlLogController.class, RoleType.Corp);
		// avs日志
		register(REST_URL.AVS_LOG, AvsLogController.class, RoleType.Corp);
		// 定时任务执行日志
		register(REST_URL.TIMER_TASK_LOG, TimerTaskLogController.class, RoleType.Corp, RoleType.User);

	}

	private static void register(String url, Class<? extends BaseController<? extends BaseEntity>> clz,
			RoleType... roles) throws Exception {
		Set<RoleType> set = new HashSet<>();
		if (roles == null || roles.length == 0) {
			for (RoleType type : RoleType.values()) {
				set.add(type);
			}
		} else {
			for (RoleType role : roles) {
				set.add(role);
			}
		}
		urlMap.put(url, new urlMapping(clz, set));
	}

	public static void init(RestExpress server) {
		try {
			init();
			String url;
			Class<? extends BaseController<? extends BaseEntity>> clz;
			Set<RoleType> role;
			for (Map.Entry<String, urlMapping> entry : urlMap.entrySet()) {
				url = entry.getKey();
				clz = entry.getValue().getController();
				role = entry.getValue().getRoleTypes();
				server.uri(url, clz.getConstructor(Set.class).newInstance(role)).action(XLINK_POST, HttpMethod.POST)
						.action(XLINK_GET, HttpMethod.GET).action(XLINK_DELETE, HttpMethod.DELETE)
						.action(XLINK_PUT, HttpMethod.PUT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/**
 * 映射实体
 */
class urlMapping {
	private Class<? extends BaseController<? extends BaseEntity>> controller;
	private Set<RoleType> roleTypes;

	public urlMapping(Class<? extends BaseController<? extends BaseEntity>> controller, Set<RoleType> roleTypes) {
		this.controller = controller;
		this.roleTypes = roleTypes;
	}

	public Class<? extends BaseController<? extends BaseEntity>> getController() {
		return controller;
	}

	public Set<RoleType> getRoleTypes() {
		return roleTypes;
	}

}
