package cn.xlink.query;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.xlink.metadata.datasetMetadata.DatasetMetadataRepository;
import cn.xlink.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.query.controller.*;
import cn.xlink.query.proxy.HttpProxy;
import cn.xlink.query.proxy.JdbcProxy;
import cn.xlink.query.proxy.Proxy;
import cn.xlink.query.service.AuthCheck;
import cn.xlink.query.service.DatapointField;
import cn.xlink.query.service.Service;
import com.strategicgains.repoexpress.mongodb.MongoConfig;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";
	private static final String DRUID_BROKER_HOST = "druid.broker.host";
	private static final String PRESTO_JDBC_URI = "presto.jdbc.uri";
	private static final String XLINK_AUTH_CHECK_HOST = "xlink.auth.check.host";
	private static final String XLINK_AUTH_CHECK_TOKEN = "xlink.auth.check.token";

	private int port;
	private String baseUrl;
	private int executorThreadPoolSize;

	private CommonController commonController;
	private CardController cardController;
	private TableController tableController;
	private ChartController chartController;
	private DatasetController datasetController;
	private DatasetMetadataService datasetMetadataService;
	private Map<String, Proxy> proxyMap;
	private Map<String, Service> serviceMap;
	private Properties properties;

	@Override
	protected void fillValues(Properties p)
	{
		this.properties = p;
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		Proxy druidProxy = new HttpProxy(p.getProperty(DRUID_BROKER_HOST), null);
		Proxy prestoProxy = new JdbcProxy(p.getProperty(PRESTO_JDBC_URI));

		this.proxyMap = new HashMap<>();
		this.proxyMap.put("druid", druidProxy);
		this.proxyMap.put("presto", prestoProxy);

		Service authCheck = new AuthCheck(p.getProperty(XLINK_AUTH_CHECK_HOST),
				Collections.singletonMap("Access-Token", p.getProperty(XLINK_AUTH_CHECK_TOKEN)));
		Service datapointField = new DatapointField("", null);

		this.serviceMap = new HashMap<>();
		this.serviceMap.put("auth", authCheck);
		this.serviceMap.put("datapoint", datapointField);

		MongoConfig mongo = new MongoConfig(p);
		initialize(mongo, p);
	}

	private void initialize(MongoConfig mongo, Properties p)
	{
		DatasetMetadataRepository datasetMetadataRepository = new DatasetMetadataRepository(mongo.getClient(), mongo.getDbName());
		DatasetMetadataService datasetMetadataService = new DatasetMetadataService(datasetMetadataRepository);
		this.datasetMetadataService = datasetMetadataService;
		commonController = new CommonController(p, this.proxyMap, this.serviceMap, datasetMetadataService);

		cardController = new CardController(p, this.proxyMap, this.serviceMap, datasetMetadataService);
		tableController = new TableController(p, this.proxyMap, this.serviceMap, datasetMetadataService);
		chartController = new ChartController(p, this.proxyMap, this.serviceMap, datasetMetadataService);

		datasetController = new DatasetController(p, this.proxyMap, this.serviceMap, datasetMetadataService);
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

	public CommonController getCommonController() {
		return commonController;
	}

	public Map<String, Proxy> getProxyMap() {
		return proxyMap;
	}

	public Map<String, Service> getServiceMap() {
		return serviceMap;
	}

	public Properties getProperties() {
		return properties;
	}

	public DatasetMetadataService getDatasetMetadataService() {
		return datasetMetadataService;
	}

	public CardController getCardController()
	{
		return cardController;
	}

	public TableController getTableController()
	{
		return tableController;
	}

	public ChartController getChartController() {
		return chartController;
	}

	public DatasetController getDatasetController() {
		return datasetController;
	}
}
