package com.shishic.cb.view;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by zhangzhihao on 2017/8/23.
 * 登录用的倒计时器
 */

public class LoginCountDownTimer extends CountDownTimer {

    private TextView mTextView;
    //介绍时显示的文字
    private String text;
    private int color;

    public LoginCountDownTimer(long millisInFuture, long countDownInterval, TextView textView, String text, int color) {
        super(millisInFuture, countDownInterval);
        mTextView = textView;
        this.text = text;
        this.color=color;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        StringBuilder builder = new StringBuilder("重新获取");
        builder.append("(")
                .append(millisUntilFinished / 1000)
                .append(")");
        mTextView.setText(builder.toString());
    }

    @Override
    public void onFinish() {
        mTextView.setText(text);
        mTextView.setEnabled(true);
        mTextView.setTextColor(color);
    }
}
