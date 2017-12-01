package cn.xlink.cassandra.db.repository.avs;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

public class AvsLogByCorpIdRepository extends SuperAvsLogRepository {

    public AvsLogByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_AVS_LOG_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
