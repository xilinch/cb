package com.shishic.cb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 */

public class HelpActivity extends BaseActivity {

    public static String title;
    private ViewPager viewPager;
    private Button button;
    // 引导图片资源
    private ArrayList<View> pageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help);
        initViewOrData();
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HelpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    private void initViewOrData() {
        ImageView view1 = new ImageView(this);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        ImageView view2 = new ImageView(mContext);
//        view2.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        ImageView view3 = new ImageView(mContext);
//        view3.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        view1.setImageResource(R.drawable.guide1);
//        view2.setImageResource(R.drawable.guide2);
//        view3.setImageResource(R.drawable.guide3);
        view1.setImageResource(R.mipmap.person_bg);
        //将view装入数组
        pageview = new ArrayList<View>();
        pageview.add(view1);
//        pageview.add(view2);
//        pageview.add(view3);

//        finishButtonDrawable = getResources().getDrawable(R.drawable.help_coming_border_bg);
        button = findViewById(R.id.imgbutton_help_finish);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new SlideImageAdapter());

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == pageview.size() - 1) {
                    button.setVisibility(View.VISIBLE);
                } else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
//                if (arg0 == 0) {
//                    int Alpha = (int) (255 * arg1);
//                    finishButtonDrawable.setAlpha(Alpha);
////                    button.setTextColor(Color.argb(Alpha, 0xfb, 0x6e, 0x52));
//                    button.setBackgroundDrawable(finishButtonDrawable);
//                }
//                if (arg0 == 2) {
//                    button.setVisibility(View.VISIBLE);
//                    button.setAlpha(1.0f);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class SlideImageAdapter extends PagerAdapter {
        @Override
        //获取当前窗体界面数
        public int getCount() {
            // TODO Auto-generated method stub
            return pageview.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(pageview.get(arg1));
        }

        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(pageview.get(arg1));
            return pageview.get(arg1);
        }
    }


}
