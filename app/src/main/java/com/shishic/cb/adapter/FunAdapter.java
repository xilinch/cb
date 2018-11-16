package com.shishic.cb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.ChatActivity;
import com.shishic.cb.FreePlanActivity;
import com.shishic.cb.H5Activity;
import com.shishic.cb.HistoryActivity;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.R;
import com.shishic.cb.SpecialActivity;
import com.shishic.cb.TrendNumberActivity;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.ArrayList;
import java.util.List;

public class FunAdapter extends RecyclerView.Adapter {

    private List<FunBean> list;

    private Context context;

    public FunAdapter(List<FunBean> list, Context context) {
        this.list = list;
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_hotspot, parent, false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final FunBean funBean = list.get(position);
        if (holder instanceof FunViewHolder) {
            FunViewHolder funViewHolder = (FunViewHolder) holder;
            funViewHolder.tv_title.setText(funBean.getText());
            String url = funBean.getIcon();
            Glide.with(context).load(url).placeholder(R.mipmap.icon_default).centerCrop().into(funViewHolder.iv_logo);
            final String link = funBean.getUrl();
            if (URLUtil.isValidUrl(link)) {
                funViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        if(funBean.getText().equals("用户聊天室")){
                            //用户聊天室
                            intent.setClass(context, ChatActivity.class);
                            context.startActivity(intent);
                        } else if(funBean.getText().equals("专家计划")){
                            //专家计划
                            intent.setClass(context, SpecialActivity.class);
                            context.startActivity(intent);
                        } else if(funBean.getText().equals("历史开奖")){
                            //历史开奖
                            intent.setClass(context, HistoryActivity.class);
                            context.startActivity(intent);
                        } else if(funBean.getText().equals("遗漏统计")){
                            //遗漏统计
                            intent.setClass(context, LostAnalyActivity.class);
                            context.startActivity(intent);
                        } else if(funBean.getText().equals("免费计划")){
                            //免费计划
                            intent.setClass(context, FreePlanActivity.class);
                            context.startActivity(intent);
                        } else if(funBean.getText().equals("走势图")){
                            //走势图
                            intent.setClass(context, TrendNumberActivity.class);
                            context.startActivity(intent);
                        } else{
                            intent.setClass(context, H5Activity.class);
                            intent.putExtra("url", link);
                            context.startActivity(intent);
                        }

                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    static class FunViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public CustomRoundImageView iv_logo;

        public FunViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            iv_logo = view.findViewById(R.id.iv_logo);
        }
    }
}
