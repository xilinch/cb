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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.H5Activity;
import com.shishic.cb.IntroduceActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.ShuidaoBean;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.List;

public class ShuidaoAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<ShuidaoBean> list;

    public ShuidaoAdapter(List<ShuidaoBean> list, Context context){
        this.list = list;
        this.context = context;
    }


    public void updateData(List<ShuidaoBean> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_shuidao,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ShuidaoBean funBean = list.get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             funViewHolder.tv_title.setText(funBean.getIntroduce());
             Glide.with(context).load(funBean.getIcon()).centerCrop().into(funViewHolder.iv_logo);
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
