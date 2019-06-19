package com.shishic.cb.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.R;

import java.util.ArrayList;
import java.util.List;

public class HotAnalyFragment extends BaseFragment {

    private LineChart mLineChart;

    private View view;

    private List<Entry> entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_lost, container, false);
            mLineChart = view.findViewById(R.id.lineChart);
            //显示边界
            mLineChart.setDrawBorders(true);
            //设置数据
            entries  = new ArrayList<>();
            if(getActivity() instanceof LostAnalyActivity){
                LostAnalyActivity lostAnalyActivity = (LostAnalyActivity)getActivity();
                List<Integer> list = lostAnalyActivity.getHotList();
                for (int i = 0; i <= 10; i++) {
                    entries.add(new Entry(i, list.get(i)));
                }
            }
            //一个LineDataSet就是一条线
            LineDataSet lineDataSet = new LineDataSet(entries, "热度分析");
            //设置曲线值的圆点是实心还是空心
            lineDataSet.setDrawCircleHole(false);
            //设置显示值的字体大小
            lineDataSet.setValueTextSize(9f);
            //线模式为圆滑曲线（默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            LineData data = new LineData(lineDataSet);
            mLineChart.setData(data);
            XAxis xAxis = mLineChart.getXAxis();
            //值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            //设置X轴坐标之间的最小间隔（因为此图有缩放功能，X轴,Y轴可设置可缩放）
            xAxis.setGranularity(1f);
            //设置X轴的刻度数量
            // 第二个参数表示是否平均分配 如果为true则按比例分为12个点、如果为false则适配X刻度的值来分配点，可能没有12个点
            xAxis.setLabelCount(10, true);
            //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
            xAxis.setAxisMinimum(0f);
            xAxis.setAxisMaximum(10f);
            lineDataSet.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    int IValue = (int) value;
                    return String.valueOf(IValue);
                }
            });


            //Y轴
            YAxis leftYAxis = mLineChart.getAxisLeft();
            YAxis rightYAxis = mLineChart.getAxisRight();
            leftYAxis.setAxisMinimum(0f);
            leftYAxis.setAxisMaximum(20f);

            rightYAxis.setAxisMinimum(0f);
            rightYAxis.setAxisMaximum(20f);

            leftYAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return (int) value + "";
                }
            });
            rightYAxis.setEnabled(false); //右侧Y轴不显示
            rightYAxis.setGranularity(1f);
            rightYAxis.setLabelCount(11,false);
            rightYAxis.setTextColor(Color.BLUE); //文字颜色
            rightYAxis.setGridColor(Color.RED); //网格线颜色
            rightYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色


        }
        return view;
    }
}
