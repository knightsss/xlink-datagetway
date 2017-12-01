package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.UserInfoSnapshotEntity;
import cn.xlink.cassandra.db.repository.user.snapshot.UserInfoSnapshotRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class UserInfoSnapshotService extends BaseService<UserInfoSnapshotEntity> {

    public UserInfoSnapshotService(ScopeType type) {
        super(UserInfoSnapshotRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<UserInfoSnapshotEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<UserInfoSnapshotEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }
}
