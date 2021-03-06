package com.shishic.cb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.IntroduceActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.SpecialBean;

import java.util.HashMap;
import java.util.List;

public class NumberChoiceAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<HashMap<String,String>> list;

    public NumberChoiceAdapter(List<HashMap<String,String>> list, Context context){
        this.list = list;
        this.context = context;
    }


    public void updateData(List<HashMap<String,String>> list){
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
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_number_choice,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final HashMap<String,String> funBean = list.get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             String type = funBean.get("type");
             if("1".equals(type)){
                 //头部信息
                 funViewHolder.tv_lost.setText(funBean.get("lost"));
                 funViewHolder.tv_number.setText(funBean.get("number"));
             } else {
                 funViewHolder.tv_lost.setText(funBean.get("lost"));
                 funViewHolder.tv_number.setText(funBean.get("number"));
             }


         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_number;
        public TextView tv_lost;


        public FunViewHolder(View view){
            super(view);
            tv_number = view.findViewById(R.id.tv_number);
            tv_lost = view.findViewById(R.id.tv_lost);

        }
    }
}
