package cn.xlink.cassandra.rest.base;

import cn.xlink.cassandra.core.exception.XlinkException;
import cn.xlink.cassandra.core.type.RoleType;
import cn.xlink.cassandra.core.utils.DateFormatTools;
import cn.xlink.cassandra.core.utils.StringTools;
import cn.xlink.cassandra.core.utils.tuple.Tuple;
import cn.xlink.cassandra.rest.check.Constant;
import cn.xlink.cassandra.rest.check.ScopeCheckManager;
import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.service.base.BaseService;
import cn.xlink.cassandra.start.config.Configuration;
import org.apache.http.auth.AuthenticationException;
import org.apache.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryRange;
import org.restexpress.exception.BadRequestException;
import org.restexpress.query.QueryRanges;

import com.alibaba.fastjson.JSONObject;

import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.*;

/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class BaseController<T extends BaseEntity> extends AbstractController {
    protected final Logger logger = Logger.getLogger(this.getClass());

    private final Set<RoleType> roleSet;

    public BaseController(Set<RoleType> roles) {
        this.roleSet = new HashSet<>();
        roleSet.addAll(roles);
    }

    @Override
    protected void check_post(Request request, Response response) throws Exception {
        funcGetCorpId(request);//api权限校验
    }

    @Override
    protected void check_get(Request request, Response response) throws Exception {
        funcGetCorpId(request);//api权限校验
    }

    @Override
    protected void check_put(Request request, Response response) throws Exception {

    }

    @Override
    protected void check_delete(Request request, Response response) throws Exception {

    }

    @Override
    protected Object post(Request request, Response response) throws Exception {
        logger.error("invalid request,method [POST],TOKEN [" + request.getHeader(ACCESS_TOKEN) + "]");
        throw new XlinkException("invalid request");
    }

    @Override
    protected Object get(Request request, Response response) throws Exception {
        logger.error("invalid request,method [GET], TOKEN[" + request.getHeader(ACCESS_TOKEN) + "]");
        throw new XlinkException("invalid request");
    }

    @Override
    protected Object put(Request request, Response response) throws Exception {
        logger.error("invalid request,method [PUT], TOKEN[" + request.getHeader(ACCESS_TOKEN) + "]");
        throw new XlinkException("invalid request");
    }

    @Override
    protected Object delete(Request request, Response response) throws Exception {
        logger.error("invalid request,method [DELETE], TOKEN[" + request.getHeader(ACCESS_TOKEN) + "]");
        throw new XlinkException("invalid request");
    }

    /**
     * Access-Token常量
     */
    protected final String ACCESS_TOKEN = "Access-Token";


    protected static final int MAX_SIZE = 2000;// 分页每页最多支持2000条


    /**
     * 从token解析到的企业id
     */
    protected final String TOKEN_CORP_ID = "TOKEN_CORP_ID";
    /**
     * 从token解析到的角色
     */
    protected final String TOKEN_ROLE = "TOKEN_ROLE";
    /**
     * 从token解析到的调用者id
     */
    protected final String TOKEN_CALL_ID = "TOKEN_CALL_ID";

    /**
     * 鉴权
     *
     * @param request
     * @return
     */
    private void funcGetCorpId(Request request) throws AuthenticationException {
        org.json.JSONObject o = Configuration.getAuthCheck().getTokenMessage(request);
        String corpId = o.getString("corp_id");
        int role = o.getInt("role");
        RoleType roleType = RoleType.fromType(role);
        if (!roleSet.contains(roleType) || corpId == null) {
            logger.error("Invalid Authorization,TOKEN [" + request.getHeader(ACCESS_TOKEN) + "]");
            throw new AuthenticationException("Invalid Authorization");
        }
        request.addHeader(TOKEN_CORP_ID, corpId);
        // 防止重新传入TOKEN_CORP_ID
        if (!request.isHeaderEqual(TOKEN_CORP_ID, corpId)) {
            String message = "params [" + TOKEN_CORP_ID + "] is illegal";
            logger.error(message + ",TOKEN [" + request.getHeader(ACCESS_TOKEN) + "]");
            throw new BadRequestException(message);
        }
        request.addHeader(TOKEN_ROLE, String.valueOf(role));
        // 防止重新传入TOKEN_ROLE
        if (!request.isHeaderEqual(TOKEN_ROLE, String.valueOf(role))) {
            String message = "params [" + TOKEN_ROLE + "] is illegal";
            logger.error(message + ",TOKEN [" + request.getHeader(ACCESS_TOKEN) + "]");
            throw new BadRequestException(message);
        }
        String id = null;
        switch (roleType) {
            case User:
                id = StringTools.getString(o.getInt("user_id"));
                break;
            case Corp:
                id = o.getString("member_id");
                break;
            case Device:
                id = StringTools.getString(o.getInt("device_id"));
                break;
            default:
                break;
        }
        request.addHeader(TOKEN_CALL_ID, id);
        // 防止重新传入TOKEN_CALL_ID
        if (!request.isHeaderEqual(TOKEN_CALL_ID, id)) {
            String message = "params [" + TOKEN_CALL_ID + "] is illegal";
            logger.error(message + ",TOKEN [" + request.getHeader(ACCESS_TOKEN) + "]");
            throw new BadRequestException(message);
        }
    }

    /**
     * 校验是否拥有获取数据的权限
     *
     * @param type
     * @param validParams
     * @param token
     * @return
     */
    protected final void scopeCheck(ScopeType type, Map<String, String> validParams, String token) throws XlinkException {
        boolean b = ScopeCheckManager.instance().check(type, validParams, token);
        if (!b) {
            throw new XlinkException("scope check was failed");
        }
    }

    /**
     * 获取数据可见范围(查询维度)及其对应的值
     *
     * @param request
     * @param response
     * @return
     * @throws BadRequestException
     */
    protected final TwoTuple<ScopeType, Object> funcGetScope(Request request, Response response) throws XlinkException {
        String dimension = request.getHeader("scope", "scope not provided");
        ScopeType scope = ScopeType.fromType(dimension);
        if (scope == ScopeType.Unknown) {
            throw new XlinkException("scope is unknown");
        }
        Object value = funcGetScopeValue(scope, request, response);
        return Tuple.tuple(scope, value);
    }

    /**
     * device_id : 默认int类型 corp_id : 默认String类型 product_id : 默认String配型 user_id :
     * 默认int类型 如果不是上述对应的类型，请重写该方法
     *
     * @param scope
     * @param request
     * @param response
     * @return
     */
    protected Object funcGetScopeValue(ScopeType scope, Request request, Response response) throws XlinkException {
        try {
            Map<String, String> params = new HashMap<>();
            String corpId = request.getHeader(TOKEN_CORP_ID);
            String token = request.getHeader(ACCESS_TOKEN);
            params.put(Constant.CORP_ID, corpId);
            switch (scope) {
                case Device:
                    String deviceId = request.getHeader("device_id", "device_id not provided");
                    params.put(Constant.DEVICE_ID, deviceId);
                    scopeCheck(scope, params, token);
                    return Integer.parseInt(deviceId);
                case Corp:
                    scopeCheck(scope, params, token);
                    return corpId;
                case Product:
                    String productId = request.getHeader("product_id", "product_id not provided");
                    params.put(Constant.PRODUCT_ID, productId);
                    scopeCheck(scope, params, token);
                    return productId;
                case User:
                    String userId = request.getHeader("user_id", "user_id not provided");
                    params.put(Constant.USER_ID, userId);
                    scopeCheck(scope, params, token);
                    return Integer.parseInt(userId);
                default:
                    return null;
            }
        } catch (Exception e) {
            logger.error("", e);
            throw new XlinkException(e);
        }
    }

    /**
     * 获取数据
     *
     * @param service
     * @param param       仅支持单一参数
     * @param betweenDate
     * @return
     */
    protected final List<T> readAll(BaseService<T> service, Object param, TwoTuple<Date, Date> betweenDate) {
        if (validateDate(betweenDate)) {
            return service.readAll(true, param, betweenDate.first, betweenDate.second);
        }
        return service.readAll(false, param);
    }

    /**
     * 分页获取数据
     *
     * @param service
     * @param limit
     * @param page
     * @param param       仅支持单一参数
     * @param betweenDate
     * @return
     */
    protected final List<T> readByPaging(BaseService<T> service, int limit, int page, Object param, TwoTuple<Date, Date> betweenDate) {
        if (validateDate(betweenDate)) {
            return service.readByPaging(true, limit, page, param, betweenDate.first, betweenDate.second);
        }
        return service.readByPaging(false, limit, page, param);
    }


    /**
     * 校验scope
     *
     * @param scopeTypeSet
     * @param scope
     */
    protected void funcValidScope(Set<ScopeType> scopeTypeSet, ScopeType scope) throws XlinkException {
        if (!scopeTypeSet.contains(scope)) {
            throw new XlinkException("scope is illegal");
        }
    }

    /**
     * 校验字符串不能为空
     *
     * @param field
     * @param str
     * @throws XlinkException
     */
    protected final void funcValidStringNotEmpty(String field, String str) throws XlinkException {
        if (StringTools.isEmpty(str)) {
            throw new XlinkException(String.format("[%s] must not empty ", field));
        }
    }

    /**
     * 校验对象不允许为空
     *
     * @param field
     * @param obj
     * @throws XlinkException
     */
    protected final void funcValidObjectNotNull(String field, Object obj) throws XlinkException {
        if (null == obj) {
            throw new XlinkException(String.format("[%s] must not empty ", field));
        }
    }

    /**
     * 获取日期，格式：yyyy-MM-dd'T'HH:mm:ss.SS'Z'
     *
     * @param field
     * @param date
     * @return
     * @throws XlinkException
     */
    protected final Date funcGetDateNotNull(String field, String date) throws XlinkException {
        Date result = DateFormatTools.funcGetDate(date);
        if (null == result) {
            throw new XlinkException(String.format("[%s] is invalid ", field));
        }
        return result;
    }


    /**
     * 获取分页的开始位和结束位 start,end
     *
     * @param request
     * @param count
     * @return
     * @deprecated
     */
    protected final TwoTuple<Integer, Integer> getPagination(Request request, long count) {
        QueryRange range = QueryRanges.parseFrom(request);
        int limit = range.getLimit();
        int offset = (int) range.getOffset();
        int start = limit + offset;
        int end;
        if (start == 0 || start > count) {
            end = (int) count;
        } else {
            end = start;
        }
        return new TwoTuple<>(offset, end > MAX_SIZE ? MAX_SIZE : end);
    }

    /**
     * 获取每页数据量
     *
     * @param request
     * @return
     */
    protected final int getLimit(Request request) throws XlinkException {
        String limit = request.getHeader("limit");
        int result = null != limit ? Integer.parseInt(limit) : MAX_SIZE;
        if (result < 0 || result > MAX_SIZE) {
            throw new XlinkException("the parameter [limit] is invalid , the minimum value is 1 and the maximum value is " + MAX_SIZE);
        }
        return result;
    }

    /**
     * 获取当前页数,默认为1
     *
     * @param request
     * @return
     */
    protected final int getPage(Request request) {
        String page = request.getHeader("page");
        return null != page ? Integer.parseInt(page) : 1;
    }

    /**
     * 获取请求体
     *
     * @param request
     * @return
     */
    protected final JSONObject funcGetRequestBody(Request request) {
        return request.getBodyAs(JSONObject.class);
    }

    /**
     * 获取请求体中的时间区间
     *
     * @param startFlag
     * @param endFlag
     * @param request
     * @return
     */
    protected final TwoTuple<Date, Date> funcGetBetweenDate(String startFlag, String endFlag, Request request) {
        Date startDate = DateFormatTools.funcGetDate(request.getHeader(startFlag));
        Date endDate = DateFormatTools.funcGetDate(request.getHeader(endFlag));
        if (null != startDate && null == endDate) {
            endDate = new Date();
        }
        return Tuple.tuple(startDate, endDate);
    }


    /**
     * 获取请求体中的时间区间
     *
     * @param request
     * @return
     */
    protected final TwoTuple<Date, Date> funcGetBetweenDate(Request request) {
        return funcGetBetweenDate("start_time", "end_time", request);
    }

    /**
     * 判断开始时间和结束时间是否都存在
     *
     * @param betweenDate
     * @return
     */
    protected final boolean validateDate(TwoTuple<Date, Date> betweenDate) {
        return null != betweenDate.first && null != betweenDate.second;
    }
}
