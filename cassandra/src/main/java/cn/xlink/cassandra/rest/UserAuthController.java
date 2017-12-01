package cn.xlink.cassandra.rest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.entity.UserAuthEntity;
import com.alibaba.fastjson.JSONArray;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import com.alibaba.fastjson.JSONObject;

import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.UserAuthService;

/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public class UserAuthController extends BaseController<UserAuthEntity> {
    public UserAuthController(Set<RoleType> roles) {
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
        UserAuthService service = new UserAuthService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<UserAuthEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            UserAuthEntity user = entities.get(i);
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
    private void funGetReturnJson(JSONObject json, UserAuthEntity user) {
        json.put("corp_id", user.getCorpId());
        json.put("user_id", user.getUserId());
        json.put("account", user.getAccount());
        json.put("ip", user.getIp());
        json.put("register_date", user.getRegisterDate());
        json.put("auth_time", user.getAuthTime());
    }
}
