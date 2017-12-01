package cn.xlink.cassandra.db.repository.device.snapshot;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceInfoSnapshotByCorpIdRepository extends SuperDeviceInfoSnapshotRepository {
    public DeviceInfoSnapshotByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_INFO_SNAPSHOT_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }

}
