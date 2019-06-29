package com.shishic.cb.fragment;

import android.graphics.Color;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.OnRefreshListener;
import com.shishic.cb.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 按照比例显示，饼形图
 */
public class LostAnalyFragment1 extends BaseFragment implements OnRefreshListener {

    private PieChart mLineChart;

    private View view;

    private List<PieEntry> entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_lost_pie, container, false);
            mLineChart = view.findViewById(R.id.lineChart);
        }
        showData();
        return view;
    }


    @Override
    public  void showData(){
        //设置数据
        if(entries == null){
            entries  = new ArrayList<>();
        }
        entries.clear();
        if(getActivity() instanceof LostAnalyActivity){
            LostAnalyActivity lostAnalyActivity = (LostAnalyActivity)getActivity();
            List<Integer> list = lostAnalyActivity.getLostList();

            if(list.size() > 0){
                for (int i = 0; i <= 10; i++) {

                    if(i < list.size()){
                        PieEntry pieData = new PieEntry(list.get(i), "" + i);
                        entries.add(pieData);
                    } else {
                        PieEntry pieData = new PieEntry(0, "" + i);
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
                        String text = "";
                        try{
                            BigDecimal bigDecimal = new BigDecimal(v);
                            BigDecimal setScale = bigDecimal.setScale(1,BigDecimal.ROUND_HALF_DOWN);
                            text = setScale + "%";
                        } catch (Exception exception){
                           exception.printStackTrace();
                        } finally {
                            return text;
                        }

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

        }


    }
}
