package cn.xlink.cassandra.proxy;

import org.json.JSONTokener;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/5/25.
 */
public interface Proxy {
    /**
     * ps:使用默认的headers参数
     *
     * @param body
     * @return
     * @throws IOException
     */
    JSONTokener post(String body) throws IOException;

    /**
     * ps:默认的headers+headers参数 = 新headers参数
     * 旧的headers参数有可能被新的headers参数替换，
     * 详见代码
     *
     * @param body
     * @param headerMap
     * @return
     * @throws IOException
     */
    JSONTokener post(String body, Map<String, String> headerMap) throws IOException;

    /**
     * ps:使用默认的headers参数
     *
     * @param urlParams
     * @param params
     * @return
     * @throws IOException
     */
    JSONTokener get(Map<String, String> urlParams, Map<String, Object> params) throws IOException;

    /**
     * ps:默认的headers+headers参数 = 新headers参数
     * 旧的headers参数有可能被新的headers参数替换，
     * 详见代码
     *
     * @param urlParams <urlParam,value> 统一资源定位符替换参数 如/v2/{user_id} --> /v2/123456
     * @param params    <a,1> ?a=1&b=2
     * @param headerMap
     * @return
     * @throws IOException
     */
    JSONTokener get(Map<String, String> urlParams, Map<String, Object> params, Map<String, String> headerMap) throws IOException;
}
