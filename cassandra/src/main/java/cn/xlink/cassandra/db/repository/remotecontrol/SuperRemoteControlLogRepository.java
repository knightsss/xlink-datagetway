package cn.xlink.cassandra.db.repository.remotecontrol;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.RemoteControlLogEntity;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperRemoteControlLogRepository extends BaseRepository<RemoteControlLogEntity> {

    public SuperRemoteControlLogRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        return defaultSecondConditions("create_time");
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return defaultOrderByCondition("create_time");
    }
}
