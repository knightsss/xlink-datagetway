package cn.xlink.cassandra.db.repository.device.snapshot;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceInfoSnapshotByProductIdRepository extends SuperDeviceInfoSnapshotRepository {
    public DeviceInfoSnapshotByProductIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_INFO_SNAPSHOT_PRODUCT_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "product_id";
    }
}
