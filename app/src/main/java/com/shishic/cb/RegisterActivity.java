package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.shishic.cb.util.NFCallback;
import com.shishic.cb.util.RegexStringUtils;
import com.shishic.cb.util.RequestUtils;
import com.shishic.cb.util.ToastUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.shishic.cb.util.Constant.URL_SCORE;

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
    //获取验证码
    private TextView tv_getCode;
    //验证码
    private EditText window_code;
    //用户名
    private EditText et_name;
    /**
     * 倒计时
     */
    private CountDownTimer countDownTimer;

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
        tv_getCode = findViewById(R.id.tv_getCode);
        window_code = findViewById(R.id.window_code);
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
        tv_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRegisterCode();
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
        String code = window_code.getText().toString().trim();
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
        if(TextUtils.isEmpty(code)){
            ToastUtils.toastShow(this, "请输入验证码");
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
        String code = window_code.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("loginCode",phone);
        params.put("tel",phone);
        params.put("password",pwd);
        params.put("userName",userName);
        params.put("validCode",code);
        RequestUtils.httpget(this, Constant.URL_REGISTER, params, new NFCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                ToastUtils.toastShow(RegisterActivity.this,R.string.network_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                try {
                    LogUtil.e("my","URL_REGISTER response:" + response);
                    //{"code":-1,"msg":"存在相同账号的用户","success":false}
                    JSONObject jsonObject = new JSONObject(response.body().string());
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
                    ToastUtils.toastShow(RegisterActivity.this,R.string.network_error);
                } finally {

                }
            }
        });
    }

    /**
     * 获取验证码率
     */
    private void requestRegisterCode(){
        String phone = window_login_number.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("tel", phone);
        tv_getCode.setEnabled(false);
        beginCountDown();
        RequestUtils.httpget(this, Constant.URL_REGISTER_CODE, params, new NFCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                ToastUtils.toastShow(RegisterActivity.this,R.string.network_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                LogUtil.e("my","URL_REGISTER_CODE  result:" + response.body().string());
            }
        });


    }

    private void beginCountDown(){
        if(countDownTimer == null){
            countDownTimer = new CountDownTimer(60000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(tv_getCode != null){
                        tv_getCode.setText( millisUntilFinished/1000 + "s");
                    }
                }

                @Override
                public void onFinish() {
                    if(tv_getCode != null){

                        tv_getCode.setText("点击获取验证码");
                        tv_getCode.setEnabled(true);
                    }
                }
            };
        }
        countDownTimer.start();
    }
}
