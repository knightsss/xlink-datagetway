package cn.xlink.cassandra.db.repository.device.active;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceActiveByProductdRepository extends SuperDeviceActiveRepository {
    public DeviceActiveByProductdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_ACTIVE_PRODUCT_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "product_id";
    }
}
