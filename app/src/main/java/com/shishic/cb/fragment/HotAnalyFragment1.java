package com.shishic.cb.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shishic.cb.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 按照比例显示，饼形图
 */
public class HotAnalyFragment1 extends BaseFragment {

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
                entries.add(new PieEntry((float) (Math.random()) * 80, i));
            }
            /**
             * 是否使用百分比
             */
            mLineChart.setUsePercentValues(true);
            /**
             * 描述信息
             */
//            mLineChart.setDescription("");
            /**
             * 设置圆环中间的文字
             */
            mLineChart.setCenterText("中间的文字");
            Legend mLegend = mLineChart.getLegend();
            //设置比例图显示在饼图的哪个位置
            mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            //设置比例图的形状，默认是方形,可为方形、圆形、线性
            mLegend.setForm(Legend.LegendForm.CIRCLE);
            //设置X轴动画
            mLineChart.animateX(1800);

            //
            PieDataSet lineDataSet = new PieDataSet(entries, "指标热度分析");
            lineDataSet.setValueTextSize(9f);
            //线模式为圆滑曲线（默认折线）
            PieData data = new PieData(lineDataSet);
            //显示在比例图上
            PieDataSet dataSet = new PieDataSet(entries, "不同颜色代表的含义");
            //设置个饼状图之间的距离
            dataSet.setSliceSpace(3f);
            // 部分区域被选中时多出的长度
            dataSet.setSelectionShift(5f);
            // 设置饼图各个区域颜色
            ArrayList<Integer> colors = new ArrayList<Integer>();
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
            dataSet.setColors(colors);
            /**
             * nameList用来表示每个饼块上的文字内容
             * 如：部分一，部分二，部分三
             */
            ArrayList<String> nameList = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                nameList.add("部分" + (i + 1));
            }


            PieData data1 = new PieData();
            //设置以百分比显示
            data.setValueFormatter(new PercentFormatter());
            //区域文字的大小
            data.setValueTextSize(11f);
            //设置区域文字的颜色
            data.setValueTextColor(Color.WHITE);
            //设置区域文字的字体
            data.setValueTypeface(Typeface.DEFAULT);




            mLineChart.setData(data);

        }
        return view;
    }
}
