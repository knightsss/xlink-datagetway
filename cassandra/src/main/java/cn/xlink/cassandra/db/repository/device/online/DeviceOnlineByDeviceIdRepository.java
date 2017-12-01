package cn.xlink.cassandra.db.repository.device.online;

import cn.xlink.cassandra.db.CassandraConstants;

import com.datastax.driver.core.Session;

public class DeviceOnlineByDeviceIdRepository extends SuperDeviceOnlineRepository {

    public DeviceOnlineByDeviceIdRepository(Session session) {
        super(session, CassandraConstants.T_DEVICE_ONLINE);
    }

    @Override
    protected String firstConditionKey() {
        return "device_id";
    }
}
