package cn.xlink.cassandra.db.repository.device.online;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.DeviceOnlineEntity;

import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperDeviceOnlineRepository extends BaseRepository<DeviceOnlineEntity>{

    public SuperDeviceOnlineRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected DeviceOnlineEntity marshalRow(Row row) {
        DeviceOnlineEntity entity = new DeviceOnlineEntity();
        entity.setCorpId(row.getString("corp_id"));
        entity.setDeviceId(row.getInt("device_id"));
        entity.setProductId(row.getString("product_id"));
        entity.setRegionId(row.getInt("region_id"));
        entity.setCmId(row.getString("cm_id"));
        entity.setIp(row.getString("ip"));
        entity.setFirmwareVersion(row.getInt("firmware_version"));
        entity.setFirmwareMod(row.getString("firmware_mod"));
        entity.setMac(row.getString("mac"));
        entity.setConnectProtocol(row.getInt("connect_protocol"));
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
