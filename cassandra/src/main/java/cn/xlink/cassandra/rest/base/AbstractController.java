package cn.xlink.cassandra.rest.base;

import org.restexpress.Request;
import org.restexpress.Response;

/**
 * Created by Zhengzhenxie on 2017/10/16.
 */
public abstract class AbstractController {

    /**
     * 校验
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected abstract void check_post(Request request, Response response) throws Exception;

    /**
     * 校验
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected abstract void check_get(Request request, Response response) throws Exception;

    /**
     * 校验
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected abstract void check_put(Request request, Response response) throws Exception;

    /**
     * 校验
     *
     * @param request
     * @param response
     * @throws Exception
     */
    protected abstract void check_delete(Request request, Response response) throws Exception;


    /**
     * post 方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract Object post(Request request, Response response) throws Exception;

    /**
     * get方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract Object get(Request request, Response response) throws Exception;


    /**
     * put方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract Object put(Request request, Response response) throws Exception;


    /**
     * delete方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract Object delete(Request request, Response response) throws Exception;


    public final Object xlinkPost(Request request, Response response) throws Exception {
        check_post(request, response);
        return post(request, response);
    }

    public final Object xlinkGet(Request request, Response response) throws Exception {
        check_get(request, response);
        return get(request, response);
    }

    public final Object xlinkPut(Request request, Response response) throws Exception {
        check_put(request, response);
        return put(request, response);
    }

    public final Object xlinkDelete(Request request, Response response) throws Exception {
        check_delete(request, response);
        return delete(request, response);
    }
}
