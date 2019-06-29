package com.shishic.cb.bean;

import android.text.TextUtils;

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
     * openCode:"5,6,0,8,2",
     * "luckyType": 1,
     * "expect": "20190602044"
     */

    private int id;
    private long journal;
    private int n1;
    private int n2;
    private int n3;
    private int n4;
    private int n5;
    private int n6;
    private int n7;
    private int n8;
    private int n9;
    private int n10;
    private int valid;
    private long createTime;
    private long updateTime;
    //彩种
    private int luckyType;
    //开奖号码
    private String openCode;
    //预测期号
    private String expect;

    //连出，根据号码的数量计算
    private int[][] lc = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};
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

    public int getN6() {
        return n6;
    }

    public void setN6(int n6) {
        this.n6 = n6;
    }

    public int getN7() {
        return n7;
    }

    public void setN7(int n7) {
        this.n7 = n7;
    }

    public int getN8() {
        return n8;
    }

    public void setN8(int n8) {
        this.n8 = n8;
    }

    public int getN9() {
        return n9;
    }

    public void setN9(int n9) {
        this.n9 = n9;
    }

    public int getN10() {
        return n10;
    }

    public void setN10(int n10) {
        this.n10 = n10;
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

    public int getLuckyType() {
        return luckyType;
    }

    public void setLuckyType(int luckyType) {
        this.luckyType = luckyType;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    /**
     * 转化以后进行计算
     */
    public void translate2Old(){
        if(TextUtils.isEmpty(openCode)){
            return;
        }
        if(luckyType < 5 ){
            //时时彩类
            if(!TextUtils.isEmpty(openCode)){
                String[] opencodeStrs = openCode.split(",");
                int size = opencodeStrs.length;
                if(size >= 5){
                    //分析前5
                    n5 = Integer.valueOf(opencodeStrs[0]);
                    n4 = Integer.valueOf(opencodeStrs[1]);
                    n3 = Integer.valueOf(opencodeStrs[2]);
                    n2 = Integer.valueOf(opencodeStrs[3]);
                    n1 = Integer.valueOf(opencodeStrs[4]);
                }

            }

        } else if(luckyType >= 5 && luckyType <= 9){
            if(luckyType == 6 || luckyType == 9){
                //10个号码
                //只出3位的
                String[] opencodeStrs = openCode.split(",");
                int size = opencodeStrs.length;
                if(size >= 10){
                    //分析前10
                    n10 = Integer.valueOf(opencodeStrs[0]);
                    n9 = Integer.valueOf(opencodeStrs[1]);
                    n8 = Integer.valueOf(opencodeStrs[2]);
                    n7 = Integer.valueOf(opencodeStrs[3]);
                    n6 = Integer.valueOf(opencodeStrs[4]);
                    n5 = Integer.valueOf(opencodeStrs[5]);
                    n4 = Integer.valueOf(opencodeStrs[6]);
                    n3 = Integer.valueOf(opencodeStrs[7]);
                    n2 = Integer.valueOf(opencodeStrs[8]);
                    n1 = Integer.valueOf(opencodeStrs[9]);
                }
            } else {
                //只出3位的
                String[] opencodeStrs = openCode.split(",");
                int size = opencodeStrs.length;
                if(size >= 3){
                    //分析前3
                    n3 = Integer.valueOf(opencodeStrs[0]);
                    n2 = Integer.valueOf(opencodeStrs[1]);
                    n1 = Integer.valueOf(opencodeStrs[2]);
                }
            }

        }
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                ", journal=" + journal +
                ", n1=" + n1 +
                ", n2=" + n2 +
                ", n3=" + n3 +
                ", n4=" + n4 +
                ", n6=" + n6 +
                ", n7=" + n7 +
                ", n8=" + n8 +
                ", n9=" + n9 +
                ", n10=" + n10 +
//                ", lc=" + erWeiInt2String(lc) +
                ", luckyType" + luckyType +
                ", openCode" + openCode +
                ", expect" + expect +
                '}';
    }

    /**
     * 计算
     * @param numbs
     * @return
     */
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
