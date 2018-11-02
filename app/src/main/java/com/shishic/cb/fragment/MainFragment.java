package com.shishic.cb.fragment;

import android.content.Intent;
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

import com.shishic.cb.H5Activity;
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
            "遗漏统计",
            "专家计划",
            "免费计划",
            "用户聊天室",
            "走势图",
            "历史开奖",
            "留言板",
            "工具",
            "活动中心",
            "公告栏"
    };

    String[] url = new String[]{
            "https://chart.cp.360.cn/kaijiang/ssccq?sb_spm=4363c04888386886a00358cd39e3efe7",
            "http://125.88.183.181:8889/",
            "http://125.88.183.181:8889/",
            "https://chart.cp.360.cn/kaijiang/ssccq?sb_spm=1e56179efbfea18a6419fc17d3fadef1",
            "http://trend.caipiao.163.com/cqssc/",
            "http://caipiao.163.com/award/cqssc/",
            "",
            "",
            "",
            "",

    };

    String[] icon = new String[]{
            "http://imgsrc.baidu.com/imgad/pic/item/0b46f21fbe096b63fad55b9906338744eaf8ac2e.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541765215&di=66098792ec261280988824cfd56f1bd6&imgtype=jpg&er=1&src=http%3A%2F%2Fimg2.xitongzhijia.net%2Fallimg%2F150604%2F48-150604100I10-L.jpg",
            "http://5b0988e595225.cdn.sohucs.com/q_mini,c_zoom,w_640/images/20170806/cf2754f0334e4ae398b58f83fc79a7d3.jpeg",
            "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=1ab85f376a59252db71a15475cf2694e/d52a2834349b033b98f441f71fce36d3d539bd9b.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/962bd40735fae6cd9fa66ba905b30f2443a70fec.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/5fdf8db1cb1349540bb849925c4e9258d0094aa5.jpg",
            "http://img.zcool.cn/community/0140f956f23cfe32f875a94465d100.jpg@1280w_1l_2o_100sh.jpg",
            "http://imgsrc.baidu.com/imgad/pic/item/e7cd7b899e510fb3657e30b3d333c895d1430caa.jpg",
            "http://img.zcool.cn/community/01599757ee1e33a84a0e282b4bc6ea.png",
            "http://pic34.photophoto.cn/20150112/0017030075105673_b.jpg",

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
        list.add(new FunBean(title[0], url[0],icon[0]));
        list.add(new FunBean(title[1],url[1],icon[1]));
        list.add(new FunBean(title[2], url[2],icon[2]));
        list.add(new FunBean(title[3], url[3],icon[3]));
        list.add(new FunBean(title[4], url[4],icon[4]));
        list.add(new FunBean(title[5], url[5],icon[5]));
        list.add(new FunBean(title[6], url[6],icon[6]));
        list.add(new FunBean(title[7],url[7],icon[7]));
        list.add(new FunBean(title[8],url[8],icon[8]));
        list.add(new FunBean(title[9],url[9],icon[9]));
        funAdapter = new FunAdapter(list,getActivity());
        recyclerView.setAdapter(funAdapter);
    }

    private void initSwitcher(){
        adTextBeans.add(new ADTextBean("推荐专家计划1，上期全中","https://chart.cp.360.cn/kaijiang/ssccq/"));
        adTextBeans.add(new ADTextBean("推荐专家计划2，百分百中奖","http://www.sina.com"));
        adTextBeans.add(new ADTextBean("推荐专家计划3，中奖率达到80%","http://www.sohu.com"));
        adTextBeans.add(new ADTextBean("推荐专家计划4，跟投必中","http://new.163.com"));
        adTextBeans.add(new ADTextBean("推荐福利彩票","http://www.cwl.gov.cn/"));
        adTextBeans.add(new ADTextBean("推荐时时彩","http://caipiao.163.com/award/cqssc/"));
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
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(params);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), H5Activity.class);
                            int length = adTextBeans.size();
                            intent.putExtra(ADTextBean.class.getSimpleName(),adTextBeans.get(index % length));
                            startActivity(intent);
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

}
