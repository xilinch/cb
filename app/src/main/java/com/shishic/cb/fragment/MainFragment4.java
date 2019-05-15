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

    String[] title = new String[]{
            "遗漏统计",
            "专家计划",
            "免费计划",
            "用户聊天室",
            "走势图",
            "历史开奖",
            "号码直选",
//            "留言板",
//            "工具",
//            "活动中心",
//            "公告栏"
            "出彩说明"
    };

    String[] url = new String[]{
            "https://chart.cp.360.cn/kaijiang/ssccq?sb_spm=4363c04888386886a00358cd39e3efe7",
            "http://125.88.183.181:8889/",
            "http://125.88.183.181:8889/",
            "https://chart.cp.360.cn/kaijiang/ssccq?sb_spm=1e56179efbfea18a6419fc17d3fadef1",
            "http://trend.caipiao.163.com/cqssc/",
            "http://caipiao.163.com/award/cqssc/"
//            "",
//            "",
//            "",
//            "",

    };

    String[] icon = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545050199&di=7c333f75d7a07212d99fc77642d00fb5&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01cc8b5541cbda00000115410bbde7.jpg%401280w_1l_2o_100sh.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542797339588&di=df2df12e4220fc40ff82507b24210acc&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7c1ed21b0ef41bd56e88dacf5ada81cb39db3d35.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544455281328&di=4a21988b5f0761e6bb0fb46deed0725b&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F3b87e950352ac65c1356014ef0f2b21193138abf.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/962bd40735fae6cd9fa66ba905b30f2443a70fec.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545050052&di=578a7ac9bbc46c2dde01091df10804e0&imgtype=jpg&er=1&src=http%3A%2F%2Fstatic.fotor.com.cn%2Fassets%2Fstickers%2Ffreelancer_lmf_0111_10%2Fbff44ef0-56d9-4c8c-8727-d5cd1c3301fc_medium_thumb.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544455604475&di=d1d6f833d661e9438dcbda9bcad42544&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fb7fd5266d016092426c5d126df0735fae6cd342e.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545050878&di=9f578488b687cc3815bec7ef8e19df7d&imgtype=jpg&er=1&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F9d82d158ccbf6c81d46952d5b73eb13533fa40b4.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/e7cd7b899e510fb3657e30b3d333c895d1430caa.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544545207581&di=568c564c0bc296af3a0775317e75210a&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F17%2F11%2F23%2Ffa8530f827c8458f9c660ce0058419cf.jpg%2521%2Ffwfh%2F804x744%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545139927&di=7d61225ba55abc788b7263b281b56175&imgtype=jpg&er=1&src=http%3A%2F%2Fpic36.photophoto.cn%2F20150812%2F0017029468154313_b.jpg",

    };

    String[] newsIcon = new String[]{
      "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553367152187&di=e11e59e8b90c0160d7b8c9ddc5b13463&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201509%2F10%2F20150910215210_Vistk.jpeg",
      "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553367152186&di=5e211e41d76ccd62de5e388f1f3ea892&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F9%2F57fdac2fd6c52.jpg%3Fdown",
      "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553367152183&di=4162ca38000f90b06a7bc385f2ca83f7&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01bc76554c05bc000001bf72813087.jpg%401280w_1l_2o_100sh.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553367152182&di=04e0e84d364a02e8ce45d618c8db8ea6&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fd833c895d143ad4bfe7bc1fb89025aafa40f06b7.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553367152181&di=d5395a10eadd892ac9a5c13d0a77fc74&imgtype=0&src=http%3A%2F%2Fp.chanyouji.cn%2F1404306111%2F53526A90-0B37-AE4E-B863-55EB1B5197A3.jpg"
    };

    int[] newIcon = new int[]{
            R.mipmap.tab1,
            R.mipmap.tab2,
            R.mipmap.tab3,
            R.mipmap.tab4,
            R.mipmap.tab5,
            R.mipmap.tab6,
            R.mipmap.tab7,
            R.mipmap.tab8,
            R.mipmap.tab8
    };

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
                    LogUtil.e("my","MSG_SHOW " + text);
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
                        intent.setClass(getContext(), TrendNumberActivity.class);
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
        tv_ad.setText("欢迎各位新老朋友");
        ImageAdapter adapter = new ImageAdapter();
        viewPager.setAdapter(adapter);
    }

    private void initRecycleView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        try {
            List<NewsBean> list = new ArrayList<>();
            NewsBean newsBean1 = new NewsBean();
            newsBean1.setAuther("双色球");
            newsBean1.setDescription("肇庆喜中双色球");
            newsBean1.setTime("2019-04-22 17:25");
            newsBean1.setTime("今年首个刮刮乐一等奖");
            newsBean1.setIcon("http://www.cwl.gov.cn/upload/resources/image/2019/03/22/766906_320x150c.jpg");
            newsBean1.setUrl("http://www.cwl.gov.cn/c/2019-03-22/450543.shtml");
            list.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.setAuther("福利彩票");
            newsBean2.setDescription("“责任福彩、理性购彩”重庆福彩参与社会工作宣传周");
            newsBean2.setTime("2019-05-11 21:29");
            newsBean2.setTime("责任福彩、理性购彩");
            newsBean2.setIcon("http://www.cwl.gov.cn/upload/resources/image/2019/03/21/766833_500x500.jpg");
            newsBean2.setUrl("http://www.cwl.gov.cn/c/2019-03-22/450543.shtml");
            list.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.setAuther("体育彩票");
            newsBean3.setDescription("海归投身福彩事业 经营半年深得彩民厚爱");
            newsBean3.setTime("2019-05-12 17:25");
            newsBean3.setTime("最受沈城百姓欢迎福彩投注站");
            newsBean3.setIcon("院长");
            newsBean3.setUrl("http://www.cwl.gov.cn/c/2019-03-22/450543.shtml");
            list.add(newsBean3);

            newsAdapter = new NewsAdapter(list,getActivity());
            recyclerView.setAdapter(newsAdapter);

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }


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
