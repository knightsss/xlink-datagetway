package cn.xlink.query.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.xlink.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.query.builder.Builder;
import cn.xlink.query.domain.RequestBody;
import cn.xlink.query.proxy.Proxy;
import cn.xlink.query.service.Service;
import org.json.JSONArray;
import org.json.JSONException;
import org.restexpress.Request;
import org.restexpress.Response;

public class CardController extends AbstractController {

	private static final int LAST_YEAR = 1;
	private static final int LAST_MONTH = 2;
	private static final int NOW = 3;

	public CardController(Properties p,
						  Map<String, Proxy> proxyMap,
						  Map<String, Service> serviceMap,
						  DatasetMetadataService datasetService)
	{
		super(p, proxyMap, serviceMap, datasetService);
	}

	public Object result(Request request, Response response) {
		RequestBody body;
		if ((body = this.init(request, response)) == null) return null;

		if (body.getFields().size() > 0 || body.getMetrics().size() != 1) {
			response.setResponseCode(400);
			response.setException(new Exception("Invalid Metric Setting"));
			return null;
		}

		Map<String, Double> data = new HashMap<String, Double>();
		try {
			data.put("result", this.getTotal(body, NOW));
		} catch (Exception e) {
			e.printStackTrace();
			response.setException(e);
		}
		return data;
	}

	public Object yearOnYear(Request request, Response response) {
		RequestBody body;
		if ((body = this.init(request, response)) == null) return null;

		Map<String, Double> data = new HashMap<String, Double>();
		try {
			double lastYearTotal = this.getTotal(body, LAST_YEAR);
			if (lastYearTotal == 0.0) {
				response.setException(new Exception("No last year total, don't support year_on_year calc!"));
			} else {
				double now = this.getTotal(body, NOW);
				double yearOnYear = now / lastYearTotal;
				data.put("year_on_year", yearOnYear);
			}
		} catch (Exception e) {
			response.setException(e);
		}
		return data;
	}

	public Object monthOnMonth(Request request, Response response) {
		RequestBody body;
		if ((body = this.init(request, response)) == null) return null;

		Map<String, Double> data = new HashMap<String, Double>();
		try {
			double lastMonthTotal = this.getTotal(body, LAST_MONTH);
			if (lastMonthTotal == 0.0) {
				response.setException(new Exception("No last month total, don't support month_on_month calc!"));
			} else {
				double now = this.getTotal(body, NOW);
				double monthOnMonth = now / lastMonthTotal;
				data.put("month_on_month", monthOnMonth);
			}
		} catch (Exception e) {
			response.setException(e);
		}
		return data;
	}

	/**
	 * 用于查询指标卡用到的计数值
	 * @param body 请求体
	 * @param flag 计算类型
	 * @return 汇总数值
	 */
	private double getTotal(RequestBody body, int flag) throws ParseException,IOException {
		try {
			Calendar c = Calendar.getInstance();
			String startTime = body.getStartTime();
			String endTime = body.getEndTime();
			switch (flag) {
				case LAST_MONTH:
				case LAST_YEAR:
					c.setTime(body.getFmt().parse(startTime));
					c.add(flag, -1);
					startTime = body.getFmt().format(c.getTime());

					c.setTime(body.getFmt().parse(endTime));
					c.add(flag, -1);
					endTime = body.getFmt().format(c.getTime());
					break;
				default:
					break;
			}

			JSONArray ret = this.getList(body, body.getFmt().parse(startTime), body.getFmt().parse(endTime));
			return (double)ret.getJSONObject(0).get(body.getMetrics().get(0).getDisplayName());
		} catch (JSONException e1) {
			return 0.0;
		}
	}
}
