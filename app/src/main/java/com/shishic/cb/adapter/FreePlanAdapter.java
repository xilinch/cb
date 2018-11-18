package com.shishic.cb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.FreePlan;
import com.shishic.cb.util.LogUtil;

import java.util.List;

public class FreePlanAdapter extends RecyclerView.Adapter {

    private List<Object> list;

    private Context context;

    public FreePlanAdapter(List<Object> list,Context context){
        this.list = list;
        this.context = context;
    }

    public void changeData(List<Object> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(List<Object> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LogUtil.e("my","onCreateViewHolder: i:" + i);
        RecyclerView.ViewHolder holder;
        Object object = list.get(i);
        if(object instanceof FreePlan){
            holder = new PlanTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan_title,viewGroup,false));
        } else if(object instanceof FreePlan.ListBean){
            holder = new PlanViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan,viewGroup,false));
        } else {
            holder = new PlanViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan,viewGroup,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Object object = list.get(i);
        LogUtil.e("my","onBindViewHolder: i:" + i);
        LogUtil.e("my","viewHolder" + viewHolder.getClass().getSimpleName());
        LogUtil.e("my","object i:" + object.getClass().getSimpleName());
        if(viewHolder instanceof PlanViewHolder && object instanceof FreePlan.ListBean){
            PlanViewHolder holder = (PlanViewHolder) viewHolder;
            FreePlan.ListBean freePlan1 = (FreePlan.ListBean) object;
            //推荐号码【04568】 第068期 出46836(6) 中
            String end = "";
            if(freePlan1.getRecommendStatus() == 0){
                //等待中
                end = "等待中";
            } else if(freePlan1.getRecommendStatus() == 1){
                end = "中";
            } else if(freePlan1.getRecommendStatus() == -1){
                end = "錯";
            }
            String showContent = "推荐号码【" + freePlan1.getRecommendNumbers() + "】 第" + freePlan1.getCurrenJounal() +
                    "期出：" + freePlan1.getLuckyNumbers() + "  "+ end;
            holder.tv_content.setText(showContent);
            holder.tv_jounal.setText(freePlan1.getFromJounal() + "-" + freePlan1.getEndJounal());
        } else if(viewHolder instanceof PlanTitleViewHolder && object instanceof FreePlan){
            FreePlan freePlan = (FreePlan)object;
            PlanTitleViewHolder holder = (PlanTitleViewHolder) viewHolder;
            holder.tv_content.setText(freePlan.getPlanName());
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
        public TextView tv_jounal;
        public TextView tv_content;

        public PlanViewHolder(View view){
            super(view);
            tv_content = view.findViewById(R.id.tv_content);
            tv_jounal = view.findViewById(R.id.tv_jounal);
        }
    }

    static class PlanTitleViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_content;

        public PlanTitleViewHolder(View view){
            super(view);
            tv_content = view.findViewById(R.id.tv_content);
        }
    }

}
