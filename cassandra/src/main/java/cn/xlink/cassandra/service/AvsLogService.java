package cn.xlink.cassandra.service;

import cn.xlink.cassandra.core.type.ScopeType;
import cn.xlink.cassandra.db.entity.AvsLogEntity;
import cn.xlink.cassandra.db.repository.avs.AvsLogRepositoryManager;
import cn.xlink.cassandra.service.base.BaseService;

import java.util.List;

public class AvsLogService extends BaseService<AvsLogEntity> {

    public AvsLogService(ScopeType type) {
        super(AvsLogRepositoryManager.instance().getRepository(type));
    }

    @Override
    public List<AvsLogEntity> readAll(boolean hasCondition, Object... values) {
        return repository.readAll(hasCondition, values);
    }

    @Override
    public List<AvsLogEntity> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        return repository.readByPaging(hasCondition, limit, page, values);
    }

    @SuppressWarnings("deprecation")
	@Override
    public long count(boolean hasCondition, Object... values) {
        return repository.count(hasCondition, values);
    }
}
