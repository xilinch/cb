package com.shishic.cb.bean;

public class ShuidaoBean extends BaseBean {


    private int id;
    private String name;
    private String introduce;
    private String icon;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ShuidaoBean( String introduce, String icon, String url) {
        this.id = id;
        this.name = name;
        this.introduce = introduce;
        this.icon = icon;
        this.url = url;
    }
}
