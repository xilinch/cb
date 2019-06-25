package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.adapter.DoubleToolAdapter;
import com.shishic.cb.bean.DoubleBean;
import com.shishic.cb.fragment.ToolFragment2;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.util.UtilInteger;

import java.util.ArrayList;
import java.util.List;

public class DoubleToolActivity2 extends BaseActivity {
    private TextView tv_title;
    private LinearLayout ll_back;

    private ToolFragment2 toolFragment2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_tool2);
        initView();
        initListener();
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);

        tv_title.setText("倍投工具");
        toolFragment2  = new ToolFragment2();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ll_content,toolFragment2);
        ft.commit();

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
