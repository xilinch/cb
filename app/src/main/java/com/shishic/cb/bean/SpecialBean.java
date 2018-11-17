package com.shishic.cb.bean;

public class SpecialBean extends BaseBean {
    /**
     * id : 1
     * name : 唐寅
     * introduce : 字伯虎，江南四大才子之首
     * content : 为人风流倜傥、玉树凌风。琴棋书画样样精通。
     * contact : 微信：tangbohu 手机：188888888
     * fee : 1000
     * valid : 1
     * createTime : 1542433711000
     * updateTime : 1542433711000
     * payed : true
     */

    private int id;
    private String name;
    private String introduce;
    private String content;
    private String contact;
    private int fee;
    private int valid;
    private long createTime;
    private long updateTime;
    private boolean payed;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
