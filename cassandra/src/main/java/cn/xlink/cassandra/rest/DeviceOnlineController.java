package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.DeviceOnlineEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.DeviceOnlineService;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import com.alibaba.fastjson.JSONObject;
import org.restexpress.exception.BadRequestException;

public class DeviceOnlineController extends BaseController<DeviceOnlineEntity> {
    public DeviceOnlineController(Set<RoleType> roles) {
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
        if (!scopeTypeSet.contains(scope)) {
            throw new BadRequestException("scope is illegal");
        }
        DeviceOnlineService service = new DeviceOnlineService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<DeviceOnlineEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            DeviceOnlineEntity device = entities.get(i);
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
    private void funGetReturnJson(JSONObject json, DeviceOnlineEntity device) {
        json.put("corp_id", device.getCorpId());
        json.put("device_id", device.getDeviceId());
        json.put("product_id", device.getProductId());
        json.put("region_id", device.getRegionId());
        json.put("cm_id", device.getCmId());
        json.put("ip", device.getIp());
        json.put("firmware_version", device.getFirmwareVersion());
        json.put("firmware_mod", device.getFirmwareMod());
        json.put("mac", device.getMac());
        json.put("connect_protocol", device.getConnectProtocol());
        json.put("create_time", device.getCreateTime());
    }
}
