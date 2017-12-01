package cn.xlink.cassandra.core.utils;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Zhengzhenxie on 2017/9/12.
 */
public class PropertiesUtil {
    private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

    /**
     * 加载配置
     *
     * @param configPath
     * @return
     */
    public static Properties load(String configPath) {
        FileInputStream file = null;
        Properties pro = new Properties();
        try {
            file = new FileInputStream(configPath);
            pro.load(file);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != file) {
                try {
                    file.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return pro;
    }
}
