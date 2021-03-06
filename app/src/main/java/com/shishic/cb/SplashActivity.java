package com.shishic.cb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.SharepreferenceUtil;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.shishic.cb.util.SharepreferenceUtil.S_FILE;
import static com.shishic.cb.util.SharepreferenceUtil.S_SHOW_HELP;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            RequestUtil.httpGet(this, Constant.URL_FUNTION_LIST, new HashMap<String, String>(), new NFHttpResponseListener<String>() {
                @Override
                public void onErrorResponse(LogError error) {
                    ToastUtils.toastShow(SplashActivity.this, R.string.network_error);
                }

                @Override
                public void onResponse(String response) {
                    LogUtil.e("my","resonse:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.optJSONObject("data");
                        if(data != null){
                            JSONArray jsonArray = data.optJSONArray("list");
                            if(jsonArray != null && jsonArray.length() > 0){
                                SharepreferenceUtil.saveFun(jsonArray.toString());
                            }

                            Intent intent;
                            SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                            boolean isShow = sp.getBoolean(S_SHOW_HELP,false);
                            if(isShow){
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            } else {
                                intent = new Intent(SplashActivity.this, HelpActivity.class);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean(S_SHOW_HELP,true);
                                editor.commit();
                            }
                            startActivity(intent);
                            finish();
                        }

                    } catch (Exception exception){
                        exception.printStackTrace();
                    } finally {

                    }


                }
            });

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }

    }


}

