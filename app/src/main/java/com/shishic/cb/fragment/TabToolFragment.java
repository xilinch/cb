package com.shishic.cb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.DoubleToolActivity;
import com.shishic.cb.DoubleToolActivity2;
import com.shishic.cb.HistoryActivity;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.NumberChoiceActivity;
import com.shishic.cb.NumberChoiceFilterActivity;
import com.shishic.cb.R;
import com.shishic.cb.TrendNumber23Activity;
import com.shishic.cb.TrendNumberActivity;
import com.shishic.cb.adapter.FunAdapter;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.HorizontalItemDecoration;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.SharepreferenceUtil;
import com.shishic.cb.util.VerticaltemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TabToolFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private RelativeLayout rl_1, rl_2, rl_3, rl_4, rl_5,rl_6,rl_7,rl_8;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_tool_tab, container, false);
            rl_1 = view.findViewById(R.id.rl_1);
            rl_2 = view.findViewById(R.id.rl_2);
            rl_3 = view.findViewById(R.id.rl_3);
            rl_4 = view.findViewById(R.id.rl_4);
            rl_5 = view.findViewById(R.id.rl_5);
            rl_6 = view.findViewById(R.id.rl_6);
            rl_7 = view.findViewById(R.id.rl_7);
            rl_8 = view.findViewById(R.id.rl_8);
            rl_1.setOnClickListener(this);
            rl_2.setOnClickListener(this);
            rl_3.setOnClickListener(this);
            rl_4.setOnClickListener(this);
            rl_5.setOnClickListener(this);
            rl_6.setOnClickListener(this);
            rl_7.setOnClickListener(this);
            rl_8.setOnClickListener(this);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_1:
                intent.setClass(getContext(), HistoryActivity.class);
                break;
            case R.id.rl_2:
                intent.setClass(getContext(), TrendNumberActivity.class);
                break;
            case R.id.rl_3:
                intent.setClass(getContext(), DoubleToolActivity2.class);
                break;
            case R.id.rl_4:
                intent.setClass(getContext(), LostAnalyActivity.class);
                break;
            case R.id.rl_5:
                intent.setClass(getContext(), NumberChoiceFilterActivity.class);
                break;
            case R.id.rl_6:
                intent.setClass(getContext(), TrendNumber23Activity.class);
                break;
            case R.id.rl_7:
                intent.setClass(getContext(), LostAnalyActivity.class);
                break;
            case R.id.rl_8:
                intent.setClass(getContext(), NumberChoiceActivity.class);
                break;
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
