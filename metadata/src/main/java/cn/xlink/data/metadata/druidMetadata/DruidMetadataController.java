package cn.xlink.data.metadata.druidMetadata;

import cn.xlink.data.metadata.Constants;
import cn.xlink.data.core.service.AuthCheck;
import cn.xlink.data.core.service.CuratorClient;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain concepts and passed to the service layer.
 * Then service layer response information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process an entity that is identified by a MongoDB ObjectId.
 */
public class DruidMetadataController
{
	private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
	private DruidMetadataService service;
	private CuratorClient curator;
	private AuthCheck authCheck;
	private ObjectMapper mapper;

	public DruidMetadataController(DruidMetadataService service, CuratorClient curator, AuthCheck authCheck)
	{
		super();
		this.service = service;
		this.curator = curator;
		this.authCheck = authCheck;
		this.mapper = new ObjectMapper();
		this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public Object create(Request request, Response response)
	{
		DruidMetadata entity = request.getBodyAs(DruidMetadata.class, "Resource details not provided");
		// check auth
//		if (!authCheck(request, response, entity)) return null;
		DruidMetadata saved = service.create(entity);

		// send id to zk
		if (!sendNotify(saved.getId().toString(), response)) return null;

		// Construct the response for create...
		response.setResponseCreated();

		// Include the Location header...
		String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_DRUID_METADATA);
		response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, new DefaultTokenResolver()));

		// Return the newly-created resource...
		try {
			return mapper.valueToTree(saved);
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setException(e);
			e.printStackTrace();
		}
		return null;
	}

	public Object read(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
		DruidMetadata entity = service.read(Identifiers.MONGOID.parse(id));
		try {
			return mapper.valueToTree(entity);
		} catch (Exception e) {
		    response.setResponseCode(500);
		    response.setException(e);
			e.printStackTrace();
		}
		return null;
	}

	public Object readAll(Request request, Response response)
	{
		QueryFilter filter = QueryFilters.parseFrom(request);
		QueryOrder order = QueryOrders.parseFrom(request);
		QueryRange range = QueryRanges.parseFrom(request, 20);
		List<DruidMetadata> entities = service.readAll(filter, range, order);

		try {
			List<JsonNode> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			for (DruidMetadata item : entities) {
				list.add(mapper.valueToTree(item));
			}
			long count = service.count(filter);
			map.put("total", count);
			map.put("list", list);
			return map;
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setException(e);
			e.printStackTrace();
		}
		return null;
	}

	public void update(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
		DruidMetadata entity = request.getBodyAs(DruidMetadata.class, "Resource details not provided");
		// check auth
//		if (!checkAuth(request, response, entity)) return ;
		entity.setId(Identifiers.MONGOID.parse(id));
		service.update(entity);
		// send id to zk
		sendNotify(id, response);
		response.setResponseNoContent();
	}

	public void delete(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
		// check auth
//		if (!checkAuth(request, response, null)) return ;
		service.delete(Identifiers.MONGOID.parse(id));
		// send id to zk
		sendNotify(id, response);
		response.setResponseNoContent();
	}

	private boolean sendNotify(String id, Response response) {
		try {
			curator.create(id);
		} catch (Exception e) {
			response.setResponseCode(HttpResponseStatus.INTERNAL_SERVER_ERROR.code());
			response.setException(e);
			return false;
		}
		return true;
	}
}
