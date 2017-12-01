package cn.xlink.cassandra.db.repository.user.snapshot;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UseInfoSnapshotByUserIdRepository extends SuperUserInfoSnapshotRepository {
    public UseInfoSnapshotByUserIdRepository(Session session) {
        super(session, CassandraConstants.T_USER_INFO_SNAPSHOT);
    }

    @Override
    protected String firstConditionKey() {
        return "user_id";
    }

}
