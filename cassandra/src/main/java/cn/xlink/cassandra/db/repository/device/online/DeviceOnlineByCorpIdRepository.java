package cn.xlink.cassandra.db.repository.device.online;

import cn.xlink.cassandra.db.CassandraConstants;

import com.datastax.driver.core.Session;

public class DeviceOnlineByCorpIdRepository extends SuperDeviceOnlineRepository {

    public DeviceOnlineByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_ONLINE_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }

}
