package cn.xlink.metadata.service;

import cn.xlink.metadata.proxy.HttpProxy;
import cn.xlink.metadata.proxy.Proxy;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/6/12.
 */
public class AuthCheck {
    private Proxy proxy;

    public AuthCheck(String cropIdUrl, Map<String, String> tokenMap) {
        this.proxy = new HttpProxy(cropIdUrl, tokenMap);
    }

    /**
     * fetch corp_id from Request
     * @param request
     * @param response
     * @return
     */
    public String getCorpId(Request request, Response response) throws AuthenticationException{
        String token = request.getHeader("Access-Token");
        if (token == null || token.equals("")) {
            throw new AuthenticationException("Invalid Authorization");
        }

        String body = "{\"access_token\":\"" + token + "\"}";
        try {
            JSONTokener res = this.proxy.post(body);
            JSONObject o = new JSONObject(res);
            return o.getString("corp_id");
        } catch (IOException e) {
            throw new AuthenticationException("Invalid Authorization");
        }
    }
}
