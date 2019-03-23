package com.shishic.cb.bean;


public class NewsBean extends BaseBean {

    private int id;

    private String description;

    private String url;

    private int valid;

    private String icon;

    private String auther;

    private String time;

    private String title;


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

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", valid=" + valid +
                ", icon='" + icon + '\'' +
                ", auther='" + auther + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
