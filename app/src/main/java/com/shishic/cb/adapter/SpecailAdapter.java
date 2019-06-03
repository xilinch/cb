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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shishic.cb.IntroduceActivity;
import com.shishic.cb.R;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.loadmore.PlusRecyclerAdapter;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.CustomRoundImageView;

import java.util.List;

public class SpecailAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<SpecialBean> list;

    public SpecailAdapter(List<SpecialBean> list, Context context){
        this.list = list;
        this.context = context;
    }


    public void updateData(List<SpecialBean> list){
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
        RecyclerView.ViewHolder ViewHolder = new FunViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_special,parent,false));
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SpecialBean funBean = list.get(position);
         if(holder instanceof FunViewHolder){
             FunViewHolder funViewHolder = (FunViewHolder) holder;
             String contact = funBean.getContact();
             if(!funBean.isPayed()){
                 //没有内容
                 contact = "点击进行支付，或者联系客服，可见联系方式";
                 funViewHolder.tv_contact.setVisibility(View.VISIBLE);
                 funViewHolder.tv_contact.setText(contact);
                 funViewHolder.tv_contact.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //todo 进行支付，支付完了刷新页面

                     }
                 });
             } else {
                 funViewHolder.tv_contact.setVisibility(View.GONE);
                 funViewHolder.tv_contact.setText(contact);
                 funViewHolder.tv_contact.setOnClickListener(null);
             }
             funViewHolder.tv_content.setText(funBean.getContent());
             funViewHolder.iv_tag.setText("TOP" + (position+ 1));
             funViewHolder.ll_root.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     if(funBean.isPayed()){
                         Intent intent = new Intent(context, IntroduceActivity.class);
                         intent.putExtra("introduce",funBean.getIntroduce());
                         context.startActivity(intent);
                     } else {
//                         ToastUtils.toastShow(context,"请联系客服，查看专家联系方式");
                         Toast.makeText(context,"请联系客服，查看专家联系方式",Toast.LENGTH_SHORT).show();
                     }

                 }
             });
         }
    }


    static class FunViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_contact;
        public TextView tv_content;
        public TextView tv_detail;
        public TextView iv_tag;
        public LinearLayout ll_root;

        public FunViewHolder(View view){
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
            tv_content = view.findViewById(R.id.tv_content);
            tv_contact = view.findViewById(R.id.tv_contact);
            tv_detail = view.findViewById(R.id.tv_detail);
            iv_tag = view.findViewById(R.id.iv_tag);
        }
    }
}
