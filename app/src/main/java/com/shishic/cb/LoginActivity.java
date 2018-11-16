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

public class LoginActivity extends BaseActivity {

    private TextView tv_title;

    private LinearLayout ll_back;
    //电话号码
    private EditText window_login_number;
    //密码
    private EditText window_login_pic_code;
    //登录
    private TextView tv_login;
    //注册
    private TextView tv_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }


    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        window_login_number = findViewById(R.id.window_login_number);
        window_login_pic_code = findViewById(R.id.window_login_pic_code);
        tv_login = findViewById(R.id.tv_login);
        tv_register = findViewById(R.id.tv_register);
        tv_title.setText("登录");
    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValide = isValide();
                if(isValide){
                    requestData();
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 是否合法
     * @return
     */
    private boolean isValide(){
        boolean isValid = false;
        String userName = window_login_number.getText().toString().trim();
        String pwd = window_login_pic_code.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            ToastUtils.toastShow(this, "请输入手机号码");
            return isValid;
        } else if(!RegexStringUtils.IsMobileFormat(userName)){
            ToastUtils.toastShow(this, "请输入正确的手机号码");
            return isValid;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtils.toastShow(this, "请输入密码");
            return isValid;
        }

        isValid = true;
        return isValid;
    }

    /**
     * 请求数据
     */
    private void requestData(){
        String userName = window_login_number.getText().toString().trim();
        String pwd = window_login_pic_code.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("loginCode",userName);
        params.put("password",pwd);
        params.put("userName",userName);
        RequestUtil.httpGet(this, Constant.URL_LOGIN, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(LoginActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_LOGIN response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
                    ToastUtils.toastShow(LoginActivity.this, msg);

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
