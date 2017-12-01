package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.DeviceInfoSnapshotEntity;
import cn.xlink.cassandra.db.repository.device.snapshot.DeviceInfoSnapshotRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class DeviceInfoSnapshotService extends BaseService<DeviceInfoSnapshotEntity> {

    public DeviceInfoSnapshotService(ScopeType type) {
        super(DeviceInfoSnapshotRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<DeviceInfoSnapshotEntity> readAll(boolean hasCondition, Object...  values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<DeviceInfoSnapshotEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object...  values) {
        return repository.count(hasCondition, values);
    }
}
