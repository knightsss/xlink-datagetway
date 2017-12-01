package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.DeviceOfflineEntity;
import cn.xlink.cassandra.db.repository.device.offline.DeviceOfflineRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class DeviceOfflineService extends BaseService<DeviceOfflineEntity> {

    public DeviceOfflineService(ScopeType type) {
        super(DeviceOfflineRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<DeviceOfflineEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<DeviceOfflineEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }
}
