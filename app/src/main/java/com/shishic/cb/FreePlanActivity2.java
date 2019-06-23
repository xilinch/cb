package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.fragment.FreePlanFragment2;

/**
 * 免费计划
 */
public class FreePlanActivity2 extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private LinearLayout ll_content;

    private FreePlanFragment2 freePlanFragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeplan1);
        initView();
        initListener();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_content = findViewById(R.id.ll_content);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("免费计划");
        freePlanFragment2 = new FreePlanFragment2();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_content,freePlanFragment2);
        transaction.commit();
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
