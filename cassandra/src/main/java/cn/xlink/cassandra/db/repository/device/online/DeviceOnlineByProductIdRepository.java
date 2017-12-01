package cn.xlink.cassandra.db.repository.device.online;

import cn.xlink.cassandra.db.CassandraConstants;

import com.datastax.driver.core.Session;

public class DeviceOnlineByProductIdRepository extends SuperDeviceOnlineRepository {

    public DeviceOnlineByProductIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_ONLINE_PRODUCT_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "product_id";
    }

}
