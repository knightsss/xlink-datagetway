package cn.xlink.metadata;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes
{
	public static void define(Configuration config, RestExpress server)
	{
		server.uri("/v2/data_platform/druid_metadata/{uuid}.{format}", config.getDruidMetadataController())
				.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
				.name(Constants.Routes.SINGLE_DRUID_METADATA);

		server.uri("/v2/data_platform/druid_metadata.{format}", config.getDruidMetadataController())
				.action("readAll", HttpMethod.GET)
				.method(HttpMethod.POST)
				.name(Constants.Routes.COLLECTION_DRUID_METADATA);

		server.uri("/v2/data_platform/dataset_metadata/{uuid}.{format}", config.getDatasetMetadataController())
				.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
				.name(Constants.Routes.SINGLE_DATASET_METADATA);

		server.uri("/v2/data_platform/dataset_metadata.{format}", config.getDatasetMetadataController())
				.action("readAll", HttpMethod.GET)
				.method(HttpMethod.POST)
				.name(Constants.Routes.COLLECTION_DATASET_METADATA);

		// or REGEX matching routes...
		// server.regex("/some.regex", config.getRouteController());
	}
}
