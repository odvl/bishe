package com.iocm.freetime.bean;

/**
 * Created by liubo on 15/6/14.
 */
public class TaskTypeName {
    private int typeId;
    private String typeName;


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public TaskTypeName(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }
}
