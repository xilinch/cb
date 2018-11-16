package com.shishic.cb.bean;

public class HistoryBean extends BaseBean {


    /**
     * expect : 20181116103
     * opencode : 5,8,7,1,1
     * opentime : 2018-11-16 22:36:30
     * opentimestamp : 1542378990
     */

    private String expect;
    private String opencode;
    private String opentime;
    private int opentimestamp;

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getOpencode() {
        return opencode;
    }

    public void setOpencode(String opencode) {
        this.opencode = opencode;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public int getOpentimestamp() {
        return opentimestamp;
    }

    public void setOpentimestamp(int opentimestamp) {
        this.opentimestamp = opentimestamp;
    }
}
