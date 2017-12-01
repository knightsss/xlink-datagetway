package cn.xlink.cassandra.db.repository.user.auth;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UserAuthByUserIdRepository extends SuperUserAuthRepository{
    public UserAuthByUserIdRepository(Session session) {
        super(session, CassandraConstants.T_USER_AUTH);
    }

    @Override
    protected String firstConditionKey() {
        return "user_id";
    }
}
