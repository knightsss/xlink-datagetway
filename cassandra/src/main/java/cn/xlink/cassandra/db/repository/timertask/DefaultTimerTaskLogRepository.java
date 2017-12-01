package cn.xlink.cassandra.db.repository.timertask;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import com.datastax.driver.core.Session;

import java.util.List;

/**
 * Created by Zhengzhenxie on 2017/9/25.
 * 定时任务日志查询：
 * 默认必须的查询条件：task_id + execute_time
 * 其他可选查询条件：status 、owner
 * 共出现4组查询可能：
 * ①task_id + execute_time
 * ②task_id + execute_time + status
 * ③task_id + execute_time + owner
 * ④task_id + execute_time + owner + status
 */
public class DefaultTimerTaskLogRepository extends SuperTimerTaskLogRepository {
    public DefaultTimerTaskLogRepository(Session session) {
        super(session);
    }

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        return defaultSecondConditions("execute_time");
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return defaultOrderByCondition("execute_time");
    }
}
