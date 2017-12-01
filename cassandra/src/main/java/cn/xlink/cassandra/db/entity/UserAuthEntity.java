package cn.xlink.cassandra.db.entity;

import java.util.Date;

import cn.xlink.cassandra.db.base.BaseEntity;

public class UserAuthEntity extends BaseEntity{

    /**
     * 企业id
     */
    private String corpId;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 认证ip
     */
    private String ip;
    /**
     * 注册时间
     */
    private Date registerDate;
    /**
     * 验证时间
     */
    private Date authTime;
    /**
     * 验证日期
     * private String authDate;
     */

    /**
     * 验证小时
     * private int authHours;
     */

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public void setAuthTime(Date authTime) {
        this.authTime = authTime;
    }

    public String getCorpId() {

        return corpId;
    }

    public int getUserId() {
        return userId;
    }

    public String getAccount() {
        return account;
    }

    public String getIp() {
        return ip;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public Date getAuthTime() {
        return authTime;
    }

}
