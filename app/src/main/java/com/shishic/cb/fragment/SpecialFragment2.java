package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.R;
import com.shishic.cb.SpecialActivity;
import com.shishic.cb.adapter.SpecailAdapter;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.NFCallback;
import com.shishic.cb.util.RequestUtils;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SpecialFragment2 extends BaseFragment {

    private View view;
    private RecyclerView recyclerView;
    private SpecailAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_special,container,false);
            initView();
        }

        return view;
    }
    private void initView() {
        recyclerView = view.findViewById(R.id.listRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List list = new ArrayList();
        adapter = new SpecailAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }



    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    /**
     * 请求新数据
     */
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        if (Account.getAccount() == null) {
            params.put("userId", String.valueOf(1));
        } else {
            params.put("userId", String.valueOf(Account.getAccount().getId()));
        }

        RequestUtils.httpget(getContext(), Constant.URL_EXPORT, params, new NFCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                super.onFailure(call, e);
                ToastUtils.toastShow(getContext(), R.string.network_error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                super.onResponse(call, response);
                try {
                    String result = response.body().string();
                    LogUtil.e("my", "URL_EXPORT response:" + response + "  result:" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    String msg = jsonObject.optString("msg");
                    boolean success = jsonObject.optBoolean("success");
                    if (success) {
                        JSONArray data = jsonObject.optJSONArray("data");
                        if (data != null && data.length() > 0) {
                            final List<SpecialBean> list = new Gson().fromJson(data.toString(), new TypeToken<List<SpecialBean>>() {
                            }.getType());
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.updateData(list);
                                }
                            });
                        }
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }
}
