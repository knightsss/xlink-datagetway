package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.DeviceActiveEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.DeviceActiveService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class DeviceActiveController extends BaseController<DeviceActiveEntity> {

    public DeviceActiveController(Set<RoleType> roles) {
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
        DeviceActiveService service = new DeviceActiveService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<DeviceActiveEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            DeviceActiveEntity device = entities.get(i);
            funGetReturnJson(json, device);
            ret_arr.add(json);
        }
        ret_json.put("list", ret_arr);
        return ret_json;
    }

    /**
     * @param json
     * @param device
     */
    private void funGetReturnJson(JSONObject json, DeviceActiveEntity device) {
        json.put("corp_id", device.getCorpId());
        json.put("product_id", device.getProductId());
        json.put("device_id", device.getDeviceId());
        json.put("ip", device.getIp());
        json.put("country", device.getCountry());
        json.put("province", device.getProvince());
        json.put("city", device.getCity());
        json.put("create_time", device.getCreateTime());
    }
}
