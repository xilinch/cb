package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.shishic.cb.DoubleToolActivity;
import com.shishic.cb.R;
import com.shishic.cb.adapter.DoubleToolAdapter;
import com.shishic.cb.bean.DoubleBean;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.util.UtilInteger;

import java.util.ArrayList;
import java.util.List;

public class ToolFragment2 extends BaseFragment {

    private View view;
    private Button btn_confirm;
    private Button btn_reset;

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
        btn_reset = view.findViewById(R.id.btn_reset);

        et_touruzushu = view.findViewById(R.id.et_touruzushu);
        et_touruqishu = view.findViewById(R.id.et_touruqishu);
        et_qishibeishu = view.findViewById(R.id.et_qishibeishu);
        et_danbeijiangjin = view.findViewById(R.id.et_danbeijiangjin);
        et_danbeijiangjin.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL) ;
        et_danbeijiangjin.setKeyListener(new DigitsKeyListener(false, true)) ;


        et_danzhubenjin = view.findViewById(R.id.et_danzhubenjin);
        et_shouyilv = view.findViewById(R.id.et_shouyilv);
        et_shouyilv.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL) ;
        et_shouyilv.setKeyListener(new DigitsKeyListener(false, true)) ;
        recyclerView = view.findViewById(R.id.recyclerView);
        List list = new ArrayList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new DoubleToolAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

    }

    private void initListener(){
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                et_touruzushu.setText("10");
                et_touruqishu.setText("20");
                et_qishibeishu.setText("10");
                et_danbeijiangjin.setText(String.valueOf(19));
                et_danzhubenjin.setText(String.valueOf(2));
                et_shouyilv.setText(String.valueOf(0));

            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String touruzushu = et_touruzushu.getText().toString().trim();
                String touruqishu = et_touruqishu.getText().toString().trim();
                String qishibeishu = et_qishibeishu.getText().toString().trim();
                String danbeijiangjin = et_danbeijiangjin.getText().toString().trim();
                String danzhubenjin = et_danzhubenjin.getText().toString().trim();
                String shouyilv = et_shouyilv.getText().toString().trim();

                int touruzushu_num = UtilInteger.parseInt(touruzushu);
                int touruqishu_num = UtilInteger.parseInt(touruqishu);
                int qishibeishu_num = UtilInteger.parseInt(qishibeishu);
                double danbeijiangjin_num = UtilInteger.parseDouble(danbeijiangjin);
                int danzhubenjin_num = UtilInteger.parseInt(danzhubenjin);
                int shouyilv_num = UtilInteger.parseInt(shouyilv);
                if(touruzushu_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置投入注数");
                    return;
                } else if(touruqishu_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置投入期数");
                    return;
                } else if(qishibeishu_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置起始倍数");
                    return;
                } else if(danbeijiangjin_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置单倍奖金");
                    return;
                } else if(danzhubenjin_num <= 0){
                    ToastUtils.toastShow(getContext(),"请设置单注本金");
                    return;
                } else if(shouyilv_num < 0){
                    ToastUtils.toastShow(getContext(),"请设置收益率");
                    return;
                }

                List<DoubleBean> list = new ArrayList<>();
                //按照这些设置好的计算20期
                double zongtouzhujin = 0;
                double zongjiangjin = 0;
                for(int i = 0;i< touruqishu_num; i++){
                    double touzhujin = 0;
                    DoubleBean bean = new DoubleBean();
                    //单倍奖金固定
                    //期数
                    bean.setQishu(i+1);
                    bean.setDanbeijiangjin(danbeijiangjin_num);
                    //倍数设置好的
                    bean.setBeishu(qishibeishu_num * touruzushu_num);
                    zongjiangjin = zongjiangjin + bean.getBeishu() * bean.getDanbeijiangjin();
                    //奖金
                    bean.setJiangjin(zongjiangjin);
                    //起始本金
                    bean.setQishibenjin(danzhubenjin_num);
                    //投注成本
                    touzhujin = bean.getQishibenjin() * qishibeishu_num * touruzushu_num;
                    bean.setTouzhuchengben(touzhujin);
                    //总成本---
                    zongtouzhujin = zongtouzhujin + touzhujin;
                    bean.setZongbenjin(zongtouzhujin);
                    //利润---
                    double lirun = bean.getJiangjin() - bean.getZongbenjin();
                    bean.setLirun(lirun);
                    double lirunlv = lirun * 100 / bean.getZongbenjin();
//                    LogUtil.e("my","lirunlv:" + lirunlv);
                    if(lirunlv >= shouyilv_num){
                        list.add(bean);
                    }
                    bean.setLirunlv(lirunlv);
                }
                DoubleBean bean = new DoubleBean();
                bean.setType(1);
                list.add(0,bean);
                adapter.changeDataList(list);
            }
        });
    }

}
