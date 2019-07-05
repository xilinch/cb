package com.shishic.cb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.H5Activity;
import com.shishic.cb.R;
import com.shishic.cb.bean.NewsBean;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter {

    private List<NewsBean> list;

    private Context context;

    public NewsAdapter(List<NewsBean> list, Context context) {
        this.list = list;
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.context = context;
    }


    public void changeDate(List<NewsBean> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_news, parent, false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final NewsBean funBean = list.get(position);
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder funViewHolder = (NewsViewHolder) holder;
            funViewHolder.tv_title.setText(funBean.getTitle());
            String url = funBean.getCover();
            Glide.with(context).load(url).centerCrop().placeholder(R.mipmap.icon_default1).into(funViewHolder.iv_icon);

            funViewHolder.tv_des.setText(funBean.getSummary());
            funViewHolder.tv_time.setVisibility(View.GONE);
            funViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String link = funBean.getUrl();
                    if (URLUtil.isValidUrl(link)){
                        Intent intent = new Intent();
                        intent.setClass(context, H5Activity.class);
                        intent.putExtra("url", link);
                        context.startActivity(intent);
                    }


                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_time;
        public TextView tv_des;
        public TextView tv_title;
        public ImageView iv_icon;

        public NewsViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_time = view.findViewById(R.id.tv_time);
            tv_des = view.findViewById(R.id.tv_des);
            iv_icon = view.findViewById(R.id.iv_icon);
        }
    }
}
