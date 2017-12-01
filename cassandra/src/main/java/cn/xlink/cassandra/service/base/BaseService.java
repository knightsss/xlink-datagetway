package cn.xlink.cassandra.service.base;

import java.util.List;

import cn.xlink.cassandra.db.base.BaseEntity;
import cn.xlink.cassandra.db.base.BaseRepository;

/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class BaseService<T extends BaseEntity> {
    protected final BaseRepository<T> repository;

    protected BaseService(BaseRepository<T> repository) {
        this.repository = repository;
    }

    /**
     * 读取所有数据
     *
     * @param hasCondition
     * @param values
     * @return
     */
    public abstract List<T> readAll(boolean hasCondition, Object... values);

    /**
     * 分页读取数据
     *
     * @param hasCondition
     * @param limit        每页数据量
     * @param page         当前页数
     * @param values
     * @return
     */
    public abstract List<T> readByPaging(boolean hasCondition, int limit, int page, Object... values);

    /**
     * 获取总数
     *
     * @param hasCondition
     * @param values
     * @return
     * @deprecated
     */
    public abstract long count(boolean hasCondition, Object... values);

}
