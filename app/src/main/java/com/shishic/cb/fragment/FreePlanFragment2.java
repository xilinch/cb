package com.shishic.cb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shishic.cb.R;
import com.shishic.cb.adapter.FreePlanAdapter;
import com.shishic.cb.bean.FreePlan;
import com.shishic.cb.bean.FreePlanTabBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 免费计划
 */
public class FreePlanFragment2 extends Fragment {

    private TextView tv_title;
    private LinearLayout ll_back;
    //当前公告
    private TextView tv_notice;
    //当前期数
    private TextView tv_currenJounal;

    private Spinner cp_type,plan;
    private int type = 1;
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> planList = new ArrayList<>();

    private ArrayAdapter typeAdapter, planAdapter;

    private RecyclerView recyclerView;

    private List<FreePlanTabBean> list;
    //显示的计划详情
    private List<FreePlan> listPlan;
    //显示的计划
    private List<Object> showList;

    private FreePlanAdapter adapter;

    private View view;

    /**
     * 中奖号码
     */
    private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8,tv_9,tv_10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view =inflater.inflate(R.layout.fragment_freeplan2,container,false);
        }
        initView();
        initListener();
        requestData();
        return view;
    }

    private void initView(){
        tv_title = view.findViewById(R.id.tv_title);
        ll_back = view.findViewById(R.id.ll_back);
        tv_notice = view.findViewById(R.id.tv_notice);
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

        tv_notice = view.findViewById(R.id.tv_notice);
        tv_currenJounal = view.findViewById(R.id.tv_currenJounal);
        plan = view.findViewById(R.id.plan);
        cp_type = view.findViewById(R.id.cp_type);
        ll_back.setVisibility(View.GONE);

        tv_title.setText("免费计划");
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new FreePlanAdapter(null,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        typeList.add("重庆时时彩");
        typeList.add("腾讯分分彩");
        typeList.add("黑龙江时时彩");
        typeList.add("天津时时彩");
        typeList.add("新疆时时彩");
        typeList.add("北京赛车");
        typeList.add("福彩3D");
        typeList.add("排列3");
        typeList.add("幸运飞艇");

        typeAdapter = new ArrayAdapter(getContext(), R.layout.item_type,typeList);
        planAdapter = new ArrayAdapter(getContext(), R.layout.item_type,planList);
        cp_type.setAdapter(typeAdapter);
        plan.setAdapter(planAdapter);
        cp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("my","cp_type onItemClick :" + typeList.get(position));
                type = position + 1;
                requestData();
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
    }

    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("type", String.valueOf(type));
        RequestUtil.httpGet(getContext(), Constant.URL_SCHEME_LIST, params, new NFHttpResponseListener<String>() {
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
                        planList.clear();
                        if(list != null && list.size() > 0){
                            FreePlanTabBean freePlanTabBean = list.get(0);
                            requestPlanData(String.valueOf(freePlanTabBean.getId()));
                            for(int i = 0; i < list.size();i++){
                                planList.add(list.get(i).getName());
                            }
                        }
                        planAdapter.notifyDataSetChanged();
                    }


                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }
    private boolean isStart = false;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                if(list != null && plan != null){
                    FreePlanTabBean freePlanTabBean = list.get(plan.getSelectedItemPosition());
                    isStart = false;
                    requestPlanData(String.valueOf(freePlanTabBean.getId()));
                }
            } catch (Exception e){

            }

        }
    };


    /**
     * 请求具体的计划数据
     */
    private void requestPlanData(String id){
        HashMap<String,String> params = new HashMap<>();
        params.put("schemeConfigId", id);
        params.put("type", String.valueOf(type));
        RequestUtil.httpGet(getContext(), Constant.URL_SCHEME_CONFIG, params, new NFHttpResponseListener<String>() {
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


                            if(listBeans != null && listBeans.size() > 1){
                                FreePlan.ListBean listBean = listBeans.get(0);
                                FreePlan.ListBean listBean1 = listBeans.get(1);
                                tv_currenJounal.setText("当前期数:" + listBean.getCurrenJounal());
                                String luckyNumbers = listBean1.getLuckyNumbers();
                                if(TextUtils.isEmpty(luckyNumbers)){
                                    luckyNumbers = "--";
                                }
                                String notice = freePlan.getNotice();
                                tv_notice.setText(notice);
                                //如果是时时彩类的，不显示-，否则显示-
                                String[] numbers = luckyNumbers.split(",");
                                if(cp_type.getSelectedItemPosition() <= 4){
                                    if(numbers != null && numbers.length >= 5){
                                        tv_1.setVisibility(View.VISIBLE);
                                        tv_2.setVisibility(View.VISIBLE);
                                        tv_3.setVisibility(View.VISIBLE);
                                        tv_4.setVisibility(View.VISIBLE);
                                        tv_5.setVisibility(View.VISIBLE);
                                        tv_6.setVisibility(View.GONE);
                                        tv_7.setVisibility(View.GONE);
                                        tv_8.setVisibility(View.GONE);
                                        tv_9.setVisibility(View.GONE);
                                        tv_10.setVisibility(View.GONE);
                                        tv_5.setText(numbers[0]);
                                        tv_4.setText(numbers[1]);
                                        tv_3.setText(numbers[2]);
                                        tv_2.setText(numbers[3]);
                                        tv_1.setText(numbers[4]);
                                    } else {
                                        tv_1.setVisibility(View.GONE);
                                        tv_2.setVisibility(View.GONE);
                                        tv_3.setVisibility(View.GONE);
                                        tv_4.setVisibility(View.GONE);
                                        tv_5.setVisibility(View.GONE);
                                        tv_6.setVisibility(View.GONE);
                                        tv_7.setVisibility(View.GONE);
                                        tv_8.setVisibility(View.GONE);
                                        tv_9.setVisibility(View.GONE);
                                        tv_10.setVisibility(View.GONE);
                                    }

                                } else {
                                    if(cp_type.getSelectedItemPosition() == 5 || cp_type.getSelectedItemPosition() == 8){
                                        //10个数字
                                        tv_1.setVisibility(View.VISIBLE);
                                        tv_2.setVisibility(View.VISIBLE);
                                        tv_3.setVisibility(View.VISIBLE);
                                        tv_4.setVisibility(View.VISIBLE);
                                        tv_5.setVisibility(View.VISIBLE);
                                        tv_6.setVisibility(View.VISIBLE);
                                        tv_7.setVisibility(View.VISIBLE);
                                        tv_8.setVisibility(View.VISIBLE);
                                        tv_9.setVisibility(View.VISIBLE);
                                        tv_10.setVisibility(View.VISIBLE);
                                        tv_10.setText(numbers[0]);
                                        tv_9.setText(numbers[1]);
                                        tv_8.setText(numbers[2]);
                                        tv_7.setText(numbers[3]);
                                        tv_6.setText(numbers[4]);
                                        tv_5.setText(numbers[5]);
                                        tv_4.setText(numbers[6]);
                                        tv_3.setText(numbers[7]);
                                        tv_2.setText(numbers[8]);
                                        tv_1.setText(numbers[9]);
                                    } else {
                                        //3个数字
                                        tv_1.setVisibility(View.VISIBLE);
                                        tv_2.setVisibility(View.VISIBLE);
                                        tv_3.setVisibility(View.VISIBLE);
                                        tv_4.setVisibility(View.GONE);
                                        tv_5.setVisibility(View.GONE);
                                        tv_6.setVisibility(View.GONE);
                                        tv_7.setVisibility(View.GONE);
                                        tv_8.setVisibility(View.GONE);
                                        tv_9.setVisibility(View.GONE);
                                        tv_10.setVisibility(View.GONE);
                                        tv_3.setText(numbers[0]);
                                        tv_2.setText(numbers[1]);
                                        tv_1.setText(numbers[2]);
                                    }
                                }
                            }
                        }
                        initDatas();
                    } else {
                        tv_notice.setText("");
                    }
                    adapter.changeData(showList);
                    //刷新完成
//                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception exception){
                    exception.printStackTrace();
                    tv_notice.setText("");
                }  finally {
                    if(getActivity() != null && !getActivity().isFinishing() && !getActivity().isDestroyed()){
                        long currentSecond = System.currentTimeMillis() ;
                        Date date = new Date();
                        date.setTime(currentSecond);
                        int seconds = date.getSeconds();
                        LogUtil.e("my","date:" + date.toString() + " seconds:" + seconds);
                        //秒
                        int repeatTime = 10;
                        if(type == 1){
                            //距离整分钟多少秒，就刷一次
                            repeatTime = 63 - seconds;
                        } else {
                            //距离05秒多长，就刷一次
                            repeatTime = 67 - seconds;
                        }
                        if(repeatTime > 30){
                            repeatTime = 30;
                        }
                        if(isStart && runnable != null){
                            handler.removeCallbacks(runnable);
                        }
                        handler.postDelayed(runnable,repeatTime * 1000);
                        isStart = true;

                    }
                }
            }
        });
    }

    /**
     * 数据转换
     */
    private void initDatas(){
        if(showList == null){
            showList = new ArrayList<>();
        }
        showList.clear();
        if(listPlan != null){
            for(int i = 0; i < listPlan.size() ; i++){
                List<FreePlan.ListBean> childList = listPlan.get(i).getList();
                int planType = listPlan.get(i).getPlanType();
                String planName = listPlan.get(i).getPlanName();
                for(int j = 0; j < childList.size(); j++){
                   childList.get(j).setPlanType(planType);
                   childList.get(j).setPlanName(planName);
                }
                showList.add(listPlan.get(i));
                if(childList != null){
                    showList.addAll(childList);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
