package com.iocm.freetime.bean;

/**
 * Created by Administrator on 2015/2/28.
 */
public class UserMsg {
    private String userIssue;
    private String userJoin;
    private String agree;
    private MsgType type;
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getUserJoin() {
        return userJoin;
    }

    public void setUserJoin(String userJoin) {
        this.userJoin = userJoin;
    }

    public String getUserIssue() {
        return userIssue;
    }

    public void setUserIssue(String userIssue) {
        this.userIssue = userIssue;
    }
}
