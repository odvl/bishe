package com.iocm.freetime.bean;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by liubo on 2015/1/24.
 */
@Table(name = "SearchHistory")
public class SearchHistory extends Model implements Serializable {

    @Column(name = "key" , unique = true)
    public String key;

    @Column(name = "time")
    public long time;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}