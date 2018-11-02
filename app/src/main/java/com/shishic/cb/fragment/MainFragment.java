package com.shishic.cb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.shishic.cb.R;
import com.shishic.cb.adapter.FunAdapter;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {


    private TextSwitcher textSwitcher;

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

    private FunAdapter funAdapter;

    private ViewSwitcher.ViewFactory viewFactory;

    String[] title = new String[]{
            "专家计划",
            "专家计划",
            "专家计划",
            "专家计划",
            "专家计划",
            "专家计划",

    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW:
                    int length = adTextBeans.size();
                    if (textSwitcher != null && adTextBeans != null && length > 0) {
                        textSwitcher.setText(adTextBeans.get(index % length).getText());
                        index++;
                    }
                    handler.sendEmptyMessageDelayed(MSG_SHOW, 4000);
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
            view = inflater.inflate(R.layout.fragment_main,container,false);
//            view = inflater.inflate(R.layout.fragment_personal_center,container,false);
        }
        textSwitcher = view.findViewById(R.id.textSwitcher);
        initRecycleView();
        initSwitcher();
        return view;
    }


    private void initRecycleView(){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<FunBean> list = new ArrayList<>();
        list.add(new FunBean("1111", "","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541151470504&di=436baefd32b5d0e14b18e22a2c84e914&imgtype=0&src=http%3A%2F%2Fimg2.cache.netease.com%2Fcnews%2F2014%2F11%2F7%2F2014110708505922a7f.jpg"));
        list.add(new FunBean("2222", "","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541151470504&di=56cd5e6b11e132f3d648c8b1245195b3&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F1c950a7b02087bf474d9f9e9f8d3572c11dfcffd.jpg"));
        list.add(new FunBean("3333", "","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541151470504&di=f3a588f9c4b543439e79e6e93cef5ca4&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F7a899e510fb30f24a61c20cac295d143ac4b0371.jpg"));
        list.add(new FunBean("4444", "","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541151470504&di=ee2008dc6296594b516f6ef777327da0&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fbba1cd11728b47103d91f1d8c9cec3fdfc032363.jpg"));
        list.add(new FunBean("5555", "","https://testpic.nfapp.southcn.com/nfplus/ossfs/pic/xy/201809/24/t2_(184X80X357X290)5ba7c6d58669582a4bfb7a4d.jpg"));
        list.add(new FunBean("6666", "","https://testpic.nfapp.southcn.com/nfplus/ossfs/pic/xy/201809/24/t2_(184X80X357X290)5ba7c6d58669582a4bfb7a4d.jpg"));
        list.add(new FunBean("7777", "","https://testpic.nfapp.southcn.com/nfplus/ossfs/pic/xy/201809/24/t2_(184X80X357X290)5ba7c6d58669582a4bfb7a4d.jpg"));
        list.add(new FunBean("8888","","https://testpic.nfapp.southcn.com/nfplus/ossfs/pic/xy/201809/24/t2_(184X80X357X290)5ba7c6d58669582a4bfb7a4d.jpg"));
        funAdapter = new FunAdapter(list,getActivity());
        recyclerView.setAdapter(funAdapter);
    }

    private void initSwitcher(){
        adTextBeans.add(new ADTextBean("推荐百度","http://www.baidu.com"));
        adTextBeans.add(new ADTextBean("推荐新浪","http://www.sina.com"));
        adTextBeans.add(new ADTextBean("推荐搜狐","http://www.sohu.com"));
        adTextBeans.add(new ADTextBean("推荐网易","http://new.163.com"));
//        adTextBeans.add(new ADTextBean("推荐福利彩票","http://www.cwl.gov.cn/"));
//        adTextBeans.add(new ADTextBean("推荐时时彩","http://caipiao.163.com/award/."));
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
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    textView.setPadding(DensityUtils.dipTopx(getContext(),10),0,0,0);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(params);
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

}
