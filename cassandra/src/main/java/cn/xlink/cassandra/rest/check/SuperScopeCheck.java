package cn.xlink.cassandra.rest.check;

import cn.xlink.cassandra.proxy.HttpProxy;
import cn.xlink.cassandra.proxy.Proxy;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * 数据访问访问校验
 * Created by Zhengzhenxie on 2017/9/13.
 */
public abstract class SuperScopeCheck {

    protected Proxy proxy;

    public SuperScopeCheck(String url, Map<String, String> headers) {
        this.proxy = new HttpProxy(url, headers);
    }

    /**
     * 校验是否有权限获取数据
     *
     * @param validParams
     * @param token
     * @return
     */
    public abstract boolean checkScope(Map<String, String> validParams, String token);

    /**
     * 默认校验方式
     *
     * @param validParams
     * @return
     */
    protected final boolean defaultCheck(Map<String, String> validParams) {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        String validCorpId = validParams.get(Constant.CORP_ID);
        try {
            JSONTokener res = this.proxy.get(validParams, params, headerMap);
            JSONObject o = new JSONObject(res);
            String corpId = o.getString("corp_id");
            if (null == corpId) {
                return false;
            }
            return validCorpId.equals(corpId);
        } catch (IOException e) {
            return false;
        }
    }
}
