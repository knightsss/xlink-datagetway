package cn.xlink.data.metadata;

import java.util.HashMap;
import java.util.Properties;

import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataController;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataRepository;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.data.metadata.druidMetadata.DruidMetadataService;
import cn.xlink.data.core.service.CuratorClient;
import cn.xlink.data.metadata.druidMetadata.DruidMetadataController;
import cn.xlink.data.metadata.druidMetadata.DruidMetadataRepository;
import cn.xlink.data.core.service.AuthCheck;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";
	private static final String DEFAULT_ZOOKEEPER_CONNECT = "127.0.0.1:2182";
	private static final String DEFAULT_ZOOKEEPER_RETRY_TIMES = "10";
	private static final String DEFAULT_ZOOKEEPER_RETRY_GAP = "5000";
	private static final String DEFAULT_ZOOKEEPER_SERVICE_NAMESPACE = "/tranquility/metadata/query";

	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";
	private static final String ZOOKEEPER_CONNECT = "zookeeper.connect";
	private static final String ZOOKEEPER_RETRY_TIMES = "zookeeper.retry.times";
	private static final String ZOOKEEPER_RETRY_GAP = "zookeeper.retry.gap";
	private static final String ZOOKEEPER_SERVICE_NAMESPACE = "zookeeper.service.namespace";
	private static final String API_VALID_URL = "api.valid.url";
	private static final String API_VALID_TOKEN = "api.valid.token";

	private int port;
	private String baseUrl;
	private int executorThreadPoolSize;
	private MetricsConfig metricsSettings;
	private String zkConnect;
	private int zkRetryTimes;
	private int zkRetryGap;
	private String zkServiceNamespace;

	private DruidMetadataController druidMetadataController;
	private DatasetMetadataController datasetMetadataController;
	private CuratorClient curator;
	private AuthCheck authCheck;

	@Override
	protected void fillValues(Properties p)
	{
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		this.metricsSettings = new MetricsConfig(p);
		this.zkConnect = p.getProperty(ZOOKEEPER_CONNECT, DEFAULT_ZOOKEEPER_CONNECT);
		this.zkRetryTimes = Integer.parseInt(p.getProperty(ZOOKEEPER_RETRY_TIMES, DEFAULT_ZOOKEEPER_RETRY_TIMES));
		this.zkRetryGap = Integer.parseInt(p.getProperty(ZOOKEEPER_RETRY_GAP, DEFAULT_ZOOKEEPER_RETRY_GAP));
		this.zkServiceNamespace = p.getProperty(ZOOKEEPER_SERVICE_NAMESPACE, DEFAULT_ZOOKEEPER_SERVICE_NAMESPACE);

		// init zookeeper
		this.curator = new CuratorClient(this.zkConnect, this.zkServiceNamespace, this.zkRetryTimes, this.zkRetryGap);

		// init auth check
		String cropIdUrl = p.getProperty(API_VALID_URL);
		java.util.Map<String, String> tokenMap = new HashMap<String, String>();
		tokenMap.put("Access-Token", p.getProperty(API_VALID_TOKEN));
		this.authCheck = new AuthCheck(cropIdUrl, tokenMap);

		MongoConfig mongo = new MongoConfig(p);
		initialize(mongo);
	}

	private void initialize(MongoConfig mongo)
	{
		DruidMetadataRepository druidMetadataRepository = new DruidMetadataRepository(mongo.getClient(), mongo.getDbName());
		DruidMetadataService druidMetadataService = new DruidMetadataService(druidMetadataRepository);
		druidMetadataController = new DruidMetadataController(druidMetadataService, curator, authCheck);

		DatasetMetadataRepository datasetMetadataRepository = new DatasetMetadataRepository(mongo.getClient(), mongo.getDbName());
		DatasetMetadataService datasetMetadataService = new DatasetMetadataService(datasetMetadataRepository);
		datasetMetadataController = new DatasetMetadataController(datasetMetadataService, authCheck);
	}

	public int getPort()
	{
		return port;
	}
	
	public String getBaseUrl()
	{
		return baseUrl;
	}
	
	public int getExecutorThreadPoolSize()
	{
		return executorThreadPoolSize;
	}

	public MetricsConfig getMetricsConfig()
    {
	    return metricsSettings;
    }

	public DruidMetadataController getDruidMetadataController() {
		return druidMetadataController;
	}

	public DatasetMetadataController getDatasetMetadataController() {
		return datasetMetadataController;
	}
}
