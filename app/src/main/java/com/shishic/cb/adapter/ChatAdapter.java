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
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<ChatBean> list;
    private Account account;

    public ChatAdapter(List<ChatBean> list, Context context) {
        this.list = list;
        this.context = context;
        account = Account.getAccount();
    }

    public List<ChatBean> getList() {
        return list;
    }

    public void addList(List<ChatBean> list) {
        if (this.list != null) {
            this.list.addAll(list);
        } else {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        ChatBean chatBean = list.get(position);
        if(account != null && chatBean.getUserId() == account.getId()){
            return R.layout.adapter_chat_right;
        } else {
            return R.layout.adapter_chat;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder;
        if(viewType == R.layout.adapter_chat_right){
            ViewHolder = new FunViewHolderRight(LayoutInflater.from(context).inflate(R.layout.adapter_chat_right, parent, false));
        } else {
            ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_chat, parent, false));
        }
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatBean funBean = list.get(position);
        if (holder instanceof BaseChatViewHolder) {
            BaseChatViewHolder funViewHolder = (BaseChatViewHolder) holder;
            funViewHolder.tv_name.setText(funBean.getUserName());
            funViewHolder.tv_content.setText(funBean.getContent());
            String url = funBean.getUserIcon();
            Glide.with(context).load(url).placeholder(R.mipmap.my_avatar_icon_default).centerCrop().into(funViewHolder.iv_icon);
        }
    }

    static class BaseChatViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_name;
        public TextView tv_content;
        public CustomRoundImageView iv_icon;

        public BaseChatViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
            iv_icon = view.findViewById(R.id.iv_icon);
        }
    }

    static class FunViewHolder extends BaseChatViewHolder {
        public FunViewHolder(View view) {
            super(view);
        }
    }

    static class FunViewHolderRight extends BaseChatViewHolder {

        public FunViewHolderRight(View view) {
            super(view);
        }
    }
}
