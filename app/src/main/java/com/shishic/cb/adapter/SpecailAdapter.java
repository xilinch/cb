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

import com.bumptech.glide.Glide;
import com.shishic.cb.IntroduceActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.List;

public class SpecailAdapter extends PlusRecyclerAdapter<SpecialBean> {

    private Context context;

    public SpecailAdapter(List<SpecialBean> list, Context context){
        super(list);
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_special,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SpecialBean funBean = getList().get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             String contact = funBean.getContact();
             if(TextUtils.isEmpty(contact) && !funBean.isPayed()){
                 //没有内容
                 contact = "支付后可见联系方式";
             }
             funViewHolder.tv_contact.setText(contact);
             funViewHolder.tv_content.setText(funBean.getContent());
             funViewHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(context, IntroduceActivity.class);
                     intent.putExtra("introduce",funBean.getIntroduce());
                     context.startActivity(intent);
                 }
             });
         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_contact;
        public TextView tv_content;
        public LinearLayout ll_root;

        public FunViewHolder(View view){
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
            tv_content = view.findViewById(R.id.tv_content);
            tv_contact = view.findViewById(R.id.tv_contact);
        }
    }
}
