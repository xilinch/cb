package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.shishic.cb.bean.Account;
import com.shishic.cb.fragment.MyFragment;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.RegexStringUtils;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class ChatActivity extends BaseActivity {

    private TextView tv_title;

    private LinearLayout ll_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initListener();
    }


    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        tv_title.setText("聊天室");
    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    /**
     * 请求数据
     */
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("loginCode",userName);
        params.put("password",pwd);
        params.put("userName",userName);
        RequestUtil.httpGet(this, Constant.URL_LOGIN, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(ChatActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_LOGIN response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
                    ToastUtils.toastShow(ChatActivity.this, msg);

                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        Account account = new Gson().fromJson(jsonObject.optString("data"), Account.class);
                        Account.saveAccount(account);
                        Intent intent = new Intent();
                        intent.setAction(MyFragment.ACTION_LOGIN);
                        sendBroadcast(intent);
                        finish();
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }




}
