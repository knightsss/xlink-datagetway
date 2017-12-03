//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.xlink.data.query;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.MessageObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMessageObserver extends MessageObserver {
    private Map<String, Timer> timers = new ConcurrentHashMap();

    public LocalMessageObserver() {
    }

    protected void onReceived(Request request, Response response) {
        this.timers.put(request.getCorrelationId(), new LocalMessageObserver.Timer());

        String originUrl = request.getHeader("Origin");
        if (originUrl == null) {
            originUrl = "*";
        }
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Access-Token");
        response.addHeader("Access-Control-Allow-Origin", originUrl);
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }

    protected void onException(Throwable exception, Request request, Response response) {
        if (request.getHttpMethod() == HttpMethod.OPTIONS) {
            response.setBody(null);
            response.setResponseCode(200);
        } else {
            System.out.println(request.getEffectiveHttpMethod().toString() + " " + request.getUrl() + " threw exception: " + exception.getClass().getSimpleName());
            exception.printStackTrace();
        }
    }

    protected void onSuccess(Request request, Response response) {
    }

    protected void onComplete(Request request, Response response) {

        LocalMessageObserver.Timer timer = (LocalMessageObserver.Timer)this.timers.remove(request.getCorrelationId());
        if(timer != null) {
            timer.stop();
        }

        StringBuffer sb = new StringBuffer(request.getEffectiveHttpMethod().toString());
        sb.append(" ");
        sb.append(request.getUrl());
        if(timer != null) {
            sb.append(" responded with ");
            sb.append(response.getResponseStatus().toString());
            sb.append(" in ");
            sb.append(timer.toString());
        } else {
            sb.append(" responded with ");
            sb.append(response.getResponseStatus().toString());
            sb.append(" (no timer found)");
        }

        System.out.println(sb.toString());
    }

    private static class Timer {
        private long startMillis = 0L;
        private long stopMillis = 0L;

        public Timer() {
            this.startMillis = System.currentTimeMillis();
        }

        public void stop() {
            this.stopMillis = System.currentTimeMillis();
        }

        public String toString() {
            long stopTime = this.stopMillis == 0L?System.currentTimeMillis():this.stopMillis;
            return stopTime - this.startMillis + "ms";
        }
    }
}
