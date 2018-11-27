package com.shishic.cb.bean;


public class FunBean extends BaseBean {

    private int id;

    private int functionCode;

    private String description;

    private String url;

    private int valid;

    private String icon;

    public FunBean(String description, String url, String icon) {
        this.description = description;
        this.url = url;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    @Override
    public String toString() {
        return "ADIMGBean{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", valid='" + valid + '\'' +
                ", functionCode='" + functionCode + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }

}
