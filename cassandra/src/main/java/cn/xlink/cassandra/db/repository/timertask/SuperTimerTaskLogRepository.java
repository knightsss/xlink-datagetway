package cn.xlink.cassandra.db.repository.timertask;

import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.CassandraConstants;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.TimerTaskLogEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


/**
 * Created by Zhengzhenxie on 2017/9/25.
 * 定时任务日志查询：
 * 默认必须的查询条件：task_id + execute_time
 * 其他可选查询条件：status 、owner
 * 共出现3组查询可能：
 * ①task_id + execute_time
 * ②task_id + execute_time + status
 * ③task_id + execute_time + owner
 */
public abstract class SuperTimerTaskLogRepository extends BaseRepository<TimerTaskLogEntity> {
    public SuperTimerTaskLogRepository(Session session) {
        super(session, CassandraConstants.T_TIMER_TASK_LOG);
    }

    @Override
    protected String firstConditionKey() {
        return "task_id";
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return null;
    }

    @Override
    protected TimerTaskLogEntity marshalRow(Row row) {
        TimerTaskLogEntity entity = new TimerTaskLogEntity();
        entity.setExecuteId(row.getString("id"));
        entity.setTaskId(row.getString("task_id"));
        entity.setTaskName(row.getString("task_name"));
        entity.setExecuteTime(row.getTimestamp("execute_time"));
        entity.setOwner(row.getString("owner"));
        entity.setAction(row.getString("action"));
        entity.setStatus(row.getInt("status"));
        entity.setError(row.getString("error"));
        entity.setResult(row.getString("result"));
        return entity;
    }
}
