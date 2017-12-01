package cn.xlink.metadata;

import java.util.Map;

import cn.xlink.metadata.datasetMetadata.DatasetMetadata;
import cn.xlink.metadata.druidMetadata.DruidMetadata;
import org.restexpress.RestExpress;
import org.restexpress.common.exception.ConfigurationException;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

public abstract class Relationships
{
	private static Map<String, String> ROUTES;

	public static void define(RestExpress server)
	{
		ROUTES = server.getRouteUrlsByName();

		HyperExpress.relationships()
		.forCollectionOf(DruidMetadata.class)
			.rel(RelTypes.SELF, href(Constants.Routes.COLLECTION_DRUID_METADATA))
				.withQuery("limit={limit}")
				.withQuery("offset={offset}")
			.rel(RelTypes.NEXT, href(Constants.Routes.COLLECTION_DRUID_METADATA) + "?offset={nextOffset}")
				.withQuery("limit={limit}")
				.optional()
			.rel(RelTypes.PREV, href(Constants.Routes.COLLECTION_DRUID_METADATA) + "?offset={prevOffset}")
				.withQuery("limit={limit}")
				.optional()

		.forClass(DruidMetadata.class)
			.rel(RelTypes.SELF, href(Constants.Routes.SINGLE_DRUID_METADATA))
			.rel(RelTypes.UP, href(Constants.Routes.COLLECTION_DRUID_METADATA))

		.forCollectionOf(DatasetMetadata.class)
			.rel(RelTypes.SELF, href(Constants.Routes.COLLECTION_DATASET_METADATA))
				.withQuery("limit={limit}")
				.withQuery("offset={offset}")
			.rel(RelTypes.NEXT, href(Constants.Routes.COLLECTION_DATASET_METADATA) + "?offset={nextOffset}")
				.withQuery("limit={limit}")
				.optional()
			.rel(RelTypes.PREV, href(Constants.Routes.COLLECTION_DATASET_METADATA) + "?offset={prevOffset}")
				.withQuery("limit={limit}")
				.optional()

		.forClass(DatasetMetadata.class)
			.rel(RelTypes.SELF, href(Constants.Routes.SINGLE_DATASET_METADATA))
			.rel(RelTypes.UP, href(Constants.Routes.COLLECTION_DATASET_METADATA));
	}

	private static String href(String name)
	{
		String href = ROUTES.get(name);
		if (href == null) throw new ConfigurationException("Route name not found: " + name);
		return href;
	}
}
