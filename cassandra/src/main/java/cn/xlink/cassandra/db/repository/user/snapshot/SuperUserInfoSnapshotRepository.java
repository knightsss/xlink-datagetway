package cn.xlink.cassandra.db.repository.user.snapshot;

import cn.xlink.cassandra.core.type.OperationType;
import cn.xlink.cassandra.core.type.OrderByType;
import cn.xlink.cassandra.core.utils.tuple.TwoTuple;
import cn.xlink.cassandra.db.base.BaseRepository;
import cn.xlink.cassandra.db.entity.UserInfoSnapshotEntity;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.List;

public abstract class SuperUserInfoSnapshotRepository extends BaseRepository<UserInfoSnapshotEntity> {
    public SuperUserInfoSnapshotRepository(Session session, String tableName) {
        super(session, tableName);
    }

    @Override
    protected UserInfoSnapshotEntity marshalRow(Row row) {
        UserInfoSnapshotEntity entity = new UserInfoSnapshotEntity();
        entity.setUserId(row.getInt("user_id"));
        entity.setCorpId(row.getString("corp_id"));
        entity.setNickname(row.getString("nickname"));
        entity.setAuthorizeCode(row.getString("authorize_code"));
        entity.setStatusType(row.getInt("status_type"));
        entity.setSourceType(row.getInt("source_type"));
        entity.setRegionId(row.getInt("region_id"));
        entity.setLocalLang(row.getString("local_lang"));
        entity.setCreateDate(row.getTimestamp("create_date"));
        entity.setAccount(row.getString("account"));
        entity.setExtend(row.getString("extend"));
        entity.setRemark(row.getString("remark"));
        entity.setTags(row.getList("tags", String.class));
        entity.setPhone(row.getString("phone"));
        entity.setPhoneValid(row.getBool("phone_valid"));
        entity.setPhoneZone(row.getInt("phone_zone"));
        entity.setEmail(row.getString("email"));
        entity.setEmailValid(row.getBool("email_valid"));
        entity.setActiveDate(row.getTimestamp("active_date"));
        entity.setAvatar(row.getString("avatar"));
        entity.setQqOpenId(row.getString("qq_open_id"));
        entity.setWxOpenId(row.getString("wx_open_id"));
        entity.setWbOpenId(row.getString("wb_open_id"));
        entity.setFbOpenId(row.getString("fb_open_id"));
        entity.setTtOpenId(row.getString("tt_open_id"));
        entity.setOtherOpenId(row.getString("other_open_id"));
        entity.setCountry(row.getString("country"));
        entity.setProvince(row.getString("province"));
        entity.setCity(row.getString("city"));
        entity.setGender(row.getString("gender"));
        entity.setAddress(row.getString("address"));
        entity.setAge(row.getInt("age"));
        entity.setPluginId(row.getString("plugin_id"));
        entity.setRegisterIp(row.getString("register_ip"));
        entity.setMessageSettings(row.getList("message_settings", String.class));
        entity.setMessageInformSetting(row.getList("message_inform_setting", String.class));
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
