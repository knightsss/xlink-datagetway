package cn.xlink.cassandra.service;


import java.util.List;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.UserAuthEntity;
import cn.xlink.cassandra.db.repository.user.auth.UserAuthRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

public class UserAuthService extends BaseService<UserAuthEntity> {

    public UserAuthService(ScopeType type) {
        super(UserAuthRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<UserAuthEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<UserAuthEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }
}
