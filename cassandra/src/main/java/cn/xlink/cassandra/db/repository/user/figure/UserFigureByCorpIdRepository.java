package cn.xlink.cassandra.db.repository.user.figure;

import cn.xlink.cassandra.db.CassandraConstants;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class UserFigureByCorpIdRepository extends SuperUserFigureRepository {
    public UserFigureByCorpIdRepository(Session session) {
        super(session, CassandraConstants.M_USER_FIGURE_CORP_ID);
    }

    @Override
    protected String firstConditionKey() {
        return "corp_id";
    }
}
