package cn.xlink.cassandra.db.repository.device.snapshot;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceInfoSnapshotByDeviceIdRepository extends SuperDeviceInfoSnapshotRepository {
    public DeviceInfoSnapshotByDeviceIdRepository(Session session) {
        super(session, CassandraConstants.T_DEVICE_INFO_SNAPSHOT);
    }

    @Override
    protected String firstConditionKey() {
        return "device_id";
    }

}
