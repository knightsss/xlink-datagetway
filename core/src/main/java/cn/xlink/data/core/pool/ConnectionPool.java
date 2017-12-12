package cn.xlink.data.core.pool;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Properties;

/**
 * Created by Ghold on 2017/12/9.
 */
public class ConnectionPool {
    private DruidDataSource dds;

    public ConnectionPool (Properties p,
                           String url,
                           String username,
                           String password,
                           String driverClassName,
                           String name) {
        dds = new DruidDataSource();
        dds.setMaxActive(Integer.valueOf(p.getProperty("jdbc.druid.maxActive", "20")));
        dds.setInitialSize(Integer.valueOf(p.getProperty("jdbc.druid.initialSize", "1")));
        dds.setMaxWait(Integer.valueOf(p.getProperty("jdbc.druid.maxWait", "60000")));
        dds.setMinIdle(Integer.valueOf(p.getProperty("jdbc.druid.minIdle", "10")));
        dds.setTimeBetweenEvictionRunsMillis(Integer.valueOf(p.getProperty("jdbc.druid.timeBetweenEvictionRunsMillis", "60000")));
        dds.setTestWhileIdle(Boolean.valueOf(p.getProperty("jdbc.druid.testWhileIdle", "true")));
        dds.setTestOnBorrow(Boolean.valueOf(p.getProperty("jdbc.druid.testOnBorrow", "false")));
        dds.setTestOnReturn(Boolean.valueOf(p.getProperty("jdbc.druid.testOnReturn", "false")));
        dds.setMaxOpenPreparedStatements(Integer.valueOf(p.getProperty("jdbc.druid.maxOpenPreparedStatements")));
        dds.setRemoveAbandoned(Boolean.valueOf(p.getProperty("jdbc.druid.removeAbandoned", "true")));
        dds.setRemoveAbandonedTimeout(Integer.valueOf(p.getProperty("jdbc.druid.removeAbandonedTimeout", "true")));
        dds.setLogAbandoned(Boolean.valueOf(p.getProperty("jdbc.druid.logAbandoned")));

        // db info
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);

        if (driverClassName != null && !driverClassName.equals("")) {
            dds.setDriverClassName(driverClassName);
        }
        dds.setName(name);
    }

    public DruidDataSource getDds() {
        return dds;
    }

    public void setDds(DruidDataSource dds) {
        this.dds = dds;
    }
}
