package com.shishic.cb.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.shishic.cb.ReaderApplication;

public class SharepreferenceUtil {


    public static final String S_FILE = "xbb";
    public static final String S_ACCOUNT = "S_ACCOUNT";
    public static final String S_SHOW_HELP = "S_SHOW_HELP";


    /**
     * 设置账户
     * @param account
     */
    public static void setAccount(String account){
        SharedPreferences sp = ReaderApplication.getInstace().getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(S_ACCOUNT,account);
        editor.commit();
    }

    /**
     * 获取账户
     * @return
     */
    public static String getAccount(){
        SharedPreferences sp = ReaderApplication.getInstace().getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
        return sp.getString(S_ACCOUNT,"");

    }

    public static void showHelp(boolean show){
        SharedPreferences sp = ReaderApplication.getInstace().getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(S_SHOW_HELP,show);
        editor.commit();
    }

    public static boolean isShowHelp(){
        boolean isShow = false;
        SharedPreferences sp = ReaderApplication.getInstace().getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
        isShow = sp.getBoolean(S_SHOW_HELP,false);

        return isShow;
    }

}