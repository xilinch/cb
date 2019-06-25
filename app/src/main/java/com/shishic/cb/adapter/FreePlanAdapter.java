package com.shishic.cb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
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

    /**
     *  ZHIXUAN(1,"直选"),
     *     SINGLE(2,"单式"),
     *     ZHU_SAN(3,"组三"),
     *     ZHU_LIU(4,"组六"),
     *     FUXUAN(5,"复选");
     */
    private int planType;

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

    public int getPlanType() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType = planType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        LogUtil.e("my","onCreateViewHolder: i:" + i);
        RecyclerView.ViewHolder holder;
        if(i == R.layout.adapter_free_plan_title){
            holder = new PlanTitleViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan_title,viewGroup,false));
        } else {
            holder = new PlanViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_free_plan,viewGroup,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        Object object = list.get(i);
//        LogUtil.e("my","onBindViewHolder: i:" + i);
//        LogUtil.e("my","viewHolder" + viewHolder.getClass().getSimpleName());
//        LogUtil.e("my","object i:" + object.getClass().getSimpleName());
        if(viewHolder instanceof PlanViewHolder && object instanceof FreePlan.ListBean){
            PlanViewHolder holder = (PlanViewHolder) viewHolder;
            FreePlan.ListBean freePlan1 = (FreePlan.ListBean) object;
            //xxx-xxx期 中

            //推荐号码【04568】 第068期 出46836(6)


            String end = "";
            if(freePlan1.getRecommendStatus() == 0){
                //等待中
                end = "等待中";
            } else if(freePlan1.getRecommendStatus() == 1){
                end = "中";
            } else if(freePlan1.getRecommendStatus() == -1){
                end = "錯";
            }
            String luckNum = freePlan1.getLuckyNumbers();
            if(TextUtils.isEmpty(luckNum)){
                luckNum = "     ";
            }
            String showContent = "推荐号码【" + freePlan1.getRecommendNumbers() + "】 第" + freePlan1.getCurrenJounal() + "期出：" + luckNum;
            SpannableString recomentSpan = new SpannableString(showContent);
            recomentSpan.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), "推荐号码【".length(), showContent.indexOf("】"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            recomentSpan.setSpan(new ForegroundColorSpan(Color.RED),"推荐号码【".length(), showContent.indexOf("】"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.tv_content.setText(showContent);
            String str1 = freePlan1.getFromJounal() + "-" + freePlan1.getEndJounal() + "期 ";
            SpannableString jounal = new SpannableString(str1 + end);
            jounal.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), str1.length(), jounal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            jounal.setSpan(new ForegroundColorSpan(Color.RED),str1.length(),jounal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tv_jounal.setText(jounal);
            //如果是单式的话，自己组合全部的集合

            if(planType == 2){
                //显示单式组合
                String all = getAllNumbers();
                holder.tv_danshi_all.setText("");
                holder.tv_danshi_all.setVisibility(View.VISIBLE);
            } else {
                holder.tv_danshi_all.setVisibility(View.GONE);
            }



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

    private String getAllNumbers(String recommendNumbers){
        StringBuilder result = new StringBuilder();
        if(recommendNumbers != null){
            String[] numbers = recommendNumbers.split(",");
            if(numbers != null ){
                for(int i = 0; i < numbers.length; i++){

                    String[] numbers1 = numbers[i].split("-");
                    if(numbers1 != null){
                        for(int j = 0 ; j < numbers1.length; j++){
                            //自动加上

                        }
                    }
                }
            }

        }
        return result.toString();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = list.get(position);
        if(object instanceof FreePlan.ListBean){
            return R.layout.adapter_free_plan;
        } else {
            return R.layout.adapter_free_plan_title;
        }
    }

    static class PlanViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_jounal;
        public TextView tv_content;
        public TextView tv_danshi_all;

        public PlanViewHolder(View view){
            super(view);
            tv_content = view.findViewById(R.id.tv_content);
            tv_jounal = view.findViewById(R.id.tv_jounal);
            tv_danshi_all = view.findViewById(R.id.tv_danshi_all);
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
