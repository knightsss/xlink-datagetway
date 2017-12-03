package cn.xlink.data.core.proxy;

import org.json.JSONArray;

import java.io.IOException;

/**
 * Created by Ghold on 2017/5/25.
 */
public interface Proxy {
    Object post(String body, String path) throws IOException;
}
