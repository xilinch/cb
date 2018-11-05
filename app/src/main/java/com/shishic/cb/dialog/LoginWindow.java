package com.shishic.cb.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.shishic.cb.R;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.RegexStringUtils;

public class LoginWindow extends PopupWindow {
    private static final String TAG = "LoginWindow";


    private static int TEXT_COLOR_ENABLE;
    private static int TEXT_COLOR_ABLE;
    private Activity mContext;
    private View window;
    private ImageView mClose, cleanPhone, loadingImage, mBack;
    private EditText phone, password;
    private TextView login;
    private String phoneText, codeText;
    private ObjectAnimator mAnimator;
    private float mWidth;  //窗口宽度
    private int mHeight;
    private FrameLayout container;

    private OnLoginResultListener onLoginResultListener;

    public LoginWindow(Activity context) {
        super(context);
        mContext = context;
        initViews();
        initListener();
        setWindow();
    }


    private void initViews() {
        window = LayoutInflater.from(mContext).inflate(R.layout.popup_window_login, null);
        mClose = window.findViewById(R.id.window_login_close);
        phone = window.findViewById(R.id.window_login_number);
        password = window.findViewById(R.id.window_login_pic_code);
        login = window.findViewById(R.id.window_login_login);
        cleanPhone = window.findViewById(R.id.window_clean_phone);
        loadingImage = window.findViewById(R.id.window_login_loading);
        mAnimator = ObjectAnimator.ofFloat(loadingImage, "rotation", 0, 360);
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        TEXT_COLOR_ENABLE = mContext.getResources().getColor(R.color.request_code_color);
        TEXT_COLOR_ABLE = mContext.getResources().getColor(R.color.c_red_f64e45);

        //选择登录方式
        mClose = window.findViewById(R.id.window_login_close);
        mBack = window.findViewById(R.id.window_login_back);

        mWidth = DensityUtils.dipTopx(mContext, 300);
        mHeight = DensityUtils.dipTopx(mContext, 356);
        container = new FrameLayout(mContext);
        container.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    private void setBackgroundAlpha(final float bgAlpha) {
        if (mContext != null) {
            if (container == null) {
                container = new FrameLayout(mContext);
                container.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }
            ViewGroup parent = (ViewGroup) container.getParent();
            if (parent != null) {
                parent.removeView(container);
            }
            if (bgAlpha == 0.5f) {
                ((ViewGroup) (mContext.getWindow().getDecorView())).addView(container, -1);
                ObjectAnimator animator = ObjectAnimator.ofInt(container, "backgroundColor", 0x00000000, 0x80000000);
                animator.setEvaluator(new ArgbEvaluator());
                animator.setDuration(250);
                animator.start();
            } else {
                ObjectAnimator animator = ObjectAnimator.ofInt(container, "backgroundColor", 0x80000000, 0x00000000);
                animator.setEvaluator(new ArgbEvaluator());
                animator.setDuration(250);
                animator.start();
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ((ViewGroup) (mContext.getWindow().getDecorView())).removeView(container);
                    }
                });
            }
        }

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setBackgroundAlpha(0.5f);

    }


    private void initListener() {
        cleanPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setText("");
                cleanPhone.setVisibility(View.GONE);
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneText = s.toString();
                isLoginEnable();
            }
        });


        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                codeText = s.toString();
                isLoginEnable();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("13450492513".equals(phoneText) && "123456".equals(password.getText())){
                    phoneLogin();
                    startLoading();
                    setLoginState(false);
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBack.setVisibility(View.GONE);
                phone.setText("");
                password.setText("");
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1f);
            }
        });

    }


    /**
     * 设置popupWindow的相关属性
     */
    private void setWindow() {
        setContentView(window);
        setWidth((int) mWidth);
        setHeight(mHeight);
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setAnimationStyle(R.style.login_popup_window_show_dismiss);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                if (x > 0 && x < mWidth && y > 0 && y < mHeight) {
                    return false;
                }
                return true;
            }
        });
    }

    /**
     * 改变登录按钮状态
     *
     * @param flag
     */
    private void setLoginState(boolean flag) {
        login.setEnabled(flag);
        if (flag) {
            login.setBackgroundColor(TEXT_COLOR_ABLE);
        } else {
            login.setBackgroundColor(TEXT_COLOR_ENABLE);
        }
    }


    /**
     * 开始加载动画
     */
    private void startLoading() {
        login.setText("");
        mAnimator.start();
        loadingImage.setVisibility(View.VISIBLE);
    }

    /**
     * 停止加载动画
     */
    private void stopLoading() {
        login.setText("登录");
        mAnimator.cancel();
        loadingImage.setVisibility(View.GONE);
    }

    /**
     * 判断是否可以点击登录
     */
    private void isLoginEnable() {
        if (RegexStringUtils.IsMobileFormat(phoneText) && !TextUtils.isEmpty(codeText) && codeText.length() >= 6) {
            setLoginState(true);
        } else {
            setLoginState(false);
        }
    }

    /**
     * 手机登录
     */
    private void phoneLogin() {
        try {
            Thread.sleep(1000);
            dismiss();
            stopLoading();
            if(onLoginResultListener != null){
                onLoginResultListener.onLoginSuccess();
            }

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }

    }

    public void setOnLoginResultListener(OnLoginResultListener onLoginResultListener) {
        this.onLoginResultListener = onLoginResultListener;
    }

    public interface OnLoginResultListener{

        void onLoginSuccess();

        void onLoginFailed();
    }

}
