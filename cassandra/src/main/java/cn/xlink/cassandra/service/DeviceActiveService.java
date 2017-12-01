package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.DeviceActiveEntity;
import cn.xlink.cassandra.db.repository.device.active.DeviceActiveRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class DeviceActiveService extends BaseService<DeviceActiveEntity> {

    public DeviceActiveService(ScopeType type) {
        super(DeviceActiveRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<DeviceActiveEntity> readAll(boolean hasCondition, Object...  values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<DeviceActiveEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object...  values) {
        return repository.count(hasCondition, values);
    }
}
