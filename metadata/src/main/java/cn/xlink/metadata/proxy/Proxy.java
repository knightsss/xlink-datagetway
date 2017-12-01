package cn.xlink.metadata.proxy;

import org.json.JSONTokener;

import java.io.IOException;

/**
 * Created by Ghold on 2017/5/25.
 */
public interface Proxy {
    JSONTokener post(String body) throws IOException;
}
