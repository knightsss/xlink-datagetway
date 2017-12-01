package cn.xlink.cassandra.rest.check;


import java.util.Map;

/**
 * 企业范围校验
 * Created by Zhengzhenxie on 2017/9/13.
 */
public class CorpScopeCheck extends SuperScopeCheck {
    public CorpScopeCheck() {
        super(null, null);
    }

    @Override
    public boolean checkScope(Map<String, String> validParams, String token) {
        String corpId = validParams.get(Constant.CORP_ID);
        return null != corpId && corpId.trim().length() > 0;
    }
}
