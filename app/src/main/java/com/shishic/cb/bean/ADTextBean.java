package com.shishic.cb.bean;


public class ADTextBean extends BaseBean {

    private int id;


    private String text;


    private String url;


    public ADTextBean(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ADTextBean{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
