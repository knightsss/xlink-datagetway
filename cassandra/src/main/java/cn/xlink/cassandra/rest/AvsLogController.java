package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.AvsLogEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.rest.check.Constant;
import cn.xlink.cassandra.service.AvsLogService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.*;

public class AvsLogController extends BaseController<AvsLogEntity> {
    public AvsLogController(Set<RoleType> roles) {
        super(roles);
    }

    /**
     * 可查询维度
     */
    private static final Set<ScopeType> scopeTypeSet = new ConcurrentSet<>();

    static {
        scopeTypeSet.add(ScopeType.Corp);
        scopeTypeSet.add(ScopeType.Product);
        scopeTypeSet.add(ScopeType.Device);
    }

    @Override
    public Object get(Request request, Response response) throws XlinkException {
        TwoTuple<ScopeType, Object> tuple = funcGetScope(request, response);
        ScopeType scope = tuple.first;
        Object param = tuple.second;
        funcValidScope(scopeTypeSet, scope);
        AvsLogService service = new AvsLogService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<AvsLogEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            AvsLogEntity avs = entities.get(i);
            funGetReturnJson(json, avs);
            ret_arr.add(json);
        }
        ret_json.put("list", ret_arr);
        return ret_json;
    }

    /**
     * 获取请求值
     *
     * @param scope
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Object funcGetScopeValue(ScopeType scope, Request request, Response response) {
        try {
            Map<String, String> params = new HashMap<>();
            String corpId = request.getHeader(TOKEN_CORP_ID);
            String token = request.getHeader(ACCESS_TOKEN);
            params.put(Constant.CORP_ID, corpId);
            switch (scope) {
                case Device:
                    String deviceId = request.getHeader("device_id", "device_id not provided");
                    params.put(Constant.DEVICE_ID, deviceId);
                    scopeCheck(scope, params, token);
                    return deviceId;
                case Corp:
                    scopeCheck(scope, params, token);
                    return corpId;
                case Product:
                    String productId = request.getHeader("product_id", "product_id not provided");
                    params.put(Constant.PRODUCT_ID, productId);
                    scopeCheck(scope, params, token);
                    return productId;
                default:
                    return null;
            }
        } catch (XlinkException e) {
            logger.error("",e);
            response.setResponseCode(HttpResponseStatus.BAD_REQUEST.code());
            response.setException(e);
            return null;
        }
    }

    /**
     * @param json
     * @param avs
     */
    private void funGetReturnJson(JSONObject json, AvsLogEntity avs) {
        json.put("corp_id", avs.getCorpId());
        json.put("user_id", avs.getUserId());
        json.put("avs_type", avs.getAvsType());
        json.put("req", avs.getReq());
        json.put("resp", avs.getResp());
        json.put("action", avs.getAction());
        json.put("device_id", avs.getDeviceId());
        json.put("appliance_id", avs.getApplianceId());
        json.put("product_id", avs.getProductId());
        json.put("create_time", avs.getCreateTime());//TODO 校验格式
    }
}
