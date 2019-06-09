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

public class BuySpecialDialog extends Dialog implements View.OnClickListener {

    /**
     * 视图
     */
    private View view;

    /**
     * 标题
     */
    private TextView tv_title;

    /**
     * 确定
     */
    private TextView tv_confirm;
    /**
     * 取消
     */
    private TextView tv_cancel;
    /**
     * 确认点击
     */
    private View.OnClickListener confirmClickListener;

    /**
     * 取消按钮
     */
    private View.OnClickListener cancelClickListener;

    private TextView mContent;

    private SpecialBean funBean;

    public BuySpecialDialog(@NonNull Context context) {
        super(context, R.style.dialog_backgroundDimAmout_5);
        init();
    }

    private void init() {
        this.view = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_buy, null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        mContent = view.findViewById(R.id.tv_content);
        int widthPx = DensityUtils.dipTopx(getContext(), 270);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(widthPx, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(view, layoutParams);
        setListener();

    }

    public void setSpecialBean(SpecialBean funBean){
        this.funBean = funBean;
        mContent.setText("您即将购买："+ funBean.getName() +"专家计划,消耗金币：" + funBean.getFee()
         + "，确认购买？");
    }
    /**
     * 监听器
     */
    private void setListener() {
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
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
                if (confirmClickListener != null) {
                    confirmClickListener.onClick(tv_confirm);
                }
                break;
            case R.id.tv_cancel:
                dismiss();
                if (cancelClickListener != null) {
                    cancelClickListener.onClick(tv_cancel);
                }
                break;
        }


    }


}
