package cn.xlink.cassandra.db.entity;

import java.util.Date;

import cn.xlink.cassandra.db.base.BaseEntity;

public class DeviceOnlineEntity extends BaseEntity{
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
	 * 所属区域id
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
	private int firmwareVersion;
	private String firmwareMod;
	/**
	 * mac地址
	 */
	private String mac;
	/**
	 * 连接协议
	 */
	private int connectProtocol;
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

    public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(int firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getFirmwareMod() {
		return firmwareMod;
	}

	public void setFirmwareMod(String firmwareMod) {
		this.firmwareMod = firmwareMod;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getConnectProtocol() {
		return connectProtocol;
	}

	public void setConnectProtocol(int connectProtocol) {
		this.connectProtocol = connectProtocol;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
