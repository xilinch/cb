package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.R;
import com.shishic.cb.adapter.FreePlanAdapter;
import com.shishic.cb.bean.FreePlan1;
import com.shishic.cb.bean.FreePlanBean;
import com.shishic.cb.bean.FreePlanTabBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FreePlanFragment extends BaseFragment {
    private View view;
    //刷新
//    private SwipeRefreshLayout swipeRefreshLayout;
    //
    private RecyclerView recyclerView;

    private FreePlanAdapter adapter;

    private int id;

    private List<FreePlan1> list;
    private List<Object> showList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_freeplan, container, false);
//            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            recyclerView = view.findViewById(R.id.recyclerView);
            initListener();
            id = getArguments().getInt("id");
            requestData();
        }
        return view;
    }

    private void initListener(){
        List list = new ArrayList<>();
        adapter = new FreePlanAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestData();
//            }
//        });
    }

    /**
     * 请求数据
     */
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("schemeConfigId", String.valueOf(id));
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
                        list = new Gson().fromJson(listData.toString(), new TypeToken<List<FreePlan1>>(){}.getType());
                        initDatas();
                    }
                    adapter.changeData(showList);
                    //刷新完成
//                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }

    /**
     * 数据转换
     */
    private void initDatas(){
        showList = new ArrayList<>();
        if(list != null){
            for(int i = 0; i < list.size() ; i++){
                List<FreePlan1.ListBean> childList = list.get(i).getList();
                showList.add(list.get(i));
                if(childList != null){
                    showList.addAll(childList);
                }
            }
        }
    }
}
