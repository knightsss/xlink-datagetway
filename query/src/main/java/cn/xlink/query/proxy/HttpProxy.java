package cn.xlink.query.proxy;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ghold on 2017/5/25.
 */
public class HttpProxy implements Proxy {

    private CloseableHttpClient client;
    private String host;
    private Map<String, String> headers;

    public HttpProxy(String host, Map<String, String> headers) {
        this.host = host;
        this.headers = headers;
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        this.client = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @Override
    public JSONTokener post(String body, String path) throws IOException{
        HttpPost post = new HttpPost(this.host + path);


        StringEntity entity = new StringEntity(body, "UTF-8");
        entity.setContentType("application/json");
        entity.setChunked(true);

        if (this.headers != null) {
            for(String key: this.headers.keySet()) {
                post.setHeader(key, this.headers.get(key));
            }
        }
        post.setEntity(entity);

        CloseableHttpResponse res = client.execute(post);
        return responseHandle(res);
    }

    public Object get(String path, Map<String, String> headers) throws IOException {
        HttpGet get = new HttpGet(this.host + path);
        if (headers != null) {
            for(String key: headers.keySet()) {
                get.setHeader(key, headers.get(key));
            }
        }

        CloseableHttpResponse res = client.execute(get);
        return responseHandle(res);
    }

    private JSONTokener responseHandle(CloseableHttpResponse res) throws IOException{
        JSONTokener inResponse = null;
        if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity1 = res.getEntity();
            String charset = "UTF-8";
            if (ContentType.getOrDefault(entity1).getCharset() != null) {
                charset = ContentType.getOrDefault(entity1).getCharset().toString();
            }
            inResponse = new JSONTokener(EntityUtils.toString(entity1,charset));
            res.close();
            return inResponse;
        } else {
            throw new IOException("Statuscode: " + res.getStatusLine().getStatusCode());
        }
    }
}
