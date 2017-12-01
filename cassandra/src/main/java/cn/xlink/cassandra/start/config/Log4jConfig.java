package cn.xlink.cassandra.start.config;

import cn.xlink.cassandra.core.utils.PropertiesUtil;
import org.apache.log4j.PropertyConfigurator;

import java.util.Properties;

/**
 * Created by Zhengzhenxie on 2017/9/12.
 */
public class Log4jConfig {

    /**
     * 初始化log4j
     *
     * @param log4jPath
     */
    public static void init(String log4jPath) {
        Properties pro = PropertiesUtil.load(log4jPath);
        PropertyConfigurator.configure(pro);
    }
}
