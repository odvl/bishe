package com.iocm.freetime.base;

import java.io.Serializable;

/**
 * Created by liubo on 15/6/14.
 */
public  class  ItemData<T> implements Serializable {
    int type;
    T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ItemData(int type, T data) {
        this.type = type;
        this.data = data;
    }
}
