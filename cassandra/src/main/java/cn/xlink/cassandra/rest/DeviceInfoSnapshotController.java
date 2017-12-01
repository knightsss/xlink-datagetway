package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.DeviceInfoSnapshotEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.DeviceInfoSnapshotService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class DeviceInfoSnapshotController extends BaseController<DeviceInfoSnapshotEntity> {
    public DeviceInfoSnapshotController(Set<RoleType> roles) {
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
        DeviceInfoSnapshotService service = new DeviceInfoSnapshotService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<DeviceInfoSnapshotEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            DeviceInfoSnapshotEntity device = entities.get(i);
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
    private void funGetReturnJson(JSONObject json, DeviceInfoSnapshotEntity device) {
        json.put("device_id", device.getDeviceId());
        json.put("corp_id", device.getCorpId());
        json.put("product_id", device.getProductId());
        json.put("mac", device.getMac());
        json.put("name", device.getName());
        json.put("is_active", device.isActive());
        json.put("active_date", device.getActiveDate());
        json.put("active_code", device.getActiveCode());
        json.put("authorize_code", device.getAuthorizeCode());
        json.put("mcu_mod", device.getMcuMod());
        json.put("mcu_version", device.getMcuVersion());
        json.put("firmware_mod", device.getFirmwareMod());
        json.put("firmware_version", device.getFirmwareVersion());
        json.put("region_id", device.getRegionId());
        json.put("access_key", device.getAccessKey());
        json.put("sn", device.getSn());
        json.put("domain", device.getDomain());
        json.put("create_time", device.getCreateTime());
        json.put("creator_id", device.getCreatorId());
        json.put("creator_type", device.getCreatorType());
        json.put("active_ip", device.getActiveIp());
        json.put("extend", device.getExtend());
        json.put("tags", device.getTags());
        json.put("dealer_scope", device.getDealerScope());
        json.put("heavy_buyer", device.getHeavyBuyer());
        json.put("heavy_buyer_organization", device.getHeavyBuyerOrganization());
        json.put("qrkey", device.getQrkey());
        json.put("home_id", device.getHomeId());
        json.put("groups", device.getGroups());
        json.put("soft_reset_date", device.getSoftResetDate());
        json.put("last_reset_date", device.getLastResetDate());
        json.put("report_time", device.getReportTime());
    }

}
