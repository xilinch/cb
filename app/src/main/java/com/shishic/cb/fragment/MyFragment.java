package com.shishic.cb.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shishic.cb.BindPhoneActivity;
import com.shishic.cb.FeedBackActivity;
import com.shishic.cb.R;
import com.shishic.cb.ReaderApplication;
import com.shishic.cb.dialog.LoginWindow;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.CircleImageView;

public class MyFragment extends BaseFragment implements LoginWindow.OnLoginResultListener {

    private TextView tv_personal_center_nickname;

    private LinearLayout ll_phone;

    private CircleImageView civ_personal_center_avatar;

    private View view;

    private LoginWindow loginWindow;
    private Dialog signDialog;
    private RelativeLayout rl_modify_pwd;
    private RelativeLayout rl_bindphone;
    private RelativeLayout rl_sign;
    private RelativeLayout rl_share;
    private RelativeLayout rl_feedback;


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
            ll_phone = view.findViewById(R.id.ll_phone);
            initView();
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
                showSign();
                rl_sign.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dismissSign();
                            }
                        });

                    }
                },1000);
            }
        });
        rl_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rl_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FeedBackActivity.class);
                startActivity(intent);
            }
        });

    }


    /**
     * 显示登录
     */
    private void showLogin(){
        if(loginWindow == null){
            loginWindow = new LoginWindow(getActivity());
            loginWindow.setOnLoginResultListener(this);
        }
        loginWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onLoginSuccess() {
        civ_personal_center_avatar.setImageResource(R.mipmap.icon_login_wechat);
        tv_personal_center_nickname.setText("无所不能");
    }

    @Override
    public void onLoginFailed() {

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
    }

    private void dismissSign(){
        if(signDialog != null){
            signDialog.dismiss();
        }
    }
}
