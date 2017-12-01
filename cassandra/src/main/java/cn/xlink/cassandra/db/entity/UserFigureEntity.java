package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;

public class UserFigureEntity extends BaseEntity  {

    /**
     * 企业id
     */
    private String corpId;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 客户端使用的语言
     */
    private String language;
    /**
     * 操作系统
     */
    private String operateSystem;
    /**
     * 机型
     */
    private String machineType;
    /**
     * 终端型号
     */
    private String modelNumber;
    /**
     * 分辨率
     */
    private String resolution;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 运营商
     */
    private String carrier;
    /**
     * 上报时间
     */
    private Date reportTime;
    /**
     * 上报日期
     *  private String reportDate;
     */

    /**
     * 上报小时
     * private int reportHours;
     */

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOperateSystem(String operateSystem) {
        this.operateSystem = operateSystem;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getCorpId() {
        return corpId;
    }

    public int getUserId() {
        return userId;
    }

    public String getLanguage() {
        return language;
    }

    public String getOperateSystem() {
        return operateSystem;
    }

    public String getMachineType() {
        return machineType;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public String getResolution() {
        return resolution;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public String getCarrier() {
        return carrier;
    }

    public Date getReportTime() {
        return reportTime;
    }

}
