package com.iocm.freetime.bean;

import java.io.Serializable;

/**
 * Created by liubo on 16/1/2.
 */
public class MessageModel implements Serializable {

    private String publishUserId;
    private String publishUserName;
    private String joinUserId;
    private String joinUserName;
    private String taskName;
    private String taskId;
    private int state;
    private String leaveMessage;
    private String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getLeaveMessage() {
        return leaveMessage;
    }

    public void setLeaveMessage(String leaveMessage) {
        this.leaveMessage = leaveMessage;
    }

    public MessageModel() {
    }

    public MessageModel(String publishUserId, String publishUserName, String joinUserId, String joinUserName, String taskName, String taskId, int state) {
        this.publishUserId = publishUserId;
        this.publishUserName = publishUserName;
        this.joinUserId = joinUserId;
        this.joinUserName = joinUserName;
        this.taskName = taskName;
        this.taskId = taskId;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    public String getPublishUserName() {
        return publishUserName;
    }

    public void setPublishUserName(String publishUserName) {
        this.publishUserName = publishUserName;
    }

    public String getJoinUserId() {
        return joinUserId;
    }

    public void setJoinUserId(String joinUserId) {
        this.joinUserId = joinUserId;
    }

    public String getJoinUserName() {
        return joinUserName;
    }

    public void setJoinUserName(String joinUserName) {
        this.joinUserName = joinUserName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
