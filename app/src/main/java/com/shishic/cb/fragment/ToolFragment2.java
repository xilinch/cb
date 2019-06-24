package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shishic.cb.DoubleToolActivity;
import com.shishic.cb.R;
import com.shishic.cb.adapter.DoubleToolAdapter;
import com.shishic.cb.bean.DoubleBean;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.util.UtilInteger;

import java.util.ArrayList;
import java.util.List;

public class ToolFragment2 extends BaseFragment {

    private View view;
    private EditText et_capital,et_double,et_qishu;
    private Button btn_confirm;

    //投入注数
    private EditText et_touruzushu;
    //投入期数
    private EditText et_touruqishu;
    //起始倍数
    private EditText et_qishibeishu;
    //单倍奖金
    private EditText et_danbeijiangjin;
    //单注本金
    private EditText et_danzhubenjin;
    //收益率
    private EditText et_shouyilv;

    private RecyclerView recyclerView;

    private DoubleToolAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view == null){
            view = inflater.inflate(R.layout.fragment_tool,container,false);
            initView();
            initListener();
        }
        return view;
    }
    private void initView(){

        btn_confirm = view.findViewById(R.id.btn_confirm);
        et_capital = view.findViewById(R.id.et_capital);
        et_double = view.findViewById(R.id.et_double);
        et_qishu = view.findViewById(R.id.et_qishu);
        et_touruzushu = view.findViewById(R.id.et_touruzushu);
        et_touruqishu = view.findViewById(R.id.et_touruqishu);
        et_qishibeishu = view.findViewById(R.id.et_qishibeishu);
        et_danbeijiangjin = view.findViewById(R.id.et_danbeijiangjin);
        et_danzhubenjin = view.findViewById(R.id.et_danzhubenjin);
        et_shouyilv = view.findViewById(R.id.et_shouyilv);
        recyclerView = view.findViewById(R.id.recyclerView);
        List list = new ArrayList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DoubleToolAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

    }

    private void initListener(){
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beishu = et_double.getText().toString().trim();
                String benjin = et_capital.getText().toString().trim();
                int beishu_num = UtilInteger.parseInt(beishu);
                int benjin_num = UtilInteger.parseInt(benjin);
                if(TextUtils.isEmpty(beishu) || benjin_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置倍数");
                    return;
                } else if(TextUtils.isEmpty(benjin)|| beishu_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置起始本金");
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
