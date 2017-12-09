package cn.xlink.data.query;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.xlink.data.core.proxy.HttpProxy;
import cn.xlink.data.core.proxy.JdbcProxy;
import cn.xlink.data.core.proxy.Proxy;
import cn.xlink.data.core.service.AuthCheck;
import cn.xlink.data.core.service.DatapointField;
import cn.xlink.data.core.service.Service;
import cn.xlink.data.query.cache.DatasetCache;
import cn.xlink.data.query.controller.*;

import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadataRepository;
import cn.xlink.data.metadata.jdbcMetadata.JdbcMetadataService;

import cn.xlink.data.metadata.pageMetadata.PageMetadataRepository;
import cn.xlink.data.metadata.pageMetadata.PageMetadataService;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataRepository;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
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

	private PageController pageController;
	private PageMetadataService pageMetadataService;

	private JdbcController jdbcController;
	private JdbcMetadataService jdbcMetadataService;

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

		DatasetCache.getInstance().init(datasetMetadataService);

		/*page*/
		PageMetadataRepository pageMetadataRepository = new PageMetadataRepository(mongo.getClient(), mongo.getDbName());
		PageMetadataService pageMetadataService = new PageMetadataService(pageMetadataRepository);
		this.pageMetadataService = pageMetadataService;

		/*jdbc*/
		JdbcMetadataRepository jdbcMetadataRepository = new JdbcMetadataRepository(mongo.getClient(), mongo.getDbName());
		JdbcMetadataService jdbcMetadataService = new JdbcMetadataService(jdbcMetadataRepository);
		this.jdbcMetadataService = jdbcMetadataService;


		commonController = new CommonController(p, this.proxyMap, this.serviceMap);

		cardController = new CardController(p, this.proxyMap, this.serviceMap);
		tableController = new TableController(p, this.proxyMap, this.serviceMap);
		chartController = new ChartController(p, this.proxyMap, this.serviceMap);

		datasetController = new DatasetController(p, this.proxyMap, this.serviceMap, datasetMetadataService);
		pageController = new PageController(p, this.proxyMap, this.serviceMap, pageMetadataService);

		/*jdbc*/
		jdbcController = new JdbcController(p, this.proxyMap, this.serviceMap, jdbcMetadataService);
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

	public PageController getPageController() {
		return pageController;
	}

	public JdbcController getJdbcController() {
		return jdbcController;
	}
}
