package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
    private Spinner spinner;
    private ArrayList<String> planList = new ArrayList<>();
    private ArrayAdapter planAdapter;

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
                return true;
            }

            @Override
            public boolean canLoad() {
                return true;
            }

            @Override
            public void refresh(int requestPage) {
                if(recyclerView != null ){
                    recyclerView.currentPage = 1;
                    requestData(spinner.getSelectedItemPosition());
                }
            }

            @Override
            public void load(int requestPage) {
                requestData(spinner.getSelectedItemPosition());
            }
        });
        planList.add("重庆时时彩");
        planList.add("腾讯分分彩");
        planList.add("黑龙江时时彩");
        planList.add("天津时时彩");
        planList.add("新疆时时彩");
        planList.add("北京赛车");
        planList.add("福彩3D");
        planList.add("排列3");
        planList.add("幸运飞艇");
        planAdapter = new ArrayAdapter(this, R.layout.item_type,planList);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(planAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择了
                if(recyclerView != null ){
                    recyclerView.currentPage = 1;
                    requestData(spinner.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setSelection(0);
//        requestData();
    }

    private void requestData(int type){
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum",String.valueOf(recyclerView.currentPage));
        params.put("pageSize","20");
        params.put("type",String.valueOf(type+1));
        RequestUtil.httpGet(this, Constant.URL_HISTORY, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_HISTORY logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_HISTORY response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.optBoolean("success");
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray listData = data.optJSONArray("list");
                    int pages = data.optInt("pages");
                    recyclerView.setTotalPage(pages);
                    if(listData != null && listData.length() > 0){
                        List<HistoryBean> list = new Gson().fromJson(listData.toString(), new TypeToken<List<HistoryBean>>(){}.getType());
                        if(recyclerView.currentPage == 1){
                            HistoryBean historyBean = new HistoryBean();
                            historyBean.setType(-2);
                            list.add(0,historyBean);
                        }
                        if(recyclerView != null && recyclerView.currentPage == 1){
                            recyclerView.updateClearAndAdd(list);
                        } else if(recyclerView != null && recyclerView.currentPage > 1){
                            recyclerView.updateAdd(list);
                        }
                    }
                    recyclerView.completeRefresh(true);
                } catch (Exception exception){
                    exception.printStackTrace();
                    recyclerView.completeRefresh(false);
                }
            }
        });
    }
}
