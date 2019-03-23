package com.shishic.cb.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.R;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment4 extends BaseFragment {


    private LineChart mLineChart;

    private TextView tv_title;

    private LinearLayout ll_back;

    private View view;

    private List<Entry> entries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_2, container, false);
            mLineChart = view.findViewById(R.id.lineChart);
            tv_title = view.findViewById(R.id.tv_title);
            ll_back = view.findViewById(R.id.ll_back);
        }
        tv_title.setText("遗漏");
        ll_back.setVisibility(View.GONE);
        requestData();
        return view;
    }

    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum",String.valueOf(1));
        params.put("pageSize","20");
        RequestUtil.httpGet(getContext(), Constant.URL_HISTORY, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_HISTORY logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_HISTORY response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray listData = data.optJSONArray("list");
                    boolean success = jsonObject.optBoolean("success");
                    if(success && listData != null && listData.length() > 0 ){
                        List<HistoryBean> list = new Gson().fromJson(listData.toString(), new TypeToken<List<HistoryBean>>(){}.getType());
                        //进行遗漏和热点分析
                        analy(list);
                        showData();
                    }
                } catch (Exception exception){
                    exception.printStackTrace();

                }
            }
        });
    }

    private List<Integer> lostList = new ArrayList<>();
    private List<Integer> hotList = new ArrayList<>();

    /**
     * 数据分析
     */
    private void analy(List<HistoryBean> list){
        //
        for(int i = 0; i < 10; i++){
            lostList.add(0);
            hotList.add(0);
        }
        if(list != null){
            for(int i = 0 ; i< list.size(); i++){
                //分析遗漏和热点
                LogUtil.e("my","list.get(i)" + list.get(i).toString());
                for(int j =0 ; j < 10; j++){
//                    LogUtil.e("my","j:" + j);
                    if(list.get(i).getN1() == j) {
                        //自增
                        hotList.set(j,hotList.get(j) + 1);
                    } else if(list.get(i).getN2() == j) {
                        //自增
                        hotList.set(j,hotList.get(j) + 1);

                    } else if(list.get(i).getN3() == j) {
                        //自增
                        hotList.set(j,hotList.get(j) + 1);

                    } else if(list.get(i).getN4() == j) {
                        //自增
                        hotList.set(j,hotList.get(j) + 1);

                    } else if(list.get(i).getN5() == j) {
                        //自增
                        hotList.set(j,hotList.get(j) + 1);
                    }

                    if(list.get(i).getN1() != j
                            && list.get(i).getN2() != j
                            && list.get(i).getN3() != j
                            && list.get(i).getN4() != j
                            && list.get(i).getN5() != j){
                        lostList.set(j,hotList.get(j) + 1);
                    }
                }
            }
        }
    }


    private void showData(){
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        entries  = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, lostList.get(i)));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "遗漏分析");
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
        xAxis.setAxisMaximum(9f);
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
}
