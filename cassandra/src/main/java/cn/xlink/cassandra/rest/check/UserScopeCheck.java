package cn.xlink.cassandra.rest.check;

import java.util.Map;

/**
 * Created by Zhengzhenxie on 2017/9/13.
 */
public class UserScopeCheck extends SuperScopeCheck {
    public UserScopeCheck(String url, Map<String, String> headers) {
        super(url, headers);
    }

    @Override
    public boolean checkScope(Map<String, String> validParams, String token) {
        return defaultCheck(validParams);
    }
}
