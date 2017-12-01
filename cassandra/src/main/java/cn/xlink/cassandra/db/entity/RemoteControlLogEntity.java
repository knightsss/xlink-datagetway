package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;
import java.util.List;

public class RemoteControlLogEntity extends BaseEntity {
    /**
     * 用户id或者设备id
     */
    private int key;
    /**
     * 上游或下游ID
     */
    private List<Integer> linkIds;
    /**
     * 企业类型
     */
    private String corpId;
    /**
     * 产品ID
     */
    private String productId;
    /**
     * CM协议版本
     */
    private int connectProtocol;
    /**
     * 协议来源
     */
    private int protocolSouce;
    /**
     * 区域ID
     */
    private int regionId;
    /**
     * CM ID
     */
    private String cmId;
    /**
     * 数据包类型
     */
    private int packageType;
    /**
     * 包数据流向类型
     */
    private int flowType;
    /**
     * 数据包
     */
    private String data;
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
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public List<Integer> getLinkIds() {
        return linkIds;
    }

    public void setLinkIds(List<Integer> linkIds) {
        this.linkIds = linkIds;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getConnectProtocol() {
        return connectProtocol;
    }

    public void setConnectProtocol(int connectProtocol) {
        this.connectProtocol = connectProtocol;
    }

    public int getProtocolSouce() {
        return protocolSouce;
    }

    public void setProtocolSouce(int protocolSouce) {
        this.protocolSouce = protocolSouce;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getCmId() {
        return cmId;
    }

    public void setCmId(String cmId) {
        this.cmId = cmId;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
