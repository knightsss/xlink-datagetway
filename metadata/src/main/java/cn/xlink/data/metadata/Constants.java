package cn.xlink.data.metadata;

public class Constants
{
	/**
	 * These define the URL parmaeters used in the route definition strings (e.g. '{userId}').
	 */
	public class Url
	{
		//TODO: Your URL parameter names here...
		public static final String SAMPLE_ID = "uuid";
		public static final String PAGE_ID = "page_id";
		public static final String JDBC_ID = "jdbc_id";
	}

	/**
	 * These define the route names used in naming each route definitions.  These names are used
	 * to retrieve URL patterns within the controllers by name to create links in responses.
	 */
	public class Routes
	{
		//TODO: Your Route names here...
		public static final String SINGLE_DRUID_METADATA = "single.druid.metadata";
		public static final String COLLECTION_DRUID_METADATA = "collection.druid.metadata";
		public static final String SINGLE_DATASET_METADATA = "single.dataset.metadata";
		public static final String COLLECTION_DATASET_METADATA = "collection.dataset.metadata";
	}
}
