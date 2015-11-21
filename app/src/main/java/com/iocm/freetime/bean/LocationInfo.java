package com.iocm.freetime.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/3/18.
 */
public class LocationInfo implements Serializable {
    private String name;
    private String distance;
    private String address;
    private double latitude;
    private double longitude;

    public LocationInfo() {
    }

    public LocationInfo(String name, String distance, String address, double latitude, double longitude) {
        this.name = name;
        this.distance = distance;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
