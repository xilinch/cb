package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.FreePlanAdapter;
import com.shishic.cb.bean.FreePlan;
import com.shishic.cb.bean.FreePlanTabBean;
import com.shishic.cb.fragment.FreePlanFragment;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 免费计划
 */
public class FreePlanActivity1 extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    //预测期号
    private TextView iv_forecast_jounal;
    //当前预测
    private TextView iv_forecast;
    //当前开奖
    private TextView tv_currenNumber;
    //当前期数
    private TextView tv_currenJounal;
    //当前期数
    private TextView iv_plan_name;

    private Spinner cp_type,plan;

    private String[] typeStr = new String[]{"时时彩"};
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> planList = new ArrayList<>();

//    private String[] typeStr = new String[]{"时时"};
    private ArrayAdapter typeAdapter, planAdapter;

    private RecyclerView recyclerView;

    private List<FreePlanTabBean> list;
    //显示的计划详情
    private List<FreePlan> listPlan;
    //显示的计划
    private List<Object> showList;

    private FreePlanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeplan1);
        initView();
        initListener();
        requestData();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        iv_forecast_jounal = findViewById(R.id.iv_forecast_jounal);
        iv_forecast = findViewById(R.id.iv_forecast);
        tv_currenNumber = findViewById(R.id.tv_currenNumber);
        tv_currenJounal = findViewById(R.id.tv_currenJounal);
        iv_plan_name = findViewById(R.id.iv_plan_name);
        plan = findViewById(R.id.plan);
        cp_type = findViewById(R.id.cp_type);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("免费计划");
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new FreePlanAdapter(null,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        typeList.add("时时彩");

        typeAdapter = new ArrayAdapter(this, R.layout.item_type,typeList);
        planAdapter = new ArrayAdapter(this, R.layout.item_type,planList);
        cp_type.setAdapter(typeAdapter);
        plan.setAdapter(planAdapter);
        cp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("my","cp_type onItemClick :" + typeList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        plan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("my","plan onItemClick :" + planList.get(position));
                if(list != null && list.size() > 0 && position < list.size()){
                    FreePlanTabBean freePlanTabBean = list.get(position);
                    requestPlanData(String.valueOf(freePlanTabBean.getId()));

                }
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


    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        RequestUtil.httpGet(this, Constant.URL_SCHEME_LIST, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_SCHEME_LIST logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_SCHEME_LIST response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.optBoolean("success");
                    JSONArray data = jsonObject.optJSONArray("data");
                    if(success && data != null && data.length() > 0){
                        list = new Gson().fromJson(data.toString(), new TypeToken<List<FreePlanTabBean>>(){}.getType());
                        if(list != null && list.size() > 0){
                            FreePlanTabBean freePlanTabBean = list.get(0);
                            requestPlanData(String.valueOf(freePlanTabBean.getId()));
                            planList.clear();
                            for(int i = 0; i < list.size();i++){
                                planList.add(list.get(i).getName());
                            }
                            planAdapter.notifyDataSetChanged();
                        }
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }


    /**
     * 请求具体的计划数据
     */
    private void requestPlanData(String id){
        HashMap<String,String> params = new HashMap<>();
        params.put("schemeConfigId", id);
        RequestUtil.httpGet(this, Constant.URL_SCHEME_CONFIG, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_SCHEME_CONFIG logError");

            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_SCHEME_CONFIG response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.optBoolean("success");
                    JSONArray listData = jsonObject.optJSONArray("data");
                    if(success && listData != null && listData.length() > 0){
                        listPlan = new Gson().fromJson(listData.toString(), new TypeToken<List<FreePlan>>(){}.getType());
                        if(listPlan != null && listPlan.size() > 0 && listPlan.get(0) instanceof FreePlan){

                            FreePlan freePlan = (FreePlan) listPlan.get(0);
                            String name = freePlan.getPlanName();
                            List<FreePlan.ListBean> listBeans = freePlan.getList();

                            //预测期号
//                            private TextView iv_forecast_jounal;
//                            //当前预测
//                            private TextView iv_forecast;
//                            //当前开奖
//                            private TextView tv_currenNumber;
//                            //当前期数
//                            private TextView tv_currenJounal;

                            if(listBeans != null && listBeans.size() > 1){
                                FreePlan.ListBean listBean = listBeans.get(0);
                                FreePlan.ListBean listBean1 = listBeans.get(1);
                                iv_forecast_jounal.setText("预测期号:" + listBean.getFromJounal() + "-"+ listBean.getEndJounal());
                                iv_forecast.setText("当前预测:" + listBean.getRecommendNumbers());
                                tv_currenJounal.setText("当前期数:" + listBean.getCurrenJounal());
                                tv_currenNumber.setText("当前开奖:" + listBean1.getLuckyNumbers());
                            }
                            iv_plan_name.setText("当前计划:" + name);

                        }
                        initDatas();
                    }
                    adapter.changeData(showList);
                    //刷新完成
//                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception exception){
                    exception.printStackTrace();
                }  finally {

                }
            }
        });
    }

    /**
     * 数据转换
     */
    private void initDatas(){
        showList = new ArrayList<>();
        if(listPlan != null){
            for(int i = 0; i < listPlan.size() ; i++){
                List<FreePlan.ListBean> childList = listPlan.get(i).getList();
//                showList.add(list.get(i));
                if(childList != null){
                    showList.addAll(childList);
                }
            }
        }
    }

}
