package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.TrendNumberAdapter;
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
 * 走势图
 */
public class TrendNumberActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ListRefreshLayout recyclerView;
    private TrendNumberAdapter adapter;
    private RadioGroup rg_tab;
    private RadioButton rb_1,rb_2,rb_3,rb_4,rb_5;
    private int type = 0;


    private Spinner spinner;
    private int cp_type = 1;
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayAdapter typeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendnumber);
        initView();
        initListener();
        requestData();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        rg_tab = findViewById(R.id.rg_tab);
        rb_1 = findViewById(R.id.rb_1);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        rb_5 = findViewById(R.id.rb_5);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("走势图");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setShowLastTips(false);
        recyclerView.setRecyclerLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        List list = new ArrayList();
        adapter = new TrendNumberAdapter(list,this);
        recyclerView.setBackgroundColorResource((R.color.c_gray_f7f7f7));
        recyclerView.setRecyclerAdapter(adapter);
        recyclerView.setHandler(new IRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return true;
            }

            @Override
            public boolean canLoad() {
                return false;
            }

            @Override
            public void refresh(int requestPage) {
                if(recyclerView != null){
                    recyclerView.currentPage = 1;
                    requestData();
                }

            }

            @Override
            public void load(int requestPage) {
//                requestData();
            }
        });
        rg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_1:
                        type = 0;
                        break;
                    case R.id.rb_2:
                        type = 1;
                        break;
                    case R.id.rb_3:
                        type = 2;
                        break;
                    case R.id.rb_4:
                        type = 3;
                        break;
                    case R.id.rb_5:
                        type = 4;
                        break;
                }

                adapter.setType(type);
            }
        });
        spinner = findViewById(R.id.spinner);
        typeList.add("重庆时时彩");
        typeList.add("腾讯分分彩");
        typeList.add("黑龙江时时彩");
        typeList.add("天津时时彩");
        typeList.add("新疆时时彩");
//        typeList.add("北京赛车");
//        typeList.add("福彩3D");
//        typeList.add("排列3");
//        typeList.add("幸运飞艇");


        typeAdapter = new ArrayAdapter(this, R.layout.item_type,typeList);
        spinner.setAdapter(typeAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择了以后切换彩种
                recyclerView.currentPage = 1;
                requestData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
     * 请求新数据
     */
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum",String.valueOf(recyclerView.currentPage));
        params.put("pageSize","20");
        params.put("type",String.valueOf(spinner.getSelectedItemPosition() + 1));
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
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray listData = data.optJSONArray("list");
                    int pages = data.optInt("pages");
                    recyclerView.setTotalPage(pages);
                    if(listData != null && listData.length() > 0){
                        List<HistoryBean> list = new Gson().fromJson(listData.toString(), new TypeToken<List<HistoryBean>>(){}.getType());
                        if(recyclerView != null && recyclerView.currentPage == 1){
                            HistoryBean historyBean = new HistoryBean();
                            historyBean.setType(-1);
                            list.add(0,historyBean);
                            for(int i= 0; i< list.size(); i++){
                                list.get(i).translate2Old();
                            }
                            recyclerView.updateClearAndAdd(list);
                            adapter.setType(type);
                        } else if(recyclerView != null && recyclerView.currentPage > 1){
                            for(int i= 0; i< list.size(); i++){
                                list.get(i).translate2Old();
                            }
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
