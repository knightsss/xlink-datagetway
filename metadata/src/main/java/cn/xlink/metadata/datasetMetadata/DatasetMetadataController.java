package cn.xlink.metadata.datasetMetadata;

import cn.xlink.metadata.Constants;
import cn.xlink.metadata.service.AuthCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.strategicgains.hyperexpress.builder.DefaultTokenResolver;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.http.auth.AuthenticationException;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

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
public class DatasetMetadataController
{
	private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
	private DatasetMetadataService service;
	private AuthCheck authCheck;
	private ObjectMapper mapper;

	public DatasetMetadataController(DatasetMetadataService sampleService, AuthCheck authCheck)
	{
		super();
		this.service = sampleService;
		this.authCheck = authCheck;
		this.mapper = new ObjectMapper();
		this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public Object create(Request request, Response response)
	{
		DatasetMetadata entity = request.getBodyAs(DatasetMetadata.class, "Resource details not provided");
		// check auth
		if (checkAuth(request, response) == null) return null;
		DatasetMetadata saved = service.create(entity);

		// Construct the response for create...
		response.setResponseCreated();

		// Include the Location header...
		String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_DATASET_METADATA);
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
		DatasetMetadata entity = service.read(Identifiers.MONGOID.parse(id));
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
		List<DatasetMetadata> entities = service.readAll(filter, range, order);
		try {
			List<JsonNode> list = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			for (DatasetMetadata item : entities) {
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
		DatasetMetadata entity = request.getBodyAs(DatasetMetadata.class, "Resource details not provided");
		// check auth
		if (checkAuth(request, response) == null) return ;
		entity.setId(Identifiers.MONGOID.parse(id));
		service.update(entity);
		response.setResponseNoContent();
	}

	public void delete(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.SAMPLE_ID, "No resource ID supplied");
		// check auth
		if (checkAuth(request, response) == null) return ;
		service.delete(Identifiers.MONGOID.parse(id));
		// send id to zk
		response.setResponseNoContent();
	}

	private String checkAuth(Request request, Response response) {
		try{
			return this.authCheck.getCorpId(request, response);
//            this.authCheck.getCorpId(request, response);
//            body.setCorpId("corp_001");
		} catch (AuthenticationException e){
			response.setResponseCode(HttpResponseStatus.FORBIDDEN.code());
			response.setException(e);
			return null;
		}
	}
}
