package cn.xlink.data.core.service;

import org.json.JSONTokener;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.Map;

/**
 * Created by Ghold on 2017/8/7.
 */
public interface Service {
    JSONTokener serve(Request request, Response response, String path, Map<String, String> header) throws Exception;
}
