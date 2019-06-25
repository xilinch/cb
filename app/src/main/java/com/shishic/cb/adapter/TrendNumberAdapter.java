package com.shishic.cb.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.util.LogUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrendNumberAdapter extends PlusRecyclerAdapter<HistoryBean> {

    private Context context;
    private int type = 0;//从个位到万位
    private int[] nums = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1};
    private int[] numBasic = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public TrendNumberAdapter(List<HistoryBean> list, Context context) {
        super(list);
        this.context = context;
    }

    public void setType(int type) {
        this.type = type;
        for (int i = 0; i < nums.length; i++) {
            nums[i] = 1;
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_trend_title, parent, false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoryBean funBean = getList().get(position);
        int beanType = funBean.getType();
        if (holder instanceof FunViewHolder) {
            FunViewHolder funViewHolder = (FunViewHolder) holder;
            if (beanType == -1) {
                //
                funViewHolder.tv_journ.setText("期数");
                for (int i = 0; i <= 10; i++) {
                    funViewHolder.textViews.get(i).setTextColor(Color.BLACK);
                    funViewHolder.textViews.get(i).setText(String.valueOf(numBasic[i]));
                    funViewHolder.textViews.get(i).setBackgroundResource(R.drawable.dd_shape_circle_white);
                }

            } else {
                //显示具体的内容
                funViewHolder.tv_journ.setText(String.valueOf(funBean.getJournal()));
                int[][] lc = funBean.getLc();
                int value = 0;
                if (type == 0) {
                    value = funBean.getN1();
                } else if (type == 1) {
                    value = funBean.getN2();
                } else if (type == 2) {
                    value = funBean.getN3();
                } else if (type == 3) {
                    value = funBean.getN4();
                } else if (type == 4) {
                    value = funBean.getN5();
                } else if (type == 5) {
                    value = funBean.getN6();
                } else if (type == 6) {
                    value = funBean.getN7();
                } else if (type == 7) {
                    value = funBean.getN8();
                } else if (type == 8) {
                    value = funBean.getN9();
                } else if (type == 9) {
                    value = funBean.getN10();
                }
                LogUtil.e("my","position:"+ position +" HistoryBean:" + funBean.toString());
                LogUtil.e("my","nums:"+ Arrays.toString(nums));
                for (int i = 0; i <= 10; i++) {
                    if (value == numBasic[i]) {
                        //中了 红色底白色字，
                        if(lc[type][i] < 0){
                            lc[type][i] = (value);
                        }
                        funViewHolder.textViews.get(i).setText(String.valueOf(value));
                        nums[i] = 1;
                        funViewHolder.textViews.get(i).setTextColor(Color.WHITE);
                        funViewHolder.textViews.get(i).setBackgroundResource(R.drawable.dd_shape_circle_red);
                    } else {
                        if(lc[type][i] < 0){
                            lc[type][i] = nums[i];
                            funViewHolder.textViews.get(i).setText(String.valueOf(nums[i]));
                            nums[i] = nums[i] + 1;
                        } else {
                            funViewHolder.textViews.get(i).setText(String.valueOf(lc[type][i]));
                        }
                        funViewHolder.textViews.get(i).setTextColor(Color.BLACK);
                        funViewHolder.textViews.get(i).setBackgroundResource(R.drawable.dd_shape_circle_white);
                    }
                }

            }
        }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_root;
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
        public ArrayList<TextView> textViews = new ArrayList<>();

        public FunViewHolder(View view) {
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
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
        }
    }


}
