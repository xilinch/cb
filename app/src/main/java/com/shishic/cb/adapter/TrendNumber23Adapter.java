package com.shishic.cb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.util.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendNumber23Adapter extends PlusRecyclerAdapter<HistoryBean> {

    private Context context;
    //2 后2, 3 后3,默认后2
    private int type = 2;
    private int[] numBasic = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10};

    public TrendNumber23Adapter(List<HistoryBean> list, Context context) {
        super(list);
        this.context = context;
    }

    public void setType(int type) {
        this.type = type;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_trend_23, parent, false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryBean funBean = getList().get(position);
        int beanType = funBean.getType();
        if (holder instanceof FunViewHolder) {
            FunViewHolder funViewHolder = (FunViewHolder) holder;
            funViewHolder.tv_journ.setText(String.valueOf(funBean.getJournal()));
            int number_1 = funBean.getN1();
            int number_2 = funBean.getN2();
            int number_3 = funBean.getN3();
            int number_4 = funBean.getN4();
            int number_5 = funBean.getN5();
//            int[] numbers = new int[]{number_1,
//                    number_2,
//                    number_3,
//                    number_4,
//                    number_5};
            //计算重复数字
            int[] numbers_num;
            if(type == 3){
                numbers_num = new int[]{number_1, number_2, number_3};
            } else {
                numbers_num = new int[]{number_1, number_2};
            }

            for (int i = 0; i < 10; i++) {
                int curNumber = numBasic[i];
                funViewHolder.textViews.get(i).setTextColor(Color.BLACK);
                funViewHolder.textViews.get(i).setText(String.valueOf(curNumber));
                funViewHolder.textViews.get(i).setBackgroundResource(R.drawable.dd_shape_circle_white);
                int curNumber_repeat_times = 0;
                for(int k =0 ;k < numbers_num.length;k++){
                    if(curNumber == numbers_num[k]){
                        funViewHolder.textViews.get(i).setBackgroundResource(R.drawable.dd_shape_circle_red);
                        curNumber_repeat_times = curNumber_repeat_times + 1;
                    }
                }
                if(curNumber_repeat_times > 0){
                    funViewHolder.textViewsNum.get(i).setVisibility(View.VISIBLE);
                    funViewHolder.textViewsNum.get(i).setText(String.valueOf(curNumber_repeat_times));
                } else {
                    funViewHolder.textViewsNum.get(i).setVisibility(View.GONE);
                }
            }

        }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_journ;
        public TextView tv_0;
        public TextView tv_1;
        public TextView tv_2;
        public TextView tv_3;
        public TextView tv_4;
        public TextView tv_5;
        public TextView tv_6;
        public TextView tv_7;
        public TextView tv_8;
        public TextView tv_9;
        public TextView tv_10;
        public TextView tv_0_num;
        public TextView tv_1_num;
        public TextView tv_2_num;
        public TextView tv_3_num;
        public TextView tv_4_num;
        public TextView tv_5_num;
        public TextView tv_6_num;
        public TextView tv_7_num;
        public TextView tv_8_num;
        public TextView tv_9_num;
        public TextView tv_10_num;
        public ArrayList<TextView> textViews = new ArrayList<>();
        public ArrayList<TextView> textViewsNum = new ArrayList<>();

        public FunViewHolder(View view) {
            super(view);
            tv_journ = view.findViewById(R.id.tv_journ);
            tv_0 = view.findViewById(R.id.tv_0);
            tv_1 = view.findViewById(R.id.tv_1);
            tv_2 = view.findViewById(R.id.tv_2);
            tv_3 = view.findViewById(R.id.tv_3);
            tv_4 = view.findViewById(R.id.tv_4);
            tv_5 = view.findViewById(R.id.tv_5);
            tv_6 = view.findViewById(R.id.tv_6);
            tv_7 = view.findViewById(R.id.tv_7);
            tv_8 = view.findViewById(R.id.tv_8);
            tv_9 = view.findViewById(R.id.tv_9);
            tv_10 = view.findViewById(R.id.tv_10);
            tv_0_num = view.findViewById(R.id.tv_0_num);
            tv_1_num = view.findViewById(R.id.tv_1_num);
            tv_2_num = view.findViewById(R.id.tv_2_num);
            tv_3_num = view.findViewById(R.id.tv_3_num);
            tv_4_num = view.findViewById(R.id.tv_4_num);
            tv_5_num = view.findViewById(R.id.tv_5_num);
            tv_6_num = view.findViewById(R.id.tv_6_num);
            tv_7_num = view.findViewById(R.id.tv_7_num);
            tv_8_num = view.findViewById(R.id.tv_8_num);
            tv_9_num = view.findViewById(R.id.tv_9_num);
            tv_10_num = view.findViewById(R.id.tv_10_num);
            textViews.add(tv_0);
            textViews.add(tv_1);
            textViews.add(tv_2);
            textViews.add(tv_3);
            textViews.add(tv_4);
            textViews.add(tv_5);
            textViews.add(tv_6);
            textViews.add(tv_7);
            textViews.add(tv_8);
            textViews.add(tv_9);
            textViews.add(tv_10);
            textViewsNum.add(tv_0_num);
            textViewsNum.add(tv_1_num);
            textViewsNum.add(tv_2_num);
            textViewsNum.add(tv_3_num);
            textViewsNum.add(tv_4_num);
            textViewsNum.add(tv_5_num);
            textViewsNum.add(tv_6_num);
            textViewsNum.add(tv_7_num);
            textViewsNum.add(tv_8_num);
            textViewsNum.add(tv_9_num);
            textViewsNum.add(tv_10_num);
        }
    }


}
