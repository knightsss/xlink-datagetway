package cn.xlink.cassandra.db.repository.device.active;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class DeviceActiveByCorpIdRepository extends SuperDeviceActiveRepository{
    public DeviceActiveByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_DEVICE_ACTIVE_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
