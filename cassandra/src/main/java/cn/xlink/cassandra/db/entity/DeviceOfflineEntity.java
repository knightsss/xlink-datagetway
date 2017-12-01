package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;

public class DeviceOfflineEntity extends BaseEntity {
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 设备id
     */
    private int deviceId;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 所属区域
     */
    private int regionId;
    /**
     * cmid
     */
    private String cmId;
    /**
     * ip地址
     */
    private String ip;
    /**
     * mac地址
     */
    private String mac;
    private long onlineCount;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建日期
     * private String create_date;
     */

    /**
     * 创建小时
     * private int createHours;
     */

    public int getDeviceId() {
        return deviceId;
    }

    public String getProductId() {
        return productId;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getCmId() {
        return cmId;
    }

    public String getIp() {
        return ip;
    }

    public String getMac() {
        return mac;
    }

    public long getOnlineCount() {
        return onlineCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCorpId() {

        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setCmId(String cmId) {
        this.cmId = cmId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setOnlineCount(long onlineCount) {
        this.onlineCount = onlineCount;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}


