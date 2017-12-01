package cn.xlink.cassandra.db.repository.device.active;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.DeviceActiveEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperDeviceActiveRepository extends BaseRepository<DeviceActiveEntity> {

    public SuperDeviceActiveRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected DeviceActiveEntity marshalRow(Row row) {
        DeviceActiveEntity entity = new DeviceActiveEntity();
        entity.setCorpId(row.getString("corp_id"));
        entity.setProductId(row.getString("product_id"));
        entity.setDeviceId(row.getInt("device_id"));
        entity.setIp(row.getString("ip"));
        entity.setCountry(row.getString("country"));
        entity.setProvince(row.getString("province"));
        entity.setCity(row.getString("city"));
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
