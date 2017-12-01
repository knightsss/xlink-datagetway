package cn.xlink.cassandra.db.repository.user.snapshot;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UseInfoSnapshotByCorpIdRepository extends SuperUserInfoSnapshotRepository {
    public UseInfoSnapshotByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_USER_INFO_SNAPSHOT_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
