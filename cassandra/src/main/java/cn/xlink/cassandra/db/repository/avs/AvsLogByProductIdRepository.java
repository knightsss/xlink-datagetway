package cn.xlink.cassandra.db.repository.avs;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;


public class AvsLogByProductIdRepository extends SuperAvsLogRepository {

    public AvsLogByProductIdRepository(Session session) {
        super(session, CassandraConstants.M_AVS_LOG_PRODUCT_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "product_id";
    }
}
