package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.astuetz.PagerSlidingTabStrip;
import com.shishic.cb.fragment.FreePlanFragment;

import java.util.ArrayList;

/**
 * 免费计划
 */
public class FreePlanActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] titles = new String[]{"免费计划1", "免费计划2", "免费计划3", "免费计划4"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeplan);
        initView();
        initListener();
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
        fragments.add(new FreePlanFragment());
        fragments.add(new FreePlanFragment());
        fragments.add(new FreePlanFragment());
        fragments.add(new FreePlanFragment());
        pager.setAdapter(new TestAdapter(titles, fragments));

        // Bind the tabs to the ViewPager
        tabs = findViewById(R.id.tabs);
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

}
