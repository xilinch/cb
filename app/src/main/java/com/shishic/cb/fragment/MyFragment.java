package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.dialog.LoginWindow;
import com.shishic.cb.view.CircleImageView;

public class MyFragment extends BaseFragment implements LoginWindow.OnLoginResultListener {

    private TextView tv_personal_center_nickname;

    private CircleImageView civ_personal_center_avatar;

    private View view;

    private LoginWindow loginWindow;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_personal_center, container, false);
            tv_personal_center_nickname = view.findViewById(R.id.tv_personal_center_nickname);
            civ_personal_center_avatar = view.findViewById(R.id.civ_personal_center_avatar);
            initView();
        }
        return view;
    }

    /**
     * 设置事件
     */
    private void initView(){
        tv_personal_center_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogin();
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
}
