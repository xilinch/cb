package com.shishic.cb.bean;

import com.google.gson.Gson;
import com.shishic.cb.util.SharepreferenceUtil;

public class Account extends BaseBean {

    private int id;

    private String name;

    private String icon;

    private String phone;

    private String sex;


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

    public Account(int id, String name, String icon, String phone, String sex) {
        this.id = id;
        this.name = name;
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
}
