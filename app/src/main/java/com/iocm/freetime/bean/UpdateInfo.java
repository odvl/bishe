package com.iocm.freetime.bean;

/**
 * Created by Administrator on 2015/2/4.
 */
public class UpdateInfo {
    private String version;
    private String description;
    private String url;

    public UpdateInfo() {
    }

    public UpdateInfo(String version, String description, String url) {
        this.version = version;
        this.description = description;
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
