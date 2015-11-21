package com.iocm.scrolldatepick.modle;

/**
 * Created by liubo on 15/10/16.
 */
public  class NameValue<T> {
    private String name;
    private T value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
