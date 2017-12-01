package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;

public class AvsLogEntity extends BaseEntity {
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 用户id
     */
    private int userId;
    /**
     * avs日志类型
     */
    private int avsType;
    /**
     * 请求内容
     */
    private String req;
    /**
     * 回复内容
     */
    private String resp;
    /**
     * 执行指令
     */
    private String action;
    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * 子设备ID
     */
    private String applianceId;
    /**
     * 设备所属产品ID
     */
    private String productId;
    /**
     * 日志时间
     */
    private Date createTime;
    /**
     * 日志日期
     * private String create_date;
     */

    /**
     * 日志小时
     * private int createHours;
     */

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAvsType() {
        return avsType;
    }

    public void setAvsType(int avsType) {
        this.avsType = avsType;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
