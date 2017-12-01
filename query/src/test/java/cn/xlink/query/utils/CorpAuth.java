package cn.xlink.query.utils;

import cn.xlink.query.proxy.HttpProxy;
import cn.xlink.query.service.Service;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Ghold on 2017/8/4.
 */
public class CorpAuth implements Service {
    private HttpProxy proxy;
    private String account;
    private String password;

    public CorpAuth(String url, String account, String password) {
        this.proxy = new HttpProxy(url, null);
        this.account = account;
        this.password = password;
    }

    /**
     * serve get datapointField
     * @param request
     * @param response
     * @return
     */
    @Override
    public JSONTokener serve(Request request, Response response, String path, Map<String, String> headers) throws Exception {
        try {
            String body = "{\"account\":\"" + account + "\"," +
                    "\"password\": \"" + password + "\"}";
            return (JSONTokener) this.proxy.post(body, path);
        } catch (IOException e) {
            throw new AuthenticationException("Invalid Authorization");
        }
    }
}
