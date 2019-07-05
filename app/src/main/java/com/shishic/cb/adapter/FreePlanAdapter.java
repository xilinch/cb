package com.shishic.cb.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.FreePlan;
import com.shishic.cb.bean.FreePlanTabBean;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;

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
        LogUtil.e("my","viewHolder" + viewHolder.getClass().getSimpleName());
        LogUtil.e("my","object i:" + object.getClass().getSimpleName());
        if(viewHolder instanceof PlanViewHolder && object instanceof FreePlan.ListBean){
            final PlanViewHolder holder = (PlanViewHolder) viewHolder;
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
            int planType = freePlan1.getPlanType();
            if(planType == 2){
                //显示单式组合
                String all = getDanshiAllNumbers(freePlan1.getRecommendNumbers());
                holder.tv_danshi_all.setText("");
                holder.tv_danshi_all.setVisibility(View.VISIBLE);
                holder.tv_danshi_all.setText(all);
                holder.tv_danshi_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //复制到粘贴板
                        String numbers = holder.tv_danshi_all.getText().toString();
                        // 11以后使用content.Clipboardmanager,之前使用text.ClipboardManager
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 将文本内容放到系统剪贴板里。
                        cm.setText(numbers);
                        ToastUtils.toastShow(context,"已复制。");
                    }
                });
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

    private String getDanshiAllNumbers(String recommendNumbers){
        StringBuilder result = new StringBuilder();
        if(recommendNumbers != null){
            String[] numbers = recommendNumbers.split(",");
            if(numbers != null ){

                int length = numbers.length;
                if(length == 1){
                    //如果是一个
                    String[] ge = numbers[0].split("-");
                    for(int i = 0; i < ge.length; i++){
                        result.append(ge[i]).append(" ");
                    }
                } else if(length == 2){
                    //计算出2所有的组合
                    String[] shi = numbers[0].split("-");
                    String[] ge = numbers[1].split("-");
                    for(int i =0 ; i < shi.length ;i++){
                        for(int j = 0 ;j < ge.length;j++){
                            result.append(shi[i]).append(ge[j]).append(" ");
                        }
                    }
                } else if(length == 3){
                    //计算出2所有的组合
                    String[] bai = numbers[0].split("-");
                    String[] shi = numbers[1].split("-");
                    String[] ge = numbers[2].split("-");
                    for(int i =0 ; i < bai.length ;i++){
                        for(int j = 0 ;j < shi.length;j++){
                            for(int k = 0 ;k < ge.length;k++){
                                result.append(bai[i]).append(shi[j]).append(ge[k]).append(" ");
                            }

                        }
                    }
                } else if(length == 4){
                    //计算出2所有的组合
                    String[] qian = numbers[0].split("-");
                    String[] bai = numbers[1].split("-");
                    String[] shi = numbers[2].split("-");
                    String[] ge = numbers[3].split("-");
                    for(int i =0 ; i < qian.length ;i++) {
                        for (int j = 0; j < bai.length; j++) {
                            for (int k = 0; k < shi.length; k++) {
                                for (int l = 0; l  < ge.length; l++) {
                                    result.append(qian[i]).append(bai[j]).append(shi[k]).append(ge[l]).append(" ");
                                }
                            }
                        }
                    }
                } else {
                    //不支持
                    result.append("暂不支持");
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
