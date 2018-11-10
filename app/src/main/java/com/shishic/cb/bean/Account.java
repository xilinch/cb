package com.shishic.cb.bean;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.shishic.cb.util.SharepreferenceUtil;

import org.json.JSONObject;
import org.json.JSONStringer;

public class Account extends BaseBean {

    private int id;

    private String userName;

    private String icon;

    private String phone;

    private String sex;

    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Account(int id, String userName, String icon, String phone, String sex, String score) {
        this.id = id;
        this.userName = userName;
        this.score = score;
        this.icon = icon;
        this.phone = phone;
        this.sex = sex;
    }


    /**
     * 获取账户
     * @return
     */
    public static Account getAccount(){
        String accountStr = SharepreferenceUtil.getAccount();
        Account account = new Gson().fromJson(accountStr, Account.class);
        return account;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", icon='" + icon + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    /**
     * 保存
     * @param account
     */
    public static void saveAccount(Account account){
        String str = new Gson().toJson(account);
        SharepreferenceUtil.setAccount(str);
    }
}
