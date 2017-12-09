package cn.xlink.data.query;

public class Constants
{
	/**
	 * These define the URL parmaeters used in the route definition strings (e.g. '{userId}').
	 */
	public class Url
	{
		//TODO: Your URL parameter names here...
		public static final String SAMPLE_ID = "sampleId";
		public static final String PAGE_ID = "page_id";
	}

	/**
	 * These define the route names used in naming each route definitions.  These names are used
	 * to retrieve URL patterns within the controllers by name to create links in responses.
	 */
	public class Routes
	{
		//TODO: Your Route names here...
		public static final String COMMON_QUERY = "common.query";
		public static final String CARD_RESULT = "card.result";
		public static final String CARD_YEAR_ON_YEAR = "card.year_on_year";
		public static final String CARD_MONTH_ON_MONTH = "card.month_on_month";

		public static final String TABLE_LIST = "table.list";

		public static final String CHART_TENDENCY = "chart.tendency";
		public static final String CHART_POSITION = "chart.position";
		public static final String CHART_TOP_N = "chart.top_n";
		public static final String CHART_RATIO = "chart.ratio";

		public static final String DATASET_LIST = "dataset.list";
		public static final String DATASET_FIELDS = "dataset.fields";

		public static final String PAGE_LIST = "page.list";
		public static final String PAGE_INST = "page.inst";
		public static final String PAGE_READ_ROUTE = "page.read.route";

		public static final String JDBC_LIST = "jdbc.list";
		public static final String JDBC_INST = "jdbc.inst";
		public static final String JDBC_READ_ROUTE = "jdbc.read.route";

		public static final String OFFLINE_QUERY = "offline.query";
	}
}
