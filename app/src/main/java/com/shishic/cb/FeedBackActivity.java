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

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.shishic.cb.bean.Account;
import com.shishic.cb.dialog.CenterDialog;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class FeedBackActivity extends BaseActivity {
    private TextView title_text;
    private ImageView vBack;
    private TextView contentView;
    private Button submitBtn;
    private TextView cancel;
    private TextView confirm;
    private CenterDialog confirmDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
    }
    private void initView() {
        title_text = (TextView) findViewById(R.id.tv_title);
        contentView = (TextView) findViewById(R.id.BL_ET_content);
        vBack = (ImageView) findViewById(R.id.iv_back);
        vBack.setVisibility(View.VISIBLE);
        submitBtn = (Button) findViewById(R.id.BL_btn_sub);

        title_text.setText("意见反馈");
        contentView.setHint("请输入您的反馈");
        submitBtn.setText("提交反馈");
        initListener();

    }

    private void initListener() {
        vBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                back();
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String contentStr = contentView.getText().toString().trim();
                if (TextUtils.isEmpty(contentStr) || TextUtils.isEmpty(contentStr.trim())) {
                    ToastUtils.toastShow(FeedBackActivity.this, "请输入反馈内容!");
                    return;
                }
                request();
            }
        });

    }

    /**
     * 填写内容后返回要弹窗确认
     */
    private void back(){
        String contentStr = contentView.getText().toString().trim();
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

    /**
     * 数据请求
     */
    private void request(){
        String contentStr = contentView.getText().toString().trim();
        HashMap<String,String> params = new HashMap<>();
        params.put("contentStr",contentStr);
        params.put("userId",String.valueOf(Account.getAccount().getId()));
        params.put("userName",Account.getAccount().getUserName());
        RequestUtil.httpGet(this, Constant.URL_FEEDBACK, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(FeedBackActivity.this,R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
                    ToastUtils.toastShow(FeedBackActivity.this, msg);
                    finish();
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }

            }
        });
    }

}
