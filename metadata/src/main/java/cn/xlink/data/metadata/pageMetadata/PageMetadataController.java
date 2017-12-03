package cn.xlink.data.metadata.pageMetadata;

import cn.xlink.data.core.proxy.Proxy;
import cn.xlink.data.core.service.AuthCheck;
import cn.xlink.data.core.service.Service;
import cn.xlink.data.metadata.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import org.json.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain concepts and passed to the service layer.
 * Then service layer response information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process an entity that is identified by a MongoDB ObjectId.
 */
public class PageMetadataController
{
	private static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
	private static final String XLINK_AUTH_CHECK_PATH = "xlink.auth.check.path";

	static final String SERVICE_AUTH = "auth";

	private PageMetadataService pageService;
	private ObjectMapper mapper;
	private Map<String, Service> serviceMap;
	private Properties p;

	public PageMetadataController(Properties p,
								  Map<String, Proxy> proxyMap,
								  Map<String, Service> serviceMap,
								  PageMetadataService pageService)
	{
		super();
		this.pageService = pageService;
		this.serviceMap = serviceMap;
		this.p = p;
		this.mapper = new ObjectMapper();
		this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	}

	public Object create(Request request, Response response) {
		PageMetadata entity = request.getBodyAs(PageMetadata.class, "Resource details not provided");

		// Construct the response for create...
		response.setResponseCreated();

		// check auth
		entity.setCorpId(authCheck(request, response));

		PageMetadata saved = pageService.create(entity);

		Map<String, Object> map= new HashMap<>();
		map.put("id", saved.getId().toString());
		return map;
	}

	/*查询战图*/
	public Object read(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.PAGE_ID, "No resource ID supplied");

		try {
		    // check auth
			authCheck(request, response);

			PageMetadata entity = pageService.read(Identifiers.MONGOID.parse(id));
			System.out.println("ID is " + entity.getId());
			return mapper.valueToTree(entity);
		} catch (ItemNotFoundException e1) {
			response.setResponseCode(400);
			response.setException(e1);
			return null;
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setException(e);
			e.printStackTrace();
		}
		return null;
	}

	/*删除战图*/
	public void delete(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.PAGE_ID, "No resource ID supplied");
		try {
			authCheck(request, response);
			pageService.delete(Identifiers.MONGOID.parse(id));
		} catch (ItemNotFoundException e1) {
			response.setResponseCode(400);
			response.setException(e1);
		} catch (Exception e) {
			response.setResponseCode(500);
			response.setException(e);
			e.printStackTrace();
		}
	}

	/*更新战图*/
	public void update(Request request, Response response)
	{
		String id = request.getHeader(Constants.Url.PAGE_ID, "No resource ID supplied");
		System.out.println("id is " + id);
		PageMetadata entity = request.getBodyAs(PageMetadata.class, "Resource details not provided");

		// check auth
		authCheck(request, response);
		entity.setId(Identifiers.MONGOID.parse(id));
		pageService.update(entity);
		response.setBody(entity.toMap());
	}

	/**
	 * 权限验证，用于用户token验证和返回企业Id
	 * @param request
	 * @param response
	 * @return corp_id
	 */
	public String authCheck(Request request, Response response) {
		try {
			JSONObject res = new JSONObject(this.serviceMap.get(SERVICE_AUTH).serve(request, response, p.getProperty(XLINK_AUTH_CHECK_PATH), null));
			return res.getString("corp_id");
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponseCode(403);
			response.setException(e);
			return null;
		}
	}
}
