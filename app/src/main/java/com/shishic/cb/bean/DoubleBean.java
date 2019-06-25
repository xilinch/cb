package com.shishic.cb.bean;

public class DoubleBean extends BaseBean {
    //起始本金*倍数
    private double touzhuchengben;

    private double qishibenjin;

    private double danbeijiangjin = 19.5;
    //最高99
    private int qishu;
    //动态倍数
    private int beishu;
    //投注(n) + 投注(n-1) + 投注... + 投注(1)
    private double zongbenjin;
    //倍数*单倍将近
    private double jiangjin;
    //奖金-总成本
    private double lirun;
    //1显示标题
    private int type;

    public double getTouzhuchengben() {
        return touzhuchengben;
    }

    public void setTouzhuchengben(double touzhuchengben) {
        this.touzhuchengben = touzhuchengben;
    }

    public double getQishibenjin() {
        return qishibenjin;
    }

    public void setQishibenjin(double qishibenjin) {
        this.qishibenjin = qishibenjin;
    }

    public double getDanbeijiangjin() {
        return danbeijiangjin;
    }

    public void setDanbeijiangjin(double danbeijiangjin) {
        this.danbeijiangjin = danbeijiangjin;
    }

    public int getQishu() {
        return qishu;
    }

    public void setQishu(int qishu) {
        this.qishu = qishu;
    }

    public int getBeishu() {
        return beishu;
    }

    public void setBeishu(int beishu) {
        this.beishu = beishu;
    }

    public double getZongbenjin() {
        return zongbenjin;
    }

    public void setZongbenjin(double zongbenjin) {
        this.zongbenjin = zongbenjin;
    }

    public double getJiangjin() {
        return jiangjin;
    }

    public void setJiangjin(double jiangjin) {
        this.jiangjin = jiangjin;
    }

    public double getLirun() {
        return lirun;
    }

    public void setLirun(double lirun) {
        this.lirun = lirun;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DoubleBean{" +
                "touzhuchengben=" + touzhuchengben +
                ", qishibenjin=" + qishibenjin +
                ", danbeijiangjin=" + danbeijiangjin +
                ", qishu=" + qishu +
                ", beishu=" + beishu +
                ", zongbenjin=" + zongbenjin +
                ", jiangjin=" + jiangjin +
                ", lirun=" + lirun +
                ", type=" + type +
                '}';
    }
}
