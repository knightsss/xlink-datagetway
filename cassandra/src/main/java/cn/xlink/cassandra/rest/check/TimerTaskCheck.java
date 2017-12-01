package cn.xlink.cassandra.rest.check;

import cn.xlink.cassandra.start.config.Configuration;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhengzhenxie on 2017/10/11.
 */
public class TimerTaskCheck extends SuperScopeCheck{
    public TimerTaskCheck() {
        super(Configuration.getTimerTaskCheckUrl(), Configuration.getTokenMap());
    }

    @Override
    public boolean checkScope(Map<String, String> validParams, String token) {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Access-Token",token);
        String validTaskId = validParams.get(Constant.TASK_ID);
        try {
            JSONTokener res = this.proxy.get(validParams, params, headerMap);
            JSONObject o = new JSONObject(res);
            String taskId = o.getString("id");
            if (null == taskId) {
                return false;
            }
            return validTaskId.equals(taskId);
        } catch (IOException e) {
            return false;
        }
    }
}
