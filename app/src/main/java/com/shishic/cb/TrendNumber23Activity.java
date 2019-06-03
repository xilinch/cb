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
import com.shishic.cb.adapter.TrendNumber23Adapter;
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
public class TrendNumber23Activity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ListRefreshLayout recyclerView;
    private TrendNumberAdapter adapter;
    private TrendNumber23Adapter adapter2;
    private RadioGroup rg_tab,tg_tab;
    private RadioButton rb_1,rb_2,rb_3,rb_5,rbx_2,rbx_3;
    private int type = 5;

    private Spinner spinner;
    private int cp_type = 1;
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayAdapter typeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trendnumber23);
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
        rbx_2 = findViewById(R.id.rbx_2);
        rbx_3 = findViewById(R.id.rbx_3);
        rb_5 = findViewById(R.id.rb_5);
        tg_tab = findViewById(R.id.tg_tab);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("后2后3走势图分析");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setShowLastTips(false);
        recyclerView.setRecyclerLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        final List list = new ArrayList();
        adapter = new TrendNumberAdapter(list,this);
        adapter2 = new TrendNumber23Adapter(list,this);
        recyclerView.setBackgroundColorResource((R.color.c_gray_f7f7f7));
//        recyclerView.setRecyclerAdapter(adapter);
        recyclerView.setHandler(new IRefreshHandler() {
            @Override
            public boolean canRefresh() {
                return false;
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
                        adapter.setType(type);
                        recyclerView.setRecyclerAdapter(adapter);
                        break;
                    case R.id.rb_2:
                        type = 1;
                        adapter.setType(type);
                        recyclerView.setRecyclerAdapter(adapter);
                        break;
                    case R.id.rb_3:
                        type = 2;
                        adapter.setType(type);
                        recyclerView.setRecyclerAdapter(adapter);
                        break;

                    case R.id.rb_4:
                        type = 3;
                        break;
                    case R.id.rb_5:
                        int type = 2;
                        //二星或者三星
                        if(rbx_2.isChecked()){
                            //
                            type =2;
                        } else if(rbx_3.isChecked()){
                            //
                            type = 3;
                        }
                        if(adapter2 == null){
                            adapter2 = new TrendNumber23Adapter(list,TrendNumber23Activity.this);
                        }
                        adapter2.setType(type);
                        recyclerView.setRecyclerAdapter(adapter2);

                        break;
                }


            }
        });
        tg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbx_2:
                        rb_1.setVisibility(View.VISIBLE);
                        rb_2.setVisibility(View.VISIBLE);
                        rb_3.setVisibility(View.GONE);

                        break;
                    case R.id.rbx_3:
                        rb_1.setVisibility(View.VISIBLE);
                        rb_2.setVisibility(View.VISIBLE);
                        rb_3.setVisibility(View.VISIBLE);
                        break;

                }
                int type = 2;
                //二星或者三星
                if(rbx_2.isChecked()){
                    //
                    type =2;
                } else if(rbx_3.isChecked()){
                    //
                    type = 3;
                }
                if(adapter2 == null){
                    adapter2 = new TrendNumber23Adapter(list,TrendNumber23Activity.this);
                }
                adapter2.setType(type);
                recyclerView.setRecyclerAdapter(adapter2);
            }
        });
        rbx_2.setChecked(true);

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
        params.put("pageSize","30");
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


                            adapter.setType(type);
                            if(type < 3){
                                recyclerView.setRecyclerAdapter(adapter);
                            } else {
                                recyclerView.setRecyclerAdapter(adapter2);
                            }
                            if(rbx_3.isChecked()){
                                adapter2.setType(3);
                            } else {
                                adapter2.setType(2);
                            }
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
