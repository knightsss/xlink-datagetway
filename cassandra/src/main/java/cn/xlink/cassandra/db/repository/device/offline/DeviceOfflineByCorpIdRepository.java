package cn.xlink.cassandra.db.repository.device.offline;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceOfflineByCorpIdRepository extends SuperDeviceOfflineRepository{

    public DeviceOfflineByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_OFFLINE_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
