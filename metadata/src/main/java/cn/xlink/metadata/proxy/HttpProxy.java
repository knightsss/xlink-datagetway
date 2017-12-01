package cn.xlink.metadata.proxy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
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

    public HttpProxy(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        this.client = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    @Override
    public JSONTokener post(String body) throws IOException{
        HttpPost post = new HttpPost(this.url);
        JSONTokener inResponse = null;

        StringEntity entity = new StringEntity(body);
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        entity.setChunked(true);

        if (this.headers != null) {
            for(String key: this.headers.keySet()) {
                post.setHeader(key, this.headers.get(key));
            }
        }
        post.setEntity(entity);

        CloseableHttpResponse res = client.execute(post);

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
