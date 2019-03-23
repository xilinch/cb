package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.SpecailAdapter;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 专家计划
 */
public class SpecialActivity extends BaseActivity {
    private TextView tv_title;
    private LinearLayout ll_back;
    private RecyclerView recyclerView;
    private SpecailAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
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
        tv_title.setText("专家");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        adapter = new SpecailAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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
        if(Account.getAccount() == null){
            params.put("userId",String.valueOf("2"));
        } else {
            params.put("userId",String.valueOf(Account.getAccount().getId()));
        }
        RequestUtil.httpPost(this, Constant.URL_EXPORT, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {
                ToastUtils.toastShow(SpecialActivity.this, R.string.network_error);
            }

            @Override
            public void onResponse(String response) {
                try {
                    LogUtil.e("my","URL_EXPORT response:" + response);
                    JSONObject jsonObject = new JSONObject(response);
                    String msg = jsonObject.optString("msg");
                    boolean success = jsonObject.optBoolean("success");
                    if(success){
                        JSONArray data = jsonObject.optJSONArray("data");
                        if(data != null && data.length() > 0){
                            List<SpecialBean> list = new Gson().fromJson(data.toString(), new TypeToken<List<SpecialBean>>(){}.getType());
                            adapter.updateData(list);
                        }
                        ToastUtils.toastShow(SpecialActivity.this,"没有更多数据了");
                    }

                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

}
