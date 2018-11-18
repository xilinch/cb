package com.shishic.cb.bean;

public class FreePlanTabBean extends BaseBean {


    /**
     * id : 1
     * name : 赢彩计划
     * valid : 1
     * createTime : 1542542721000
     * updateTime : 1542542726000
     */

    private int id;
    private String name;
    private int valid;
    private long createTime;
    private long updateTime;

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
}
