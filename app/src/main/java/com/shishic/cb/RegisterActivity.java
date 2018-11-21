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

public class RegisterActivity extends BaseActivity {

    private TextView tv_title;

    private LinearLayout ll_back;
    //电话号码
    private EditText window_login_number;
    //密码
    private EditText window_login_pic_code;
    //重复密码
    private EditText et_pwd_again;
    //登录
    private TextView tv_login;
    //用户名
    private EditText et_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }


    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        window_login_number = findViewById(R.id.window_login_number);
        window_login_pic_code = findViewById(R.id.window_login_pic_code);
        et_pwd_again = findViewById(R.id.et_pwd_again);
        tv_login = findViewById(R.id.tv_login);
        et_name = findViewById(R.id.et_name);
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
    }

    /**
     * 是否合法
     * @return
     */
    private boolean isValide(){
        boolean isValid = false;
        String phone = window_login_number.getText().toString().trim();
        String userName = et_name.getText().toString().trim();
        String pwd = window_login_pic_code.getText().toString().trim();
        String pwd_again = et_pwd_again.getText().toString().trim();
        if(TextUtils.isEmpty(userName)){
            ToastUtils.toastShow(this, "请输入用户名");
            return isValid;
        } else if(TextUtils.isEmpty(phone)){
            ToastUtils.toastShow(this, "请输入手机号码");
            return isValid;
        } else if(!RegexStringUtils.IsMobileFormat(phone)){
            ToastUtils.toastShow(this, "请输入正确的手机号码");
            return isValid;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtils.toastShow(this, "请输入密码");
            return isValid;
        }
        if(TextUtils.isEmpty(pwd_again)){
            ToastUtils.toastShow(this, "请输入密码");
            return isValid;
        }
        if(!pwd.equals(pwd_again)){
            ToastUtils.toastShow(this, "两次密码不一致，请重新输入");
            return isValid;
        }
        isValid = true;
        return isValid;
    }

    /**
     * 请求数据
     */
    private void requestData(){
        String phone = window_login_number.getText().toString().trim();
        String userName = et_name.getText().toString().trim();
        String pwd = window_login_pic_code.getText().toString().trim();
        String pwd_again = et_pwd_again.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("loginCode",phone);
        params.put("password",pwd);
        params.put("userName",userName);
        RequestUtil.httpGet(this, Constant.URL_REGISTER, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(RegisterActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_REGISTER response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
                    ToastUtils.toastShow(RegisterActivity.this, msg);

                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        Account account = new Gson().fromJson(jsonObject.optString("data"), Account.class);
                        Account.saveAccount(account);
                        Intent intent = new Intent();
                        intent.setAction(MyFragment.ACTION_LOGIN);
                        sendBroadcast(intent);
                        finish();
                    } else {
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

}
