package cn.xlink.cassandra.db.repository.user.figure;


import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import com.datastax.driver.core.Row;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.UserFigureEntity;
import com.datastax.driver.core.Session;

import java.util.List;


/**
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class SuperUserFigureRepository extends BaseRepository<UserFigureEntity> {

    public SuperUserFigureRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected UserFigureEntity marshalRow(Row row) {
        UserFigureEntity entity = new UserFigureEntity();
        entity.setCorpId(row.getString("corp_id"));
        entity.setUserId(row.getInt("user_id"));
        entity.setLanguage(row.getString("language"));
        entity.setOperateSystem(row.getString("operate_system"));
        entity.setMachineType(row.getString("machine_type"));
        entity.setModelNumber(row.getString("model_number"));
        entity.setResolution(row.getString("resolution"));
        entity.setOsVersion(row.getString("os_version"));
        entity.setCarrier(row.getString("carrier"));
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
