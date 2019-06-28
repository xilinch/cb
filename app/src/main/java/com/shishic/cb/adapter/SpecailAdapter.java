package com.shishic.cb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.IntroduceActivity;
import com.shishic.cb.LoginActivity;
import com.shishic.cb.R;
import com.shishic.cb.SpecialActivity;
import com.shishic.cb.SpecialActivity2;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.dialog.BuySpecialDialog;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.LoginUtil;
import com.shishic.cb.util.NFCallback;
import com.shishic.cb.util.RequestUtils;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SpecailAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<SpecialBean> list;

    private OnRefreshListener onRefreshListener;


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

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
                         //判断是否登录，如果未登录，先登录
                         Account account = Account.getAccount();
                         if(account != null){
                             showConfirmDialog(funBean);
                         } else {
                             Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(context,LoginActivity.class);
                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             context.startActivity(intent);
                         }
                         //

                     }
                 });
             } else {
//                 contact = "点击查看，专家联系方式。";
                 funViewHolder.tv_contact.setVisibility(View.VISIBLE);
                 funViewHolder.tv_contact.setText(contact);
                 funViewHolder.tv_contact.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent intent = new Intent(context, IntroduceActivity.class);
                         intent.putExtra("introduce",funBean.getIntroduce());
                         context.startActivity(intent);
                     }
                 });
             }

             funViewHolder.tv_title.setText("专家名称：" + funBean.getName());
             funViewHolder.tv_fee.setText("费用：" + funBean.getFee()+ "金币");
             funViewHolder.tv_content.setText(funBean.getContent());
             funViewHolder.iv_tag.setText("购买人气榜TOP" + (position+ 1));
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
        public TextView tv_title;
        public TextView tv_fee;
        public LinearLayout ll_root;

        public FunViewHolder(View view){
            super(view);
            ll_root = view.findViewById(R.id.ll_root);
            tv_content = view.findViewById(R.id.tv_content);
            tv_contact = view.findViewById(R.id.tv_contact);
            tv_detail = view.findViewById(R.id.tv_detail);
            iv_tag = view.findViewById(R.id.iv_tag);
            tv_title = view.findViewById(R.id.tv_title);
            tv_fee = view.findViewById(R.id.tv_fee);
        }
    }

    private BuySpecialDialog buySpecialDialog;

    /**
     * 购买确认
     * @param funBean
     */
    private void showConfirmDialog(final SpecialBean funBean){
        if(buySpecialDialog == null){
            buySpecialDialog = new BuySpecialDialog(context);
        }
        buySpecialDialog.setConfirmClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buy(funBean);
            }
        });
        buySpecialDialog.setSpecialBean(funBean);
        buySpecialDialog.show();
    }

    private void buy(SpecialBean funBean){
        HashMap<String,String> params = new HashMap<>();
        if(Account.getAccount() == null){
            params.put("userId",String.valueOf("0"));
        } else {
            params.put("userId",String.valueOf(Account.getAccount().getId()));
        }
        params.put("eId",String.valueOf(funBean.getId()));

        RequestUtils.httpget(context, Constant.URL_EXPORT_BUY, params, new NFCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                ToastUtils.toastShow(context, R.string.network_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                try {
                    String result = response.body().string();
                    LogUtil.e("my","URL_EXPORT response:" + response + " result:" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    final String msg = jsonObject.optString("msg");
                    boolean success = jsonObject.optBoolean("success");
                    //刷新列表
                    if(onRefreshListener != null && success){
                        onRefreshListener.onRefresh();
                    }
                    int code = jsonObject.optInt("code");
                    if(code == 5000){
                        LoginUtil.login();
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

    public interface OnRefreshListener{
        void onRefresh();
    }
}
