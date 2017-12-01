package cn.xlink.cassandra.db.repository.user.auth;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UserAuthByCorpIdRepository extends SuperUserAuthRepository{
    public UserAuthByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_USER_AUTH_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
