package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.UserInfoSnapshotEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.UserInfoSnapshotService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class UserInfoSnapshotController extends BaseController<UserInfoSnapshotEntity> {
    public UserInfoSnapshotController(Set<RoleType> roles) {
        super(roles);
    }

    /**
     * 可查询维度
     */
    private static final Set<ScopeType> scopeTypeSet = new ConcurrentSet<>();

    static {
        scopeTypeSet.add(ScopeType.Corp);
        scopeTypeSet.add(ScopeType.User);
    }

    @Override
    public Object get(Request request, Response response) throws XlinkException {
        TwoTuple<ScopeType, Object> tuple = funcGetScope(request, response);
        ScopeType scope = tuple.first;
        Object param = tuple.second;
        funcValidScope(scopeTypeSet, scope);
        UserInfoSnapshotService service = new UserInfoSnapshotService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<UserInfoSnapshotEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            UserInfoSnapshotEntity user = entities.get(i);
            funGetReturnJson(json, user);
            ret_arr.add(json);
        }
        ret_json.put("list", ret_arr);
        return ret_json;
    }

    /**
     * @param json
     * @param user
     */
    private void funGetReturnJson(JSONObject json, UserInfoSnapshotEntity user) {
        json.put("user_id", user.getUserId());
        json.put("corp_id", user.getCorpId());
        json.put("nickname", user.getNickname());
        json.put("authorize_code", user.getAuthorizeCode());
        json.put("status_type", user.getStatusType());
        json.put("source_type", user.getSourceType());
        json.put("region_id", user.getRegionId());
        json.put("local_lang", user.getLocalLang());
        json.put("create_date", user.getCreateDate());
        json.put("account", user.getAccount());
        json.put("extend", user.getExtend());
        json.put("remark", user.getRemark());
        json.put("tags", user.getTags());
        json.put("phone", user.getPhone());
        json.put("phone_valid", user.isPhoneValid());
        json.put("phone_zone", user.getPhoneZone());
        json.put("email", user.getEmail());
        json.put("email_valid", user.isEmailValid());
        json.put("active_date", user.getActiveDate());
        json.put("avatar", user.getAvatar());
        json.put("qq_open_id", user.getQqOpenId());
        json.put("wx_open_id", user.getWxOpenId());
        json.put("wb_open_id", user.getWbOpenId());
        json.put("fb_open_id", user.getFbOpenId());
        json.put("tt_open_id", user.getTtOpenId());
        json.put("other_open_id", user.getOtherOpenId());
        json.put("country", user.getCountry());
        json.put("province", user.getProvince());
        json.put("city", user.getCity());
        json.put("gender", user.getGender());
        json.put("address", user.getAddress());
        json.put("age", user.getAge());
        json.put("plugin_id", user.getPluginId());
        json.put("register_ip", user.getRegisterIp());
        json.put("message_settings", user.getMessageSettings());
        json.put("message_inform_setting", user.getMessageInformSetting());
        json.put("report_time", user.getReportTime());
    }
}
