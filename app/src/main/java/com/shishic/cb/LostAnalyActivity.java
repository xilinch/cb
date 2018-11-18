package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.fragment.HotAnalyFragment;
import com.shishic.cb.fragment.HotAnalyFragment1;
import com.shishic.cb.fragment.LostAnalyFragment;
import com.shishic.cb.fragment.LostAnalyFragment1;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 遗漏统计
 */
public class LostAnalyActivity extends BaseActivity {
    private TextView tv_title;
    private LinearLayout ll_back;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    private String[] titles = new String[]{"遗漏分析", "冷热分析", "指标遗漏分析", "指标冷热分析"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        initView();
        initListener();
        requestData();

    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("遗漏分析");
        // Initialize the ViewPager and set an adapter
        pager = findViewById(R.id.pager);
        // Bind the tabs to the ViewPager
        tabs = findViewById(R.id.tabs);
    }


    private void initTabs(){
        fragments.add(new LostAnalyFragment());
        fragments.add(new HotAnalyFragment());
        fragments.add(new LostAnalyFragment1());
        fragments.add(new HotAnalyFragment1());
        pager.setAdapter(new TestAdapter(titles, fragments));
        pager.setCurrentItem(0);
        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum",String.valueOf(1));
        params.put("pageSize","20");
        RequestUtil.httpGet(this, Constant.URL_HISTORY, params, new NFHttpResponseListener<String>() {
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
                        //显示tab
                        initTabs();
                    }
                } catch (Exception exception){
                    exception.printStackTrace();

                }
            }
        });
    }

    private List<Integer> lostList = new ArrayList<>();
    private List<Integer> hotList = new ArrayList<>();

    public List<Integer> getLostList() {
        return lostList;
    }

    public List<Integer> getHotList() {
        return hotList;
    }

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
                LogUtil.e("my","lostList:" + lostList.toString());
                LogUtil.e("my","hotList:" + hotList.toString());
            }
        }
        LogUtil.e("my","lostList:" + lostList.toString());
        LogUtil.e("my","hotList:" + hotList.toString());
    }


    private void initListener() {
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    class TestAdapter extends FragmentPagerAdapter {

        private String[] titles;
        ArrayList<Fragment> fragments;

        //根据需求定义构造方法，方便外部调用
        public TestAdapter(String[] titles, ArrayList<Fragment> fragments) {
            super(getSupportFragmentManager());
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        //设置每页的标题
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


}
