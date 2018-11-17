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
import com.shishic.cb.fragment.HotAnalyFragment;
import com.shishic.cb.fragment.HotAnalyFragment1;
import com.shishic.cb.fragment.LostAnalyFragment;
import com.shishic.cb.fragment.LostAnalyFragment1;
import java.util.ArrayList;


/**
 * 遗漏统计
 */
public class LostAnalyActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private ViewPager pager;
    private PagerSlidingTabStrip tabs;

    private LostAnalyFragment lostAnalyFragment;
    private HotAnalyFragment hotAnalyFragment;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    private String[] titles = new String[]{"遗漏分析", "冷热分析", "指标遗漏分析", "指标冷热分析"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost);
        initView();
        initListener();

        fragments.add(new LostAnalyFragment());
        fragments.add(new HotAnalyFragment());
        fragments.add(new LostAnalyFragment1());
        fragments.add(new HotAnalyFragment1());
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
