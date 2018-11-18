package com.shishic.cb.bean;

public class FreePlanBean extends BaseBean {

    private int id;

    private String content;

    private int jounal;

    public FreePlanBean(String content){
        this.content = content;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getJounal() {
        return jounal;
    }

    public void setJounal(int jounal) {
        this.jounal = jounal;
    }
}
