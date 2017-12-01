package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.DeviceOnlineEntity;
import cn.xlink.cassandra.db.repository.device.online.DeviceOnlineRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class DeviceOnlineService extends BaseService<DeviceOnlineEntity> {

    public DeviceOnlineService(ScopeType type) {
        super(DeviceOnlineRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<DeviceOnlineEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<DeviceOnlineEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }


}
