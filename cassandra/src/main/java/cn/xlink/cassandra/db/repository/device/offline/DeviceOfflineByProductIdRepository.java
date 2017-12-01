package cn.xlink.cassandra.db.repository.device.offline;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceOfflineByProductIdRepository extends SuperDeviceOfflineRepository{

    public DeviceOfflineByProductIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_OFFLINE_PRODUCT_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "product_id";
    }
}
