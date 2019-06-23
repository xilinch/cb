package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.util.LogUtil;

public class IntroduceActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_intrudoce;
    private LinearLayout ll_back;
    private String introduce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_intrudoce);
        initView();
        initListener();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        tv_intrudoce = findViewById(R.id.tv_intrudoce);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("专家详情");
        introduce = getIntent().getStringExtra("introduce");
        LogUtil.e("my","introduce:" + introduce);
        tv_intrudoce.setText(introduce);
    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
