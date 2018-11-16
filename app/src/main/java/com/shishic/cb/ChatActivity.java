package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.ChatAdapter;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.ChatBean;
import com.shishic.cb.fragment.MyFragment;
import com.shishic.cb.loadmore.IRefreshHandler;
import com.shishic.cb.loadmore.ListRefreshLayout;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;

    private EditText et_content;

    private TextView tv_submit;


    private ListRefreshLayout recyclerView;

    private ChatAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initListener();
        requestData();
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_content = findViewById(R.id.et_content);
        tv_submit = findViewById(R.id.tv_submit);
        tv_title.setText("用户聊天室");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setShowLastTips(false);
        recyclerView.setRecyclerLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        adapter = new ChatAdapter(list,this);
        recyclerView.setBackgroundColorResource((R.color.c_gray_f7f7f7));
        recyclerView.setRecyclerAdapter(adapter);
        recyclerView.setHandler(new IRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return true;
            }

            @Override
            public boolean canLoad() {
                return true;
            }

            @Override
            public void refresh(int requestPage) {
                requestHistoryData();
            }

            @Override
            public void load(int requestPage) {
                requestData();
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //评论提交
                requestComment();
            }
        });
    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    /**
     * 请求以前的数据
     */
    private void requestHistoryData(){
        HashMap<String,String> params = new HashMap<>();
        String lastMinId = "1";
        if(adapter != null && adapter.getList() != null && adapter.getList().size() > 0){
            lastMinId =  String.valueOf(adapter.getList().get(0).getId());
        }
        params.put("lastMinId",lastMinId);
        params.put("length","20");
//        params.put("userName",Account.getAccount().getUserName());
//        params.put("userId",String.valueOf(Account.getAccount().getId()));
        RequestUtil.httpGet(this, Constant.URL_CHAT_HISTORY, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(ChatActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_CHAT_RECORD response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
//                    ToastUtils.toastShow(ChatActivity.this, msg);
                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        JSONArray data = jsonObject.optJSONArray("data");
                        if(data != null && data.length() > 0){
                            List<ChatBean> list = new Gson().fromJson(data.toString(), new TypeToken<List<ChatBean>>(){}.getType());
                            if(adapter != null && recyclerView != null){
                                adapter.getList().addAll(0,list);
                            }
                            recyclerView.completeRefresh(true);
                        }
                    }
                    recyclerView.setTotalPage(10000);
                    recyclerView.completeRefresh(true);
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }


    /**
     * 请求新数据
     */
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        String lastId = "0";
        if(adapter != null && adapter.getList() != null && adapter.getList().size() > 0){
            lastId =  String.valueOf(adapter.getList().get(adapter.getList().size()-1).getId());
        }
        params.put("lastId",lastId);
        params.put("length","20");
//        params.put("userName",Account.getAccount().getUserName());
//        params.put("userId",String.valueOf(Account.getAccount().getId()));
        RequestUtil.httpGet(this, Constant.URL_CHAT_RECORD, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(ChatActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_CHAT_RECORD response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
//                    ToastUtils.toastShow(ChatActivity.this, msg);
                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        JSONArray data = jsonObject.optJSONArray("data");
                        if(data != null && data.length() > 0){
                            List<ChatBean> list = new Gson().fromJson(data.toString(), new TypeToken<List<ChatBean>>(){}.getType());
                            if(recyclerView != null){
                                recyclerView.updateAdd(list);
                            }

                        }
                    }
                    recyclerView.setTotalPage(10000);
                    recyclerView.completeRefresh(true);
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

    private void requestComment(){
        String content = et_content.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            ToastUtils.toastShow(this, "请输入聊天内容");
            return;
        }
        HashMap<String,String> params = new HashMap<>();
        params.put("lastMinId","0");
        params.put("length","20");
        params.put("userName",Account.getAccount().getUserName());
        params.put("userId",String.valueOf(Account.getAccount().getId()));
        params.put("content",content);
        params.put("userIcon",String.valueOf(Account.getAccount().getIcon()));
        RequestUtil.httpGet(this, Constant.URL_CHAT_SAVE, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","onErrorResponse URL_CHAT_RECORD");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_CHAT_RECORD response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        if(et_content != null){
                            et_content.setText("");
                        }
                        //去刷新接口，
                        requestData();
                    }
                } catch (Exception exception){
                   exception.printStackTrace();
                }
            }
        });


    }


}
