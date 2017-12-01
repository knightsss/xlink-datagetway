package cn.xlink.cassandra.db.base;

import java.util.*;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.Tuple;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import com.datastax.driver.core.*;
import com.strategicgains.repoexpress.cassandra.AbstractCassandraRepository;
import com.strategicgains.repoexpress.domain.Identifier;

import cn.xlink.cassandra.core.utils.StringTools;
import org.apache.log4j.Logger;


/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class BaseRepository<T extends BaseEntity> extends AbstractCassandraRepository<T> {
    protected final Logger logger = Logger.getLogger("cassandra repository");

    protected static final String READ_ALL_CQL = "select * from %s";
    protected static final String READ_ALL_COUNT_CQL = "select count(*) from %s";

    /**
     * 查询所有（除了主键过滤，无其他条件）
     */
    protected PreparedStatement readAllStmt;
    /**
     * 查询所有（除了主键过滤，还有其他条件）
     */
    protected PreparedStatement readAllByConditionStmt;
    /**
     * 查询所有的总数(除了主键过滤，无其他条件)
     */
    protected PreparedStatement readAllCountStmt;
    /**
     * 查询所有的总数(除了主键过滤，还有其他条件)
     */
    protected PreparedStatement readAllCountByConditionStmt;

    public BaseRepository(Session session, String tableName) {
        super(session, tableName);
        init();
    }

    private void init() {
        String readAllCql = query(READ_ALL_CQL, false);
        String readAllCountCql = queryNoOrderBy(READ_ALL_COUNT_CQL, false);
        readAllStmt = getSession().prepare(readAllCql);
        readAllCountStmt = getSession().prepare(readAllCountCql);
        if (!StringTools.isEmpty(second())) {
            String readAllCqlHasCon = query(READ_ALL_CQL, true);
            String readAllCountHasCon = queryNoOrderBy(READ_ALL_COUNT_CQL, true);
            readAllByConditionStmt = getSession().prepare(readAllCqlHasCon);
            readAllCountByConditionStmt = getSession().prepare(readAllCountHasCon);
        }
    }

    /**
     * 获取预处理的查询条件
     *
     * @param cql
     * @param hasCondition
     * @return
     */
    private String query(String cql, boolean hasCondition) {
        return hasCondition ? String.format(new StringBuffer().append(cql).append(" where ")
                .append(first()).append(" and ")
                .append(second())
                .append(orderBy()).toString(), getTable())
                : String.format(new StringBuffer().append(cql).append(" where ")
                .append(first())
                .append(orderBy()).toString(), getTable());
    }

    /**
     * 获取预处理的查询条件(无排序)
     *
     * @param cql
     * @param hasCondition
     * @return
     */
    private String queryNoOrderBy(String cql, boolean hasCondition) {
        return hasCondition ? String.format(new StringBuffer().append(cql).append(" where ").append(first()).append(" and ")
                .append(second()).toString(), getTable())
                : String.format(new StringBuffer().append(cql).append(" where ").append(first()).toString(), getTable());
    }

    /**
     * 分页读取所有数据
     *
     * @param hasCondition
     * @param limit        每页数据量
     * @param page         当前页数
     * @param values
     * @return
     */
    public final List<T> readByPaging(boolean hasCondition, int limit, int page, Object... values) {
        try {
            BoundStatement bs = new BoundStatement(hasCondition ? readAllByConditionStmt : readAllStmt);
            bs.setFetchSize(limit);
            bs.bind(values);
            return (marshalAllByPaging(limit, page, getSession().execute(bs)));
        } catch (Exception e) {
            logger.error("", e);
            return new ArrayList<>();
        }
    }

    /**
     * 读取所有数据
     *
     * @param hasCondition
     * @param values
     * @return
     */
    public final List<T> readAll(boolean hasCondition, Object... values) {

        try {
            BoundStatement bs = new BoundStatement(hasCondition ? readAllByConditionStmt : readAllStmt);
            bs.bind(values);
            return (marshalAll(getSession().execute(bs)));
        } catch (Exception e) {
            logger.error("",e);
            return new ArrayList<>();
        }
    }

    /**
     * 获取总数
     *
     * @param hasCondition
     * @param values
     * @return
     * @deprecated
     */
    public final long count(boolean hasCondition, Object... values) {
        try {
            BoundStatement bs = new BoundStatement(hasCondition ? readAllCountByConditionStmt : readAllCountStmt);
            bs.bind(values);
            return (getSession().execute(bs).one().getLong(0));
        } catch (Exception e) {
            logger.error("",e);
            return 0;
        }
    }


    /**
     * 分页处理数据集
     *
     * @param limit
     * @param page
     * @param rs
     * @return
     */
    private List<T> marshalAllByPaging(int limit, int page, ResultSet rs) {
        List<T> results = new ArrayList<>();
        Set<String> set = new HashSet<>();
        PagingState pageState;
        int preFetchSize = limit >> 1;
        for (Row row : rs) {
            //记录分页状态
            pageState = rs.getExecutionInfo().getPagingState();
            if (null != pageState) {
                set.add(pageState.toString());
            } else {
                set.add("");
            }

            //如果分页状态还没到达请求的分页数，丢弃掉前面的数据
            if (set.size() < page) {
                continue;
            }

            //如果分页状态已经超过请求的分页数，丢弃后面的数据
            if (set.size() > page) {
                break;
            }

            //预加载数据
            if (rs.getAvailableWithoutFetching() == preFetchSize && !rs.isFullyFetched()) {
                rs.fetchMoreResults();
            }

            //处理返回的业务数据
            results.add(marsha(row));
        }
        return results;
    }


    /**
     * 处理数据集
     *
     * @param rs
     * @return
     */
    private List<T> marshalAll(ResultSet rs) {
        List<T> results = new ArrayList<>();
        Iterator<Row> i = rs.iterator();

        while (i.hasNext()) {
            results.add(marsha(i.next()));
        }

        return results;
    }

    /**
     * 第一查询条件（主键）必须，只支持"="
     *
     * @return
     */
    protected abstract String firstConditionKey();

    private String first() {
        String key = firstConditionKey();
        return key + " = ?";
    }

    /**
     * 第二查询条件 非必须
     *
     * @return
     */
    protected abstract List<TwoTuple<String, OperationType>> secondConditions();

    private String second() {
        List<TwoTuple<String, OperationType>> condition = secondConditions();
        if (null != condition) {
            int end = condition.size();
            int i = 0;
            StringBuffer sb = new StringBuffer();
            for (TwoTuple<String, OperationType> tuple : condition) {
                i++;
                if (i != end) {
                    sb.append(tuple.first).append(" ").append(tuple.second.type()).append(" ?").append(" and ");
                } else {
                    sb.append(tuple.first).append(" ").append(tuple.second.type()).append(" ?");
                }
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 默认第二查询条件 key > ? and key < ?
     *
     * @param key
     * @return
     */
    protected final List<TwoTuple<String, OperationType>> defaultSecondConditions(String key) {
        List<TwoTuple<String, OperationType>> ls = new ArrayList<>();
        ls.add(Tuple.tuple(key, OperationType.Gt));
        ls.add(Tuple.tuple(key, OperationType.Lt));
        return ls;
    }


    /**
     * 排序条件,仅支持一个排序条件,非必须
     *
     * @return
     */
    protected abstract TwoTuple<String, OrderByType> orderByCondition();

    private String orderBy() {
        TwoTuple<String, OrderByType> tuple = orderByCondition();
        if (null != tuple) {
            StringBuffer sb = new StringBuffer();
            sb.append(" order by ").append(tuple.first).append(" ").append(tuple.second.type());
            return sb.toString();
        }
        return "";
    }

    /**
     * 默认排序 key order by desc
     *
     * @param key
     * @return
     */
    protected final TwoTuple<String, OrderByType> defaultOrderByCondition(String key) {
        return Tuple.tuple(key, OrderByType.Desc);
    }

    /**
     * 返回的数据集处理
     *
     * @param row
     * @return
     */
    protected abstract T marshalRow(Row row);

    /**
     * 返回的数据集处理
     *
     * @param row
     * @return
     */
    private final T marsha(Row row) {
        if (null == row) {
            return null;
        }
        return marshalRow(row);
    }

    @Override
    protected T readEntityById(Identifier identifier) {
        return null;
    }

    @Override
    protected T createEntity(T entity) {
        return null;
    }

    @Override
    protected T updateEntity(T entity) {
        return null;
    }

    @Override
    protected void deleteEntity(T entity) {
    }
}
