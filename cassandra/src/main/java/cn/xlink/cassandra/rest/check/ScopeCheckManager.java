package cn.xlink.cassandra.rest.check;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.core.utils.StringTools;
import cn.xlink.cassandra.start.config.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhengzhenxie on 2017/9/13.
 */
public class ScopeCheckManager {
    private ScopeCheckManager() {
        init();
    }

    private static ScopeCheckManager singleton = new ScopeCheckManager();

    public static ScopeCheckManager instance() {
        return singleton;
    }

    private static Map<ScopeType, SuperScopeCheck> check;

    private void init() {
        check = new ConcurrentHashMap<>();
        check.put(ScopeType.Corp, new CorpScopeCheck());
        check.put(ScopeType.Device, new DeviceScopeCheck(Configuration.getDeviceCheckUrl(), Configuration.getTokenMap()));
        check.put(ScopeType.Product, new ProductScopeCheck(Configuration.getProductCheckUrl(), Configuration.getTokenMap()));
        check.put(ScopeType.User, new UserScopeCheck(Configuration.getUserCheckUrl(), Configuration.getTokenMap()));
    }

    /**
     * 校验是否拥有获取数据的权限
     *
     * @param type
     * @param validParams
     * @param token
     * @return
     */
    public boolean check(ScopeType type, Map<String, String> validParams, String token) {
        if (null == validParams) {
            validParams = new HashMap<>();
        }
        String corpId = validParams.get(Constant.CORP_ID);
        if (null == type || StringTools.isEmpty(corpId) || StringTools.isEmpty(token)) {
            return false;
        }

        return check.get(type).checkScope(validParams, token);
    }

}
