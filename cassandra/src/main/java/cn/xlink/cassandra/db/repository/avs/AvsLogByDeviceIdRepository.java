package cn.xlink.cassandra.db.repository.avs;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;


public class AvsLogByDeviceIdRepository extends SuperAvsLogRepository{

	public AvsLogByDeviceIdRepository(Session session) {
		super(session, CassandraConstants.T_AVS_LOG);
	}

    @Override
    protected String firstConditionKey() {
        return "device_id";
    }
}
