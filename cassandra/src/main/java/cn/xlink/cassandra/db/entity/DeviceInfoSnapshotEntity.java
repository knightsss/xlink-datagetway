package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;
import java.util.List;

public class DeviceInfoSnapshotEntity extends BaseEntity {
    /**
     * 设备id
     */
    private int deviceId;
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 产品id
     */
    private String productId;
    /**
     * mac地址
     */
    private String mac;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 是否激活
     */
    private boolean isActive;
    /**
     * 激活日期
     */
    private Date activeDate;
    /**
     * 激活码
     */
    private String activeCode;
    /**
     * 授权码
     */
    private String authorizeCode;
    /**
     * MCU型号
     */
    private String mcuMod;
    /**
     * MCU版本号
     */
    private int mcuVersion;
    /**
     * 固件型号
     */
    private String firmwareMod;

    /**
     * 固件版本号
     */
    private int firmwareVersion;
    /**
     * 所在区域ID
     */
    private int regionId;
    /**
     * 设备内网访问密码
     */
    private int accessKey;
    /**
     * 设备序列号
     */
    private String sn;

    /**
     * 域、或分组
     */
    private String domain;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建者ID
     */
    private String creatorId;
    /**
     * 创建者类型
     */
    private int creatorType;
    /**
     * 激活时IP
     */
    private String activeIp;
    /**
     * 扩展属性
     */
    private String extend;
    /**
     * 标签
     */
    private List<String> tags;
    /**
     * 经销商可访问的范围
     */
    private String dealerScope;
    /**
     * 大客户的id
     */
    private String heavyBuyer;
    /**
     * 所属大客户组织命名空间
     */
    private String heavyBuyer_organization;
    /**
     * 二维码key
     */
    private String qrkey;
    /**
     * 所属Home ID
     */
    private String homeId;
    /**
     * 所属分组
     */
    private List<Integer> groups;

    /**
     * 软重置时间
     */
    private Date softResetDate;
    /**
     * 设备最后一次reset时间
     */
    private Date lastResetDate;
    /**
     * 上报时间
     */
    private Date reportTime;
    /**
     * 上报日期
     * private String reportDate;
     */

    /**
     * 上报小时
     * private int reportHours;
     */

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }

    public void setMcuMod(String mcuMod) {
        this.mcuMod = mcuMod;
    }

    public void setMcuVersion(int mcuVersion) {
        this.mcuVersion = mcuVersion;
    }

    public void setFirmwareMod(String firmwareMod) {
        this.firmwareMod = firmwareMod;
    }

    public void setFirmwareVersion(int firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setAccessKey(int accessKey) {
        this.accessKey = accessKey;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public void setCreatorType(int creatorType) {
        this.creatorType = creatorType;
    }

    public void setActiveIp(String activeIp) {
        this.activeIp = activeIp;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setDealerScope(String dealerScope) {
        this.dealerScope = dealerScope;
    }

    public void setHeavyBuyer(String heavyBuyer) {
        this.heavyBuyer = heavyBuyer;
    }

    public void setHeavyBuyerOrganization(String heavyBuyerOrganization) {
        this.heavyBuyer_organization = heavyBuyerOrganization;
    }

    public void setQrkey(String qrkey) {
        this.qrkey = qrkey;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public void setSoftResetDate(Date softResetDate) {
        this.softResetDate = softResetDate;
    }

    public void setLastResetDate(Date lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public int getDeviceId() {

        return deviceId;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getProductId() {
        return productId;
    }

    public String getMac() {
        return mac;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public String getAuthorizeCode() {
        return authorizeCode;
    }

    public String getMcuMod() {
        return mcuMod;
    }

    public int getMcuVersion() {
        return mcuVersion;
    }

    public String getFirmwareMod() {
        return firmwareMod;
    }

    public int getFirmwareVersion() {
        return firmwareVersion;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getAccessKey() {
        return accessKey;
    }

    public String getSn() {
        return sn;
    }

    public String getDomain() {
        return domain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public int getCreatorType() {
        return creatorType;
    }

    public String getActiveIp() {
        return activeIp;
    }

    public String getExtend() {
        return extend;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getDealerScope() {
        return dealerScope;
    }

    public String getHeavyBuyer() {
        return heavyBuyer;
    }

    public String getHeavyBuyerOrganization() {
        return heavyBuyer_organization;
    }

    public String getQrkey() {
        return qrkey;
    }

    public String getHomeId() {
        return homeId;
    }

    public List<Integer> getGroups() {
        return groups;
    }

    public Date getSoftResetDate() {
        return softResetDate;
    }

    public Date getLastResetDate() {
        return lastResetDate;
    }

    public Date getReportTime() {
        return reportTime;
    }

}
