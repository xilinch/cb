package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shishic.cb.bean.Account;
import com.shishic.cb.dialog.CenterDialog;
import com.shishic.cb.util.ToastUtils;

public class FeedBackActivity extends BaseActivity {
    private TextView title_text;
    private ImageView vBack;
    private TextView contentView, nameView;
    private Button submitBtn;
    private String  contentStr;
    private Account accountInfo;
    private ProgressBar progressBar;
    private View view;//弹窗
    private TextView cancel;
    private TextView confirm;
    private CenterDialog confirmDialog;
    private String nameStr = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }
    private void initView() {
        title_text = (TextView) findViewById(R.id.tv_title);
        contentView = (TextView) findViewById(R.id.BL_ET_content);
        nameView = (TextView) findViewById(R.id.BL_ET_name);
        vBack = (ImageView) findViewById(R.id.iv_back);
        vBack.setVisibility(View.VISIBLE);
        submitBtn = (Button) findViewById(R.id.BL_btn_sub);
        progressBar = (ProgressBar) findViewById(R.id.pb_feed_back);

        title_text.setText("意见反馈");
        contentView.setHint("请输入您的反馈");
        submitBtn.setText("提交反馈");
        submitBtnClickable();
        initListener();

    }

    private void initListener() {
        vBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                back();
            }
        });
        contentView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                contentStr = s.toString();
                submitBtnClickable();
            }
        });

        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameStr = s.toString();
                submitBtnClickable();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contentStr = contentView.getText().toString().trim();
                nameStr = nameView.getText().toString().trim();
                if (TextUtils.isEmpty(contentStr) || TextUtils.isEmpty(contentStr.trim())) {
                    ToastUtils.toastShow(FeedBackActivity.this, "请输入反馈内容!");
                    return;
                }

                submitBtn.setBackgroundResource(R.drawable.solid_long_btn_bg);
                progressBar.setVisibility(View.VISIBLE);
                submitFeedBack();
            }
        });

    }



    private void submitBtnClickable() {
        contentStr = contentView.getText().toString().trim();
        nameStr = nameView.getText().toString().trim();
        if (!TextUtils.isEmpty(contentStr)) {
            submitBtn.setBackgroundResource(R.drawable.solid_long_btn_selector_bg);
            submitBtn.setClickable(true);
        } else {
            submitBtn.setBackgroundResource(R.drawable.solid_long_btn_bg);
        }

    }


    private void submitFeedBack() {
        contentStr = contentView.getText().toString().trim();
        nameStr = nameView.getText().toString().trim();

        if (submitCheck()) {
            submitBtn.setClickable(false);
            submitBtn.setText("准备提交...");
//            ToastUtils.toastShow(this, "提交成功");
            submitBtn.setText("提交反馈");
        }
    }


    private boolean submitCheck() {
        if (TextUtils.isEmpty(contentStr)) {
//            Toast.makeText(this, "请输入意见内容", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    /**
     * 填写内容后返回要弹窗确认
     */
    private void back(){
        if(!TextUtils.isEmpty(contentStr)){

            if (confirmDialog == null) {
                View view = LayoutInflater.from(FeedBackActivity.this).inflate(R.layout.view_setting_custom_dialog,
                        null);
                confirmDialog = new CenterDialog(FeedBackActivity.this)
                        .builder("isFromQuitLogin", R.style.ActionSheetDialogStyle)
                        .setView(view).setCanceledOnTouchOutside(true);
                cancel = view.findViewById(R.id.btn_cancel);
                confirm = view.findViewById(R.id.btn_confirm);
                TextView title = view.findViewById(R.id.tv_custom_dialog_title);
                title.setText("确定放弃编辑？");
                cancel.setText("取消");
                confirm.setText("确定");
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(confirmDialog!=null){
                            confirmDialog.dismiss();
                        }
                    }
                });
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(confirmDialog!=null){
                            confirmDialog.dismiss();
                        }
                        FeedBackActivity.this.finish();
                    }
                });
            }
            confirmDialog.show();
        } else {
            FeedBackActivity.this.finish();
        }
    }

}
