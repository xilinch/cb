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
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.ArrayList;
import java.util.List;

public class FunAdapter extends RecyclerView.Adapter {

    private List<FunBean> list;

    private Context context;

    public FunAdapter(List<FunBean> list,Context context){
        this.list = list;
        if(this.list == null){
            this.list = new ArrayList<>();
        }
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_hotspot,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FunBean funBean = list.get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             funViewHolder.tv_title.setText(funBean.getText());
             String url = funBean.getIcon();
             Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher).into(funViewHolder.iv_logo);

         }
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title;
        public CustomRoundImageView iv_logo;

        public FunViewHolder(View view){
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            iv_logo = view.findViewById(R.id.iv_logo);
        }
    }
}
