package cn.xlink.cassandra.db.repository.device.snapshot;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.DeviceInfoSnapshotEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperDeviceInfoSnapshotRepository extends BaseRepository<DeviceInfoSnapshotEntity> {

    public SuperDeviceInfoSnapshotRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected DeviceInfoSnapshotEntity marshalRow(Row row) {
        DeviceInfoSnapshotEntity entity = new DeviceInfoSnapshotEntity();
        entity.setDeviceId(row.getInt("device_id"));
        entity.setCorpId(row.getString("corp_id"));
        entity.setProductId(row.getString("product_id"));
        entity.setMac(row.getString("mac"));
        entity.setName(row.getString("name"));
        entity.setActive(row.getBool("is_active"));
        entity.setActiveDate(row.getTimestamp("active_date"));
        entity.setActiveCode(row.getString("active_code"));
        entity.setAuthorizeCode(row.getString("authorize_code"));
        entity.setMcuMod(row.getString("mcu_mod"));
        entity.setMcuVersion(row.getInt("mcu_version"));
        entity.setFirmwareMod(row.getString("firmware_mod"));
        entity.setFirmwareVersion(row.getInt("firmware_version"));
        entity.setRegionId(row.getInt("region_id"));
        entity.setAccessKey(row.getInt("access_key"));
        entity.setSn(row.getString("sn"));
        entity.setDomain(row.getString("domain"));
        entity.setCreateTime(row.getTimestamp("create_time"));
        entity.setCorpId(row.getString("corp_id"));
        entity.setCreatorId(row.getString("creator_id"));
        entity.setCreatorType(row.getInt("creator_type"));
        entity.setActiveIp(row.getString("active_ip"));
        entity.setExtend(row.getString("extend"));
        entity.setTags(row.getList("tags",String.class));
        entity.setDealerScope(row.getString("dealer_scope"));
        entity.setHeavyBuyer(row.getString("heavy_buyer"));
        entity.setHeavyBuyerOrganization(row.getString("heavy_buyer_organization"));
        entity.setQrkey(row.getString("qrkey"));
        entity.setHomeId(row.getString("home_id"));
        entity.setGroups(row.getList("groups",Integer.class));
        entity.setSoftResetDate(row.getTimestamp("soft_reset_date"));
        entity.setLastResetDate(row.getTimestamp("last_reset_date"));
        entity.setReportTime(row.getTimestamp("report_time"));
        return entity;
    }

    @Override
    protected List<TwoTuple<String, OperationType>> secondConditions() {
        return defaultSecondConditions("report_time");
    }

    @Override
    protected TwoTuple<String, OrderByType> orderByCondition() {
        return defaultOrderByCondition("report_time");
    }
}
