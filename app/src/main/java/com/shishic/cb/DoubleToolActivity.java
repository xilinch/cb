package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.util.UtilInteger;

import java.util.ArrayList;
import java.util.List;

public class DoubleToolActivity extends BaseActivity {
    private TextView tv_title;
    private LinearLayout ll_back;
    private EditText et_capital,et_double,et_qishu;
    private Button btn_confirm;

    private RecyclerView recyclerView;

    private DoubleToolAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_tool);
        initView();
        initListener();
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        btn_confirm = findViewById(R.id.btn_confirm);
        et_capital = findViewById(R.id.et_capital);
        et_double = findViewById(R.id.et_double);
        et_qishu = findViewById(R.id.et_qishu);
        recyclerView = findViewById(R.id.recyclerView);
        tv_title.setText("倍投工具");
        List list = new ArrayList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoubleToolAdapter(list, this);
        recyclerView.setAdapter(adapter);

    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beishu = et_double.getText().toString().trim();
                String benjin = et_capital.getText().toString().trim();
                int beishu_num = UtilInteger.parseInt(beishu);
                int benjin_num = UtilInteger.parseInt(benjin);
                if(TextUtils.isEmpty(beishu) || benjin_num <= 0){
                    ToastUtils.toastShow(DoubleToolActivity.this,"请设置倍数");
                    return;
                } else if(TextUtils.isEmpty(benjin)|| beishu_num <= 0){
                    ToastUtils.toastShow(DoubleToolActivity.this,"请设置起始本金");
                    return;
                }
                String qishu = et_qishu.getText().toString();
                int qishu_int = 20;
                if(!TextUtils.isEmpty(qishu)){
                    qishu_int = Integer.valueOf(qishu);
                }

                List<DoubleBean> list = new ArrayList<>();
                //按照这些设置好的计算20期
                double zongtouzhujin = 0;
                for(int i = 0;i< qishu_int; i++){
                    double touzhujin = 0;
                    DoubleBean bean = new DoubleBean();
                    //单倍奖金固定
                    //期数
                    bean.setQishu(i+1);
                    //倍数设置好的
                    bean.setBeishu(beishu_num);
                    //奖金
                    bean.setJiangjin(beishu_num * bean.getDanbeijiangjin());
                    //起始本金
                    bean.setQishibenjin(benjin_num);
                    //投注成本
                    touzhujin = bean.getQishibenjin() *beishu_num;
                    bean.setTouzhuchengben(touzhujin);
                    //总成本---
                    zongtouzhujin = zongtouzhujin + touzhujin;
                    bean.setZongbenjin(zongtouzhujin);
                    //利润---
                    bean.setLirun(bean.getJiangjin() - bean.getZongbenjin());
                    list.add(bean);
                }
                DoubleBean bean = new DoubleBean();
                bean.setType(1);
                list.add(0,bean);
                adapter.changeDataList(list);
            }
        });
    }

}
