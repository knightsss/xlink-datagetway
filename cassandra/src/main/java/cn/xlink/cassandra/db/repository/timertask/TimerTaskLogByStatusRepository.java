package cn.xlink.cassandra.db.repository.timertask;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.utils.tuple.Tuple;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import com.datastax.driver.core.Session;

import java.util.List;

/**
 * Created by Zhengzhenxie on 2017/9/25.
 */
public class TimerTaskLogByStatusRepository extends SuperTimerTaskLogRepository {
    public TimerTaskLogByStatusRepository(Session session) {
        super(session);
    }

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        List<TwoTuple<String, OperationType>> ls = defaultSecondConditions("execute_time");
        ls.add(Tuple.tuple("status", OperationType.Eq));
        return ls;
    }
}
