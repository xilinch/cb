package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shishic.cb.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 按照比例显示，饼形图
 */
public class LostAnalyFragment1 extends BaseFragment {

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
            for (int i = 0; i < 10; i++) {
                entries.add(new PieEntry(i, (float) (Math.random()) * 80));
            }
            //一个LineDataSet就是一条线
            PieDataSet lineDataSet = new PieDataSet(entries, "指标遗漏分析");
            lineDataSet.setValueTextSize(9f);
            //线模式为圆滑曲线（默认折线）
            PieData data = new PieData(lineDataSet);
            mLineChart.setData(data);

        }
        return view;
    }
}
