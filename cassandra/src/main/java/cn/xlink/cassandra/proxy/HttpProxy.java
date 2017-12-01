package cn.xlink.cassandra.proxy;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/5/25.
 */
public class HttpProxy implements Proxy {

    private CloseableHttpClient client;
    private String url;
    private Map<String, String> headers;

    private static final int CONNECT_TIMEOUT = 30000;
    private static final int SOCKET_TIMEOUT = 30000;

    public HttpProxy(String url, Map<String, String> headers) {
        this.headers = headers;
        init(url);
    }

    private void init(String url) {
        this.url = url;
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        this.client = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @Override
    public JSONTokener post(String body) throws IOException {
        return post(body, headers);
    }

    @Override
    public JSONTokener post(String body, Map<String, String> headerMap) throws IOException {
        HttpPost post = new HttpPost(this.url);
        StringEntity entity = new StringEntity(body);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        entity.setChunked(true);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        post.setEntity(entity);
        CloseableHttpResponse res = client.execute(post);
        return ret(res);
    }

    @Override
    public JSONTokener get(Map<String, String> urlParams, Map<String, Object> params) throws IOException {
        return get(urlParams, params, headers);
    }

    @Override
    public JSONTokener get(Map<String, String> urlParams, Map<String, Object> params, Map<String, String> headerMap) throws IOException {
        StringBuffer param = new StringBuffer();
        if (null != params) {
            int i = 0;
            int size = params.size() - 1;
            for (String key : params.keySet()) {
                if (i == size) {
                    param.append("?").append(key).append("=").append(params.get(key));
                } else {
                    param.append("?").append(key).append("=").append(params.get(key)).append("&");
                }
                i++;
            }
        }
        String nUrl = url;
        nUrl += param;
        if (null != urlParams) {
            for (String key : urlParams.keySet()) {
                nUrl = nUrl.replace(key, urlParams.get(key));
            }
        }
        HttpGet httpGet = new HttpGet(nUrl);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpGet.setConfig(RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build());
        CloseableHttpResponse res = client.execute(httpGet);
        return ret(res);
    }


    /**
     * @param res
     * @return
     * @throws IOException
     */
    private JSONTokener ret(CloseableHttpResponse res) throws IOException {
        JSONTokener inResponse;
        if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity1 = res.getEntity();
            String charset = "UTF-8";
            if (ContentType.getOrDefault(entity1).getCharset() != null) {
                charset = ContentType.getOrDefault(entity1).getCharset().toString();
            }
            inResponse = new JSONTokener(EntityUtils.toString(entity1, charset));
            res.close();
            return inResponse;
        } else {
            throw new IOException("Statuscode: " + res.getStatusLine().getStatusCode());
        }
    }
}
