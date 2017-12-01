package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.RemoteControlLogEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.RemoteControlLogService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class RemoteControlLogController extends BaseController<RemoteControlLogEntity> {
    public RemoteControlLogController(Set<RoleType> roles) {
        super(roles);
    }

    /**
     * 可查询维度
     */
    private static final Set<ScopeType> scopeTypeSet = new ConcurrentSet<>();

    static {
        scopeTypeSet.add(ScopeType.User);
        scopeTypeSet.add(ScopeType.Device);
    }

    @Override
    public Object get(Request request, Response response) throws XlinkException {
        TwoTuple<ScopeType, Object> tuple = funcGetScope(request, response);
        ScopeType scope = tuple.first;
        Object param = tuple.second;
        funcValidScope(scopeTypeSet, scope);
        RemoteControlLogService service = new RemoteControlLogService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<RemoteControlLogEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            RemoteControlLogEntity remote = entities.get(i);
            funGetReturnJson(scope, json, remote);
            ret_arr.add(json);
        }
        ret_json.put("list", ret_arr);
        return ret_json;
    }

    /**
     * @param scope
     * @param json
     * @param remote
     */
    private void funGetReturnJson(ScopeType scope, JSONObject json, RemoteControlLogEntity remote) {
        String keyName = ScopeType.User == scope ? "user_id" : (ScopeType.Device == scope ? "device_id" : "");
        json.put(keyName, remote.getKey());
        json.put("link_ids", remote.getLinkIds());
        json.put("corp_id", remote.getCorpId());
        json.put("product_id", remote.getProductId());
        json.put("connect_protocol", remote.getConnectProtocol());
        json.put("protocol_souce", remote.getProtocolSouce());
        json.put("region_id", remote.getRegionId());
        json.put("cm_id", remote.getCmId());
        json.put("package_type", remote.getPackageType());
        json.put("flow_type", remote.getFlowType());
        json.put("data", remote.getData());
        json.put("create_time", remote.getCreateTime());
    }
}
