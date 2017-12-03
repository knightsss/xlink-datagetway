package cn.xlink.data.core.service;

import cn.xlink.data.core.proxy.HttpProxy;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/8/4.
 */
public class DatapointField implements Service{
    private HttpProxy proxy;

    public DatapointField(String url, Map<String, String> tokenMap) {
        this.proxy = new HttpProxy(url, tokenMap);
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
            return (JSONTokener) this.proxy.get(path, headers);
        } catch (IOException e) {
            throw new AuthenticationException("Invalid Authorization");
        }
    }
}
