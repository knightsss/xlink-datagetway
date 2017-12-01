package cn.xlink.cassandra.start.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.xlink.cassandra.service.check.ApiAuthCheckService;
import org.apache.log4j.Logger;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration extends Environment {
	private final Logger logger = Logger.getLogger(Configuration.class);

	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";
	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String LOG4J_PATH_PROPERTY = "log4j.path";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";
	private static final String API_VALID_URL = "api.valid.url";
	private static final String API_VALID_TOKEN = "api.valid.token";
	private static final String DEVICE_CHECK_URL = "device.check.url";
	private static final String PRODUCT_CHECK_URL = "product.check.url";
	private static final String USER_CHECK_URL = "user.check.url";
	private static final String TIMERTASK_CHECK_URL = "timertask.check.url";

	private String log4jPath;
	private int port;
	private String baseUrl;
	private static String deviceCheckUrl;
	private static String productCheckUrl;
	private static String userCheckUrl;
	private static String timerTaskCheckUrl;
	private int executorThreadPoolSize;
	private MetricsConfig metricsSettings;
	private static ApiAuthCheckService authCheck;

	/**
	 * api token
	 */
	private static final Map<String, String> tokenMap = new HashMap<>();

	@Override
	protected void fillValues(Properties p) {
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + port);
		this.executorThreadPoolSize = Integer
				.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		this.metricsSettings = new MetricsConfig(p);
		deviceCheckUrl = p.getProperty(DEVICE_CHECK_URL);
		productCheckUrl = p.getProperty(PRODUCT_CHECK_URL);
		userCheckUrl = p.getProperty(USER_CHECK_URL);
		timerTaskCheckUrl = p.getProperty(TIMERTASK_CHECK_URL);

		log4jPath = p.getProperty(LOG4J_PATH_PROPERTY);
		Log4jConfig.init(log4jPath);

		logger.info("init auth check server...");
		String cropIdUrl = p.getProperty(API_VALID_URL);
		tokenMap.put("Access-Token", p.getProperty(API_VALID_TOKEN));
		authCheck = new ApiAuthCheckService(cropIdUrl, tokenMap);

		logger.info("init cassandra...");
		new XlinkCassandraConfig(p);
	}

	public int getPort() {
		return port;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public static String getDeviceCheckUrl() {
		return deviceCheckUrl;
	}

	public static String getProductCheckUrl() {
		return productCheckUrl;
	}

	public static String getUserCheckUrl() {
		return userCheckUrl;
	}

	public int getExecutorThreadPoolSize() {
		return executorThreadPoolSize;
	}

	public MetricsConfig getMetricsConfig() {
		return metricsSettings;
	}

	public static Map<String, String> getTokenMap() {
		return tokenMap;
	}

	public static String getTimerTaskCheckUrl() {
		return timerTaskCheckUrl;
	}

	public static ApiAuthCheckService getAuthCheck() {
		return authCheck;
	}
}
