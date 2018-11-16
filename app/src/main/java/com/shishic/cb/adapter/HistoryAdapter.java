package com.shishic.cb.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.R;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.List;

public class HistoryAdapter extends PlusRecyclerAdapter<HistoryBean> {

    private Context context;

    public HistoryAdapter(List<HistoryBean> list, Context context){
        super(list);
        this.context = context;
    }

    public void changeList(){

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_history,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryBean funBean = getList().get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             funViewHolder.tv_opencode.setText("开奖号码：" + funBean.getOpencode());
             funViewHolder.tv_expect.setText("开奖期数：" + funBean.getExpect());
             funViewHolder.tv_opentime.setText("开奖时间：" + funBean.getOpentime());
         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_opencode;
        public TextView tv_expect;
        public TextView tv_opentime;

        public FunViewHolder(View view){
            super(view);
            tv_opencode = view.findViewById(R.id.tv_opencode);
            tv_expect = view.findViewById(R.id.tv_expect);
            tv_opentime = view.findViewById(R.id.tv_opentime);
        }
    }
}
