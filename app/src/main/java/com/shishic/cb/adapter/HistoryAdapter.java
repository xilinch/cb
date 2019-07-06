package com.shishic.cb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shishic.cb.R;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.List;

public class HistoryAdapter extends PlusRecyclerAdapter<HistoryBean> {

    private Context context;

    public HistoryAdapter(List<HistoryBean> list, Context context){
        super(list);
        this.context = context;
    }

    public void changeList(){

    }

    @Override
    public int getItemViewType(int position) {
        HistoryBean funBean = getList().get(position);
        int type = funBean.getType();
        if(type < 0){
            return R.layout.adapter_history_top;
        } else {

            return R.layout.adapter_history;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType == R.layout.adapter_history_top){
           return new FunTopViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_history_top,parent,false));
       } else {
           RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_history,parent,false));
           return ViewHolder;
       }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryBean funBean = getList().get(position);

         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
//             String code = "【" +funBean.getN5() + "   " + funBean.getN4() + "   " + funBean.getN3() + "   " + funBean.getN2() + "   " + funBean.getN1() + "】";
             String result = funBean.getOpenCode();
             SpannableString sb = new SpannableString(result);
             sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             sb.setSpan(new ForegroundColorSpan(Color.RED), 0,result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

             funViewHolder.tv_opencode.setText(sb);
             String journal = String.valueOf(funBean.getJournal());
             funViewHolder.tv_expect.setText(journal);
//             funViewHolder.tv_opentime.setDescription("开奖时间：" + funBean.getOpentime());
             if(position % 2 == 1){
                 funViewHolder.ll_root.setBackgroundColor(Color.parseColor("#f2f2f2"));
             } else {
                 funViewHolder.ll_root.setBackgroundColor(Color.WHITE);
             }
         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_opencode;
        public TextView tv_expect;
        public TextView tv_opentime;
        public LinearLayout ll_root;

        public FunViewHolder(View view){
            super(view);
            tv_opencode = view.findViewById(R.id.tv_opencode);
            tv_expect = view.findViewById(R.id.tv_expect);
            tv_opentime = view.findViewById(R.id.tv_opentime);
            ll_root = view.findViewById(R.id.ll_root);
        }
    }

    static class FunTopViewHolder extends RecyclerView.ViewHolder{

        public FunTopViewHolder(View view){
            super(view);

        }
    }
}
