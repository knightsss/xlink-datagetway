package cn.xlink.cassandra.rest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.UserFigureEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.ConcurrentSet;
import org.restexpress.Request;
import org.restexpress.Response;

import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.service.UserFigureService;

/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public class UserFigureController extends BaseController<UserFigureEntity> {
    public UserFigureController(Set<RoleType> roles) {
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
        UserFigureService service = new UserFigureService(scope);
        TwoTuple<Date, Date> betweenDate = funcGetBetweenDate(request);
        int limit = getLimit(request);
        int page = getPage(request);
        List<UserFigureEntity> entities = readByPaging(service, limit, page, param, betweenDate);

        //返回参数
        JSONObject ret_json = new JSONObject();
        JSONArray ret_arr = new JSONArray();
        JSONObject json;
        for (int i = 0, size = entities.size(); i < size; i++) {
            json = new JSONObject();
            UserFigureEntity user = entities.get(i);
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
    private void funGetReturnJson(JSONObject json, UserFigureEntity user) {
        json.put("corp_id", user.getCorpId());
        json.put("user_id", user.getUserId());
        json.put("language", user.getLanguage());
        json.put("operate_system", user.getOperateSystem());
        json.put("machine_type", user.getMachineType());
        json.put("model_number", user.getModelNumber());
        json.put("resolution", user.getResolution());
        json.put("os_version", user.getOsVersion());
        json.put("carrier", user.getCarrier());
        json.put("report_time", user.getReportTime());
    }
}
