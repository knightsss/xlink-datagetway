package cn.xlink.cassandra.start.config;

import cn.xlink.cassandra.db.repository.avs.AvsLogRepositoryManager;
import cn.xlink.cassandra.db.repository.device.active.DeviceActiveRepositoryManager;
import cn.xlink.cassandra.db.repository.device.offline.DeviceOfflineRepositoryManager;
import cn.xlink.cassandra.db.repository.device.online.DeviceOnlineRepositoryManager;
import cn.xlink.cassandra.db.repository.device.snapshot.DeviceInfoSnapshotRepositoryManager;
import cn.xlink.cassandra.db.repository.remotecontrol.RemoteControlLogRepositoryManager;
import cn.xlink.cassandra.db.repository.timertask.TimerTaskLogRepositoryManager;
import cn.xlink.cassandra.db.repository.user.auth.UserAuthRepositoryManager;
import cn.xlink.cassandra.db.repository.user.figure.UserFigureRepositoryManager;
import cn.xlink.cassandra.db.repository.user.snapshot.UserInfoSnapshotRepositoryManager;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.strategicgains.repoexpress.cassandra.CassandraConfig;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by Zhengzhenxie on 2017/8/21.
 */
public class XlinkCassandraConfig extends CassandraConfig {
    private final String userName;
    private final String pwd;
    private final Logger logger = Logger.getLogger(this.getClass());

    public XlinkCassandraConfig(Properties p) {
        super(p);
        this.userName = p.getProperty("cassandra.user");
        this.pwd = p.getProperty("cassandra.pwd");
        init();
    }

    @Override
    protected void enrichCluster(Cluster.Builder clusterBuilder) {
        super.enrichCluster(clusterBuilder);
        clusterBuilder.withCredentials(userName, pwd);
    }

    private void init() {
        /*********** cassandra init ***********/
        Session session = null;
        try {
            session = this.getSession();
        } catch (Exception e) {
            logger.error("", e);
        }
        //用户认证
        UserAuthRepositoryManager.instance().init(session);
        //用户画像
        UserFigureRepositoryManager.instance().init(session);
        //用户快照
        UserInfoSnapshotRepositoryManager.instance().init(session);
        //设备快照
        DeviceInfoSnapshotRepositoryManager.instance().init(session);
        //设备激活
        DeviceActiveRepositoryManager.instance().init(session);
        //设备上线
        DeviceOnlineRepositoryManager.instance().init(session);
        //设备下线
        DeviceOfflineRepositoryManager.instance().init(session);
        //远程控制日志
        RemoteControlLogRepositoryManager.instance().init(session);
        //avs日志
        AvsLogRepositoryManager.instance().init(session);
        //定时任务执行日志
        TimerTaskLogRepositoryManager.instance().init(session);
    }
}
