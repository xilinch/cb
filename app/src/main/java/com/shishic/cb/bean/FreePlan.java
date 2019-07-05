package com.shishic.cb.bean;

import java.util.List;

public class FreePlan extends BaseBean {


    /**
     * planId : 1
     * planName : 二星胆直选
     * notice : 神牛计划的广告
     * list : [{"id":41,"planId":1,"recommendNumbers":"13478","luckyNumbers":"14002","jounal":20181118120,"fromJounal":20181118120,"currenJounal":20181118120,"recommendStatus":0,"valid":1,"createTime":1542556684000,"updateTime":1542557161000,"endJounal":20181118124}]
     */

    private int planId;
    private String planName;
    private String notice;
    private List<ListBean> list;
    /**
     *  ZHIXUAN(1,"直选"),
     *     SINGLE(2,"单式"),
     *     ZHU_SAN(3,"组三"),
     *     ZHU_LIU(4,"组六"),
     *     FUXUAN(5,"复选");
     */
    private int planType;

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }



    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public static class ListBean {
        /**
         * id : 41
         * planId : 1
         * recommendNumbers : 13478
         * luckyNumbers : 14002
         * jounal : 20181118120
         * fromJounal : 20181118120
         * currenJounal : 20181118120
         * recommendStatus : 0
         * valid : 1
         * createTime : 1542556684000
         * updateTime : 1542557161000
         * endJounal : 20181118124
         * luckType :
         * planType :
         */

        private int id;
        private int planId;
        private String recommendNumbers;
        private String luckyNumbers;
        private long jounal;
        private long fromJounal;
        private long currenJounal;
        private int recommendStatus;
        private int valid;
        private long createTime;
        private long updateTime;
        private long endJounal;
        private int luckType;
        private int planType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPlanId() {
            return planId;
        }

        public void setPlanId(int planId) {
            this.planId = planId;
        }

        public String getRecommendNumbers() {
            return recommendNumbers;
        }

        public void setRecommendNumbers(String recommendNumbers) {
            this.recommendNumbers = recommendNumbers;
        }

        public String getLuckyNumbers() {
            return luckyNumbers;
        }

        public void setLuckyNumbers(String luckyNumbers) {
            this.luckyNumbers = luckyNumbers;
        }

        public long getJounal() {
            return jounal;
        }

        public void setJounal(long jounal) {
            this.jounal = jounal;
        }

        public long getFromJounal() {
            return fromJounal;
        }

        public void setFromJounal(long fromJounal) {
            this.fromJounal = fromJounal;
        }

        public long getCurrenJounal() {
            return currenJounal;
        }

        public void setCurrenJounal(long currenJounal) {
            this.currenJounal = currenJounal;
        }

        public int getRecommendStatus() {
            return recommendStatus;
        }

        public void setRecommendStatus(int recommendStatus) {
            this.recommendStatus = recommendStatus;
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

        public long getEndJounal() {
            return endJounal;
        }

        public void setEndJounal(long endJounal) {
            this.endJounal = endJounal;
        }

        public int getLuckType() {
            return luckType;
        }

        public void setLuckType(int luckType) {
            this.luckType = luckType;
        }

        public int getPlanType() {
            return planType;
        }

        public void setPlanType(int planType) {
            this.planType = planType;
        }
    }
}
