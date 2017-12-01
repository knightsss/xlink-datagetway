package cn.xlink.cassandra.db.repository.device.active;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceActiveByDeviceIdRepository extends SuperDeviceActiveRepository {
    public DeviceActiveByDeviceIdRepository(Session session) {
        super(session, CassandraConstants.T_DEVICE_ACTIVE);
    }


    @Override
    protected String firstConditionKey() {
        return "device_id";
    }
}
