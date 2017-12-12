package cn.xlink.data.query;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes
{
	public static void define(Configuration config, RestExpress server)
    {
		//TODO: Your routes here...
// or...
//		server.regex("/some.regex", config.getRouteController());

		// Common routes
		server.uri("/v2/data_platform/common/query.{format}", config.getCommonController())
				.action("query", HttpMethod.POST)
				.name(Constants.Routes.COMMON_QUERY);

		// Card routes
		server.uri("/v2/data_platform/card/result.{format}", config.getCardController())
				.action("result", HttpMethod.POST)
				.name(Constants.Routes.CARD_RESULT);

		server.uri("/v2/data_platform/card/year_on_year.{format}", config.getCardController())
				.action("yearOnYear", HttpMethod.POST)
				.name(Constants.Routes.CARD_YEAR_ON_YEAR);

		server.uri("/v2/data_platform/card/month_on_month.{format}", config.getCardController())
				.action("monthOnMonth", HttpMethod.POST)
				.name(Constants.Routes.CARD_MONTH_ON_MONTH);

		// Table routes
		server.uri("/v2/data_platform/table/list.{format}", config.getTableController())
				.action("list", HttpMethod.POST)
				.name(Constants.Routes.TABLE_LIST);

		// Chart routes
		server.uri("/v2/data_platform/chart/tendency.{format}", config.getChartController())
				.action("tendency", HttpMethod.POST)
				.name(Constants.Routes.CHART_TENDENCY);

		server.uri("/v2/data_platform/chart/position.{format}", config.getChartController())
				.action("position", HttpMethod.POST)
				.name(Constants.Routes.CHART_POSITION);

		server.uri("/v2/data_platform/chart/top_n.{format}", config.getChartController())
				.action("top_n", HttpMethod.POST)
				.name(Constants.Routes.CHART_TOP_N);

		server.uri("/v2/data_platform/chart/ratio.{format}", config.getChartController())
				.action("ratio", HttpMethod.POST)
				.name(Constants.Routes.CHART_RATIO);

		server.uri("/v2/data_platform/dataset/list.{format}", config.getDatasetController())
				.action("list", HttpMethod.GET)
				.name(Constants.Routes.DATASET_LIST);

		server.uri("/v2/data_platform/dataset/fields.{format}", config.getDatasetController())
				.action("fields", HttpMethod.POST)
				.name(Constants.Routes.DATASET_FIELDS);

		/////////////////////////////////////////////page///////////////////////////////////
		/*通过企业ID获取战图列表*/
		server.uri("/v2/data_platform/page.{format}", config.getPageController())
				.action("readAll", HttpMethod.GET)
				.name(Constants.Routes.PAGE_LIST);

		/*创建战图*/
		server.uri("/v2/data_platform/page.{format}", config.getPageController())
				.action("create", HttpMethod.POST)
				.name(Constants.Routes.PAGE_INST);

		/*通过战图ID查询、更新、删除战图*/
		server.uri("/v2/data_platform/page/{page_id}.{format}", config.getPageController())
				.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
				.name(Constants.Routes.PAGE_READ_ROUTE);

		/*更新战图部分信息*/
		server.uri("/v2/data_platform/page/individual/{page_id}.{format}", config.getPageController())
				.action("updateIndividual", HttpMethod.PUT)
				.name(Constants.Routes.PAGE_INDIVIDUAL_UPDATE);

		//////////////////////////////////////////////////////JDBC//////////////////////////////////
		/*通过企业ID获取jdbc列表*/
		server.uri("/v2/data_platform/jdbc.{format}", config.getJdbcController())
				.action("readAll", HttpMethod.GET)
				.name(Constants.Routes.JDBC_LIST);
		/*创建jdbc*/
		server.uri("/v2/data_platform/jdbc.{format}", config.getJdbcController())
				.action("create", HttpMethod.POST)
				.name(Constants.Routes.JDBC_INST);

		/*通过jdbc ID查询 删除 更新jdbc dataset*/
		server.uri("/v2/data_platform/jdbc/{jdbc_id}.{format}", config.getJdbcController())
				.method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
				.name(Constants.Routes.JDBC_READ_ROUTE);
    }
}
