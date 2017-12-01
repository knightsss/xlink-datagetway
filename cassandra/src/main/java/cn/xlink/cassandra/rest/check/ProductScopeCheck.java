package cn.xlink.cassandra.rest.check;


import java.util.Map;

/**
 * 产品范围校验
 * Created by Zhengzhenxie on 2017/9/13.
 */
public class ProductScopeCheck extends SuperScopeCheck {

    public ProductScopeCheck(String url, Map<String, String> headers) {
        super(url, headers);
    }

    @Override
    public boolean checkScope(Map<String, String> validParams, String token) {
        return defaultCheck(validParams);
    }
}
