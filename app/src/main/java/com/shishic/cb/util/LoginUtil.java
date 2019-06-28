package com.shishic.cb.util;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.shishic.cb.LoginActivity;
import com.shishic.cb.R;
import com.shishic.cb.ReaderApplication;
import com.shishic.cb.bean.Account;
import com.shishic.cb.fragment.MyFragment;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

public class LoginUtil {

    public static void login(){

        String userInfo = SharepreferenceUtil.getUserInfo();
        if(!TextUtils.isEmpty(userInfo)){
            try {
                JSONObject jsonObject1 = new JSONObject(userInfo);
                String userName = jsonObject1.optString("userName");
                String password = jsonObject1.optString("password");

                HashMap<String,String> params = new HashMap<>();
                params.put("loginCode",userName);
                params.put("password",password);
                params.put("userName",userName);

                RequestUtils.httpget(ReaderApplication.getInstace(), Constant.URL_LOGIN, params, new NFCallback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                        ToastUtils.toastShow(ReaderApplication.getInstace(), R.string.network_error);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        super.onResponse(call, response);
                        LogUtil.e("my","URL_LOGIN response:" + response.body().toString());
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Account account = new Gson().fromJson(jsonObject.optString("data"), Account.class);
                            Account.saveAccount(account);
                            Intent intent = new Intent();
                            intent.setAction(MyFragment.ACTION_LOGIN);
                            ReaderApplication.getInstace().sendBroadcast(intent);

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
}
