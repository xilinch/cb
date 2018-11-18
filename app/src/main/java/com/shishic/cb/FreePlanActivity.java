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
import com.shishic.cb.bean.FreePlanTabBean;
import com.shishic.cb.fragment.FreePlanFragment;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 免费计划
 */
public class FreePlanActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private List<FreePlanTabBean> list;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeplan);
        initView();
        initListener();
        requestData();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        pager = findViewById(R.id.pager);
        tabs = findViewById(R.id.tabs);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("免费计划");
        pager = findViewById(R.id.pager);
        // Bind the tabs to the ViewPager
        tabs = findViewById(R.id.tabs);

    }

    private void initListener(){
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

    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        RequestUtil.httpGet(this, Constant.URL_SCHEME_LIST, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_SCHEME_LIST logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_SCHEME_LIST response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.optBoolean("success");
                    JSONArray data = jsonObject.optJSONArray("data");
                    if(success && data != null && data.length() > 0){
                        list = new Gson().fromJson(data.toString(), new TypeToken<List<FreePlanTabBean>>(){}.getType());
                        initTabs();
                    }
                } catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
    }

    private void initTabs(){
        if(list != null){
            titles = new String[list.size()];
            for(int i = 0; i < list.size(); i++){
                FreePlanFragment freePlanFragment = new FreePlanFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", list.get(i).getId());
                freePlanFragment.setArguments(bundle);
                fragments.add(freePlanFragment);
                titles[i] = list.get(i).getName();
            }
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

    }

}
