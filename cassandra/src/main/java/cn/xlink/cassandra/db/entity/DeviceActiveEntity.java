package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;

public class DeviceActiveEntity extends BaseEntity{
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 产品id
     */
    private String productId;
    /**
     * 设备id
     */
    private int deviceId;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 国家
     */
    private String country;
    /**
     * 省/州
     */
    private String province;
    /**
     * 城市
     */
    private String city;
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

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCorpId() {

        return corpId;
    }

    public String getProductId() {
        return productId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getIp() {
        return ip;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public Date getCreateTime() {
        return createTime;
    }

}
