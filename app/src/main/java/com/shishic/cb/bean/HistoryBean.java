package com.shishic.cb.bean;

import java.util.ArrayList;
import java.util.Arrays;

public class HistoryBean extends BaseBean {


    /**
     * id : 25
     * journal : 20181117060
     * n1 : 1
     * n2 : 4
     * n3 : 1
     * n4 : 8
     * n5 : 2
     * valid : 1
     * createTime : 1542441666000
     * updateTime : 1542442201000
     */

    private int id;
    private long journal;
    private int n1;
    private int n2;
    private int n3;
    private int n4;
    private int n5;
    private int valid;
    private long createTime;
    private long updateTime;
    //连出
    private int[][] lc = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
    //本地类型，-1标识头部
    private int type ;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getJournal() {
        return journal;
    }

    public void setJournal(long journal) {
        this.journal = journal;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getN3() {
        return n3;
    }

    public void setN3(int n3) {
        this.n3 = n3;
    }

    public int getN4() {
        return n4;
    }

    public void setN4(int n4) {
        this.n4 = n4;
    }

    public int getN5() {
        return n5;
    }

    public void setN5(int n5) {
        this.n5 = n5;
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

    public int[][] getLc() {
        return lc;
    }

    public void setLc(int[][] lc) {
        this.lc = lc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                ", journal=" + journal +
                ", n1=" + n1 +
                ", n2=" + n2 +
                ", n3=" + n3 +
                ", n4=" + n4 +
                ", n5=" + n5 +
                ", lc=" + erWeiInt2String(lc) +
                '}';
    }

    private String erWeiInt2String(int[][] numbs){
        String string = "";
        if(numbs != null){
            int size  = numbs.length;
            for(int i =0; i < size;i++){
                string = string + "{" + i + "{";
                int length = numbs[i].length;
                for(int j = 0; j < length; j++){
                    string = string + numbs[i][j] + ",";
                }
            }
        }
        return string;
    }
}
