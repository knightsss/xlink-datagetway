package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.TimerTaskLogType;
import cn.xlink.cassandra.db.entity.TimerTaskLogEntity;
import cn.xlink.cassandra.db.repository.timertask.TimerTaskLogRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class TimerTaskLogService extends BaseService<TimerTaskLogEntity> {

    public TimerTaskLogService(TimerTaskLogType type) {
        super(TimerTaskLogRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<TimerTaskLogEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<TimerTaskLogEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }


}
