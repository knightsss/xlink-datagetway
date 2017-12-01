package cn.xlink.cassandra.db.entity;

import cn.xlink.cassandra.db.base.BaseEntity;

import java.util.Date;

/**
 * Created by Zhengzhenxie on 2017/9/25.
 */
public class TimerTaskLogEntity extends BaseEntity {
    /**
     * 执行日志标识
     */
    private String executeId;
    /**
     * 执行任务标识
     */
    private String taskId;
    /**
     * 执行任务名称
     */
    private String taskName;
    /**
     * 执行时间
     */
    private Date executeTime;
    /**
     * 拥有者, 用户为USER_用户ID, 成员则为MEMBER_成员ID
     */
    private String owner;
    /**
     * 定时作业业务内容
     */
    private String action;
    /**
     * 执行状态, 1:成功, 2:失败
     */
    private int status;
    /**
     * 执行失败时错误码, 6001000:成功, 6001001:设备不在线, 6001002:网络超时
     */
    private String error;

    /**
     * 执行结果
     */
    private String result;

    public String getExecuteId() {
        return executeId;
    }

    public void setExecuteId(String executeId) {
        this.executeId = executeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = executeTime;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
