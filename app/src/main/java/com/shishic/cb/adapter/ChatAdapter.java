package com.shishic.cb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.H5Activity;
import com.shishic.cb.R;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ChatBean> list;

    public ChatAdapter(List<ChatBean> list, Context context){
        this.list = list;
        this.context = context;
    }

    public List<ChatBean> getList() {
        return list;
    }

    public void addList(List<ChatBean> list){
        if(this.list != null){
            this.list.addAll(list);
        } else {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_chat,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBean funBean = list.get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             funViewHolder.tv_name.setText(funBean.getUserName());
             funViewHolder.tv_content.setText(funBean.getContent());
             String url = funBean.getUserIcon();
             Glide.with(context).load(url).placeholder(R.mipmap.icon_default).centerCrop().into(funViewHolder.iv_icon);


         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_name;
        public TextView tv_content;
        public CustomRoundImageView iv_icon;

        public FunViewHolder(View view){
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
            iv_icon = view.findViewById(R.id.iv_icon);
        }
    }
}
