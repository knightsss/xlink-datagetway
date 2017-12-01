package cn.xlink.cassandra.service.check;

import cn.xlink.cassandra.proxy.HttpProxy;
import cn.xlink.cassandra.proxy.Proxy;
import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.restexpress.Request;

import java.io.IOException;
import java.util.Map;

/**
 * api接口鉴权
 * Created by Zhengzhenxie on 2017/9/13.
 */
public class ApiAuthCheckService {

    protected Proxy proxy;
    private final Logger logger = Logger.getLogger(this.getClass());

    public ApiAuthCheckService(String cropIdUrl, Map<String, String> tokenMap) {
        this.proxy = new HttpProxy(cropIdUrl, tokenMap);
    }

    /**
     * 获取token信息
     *
     * @param request
     * @return
     */
    public JSONObject getTokenMessage(Request request) throws AuthenticationException {
        String token = request.getHeader("Access-Token");
        if (token == null || token.equals("")) {
            throw new AuthenticationException("Invalid Authorization");
        }

        String body = "{\"access_token\":\"" + token + "\"}";
        try {
            JSONTokener res = this.proxy.post(body);
            JSONObject o = new JSONObject(res);
            return o;
        } catch (IOException e) {
            logger.error("Invalid Authorization", e);
            throw new AuthenticationException("Invalid Authorization");
        }
    }
}
