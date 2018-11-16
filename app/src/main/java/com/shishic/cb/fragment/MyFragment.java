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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.shishic.cb.BindPhoneActivity;
import com.shishic.cb.FeedBackActivity;
import com.shishic.cb.LoginActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.Account;
import com.shishic.cb.dialog.ServiceIntroduceDialog;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.CircleImageView;

import java.util.HashMap;

import static com.shishic.cb.util.Constant.URL_SCORE;

public class MyFragment extends BaseFragment{

    private TextView tv_personal_center_nickname;

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
    public static final String ACTION_LOGIN = "com.shishic.cb.ACTION_LOGIN";
    private ServiceIntroduceDialog dialog;

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
    }

    private boolean needLogin(){
        boolean result = false;
        Account account = Account.getAccount();
        if(account != null){
            result = true;
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
        HashMap<String,String> params = new HashMap<String, String>();
        params.put("userId",String.valueOf(Account.getAccount().getId()));
        RequestUtil.httpGet(getActivity(), URL_SCORE, params , new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                dismissSign();
                ToastUtils.toastShow(getActivity(),R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_SCORE response:" + response);
                dismissSign();
                ToastUtils.toastShow(getActivity(),"签到成功");
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
        }
    }

    private void dismissSign(){
        if(signDialog != null){
            signDialog.dismiss();
        }
    }



}