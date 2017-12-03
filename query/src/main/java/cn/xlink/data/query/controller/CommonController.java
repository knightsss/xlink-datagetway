package cn.xlink.data.query.controller;

import cn.xlink.data.core.service.Service;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.core.proxy.Proxy;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
import org.json.JSONArray;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CommonController extends AbstractController{

	public CommonController(Properties p,
							Map<String, Proxy> proxyMap,
							Map<String, Service> serviceMap)
	{
		super(p, proxyMap, serviceMap);
	}

	/**
	 * 通用配置查询
	 * @param request
	 * @param response
	 * @return
	 */
	public Object query(Request request, Response response) {
		RequestBody body;

		// 初始化RequestBody对象
		if ((body = this.init(request, response)) == null) return null;

		Map<String, Object> data = new HashMap<String, Object>();
		try {
			JSONArray list = this.getList(body,
					body.getFmt().parse(body.getStartTime()),
					body.getFmt().parse(body.getEndTime())
			);
			data.put("results", this.getDataList(body, list));
		} catch (Exception e) {
			response.setException(e);
		}
		return data;
	}
}
