package com.iocm.freetime.activity;

import android.app.Application;

/**
 * Created by Administrator on 2015/2/13.
 */
public class UserLoginApp extends Application {
    private String userName;
    private String passWord;
    private String phoneNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
