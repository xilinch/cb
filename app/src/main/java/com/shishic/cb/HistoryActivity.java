package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.ChatAdapter;
import com.shishic.cb.adapter.HistoryAdapter;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.loadmore.IRefreshHandler;
import com.shishic.cb.loadmore.ListRefreshLayout;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 历史开奖
 */
public class HistoryActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ListRefreshLayout recyclerView;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
        initListener();
    }

    private void initView(){

    }

    private void initListener(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("历史开奖");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setShowLastTips(false);
        recyclerView.setRecyclerLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        adapter = new HistoryAdapter(list,this);
        recyclerView.setRecyclerAdapter(adapter);
        recyclerView.setHandler(new IRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return false;
            }

            @Override
            public boolean canLoad() {
                return true;
            }

            @Override
            public void refresh(int requestPage) {

            }

            @Override
            public void load(int requestPage) {
                requestData();
            }
        });
        requestData();
    }

    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        RequestUtil.httpGet(this, Constant.URL_CHAT_HISTORY, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("","URL_HISTORY logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("","URL_HISTORY response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.optJSONArray("data");
                    if(data != null && data.length() > 0){
                        List<HistoryBean> list = new Gson().fromJson(data.toString(), new TypeToken<List<HistoryBean>>(){}.getType());
                        if(recyclerView != null){
                            recyclerView.updateClearAndAdd(list);
                        }
                        recyclerView.setTotalPage(1000);
                        recyclerView.completeRefresh(true);
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        });
    }


}
