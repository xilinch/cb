package com.shishic.cb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.util.DensityUtils;


/**
 * 专家购买
 */

public class SignDialog extends Dialog implements View.OnClickListener {

    /**
     * 视图
     */
    private View view;

    /**
     * 图片
     */
    private ImageView iv_bg;
    // 0 ,签到成功， 1已经签到 ， 2 签到失败
    private int type;
    /**
     * 确认点击
     */
    private View.OnClickListener confirmClickListener;

    /**
     * 取消按钮
     */
    private View.OnClickListener cancelClickListener;

    public SignDialog(@NonNull Context context) {
        super(context, R.style.dialog_backgroundDimAmout_5);
        init();
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        if(type == 0){
            iv_bg.setImageResource(R.mipmap.icon_sign_succee);
        } else if(type == 1){
            iv_bg.setImageResource(R.mipmap.icon_sign_already);
        } else {
            iv_bg.setImageResource(R.mipmap.icon_sign_fail);
        }
    }

    private void init() {
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sign, null);
        iv_bg = view.findViewById(R.id.iv_bg);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(view, layoutParams);

    }



    public void setConfirmClickListener(View.OnClickListener confirmClickListener) {
        this.confirmClickListener = confirmClickListener;
    }

    public void setCancelClickListener(View.OnClickListener cancelClickListener) {
        this.cancelClickListener = cancelClickListener;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_confirm:
                dismiss();

                break;
            case R.id.tv_cancel:
                dismiss();

                break;
        }


    }


}
