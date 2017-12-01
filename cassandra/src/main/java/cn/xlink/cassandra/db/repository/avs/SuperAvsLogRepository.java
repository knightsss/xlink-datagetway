package cn.xlink.cassandra.db.repository.avs;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.AvsLogEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperAvsLogRepository extends BaseRepository<AvsLogEntity> {

    public SuperAvsLogRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected AvsLogEntity marshalRow(Row row) {
        AvsLogEntity entity = new AvsLogEntity();
        entity.setCorpId(row.getString("corp_id"));
        entity.setUserId(row.getInt("user_id"));
        entity.setAvsType(row.getInt("avs_type"));
        entity.setReq(row.getString("req"));
        entity.setResp(row.getString("resp"));
        entity.setAction(row.getString("action"));
        entity.setDeviceId(row.getString("device_id"));
        entity.setApplianceId(row.getString("appliance_id"));
        entity.setProductId(row.getString("product_id"));
        entity.setCreateTime(row.getTimestamp("create_time"));
        return entity;
    }

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        return defaultSecondConditions("create_time");
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return defaultOrderByCondition("create_time");
    }

}
