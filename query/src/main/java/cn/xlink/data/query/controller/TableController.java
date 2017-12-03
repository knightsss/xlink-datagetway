package cn.xlink.data.query.controller;

import cn.xlink.data.core.service.Service;
import cn.xlink.data.query.domain.RequestBody;
import cn.xlink.data.metadata.datasetMetadata.DatasetMetadataService;
import cn.xlink.data.core.proxy.Proxy;
import org.json.JSONArray;
import org.restexpress.Request;
import org.restexpress.Response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TableController extends AbstractController{

	private DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public TableController(Properties p,
						   Map<String, Proxy> proxyMap,
						   Map<String, Service> serviceMap)
	{
		super(p, proxyMap, serviceMap);
	}

	public Object list(Request request, Response response) {
		RequestBody body;
		if ((body = this.init(request, response)) == null) return null;

		Map<String, Object> data = new HashMap<String, Object>();
		try {
			JSONArray list = this.getList(body, body.getFmt().parse(body.getStartTime()), body.getFmt().parse(body.getEndTime()));
			data.put("total", list.length());
			data.put("list", this.getDataList(body, list));
		} catch (Exception e) {
			response.setException(e);
		}
		return data;
	}
}
