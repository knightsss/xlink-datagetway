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

		/*通过企业ID获取战图列表*/
		server.uri("/v2/data_platform/page.{format}", config.getPageController())
				.action("readAll", HttpMethod.GET)
				.name(Constants.Routes.FIGURE_LIST);

		/*创建战图*/
		server.uri("/v2/data_platform/page.{format}", config.getPageController())
				.action("create", HttpMethod.POST)
				.name(Constants.Routes.FIGURE_INST);


		/*通过战图ID查询战图*/
		server.uri("/v2/data_platform/page/{page_id}.{format}", config.getPageController())
				.action("read", HttpMethod.GET);

		/*通过战图ID删除战图*/
		server.uri("/v2/data_platform/page/{page_id}.{format}", config.getPageController())
				.action("delete", HttpMethod.DELETE);


		/*通过战图ID更新战图*/
		server.uri("/v2/data_platform/page/{page_id}.{format}", config.getPageController())
				.action("update", HttpMethod.PUT);

    }
}
