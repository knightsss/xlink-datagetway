package cn.xlink.data.core.service;

import cn.xlink.data.core.proxy.HttpProxy;
import cn.xlink.data.core.service.Service;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/6/12.
 */
public class AuthCheck implements Service {
    private HttpProxy proxy;

    public AuthCheck(String cropIdUrl, Map<String, String> tokenMap) {
        this.proxy = new HttpProxy(cropIdUrl, tokenMap);
    }

    /**
     * fetch corp_id from Request
     * @param request
     * @param response
     * @return
     */
    public JSONTokener serve(Request request, Response response, String path, Map<String, String> headers) throws AuthenticationException{
        String token = request.getHeader("Access-Token");
        if (token == null || token.equals("")) {
            throw new AuthenticationException("Invalid Authorization");
        }

        String body = "{\"access_token\":\"" + token + "\"}";
        try {
            return (JSONTokener) this.proxy.post(body, path);
        } catch (IOException e) {
            throw new AuthenticationException("Invalid Authorization");
        }
    }
}
