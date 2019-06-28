package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.fragment.SpecialFragment2;

/**
 * 专家计划
 */
public class SpecialActivity2 extends BaseActivity{
    private TextView tv_title;
    private LinearLayout ll_back;
    private LinearLayout ll_content;
    private SpecialFragment2 specialFragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special2);
        initView();
        initListener();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("专家");
        specialFragment2 = new SpecialFragment2();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_content, specialFragment2);
        transaction.commit();
    }

    private void initListener() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public SpecialFragment2 getSpecialFragment2() {
        return specialFragment2;
    }

    public void setSpecialFragment2(SpecialFragment2 specialFragment2) {
        this.specialFragment2 = specialFragment2;
    }
}
