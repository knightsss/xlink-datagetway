package cn.xlink.cassandra.db.repository.user.auth;



import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.UserAuthEntity;

import java.util.List;


/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class SuperUserAuthRepository extends BaseRepository<UserAuthEntity>{
    public SuperUserAuthRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
	protected UserAuthEntity marshalRow(Row row) {
		UserAuthEntity entity = new UserAuthEntity();
        entity.setCorpId(row.getString("corp_id"));
		entity.setUserId(row.getInt("user_id"));
		entity.setAccount(row.getString("account"));
		entity.setIp(row.getString("ip"));
		entity.setRegisterDate(row.getTimestamp("register_date"));
		entity.setAuthTime(row.getTimestamp("auth_time"));
		return entity;
	}

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        return defaultSecondConditions("auth_time");
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return defaultOrderByCondition("auth_time");
    }
}
