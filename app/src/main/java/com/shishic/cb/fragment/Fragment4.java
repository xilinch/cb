package com.shishic.cb.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.R;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Fragment4 extends BaseFragment {


    private PieChart mLineChart;

    private View view;

    private List<PieEntry> entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_lost_pie, container, false);
            mLineChart = view.findViewById(R.id.lineChart);

            //设置数据
            entries  = new ArrayList<>();
            if(getActivity() instanceof LostAnalyActivity){
                LostAnalyActivity lostAnalyActivity = (LostAnalyActivity)getActivity();
                List<Integer> list = lostAnalyActivity.getLostList();
                for (int i = 0; i < 10; i++) {
                    PieEntry pieData = new PieEntry(list.get(i), "" + i);
                    entries.add(pieData);
                }
            }

            PieDataSet lineDataSet = new PieDataSet(entries, "指标遗漏分析");

            lineDataSet.setValueTextSize(9f);
            List<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.BLUE);
            colors.add(Color.YELLOW);
            colors.add(Color.MAGENTA);
            colors.add(Color.DKGRAY);
            colors.add(Color.CYAN);
            colors.add(Color.LTGRAY);
            colors.add(Color.MAGENTA);
            colors.add(Color.GREEN);
            lineDataSet.setColors(colors);
            lineDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                    BigDecimal bigDecimal = new BigDecimal(v);
                    BigDecimal setScale = bigDecimal.setScale(1,BigDecimal.ROUND_HALF_DOWN);
                    return setScale + "%";
                }
            });
            //线模式为圆滑曲线（默认折线）
            PieData data = new PieData(lineDataSet);
            /**
             * 是否使用百分比
             */
            mLineChart.setUsePercentValues(true);
            //设置X轴动画
            mLineChart.animateX(1800);
            mLineChart.setData(data);
            mLineChart.setDrawEntryLabels(true);              //设置pieChart是否只显示饼图上百分比不显示文字（true：下面属性才有效果）
            mLineChart.setEntryLabelColor(Color.WHITE);       //设置pieChart图表文本字体颜色
            mLineChart.setEntryLabelTextSize(10f);            //设置pieChart图表文本字体大小

        }
        return view;
    }

}
