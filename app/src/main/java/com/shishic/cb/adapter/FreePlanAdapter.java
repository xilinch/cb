package com.shishic.cb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.FreePlanBean;

import java.util.List;

public class FreePlanAdapter extends RecyclerView.Adapter {

    private List<FreePlanBean> list;

    private Context context;

    public FreePlanAdapter(List<FreePlanBean> list,Context context){
        this.list = list;
        this.context = context;
    }

    public void changeData(List<FreePlanBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<FreePlanBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder = new PlanViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof PlanViewHolder){
            PlanViewHolder holder = (PlanViewHolder) viewHolder;
            holder.tv_content.setText(list.get(i).getContent());
        }
    }

    @Override
    public int getItemCount() {
        if(list != null){
            return list.size();
        }
        return 0;
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_content;

        public PlanViewHolder(View view){
            super(view);
            tv_content = view.findViewById(R.id.tv_content);
        }
    }

}
