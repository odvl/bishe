package com.iocm.freetime.bean;

import java.io.Serializable;

/**
 * Created by liubo on 16/1/6.
 */
public class UserValue implements Serializable{

    private String username;
    private String userId;

    public UserValue(String username, String userId) {
        this.username = username;
        this.userId = userId;
    }

    public UserValue() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
