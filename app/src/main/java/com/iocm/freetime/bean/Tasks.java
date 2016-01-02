package com.iocm.freetime.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by liubo on 2015/1/24.
 */
@Table(name = "Tasks")
public class Tasks extends Model implements Serializable {

    @Column(name = "objectId" , unique = true)
    public String objectId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Column(name = "userId")
    public String userId;


    @Column(name = "title")
    public String title;

    @Column(name = "body")
    public String body;

    @Column(name = "beginTime")
    public String beginTime;

    @Column(name = "endTime")
    public String endTime;

    @Column(name = "phoneNumber")
    public String phoneNumber;

    @Column(name = "name")
    public String name;

    @Column(name = "type")
    public Type type;

    @Column(name = "msg")
    public String msg;

    @Column(name = "latitude")
    public Double latitude;

    @Column(name = "longitude")
    public Double longitude;

    @Column(name = "build")
    public String build;

    @Column(name = "like")
    public boolean like;

    public int joinedNum;

    public int getJoinedNum() {
        return joinedNum;
    }

    public void setJoinedNum(int joinedNum) {
        this.joinedNum = joinedNum;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public Tasks() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Tasks(String title, String body, String beginTime, String endTime, String phoneNumber,
                 String name, Type type, String msg, Double latitude, Double longitude, String build) {
        this.title = title;
        this.body = body;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.type = type;
        this.msg = msg;
        this.latitude = latitude;
        this.longitude = longitude;
        this.build = build;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
//        return "Activities{" +
//                "title='" + title + '\'' +
//                ", body='" + body + '\'' +
//                ", beginTime='" + beginTime + '\'' +
//                ", endTime='" + endTime + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", name='" + name + '\'' +
//                ", type=" + type +
//                '}';
        return name + "@" + title + "@" + body + "@" + beginTime + "@" + endTime + "@" + phoneNumber + "@" + type + "@" + msg + "@" + build + "@" + latitude + "@" + longitude;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        MATCH, PLAY, SCIENCE
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
