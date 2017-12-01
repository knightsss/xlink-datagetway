package cn.xlink.cassandra.db.repository.remotecontrol;

import cn.xlink.cassandra.db.CassandraConstants;
import cn.xlink.cassandra.db.entity.RemoteControlLogEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Created by Zhengzhenxie on 2017/8/17.
 */
public class RemoteControlLogByUserIdRepository extends SuperRemoteControlLogRepository {

    public RemoteControlLogByUserIdRepository(Session session) {
        super(session, CassandraConstants.T_REMOTE_CONTROL_LOG_USER);
    }

    @Override
    protected RemoteControlLogEntity marshalRow(Row row) {
        RemoteControlLogEntity entity = new RemoteControlLogEntity();
        entity.setKey(row.getInt("user_id"));
        entity.setLinkIds(row.getList("link_id", Integer.class));
        entity.setCorpId(row.getString("corp_id"));
        entity.setProductId(row.getString("product_id"));
        entity.setConnectProtocol(row.getInt("connect_protocol"));
        entity.setProtocolSouce(row.getInt("protocol_souce"));
        entity.setRegionId(row.getInt("region_id"));
        entity.setCmId(row.getString("cm_id"));
        entity.setPackageType(row.getInt("package_type"));
        entity.setFlowType(row.getInt("flow_type"));
        entity.setData(row.getString("data"));
        entity.setCreateTime(row.getTimestamp("create_time"));
        return entity;
    }

    @Override
    protected String firstConditionKey() {
        return "user_id";
    }
}
