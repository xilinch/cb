package com.shishic.cb.bean;

import com.avos.avoscloud.AVObject;


public class HtmlAVObject extends AVObject {

    private String des;

    private String url;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "HtmlAVObject{" +
                "des='" + des + '\'' +
                ", url='" + url + '\'' +
                ", requestStatistic=" + requestStatistic +
                ", objectId='" + objectId + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", acl=" + acl +
                '}';
    }
}
