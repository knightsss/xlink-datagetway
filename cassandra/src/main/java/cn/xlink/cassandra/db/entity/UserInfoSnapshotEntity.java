package cn.xlink.cassandra.db.entity;

import java.util.Date;
import java.util.List;

import cn.xlink.cassandra.db.base.BaseEntity;

public class UserInfoSnapshotEntity extends BaseEntity{
	/**
	 * 用户id
	 */
	private int userId;
	/**
	 * 企业id
	 */
	private String corpId;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 认证码
	 */
	private String authorizeCode;
	/**
	 * 用户状态
	 */
	private int statusType;
	/**
	 * 用户来源
	 */
	private int sourceType;
	/**
	 * 所在区域id
	 */
	private int regionId;
	/**
	 * 本地语言
	 */
	private String localLang;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 用户账号
	 */
	private String account;
	/**
	 * 用户扩展属性
	 */
	private String extend;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 标签
	 */
	private List<String> tags;

	/**
	 * 手机
	 */
	private String phone;
	/**
	 * 手机是否认证
	 */
	private boolean phoneValid;
	/**
	 * 手机区号
	 */
	private int phoneZone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 邮箱是否认证
	 */
	private boolean emailValid;
	/**
	 * 激活时间
	 */
	private Date activeDate;
	/**
	 * 用户头像
	 */
	private String avatar;
	/**
	 * qq账号id
	 */
	private String qqOpenId;
	/**
	 * 微信账号id
	 */
	private String wxOpenId;
	/**
	 * 微博账号id
	 */
	private String wbOpenId;
	/**
	 * facebook id
	 */
	private String fbOpenId;
	/**
	 * 推特id
	 */
    private String ttOpenId;
	/**
	 * 其他第三方账号id
	 */
	private String otherOpenId;
	/**
	 * 所在国家
	 */
	private String country;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 所属应用id
	 */
	private String pluginId;
	/**
	 * 注册ip
	 */
	private String registerIp;
	/**
	 * 用户消息设置
	 */
	private List<String> messageSettings;
	/**
	 * 用户消息提醒设置
	 */
	private List<String> messageInformSetting;
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

    public int getUserId() {
        return userId;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getAuthorizeCode() {
        return authorizeCode;
    }

    public int getStatusType() {
        return statusType;
    }

    public int getSourceType() {
        return sourceType;
    }

    public int getRegionId() {
        return regionId;
    }

    public String getLocalLang() {
        return localLang;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getAccount() {
        return account;
    }

    public String getExtend() {
        return extend;
    }

    public String getRemark() {
        return remark;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isPhoneValid() {
        return phoneValid;
    }

    public int getPhoneZone() {
        return phoneZone;
    }

    public String getEmail() {
        return email;
    }

    public boolean isEmailValid() {
        return emailValid;
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public String getWbOpenId() {
        return wbOpenId;
    }

    public String getFbOpenId() {
        return fbOpenId;
    }

    public String getTtOpenId() {
        return ttOpenId;
    }

    public String getOtherOpenId() {
        return otherOpenId;
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

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getPluginId() {
        return pluginId;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public List<String> getMessageSettings() {
        return messageSettings;
    }

    public List<String> getMessageInformSetting() {
        return messageInformSetting;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setAuthorizeCode(String authorizeCode) {
        this.authorizeCode = authorizeCode;
    }

    public void setStatusType(int statusType) {
        this.statusType = statusType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public void setLocalLang(String localLang) {
        this.localLang = localLang;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPhoneValid(boolean phoneValid) {
        this.phoneValid = phoneValid;
    }

    public void setPhoneZone(int phoneZone) {
        this.phoneZone = phoneZone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailValid(boolean emailValid) {
        this.emailValid = emailValid;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public void setWbOpenId(String wbOpenId) {
        this.wbOpenId = wbOpenId;
    }

    public void setFbOpenId(String fbOpenId) {
        this.fbOpenId = fbOpenId;
    }

    public void setTtOpenId(String ttOpenId) {
        this.ttOpenId = ttOpenId;
    }

    public void setOtherOpenId(String otherOpenId) {
        this.otherOpenId = otherOpenId;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public void setMessageSettings(List<String> messageSettings) {
        this.messageSettings = messageSettings;
    }

    public void setMessageInformSetting(List<String> messageInformSetting) {
        this.messageInformSetting = messageInformSetting;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

}
