package cn.xlink.cassandra.rest;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.type.TimerTaskLogStatusType;
import cn.xlink.cassandra.core.type.TimerTaskLogType;
import cn.xlink.cassandra.core.utils.StringTools;
import cn.xlink.cassandra.db.entity.TimerTaskLogEntity;
import cn.xlink.cassandra.rest.base.BaseController;
import cn.xlink.cassandra.rest.check.Constant;
import cn.xlink.cassandra.rest.check.TimerTaskCheck;
import cn.xlink.cassandra.service.TimerTaskLogService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.*;

public class TimerTaskLogController extends BaseController<TimerTaskLogEntity> {
    public TimerTaskLogController(Set<RoleType> roles) {
        super(roles);
    }

    @Override
    public Object post(Request request, Response response) throws XlinkException {
        JSONObject json_body = funcGetRequestBody(request);
        String taskId = json_body.getString("task_id");
        //校验访问权限
        Map<String, String> validParams = new HashMap<>();
        validParams.put(Constant.TASK_ID, taskId);
        String token = request.getHeader(ACCESS_TOKEN);
        boolean check = new TimerTaskCheck().checkScope(validParams, token);
        if (!check) {
            throw new XlinkException("data permissions illegal !");
        }
        funcValidStringNotEmpty("task_id", taskId);
        JSONObject execute_time_json = json_body.getJSONObject("execute_time");
        funcValidObjectNotNull("execute_time", execute_time_json);
        Date startTime = funcGetDateNotNull("start", execute_time_json.getString("start"));
        Date endTime = funcGetDateNotNull("end", execute_time_json.getString("end"));
        int status = json_body.getIntValue("status");
        String owner = json_body.getString("owner");
        int limit = getLimit(request);
        int page = getPage(request);

        TimerTaskLogService service;
        List<TimerTaskLogEntity> entities = null;
        //TODO 查询逻辑:待验证
        //1.status、owner都不存在，则默认查询
        //2.status存在、owner不存在，则添加status条件
        //3.owner存在、status不存在，则添加owner条件
        //4.status、owner都存在，则添加owner条件，再过滤掉status
        boolean emptyStatus = TimerTaskLogStatusType.fromType(status) == TimerTaskLogStatusType.Unknown;//status是否为空
        boolean emptyOwner = StringTools.isEmpty(owner);//owner是否为空

        if (emptyStatus && emptyOwner) {// 1
            service = new TimerTaskLogService(TimerTaskLogType.Default);
            entities = service.readByPaging(true, limit, page, taskId, startTime, endTime);
        }
        if (!emptyStatus && emptyOwner) {// 2
            service = new TimerTaskLogService(TimerTaskLogType.Status);
            entities = service.readByPaging(true, limit, page, taskId, startTime, endTime, status);
        }
        if (!emptyOwner && emptyStatus) {// 3
            service = new TimerTaskLogService(TimerTaskLogType.Owner);
            entities = service.readByPaging(true, limit, page, taskId, startTime, endTime, owner);
        }
        if (!emptyOwner && !emptyStatus) { //4
            service = new TimerTaskLogService(TimerTaskLogType.Owner);
            entities = service.readByPaging(true, limit, page, taskId, startTime, endTime, owner);
            if (entities.size() == 0 || entities.get(0).getStatus() != status) {
                return new JSONObject();
            }
        }
        JSONArray ret_array = new JSONArray();
        //返回参数
        if (null != entities) {
            JSONObject ret_json;
            for (TimerTaskLogEntity e : entities) {
                ret_json = new JSONObject();
                funGetReturnJson(ret_array, ret_json, e);
            }
        }
        return ret_array;
    }

    /**
     * @param array
     * @param json
     * @param timerTaskLog
     */
    private void funGetReturnJson(JSONArray array, JSONObject json, TimerTaskLogEntity timerTaskLog) {
        if (null != timerTaskLog) {
            json.put("id", timerTaskLog.getExecuteId());
            json.put("task_id", timerTaskLog.getTaskId());
            json.put("task_name", timerTaskLog.getTaskName());
            json.put("execute_time", timerTaskLog.getExecuteTime());
            json.put("owner", timerTaskLog.getOwner());
            json.put("action", timerTaskLog.getAction());
            json.put("status", timerTaskLog.getStatus());
            json.put("error", timerTaskLog.getError());
            json.put("result", timerTaskLog.getResult());
            array.add(json);
        }
    }
}
