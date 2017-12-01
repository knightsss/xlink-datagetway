package cn.xlink.cassandra.db.repository.user.figure;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UserFigureByUserIdRepository extends SuperUserFigureRepository {
    public UserFigureByUserIdRepository(Session session) {
        super(session, CassandraConstants.T_USER_FIGURE);
    }

    @Override
    protected String firstConditionKey() {
        return "user_id";
    }
}
