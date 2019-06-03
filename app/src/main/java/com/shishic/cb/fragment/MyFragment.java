package com.shishic.cb.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.bumptech.glide.Glide;
import com.shishic.cb.BindPhoneActivity;
import com.shishic.cb.FeedBackActivity;
import com.shishic.cb.LoginActivity;
import com.shishic.cb.MessageActivity;
import com.shishic.cb.NotifyActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.Account;
import com.shishic.cb.dialog.CenterDialog;
import com.shishic.cb.dialog.ServiceIntroduceDialog;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.NFCallback;
import com.shishic.cb.util.RequestUtils;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.CircleImageView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

import static com.shishic.cb.util.Constant.URL_SCORE;

public class MyFragment extends BaseFragment{

    private TextView tv_personal_center_nickname,tv_logon_text;

    private LinearLayout ll_phone;

    private CircleImageView civ_personal_center_avatar;

    private View view;

    private Dialog signDialog;
    private RelativeLayout rl_modify_pwd;
    private RelativeLayout rl_bindphone;
    private RelativeLayout rl_sign;
    private RelativeLayout rl_share;
    private RelativeLayout rl_feedback;
    private RelativeLayout rl_online_service;
    private RelativeLayout rl_message;
    private RelativeLayout rl_cache;
    private RelativeLayout rl_notify;
    private RelativeLayout rl_login;
    public static final String ACTION_LOGIN = "com.shishic.cb.ACTION_LOGIN";
    private ServiceIntroduceDialog dialog;
    private CenterDialog cacheDialog;

    private BroadcastReceiver loginBroadCast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUser();
        }
    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_personal_center, container, false);
            tv_personal_center_nickname = view.findViewById(R.id.tv_personal_center_nickname);
            civ_personal_center_avatar = view.findViewById(R.id.civ_personal_center_avatar);
            rl_modify_pwd = view.findViewById(R.id.rl_modify_pwd);
            rl_bindphone = view.findViewById(R.id.rl_bindphone);
            rl_sign = view.findViewById(R.id.rl_sign);
            rl_share = view.findViewById(R.id.rl_share);
            rl_feedback = view.findViewById(R.id.rl_feedback);
            rl_online_service = view.findViewById(R.id.rl_online_service);
            ll_phone = view.findViewById(R.id.ll_phone);
            rl_message = view.findViewById(R.id.rl_message);
            rl_cache = view.findViewById(R.id.rl_cache);
            rl_notify = view.findViewById(R.id.rl_notify);
            rl_login = view.findViewById(R.id.rl_login);
            tv_logon_text = view.findViewById(R.id.tv_logon_text);
            initView();
            IntentFilter filter = new IntentFilter(ACTION_LOGIN);
            getActivity().registerReceiver(loginBroadCast, filter);
            updateUser();
        }
        return view;
    }

    /**
     * 设置事件
     */
    private void initView(){
        ll_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogin();
            }
        });
        rl_modify_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                startActivity(intent);
            }
        });
        rl_bindphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BindPhoneActivity.class);
                startActivity(intent);
            }
        });
        rl_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(needLogin()){
                    Intent intent =new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    showSign();
                }

            }
        });
        rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                startActivity(intent);
            }
        });
        rl_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除缓存
                LayoutInflater inflater = LayoutInflater.from(getContext());
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.clear_cache_dialog, null);

                cacheDialog = new CenterDialog(getContext()).builder("isFromClearCache", R.style.ActionDialogStyle).setView(layout).setCanceledOnTouchOutside(true);
                cacheDialog.show();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Glide.get(getContext()).clearDiskCache();
                    }
                }
                .start();


                CookieSyncManager.createInstance(getContext());
                CookieManager.getInstance().removeAllCookie();
                rl_cache.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cacheDialog.dismiss();
                    }
                },1000);
            }
        });
        rl_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NotifyActivity.class);
                startActivity(intent);
            }
        });
        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //
            }
        });
        rl_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(needLogin()){
                    Intent intent =new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), FeedBackActivity.class);
                    startActivity(intent);
                }

            }
        });
        rl_online_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
               showDialog();
            }
        });
        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Account account = Account.getAccount();
                if(account == null){
                    showLogin();
                } else {
                    //
                    Account.saveAccount(null);
                    updateUser();
                }
            }
        });
    }

    private boolean needLogin(){
        boolean result = true;
        Account account = Account.getAccount();
        if(account != null){
            result = false;
        }
        return result;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(loginBroadCast != null){
            getActivity().unregisterReceiver(loginBroadCast);
        }
        dismissDialog();
    }

    /**
     * 显示登录
     */
    private void showLogin(){
       Intent intent = new Intent(getContext(), LoginActivity.class);
       startActivity(intent);
    }

    private void showSign(){
        //对话框
        if (signDialog == null) {
            signDialog = new Dialog(getContext(), R.style.ActionSheetDialogStyle);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_waite, null);
            int sp_120 = DensityUtils.dipTopx(getContext(), 120);
            int sp_60 = DensityUtils.dipTopx(getContext(), 50);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(sp_120, sp_60);
            signDialog.setContentView(view,params);
        }
        signDialog.show();
//        HashMap<String,String> params = new HashMap<String, String>();
//        params.put("userId",String.valueOf(Account.getAccount().getId()));
//        RequestUtil.httpGet(getActivity(), URL_SCORE, params , new NFHttpResponseListener<String>() {
//            @Override
//            public void onErrorResponse(LogError error) {
//                dismissSign();
//                ToastUtils.toastShow(getActivity(),R.string.network_error);
//            }
//
//            @Override
//            public void onResponse(String response) {
//                LogUtil.e("my","URL_SCORE response:" + response);
//                dismissSign();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String msg = jsonObject.optString("msg");
//                    ToastUtils.toastShow(getActivity(),msg);
//                } catch (Exception exception){
//                    exception.printStackTrace();
//                } finally {
//
//                }
//
//            }
//        });
        //TODO 调用示例
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userId",String.valueOf(Account.getAccount().getId()));
        RequestUtils.httpget(getActivity(), URL_SCORE, params, new NFCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                dismissSign();
                ToastUtils.toastShow(getActivity(),R.string.network_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                LogUtil.e("my","URL_SCORE response:" + response );
                dismissSign();
                try {
                    String result = response.body().string();
                    JSONObject jsonObject = new JSONObject(result);
                    String msg = jsonObject.optString("msg");
                    ToastUtils.toastShow(getActivity(),msg);
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });

    }

    private void showDialog(){
        if(dialog == null){
            dialog = new ServiceIntroduceDialog(getContext());
        }
        dialog.show();
    }

    private void dismissDialog(){
        if(dialog != null){
            dialog.dismiss();
        }
    }

    private void updateUser(){
        Account account = Account.getAccount();
        if(account != null && civ_personal_center_avatar != null && tv_personal_center_nickname!= null){
            String name = account.getUserName();
            civ_personal_center_avatar.setImageResource(R.mipmap.icon_login_wechat);
            tv_personal_center_nickname.setText(name);
            tv_logon_text.setText("退出登录");
        } else {
            civ_personal_center_avatar.setImageResource(R.mipmap.person_icon_default);
            tv_personal_center_nickname.setText("点击登录");
            tv_logon_text.setText("点击登录");
        }
    }

    private void dismissSign(){
        if(signDialog != null){
            signDialog.dismiss();
        }
    }



}
