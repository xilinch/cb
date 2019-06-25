package com.shishic.cb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.DoubleBean;
import com.shishic.cb.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class DoubleToolAdapter extends RecyclerView.Adapter {

    private List<DoubleBean> list;

    private Context context;

    public DoubleToolAdapter(List<DoubleBean> list, Context context) {
        this.list = list;
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.context = context;
    }

    public void changeDataList(List<DoubleBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_double_tool, parent, false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LogUtil.e("my","onBindViewHolder position:" + position + list.get(position).toString());
        final DoubleBean funBean = list.get(position);
        if (holder instanceof FunViewHolder) {
            FunViewHolder funViewHolder = (FunViewHolder) holder;
            int type = funBean.getType();
            if(type == 1){

                //投入注数/投入期数/起始倍数/单注奖金

                //显示标题
                funViewHolder.tv_danbeijiangjin.setText("单倍奖金");
                funViewHolder.tv_jiangjin.setText("奖金");
                funViewHolder.tv_qishibenjin.setText("起始本金");
                funViewHolder.tv_qishu.setText("期数");
                funViewHolder.tv_touzhuchengben.setText("投注成本");
                funViewHolder.tv_zongbenjin.setText("总本金");
                funViewHolder.tv_lirun.setText("利润");
                funViewHolder.tv_danbeijiangjin.setTextColor(Color.GREEN);
                funViewHolder.tv_danbeijiangjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_jiangjin.setTextColor(Color.GREEN);
                funViewHolder.tv_jiangjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_qishibenjin.setTextColor(Color.GREEN);
                funViewHolder.tv_qishibenjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_qishu.setTextColor(Color.GREEN);
                funViewHolder.tv_qishu.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_touzhuchengben.setTextColor(Color.GREEN);
                funViewHolder.tv_touzhuchengben.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_zongbenjin.setTextColor(Color.GREEN);
                funViewHolder.tv_zongbenjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_lirun.setTextColor(Color.GREEN);
                funViewHolder.tv_lirun.setBackgroundColor(Color.WHITE);

            } else {
                //显示数值
                funViewHolder.tv_danbeijiangjin.setText(String.valueOf(funBean.getDanbeijiangjin()));
                funViewHolder.tv_jiangjin.setText(String.valueOf(funBean.getJiangjin()));
                funViewHolder.tv_qishibenjin.setText(String.valueOf(funBean.getQishibenjin()));
                funViewHolder.tv_qishu.setText(String.valueOf(funBean.getQishu()));
                funViewHolder.tv_touzhuchengben.setText(String.valueOf(funBean.getTouzhuchengben()));
                funViewHolder.tv_zongbenjin.setText(String.valueOf(funBean.getZongbenjin()));
                funViewHolder.tv_lirun.setText(String.valueOf(funBean.getLirun()));

                funViewHolder.tv_danbeijiangjin.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_danbeijiangjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_jiangjin.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_jiangjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_qishibenjin.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_qishibenjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_qishu.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_qishu.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_touzhuchengben.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_touzhuchengben.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_zongbenjin.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_zongbenjin.setBackgroundColor(Color.WHITE);
                funViewHolder.tv_lirun.setTextColor(Color.parseColor("#333333"));
                funViewHolder.tv_lirun.setBackgroundColor(Color.WHITE);
            }

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        LogUtil.e("my","getItemCount:" + list.size());
        return list.size();
    }

    static class FunViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_qishu;
        public TextView tv_qishibenjin;
        public TextView tv_danbeijiangjin;
        public TextView tv_touzhuchengben;
        public TextView tv_zongbenjin;
        public TextView tv_jiangjin;
        public TextView tv_lirun;

        public FunViewHolder(View view) {
            super(view);
            tv_qishu = view.findViewById(R.id.tv_qishu);
            tv_qishibenjin = view.findViewById(R.id.tv_qishibenjin);
            tv_danbeijiangjin = view.findViewById(R.id.tv_danbeijiangjin);
            tv_touzhuchengben = view.findViewById(R.id.tv_touzhuchengben);
            tv_zongbenjin = view.findViewById(R.id.tv_zongbenjin);
            tv_jiangjin = view.findViewById(R.id.tv_jiangjin);
            tv_lirun = view.findViewById(R.id.tv_lirun);
        }
    }
}
