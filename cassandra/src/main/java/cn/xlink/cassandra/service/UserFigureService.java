package cn.xlink.cassandra.service;


import java.util.List;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.UserFigureEntity;
import cn.xlink.cassandra.db.repository.user.figure.UserFigureRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

public class UserFigureService extends BaseService<UserFigureEntity> {

    public UserFigureService(ScopeType type) {
        super(UserFigureRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<UserFigureEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<UserFigureEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
    @Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }

}
