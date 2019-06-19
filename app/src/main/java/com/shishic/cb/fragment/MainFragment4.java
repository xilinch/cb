package com.shishic.cb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.DoubleToolActivity;
import com.shishic.cb.HistoryActivity;
import com.shishic.cb.LostAnalyActivity;
import com.shishic.cb.NumberChoiceActivity;
import com.shishic.cb.NumberChoiceFilterActivity;
import com.shishic.cb.R;
import com.shishic.cb.SpecialActivity;
import com.shishic.cb.TrendNumberActivity;
import com.shishic.cb.adapter.NewsAdapter;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.bean.NewsBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.LogUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainFragment4 extends BaseFragment {


    private TextSwitcher textSwitcher;

    private TextView tv_ad;

    private ViewPager viewPager;

    private ArrayList<ImageView> imageViews = new ArrayList<>();

    private List<ADTextBean> adTextBeans = new ArrayList<>();

    private RecyclerView recyclerView;
    /**
     * 显示标题
     */
    private static final int MSG_SHOW = 1;

    /**
     * 索引
     */
    private int index = 0;

    /**
     * 停止
     */
    private static final int MSG_STOP = 2;

    private View view;

    private NewsAdapter newsAdapter;

    private ViewSwitcher.ViewFactory viewFactory;

    private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW:
                    int length = adTextBeans.size();
                    String text = "";
                    if (textSwitcher != null && adTextBeans != null && length > 0) {
                        text = adTextBeans.get(index % length).getTitle();
                        textSwitcher.setText(text);
                        index++;
                    }
//                    LogUtil.e("my","MSG_SHOW " + text);
                    handler.sendEmptyMessageDelayed(MSG_SHOW, 1000);
                    break;
                case MSG_STOP:
                    handler.removeMessages(MSG_SHOW);

                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_main4,container,false);
        }
        textSwitcher = view.findViewById(R.id.textSwitcher);
        tv_ad = view.findViewById(R.id.tv_ad);
        viewPager = view.findViewById(R.id.viewPager);
        tv_1 = view.findViewById(R.id.tv_1);
        tv_2 = view.findViewById(R.id.tv_2);
        tv_3 = view.findViewById(R.id.tv_3);
        tv_4 = view.findViewById(R.id.tv_4);
        tv_5 = view.findViewById(R.id.tv_5);
        tv_6 = view.findViewById(R.id.tv_6);
        tv_7 = view.findViewById(R.id.tv_7);
        tv_8 = view.findViewById(R.id.tv_8);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (v.getId()) {
                    case R.id.tv_1:
                        intent.setClass(getContext(), TrendNumberActivity.class);
                        break;
                    case R.id.tv_2:
                        intent.setClass(getContext(), LostAnalyActivity.class);
                        break;
                    case R.id.tv_3:
                        intent.setClass(getContext(), HistoryActivity.class);
                        break;
                    case R.id.tv_4:
                        intent.setClass(getContext(), NumberChoiceActivity.class);
                        break;
                    case R.id.tv_5:
                        intent.setClass(getContext(), DoubleToolActivity.class);
                        break;
                    case R.id.tv_6:
                        intent.setClass(getContext(), NumberChoiceFilterActivity.class);
                        break;
                    case R.id.tv_7:
                        intent.setClass(getContext(), HistoryActivity.class);
                        break;
                    case R.id.tv_8:
                        intent.setClass(getContext(), SpecialActivity.class);
                        break;
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        };
        tv_1.setOnClickListener(onClickListener);
        tv_2.setOnClickListener(onClickListener);
        tv_3.setOnClickListener(onClickListener);
        tv_4.setOnClickListener(onClickListener);
        tv_5.setOnClickListener(onClickListener);
        tv_6.setOnClickListener(onClickListener);
        tv_7.setOnClickListener(onClickListener);
        tv_8.setOnClickListener(onClickListener);
        initRecycleView();
        initSwitcher();
        requestNotice();
        initViewPager();
        return view;
    }

    /**
     *
     */
    private void initViewPager(){
        for(int i = 0; i< 3; i++){
            ImageView imageView = new ImageView(getContext());
//            Glide.with(getContext()).load(newsIcon[i]).centerCrop().placeholder(R.mipmap.banner1).into(imageView);
            Glide.with(getContext()).load(R.mipmap.banner1).centerCrop().placeholder(R.mipmap.banner1).into(imageView);
            imageViews.add(imageView);
        }
        ImageAdapter adapter = new ImageAdapter();
        viewPager.setAdapter(adapter);
    }

    private void initRecycleView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<NewsBean> list = new ArrayList<>();
        newsAdapter = new NewsAdapter(list,getActivity());
        recyclerView.setAdapter(newsAdapter);
        requestNews();


    }

    /**
     * 获取新闻数据
     */
    private void requestNews(){
        //
        RequestUtil.httpPost(getContext(), Constant.URL_NEWS_LIST, null, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_NEWS_LIST error:" );
            }

            @Override
            public void onResponse(String o) {
                //拿到新闻数据显示内容
                try{
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray listJSONArray = data.getJSONArray("list");
                    List<NewsBean> list = new Gson().fromJson(listJSONArray.toString(),new TypeToken<List<NewsBean>>(){}.getType());
                    LogUtil.e("my","URL_NEWS_LIST:" + o + " list:" + list);
                    newsAdapter.changeDate(list);
                } catch (Exception exception){
                   exception.printStackTrace();
                }

            }
        });

    }


    private void initSwitcher(){
        adTextBeans.add(new ADTextBean("欢迎光临",""));
        newMessage(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        newMessage(false);
    }

    /**
     * @param isScroll 是否自动滚动 数量大于1 true
     */
    private void newMessage(boolean isScroll) {
        if (viewFactory == null) {
            viewFactory = new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    TextView textView = new TextView(getContext());
                    textView.setSingleLine();
                    textView.setTextSize(14);//字号
                    textView.setTextColor(getResources().getColor(R.color.c_gray_666666));
//                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setPadding(DensityUtils.dipTopx(getContext(),10),0,0,0);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(params);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO 屏蔽掉打开应用
//                            Intent intent = new Intent(getContext(), H5Activity.class);
//                            int length = adTextBeans.size();
//                            intent.putExtra(ADTextBean.class.getSimpleName(),adTextBeans.get(index % length));
//                            startActivity(intent);
                        }
                    });
                    return textView;
                }
            };
            textSwitcher.setFactory(viewFactory);
        }
        if (handler != null && textSwitcher != null) {
            handler.removeMessages(MSG_SHOW);
            if (isScroll) {
                handler.sendEmptyMessage(MSG_SHOW);
            }
        }
    }

    public void requestNotice(){
        RequestUtil.httpGet(getContext(), Constant.URL_NOTICE_LIST, new HashMap<String, String>(), new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError error) {

            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_NOTICE_LIST response:" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray jsonArray = data.optJSONArray("list");
                    if(jsonArray != null && jsonArray.length() > 0){
                        adTextBeans = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<ADTextBean>>(){}.getType());
                        //组装一次所有的内容
                        StringBuilder sb = new StringBuilder();
                        int size = adTextBeans.size();
                        for(int i = 0; i < size ;i++){
                            sb.append("                 ").append(adTextBeans.get(i).getTitle()).append("           ");
                        }
                        tv_ad.setText(sb.toString());
                    }
                    newMessage(true);
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

    private class ImageAdapter extends PagerAdapter{

        public ImageAdapter() {
            super();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,DensityUtils.dipTopx(getContext(),120));
//            params.width = DensityUtils.getScreenWidth(getContext());
//            params.height = DensityUtils.dipTopx(getContext(),120);
            container.removeView(imageViews.get(position % imageViews.size()));
            container.addView(imageViews.get(position % imageViews.size()), 0,params);
            ImageView imageView = imageViews.get(position % imageViews.size());
            if(adTextBeans != null && adTextBeans.size() > 0){
                int setAd = position % (adTextBeans.size());
                ADTextBean adTextBean = adTextBeans.get(setAd);
                Glide.with(getContext()).load(adTextBean.getContent()).centerCrop().placeholder(R.mipmap.banner1).into(imageView);
            }

            return imageView;
        }

        @Override
        public int getCount() {
            return imageViews.size() * 10000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position % imageViews.size()));
        }

    }

}
