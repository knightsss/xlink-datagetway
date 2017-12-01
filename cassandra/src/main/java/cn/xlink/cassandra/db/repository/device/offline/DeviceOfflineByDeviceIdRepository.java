package cn.xlink.cassandra.db.repository.device.offline;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceOfflineByDeviceIdRepository extends SuperDeviceOfflineRepository{

    public DeviceOfflineByDeviceIdRepository(Session session) {
        super(session, CassandraConstants.T_DEVICE_OFFLINE);
    }

    @Override
    protected String firstConditionKey() {
        return "device_id";
    }
}
