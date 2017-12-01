package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.RemoteControlLogEntity;
import cn.xlink.cassandra.db.repository.remotecontrol.RemoteControlLogRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class RemoteControlLogService extends BaseService<RemoteControlLogEntity> {

    public RemoteControlLogService(ScopeType type) {
        super(RemoteControlLogRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<RemoteControlLogEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<RemoteControlLogEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }
}
