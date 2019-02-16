package com.shishic.cb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.R;
import com.shishic.cb.adapter.FunAdapter;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.bean.FunBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.DensityUtils;
import com.shishic.cb.util.HorizontalItemDecoration;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.SharepreferenceUtil;
import com.shishic.cb.util.VerticaltemDecoration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainFragment1 extends BaseFragment {


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
    int[] newIcon = new int[]{
            R.mipmap.icon_1_yilou,
            R.mipmap.icon_2_zhuanjia,
            R.mipmap.icon_3_zhixuan,
            R.mipmap.icon_4_lishi,
            R.mipmap.icon_5_zoushitu,
            R.mipmap.icon_6_liaotian,
            R.mipmap.icon_7_gongju,
            R.mipmap.icon_8_beitou,
            R.mipmap.icon_9,
            R.mipmap.icon_10
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SHOW:
                    int length = adTextBeans.size();
                    if (textSwitcher != null && adTextBeans != null && length > 0) {
                        textSwitcher.setText(adTextBeans.get(index % length).getTitle());
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
        requestNotice();
        return view;
    }


    private void initRecycleView(){
        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.addItemDecoration(new HorizontalItemDecoration(DensityUtils.dipTopx(getContext(),15)));
        recyclerView.addItemDecoration(new VerticaltemDecoration(DensityUtils.dipTopx(getContext(),15)));
        String funList = SharepreferenceUtil.getFun();
        try {
            JSONArray jsonArray = new JSONArray(funList);
            String funStr = "[\n" +
                    "{\n" +
                    "id: 0,\n" +
                    "functionCode: 0,\n" +
                    "description: \"遗漏统计\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1542015644000,\n" +
                    "updateTime: 1543324829000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 1,\n" +
                    "functionCode: 1,\n" +
                    "description: \"专家计划\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1542015644000,\n" +
                    "updateTime: 1543324829000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 2,\n" +
                    "functionCode: 2,\n" +
                    "description: \"免费计划\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1542015680000,\n" +
                    "updateTime: 1544429914000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 3,\n" +
                    "functionCode: 3,\n" +
                    "description: \"用户聊天室\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324322000,\n" +
                    "updateTime: 1543328210000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 4,\n" +
                    "functionCode: 4,\n" +
                    "description: \"走势图\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324336000,\n" +
                    "updateTime: 1544429925000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 5,\n" +
                    "functionCode: 5,\n" +
                    "description: \"历史开奖\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324345000,\n" +
                    "updateTime: 1544429921000\n" +
                    "},\n" +
                    "{\n" +
                    "id: 6,\n" +
                    "functionCode: 6,\n" +
                    "description: \"号码直选\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324345000,\n" +
                    "updateTime: 1544429921000\n" +
                    "},\n" +
                    "{id: 7,\n" +
                    "functionCode: 7,\n" +
                    "description: \"号码过滤工具\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324345000,\n" +
                    "updateTime: 1544429921000\n" +
                    "},\n" +
                    "{id: 8,\n" +
                    "functionCode: 8,\n" +
                    "description: \"二星三星走势分析\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324345000,\n" +
                    "updateTime: 1544429921000\n" +
                    "},\n" +
                    "{id: 9,\n" +
                    "functionCode: 9,\n" +
                    "description: \"倍投工具\",\n" +
                    "valid: 1,\n" +
                    "createTime: 1543324345000,\n" +
                    "updateTime: 1544429921000\n" +
                    "}\n" +
                    "]";
//            List<FunBean> list = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<FunBean>>(){}.getType());
            List<FunBean> list = new Gson().fromJson(funStr, new TypeToken<List<FunBean>>(){}.getType());
            Iterator<FunBean> iterator =  list.iterator();
            boolean isTest = false;
            while (iterator.hasNext()) {
                FunBean funBean = iterator.next();
                int functionCode = funBean.getFunctionCode();
                if(functionCode < 9000 && funBean.getValid() == 1 && functionCode < newIcon.length){
                    //功能列表
                    funBean.setIconSrcId(newIcon[functionCode]);
                } else if(functionCode == 9999){
                    isTest = true;
                    iterator.remove();
                }
            }
//            isTest = false;
            if(isTest){
                //审核中加入本地应用
                list.add(new FunBean("产物介绍", "https://baike.baidu.com/item/%E7%A8%BB%E8%B0%B7/2073705?fr=aladdin","https://gss2.bdstatic.com/9fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike92%2C5%2C5%2C92%2C30/sign=4d6c0bc22f1f95cab2f89ae4a87e145b/b999a9014c086e06c7f331f708087bf40ad1cb37.jpg"));
                list.add(new FunBean("育种", "https://baike.baidu.com/item/%E6%B0%B4%E7%A8%BB%E8%82%B2%E7%A7%8D%E5%AD%A6/12174166","https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3954302348,2457306993&fm=26&gp=0.jpg"));
                list.add(new FunBean("选种", "https://baijiahao.baidu.com/s?id=1601879875483450610&wfr=spider&for=pc","https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543338588707&di=d018930b6d7a5757b659ed795ffe2845&imgtype=0&src=http%3A%2F%2Fpic25.photophoto.cn%2F20121022%2F0034034563273819_b.jpg"));
                list.add(new FunBean("大拇指稻谷", "https://baijiahao.baidu.com/s?id=1601879875483450610&wfr=spider&for=pc","https://gd3.alicdn.com/imgextra/i3/2819709291/TB2u4KhdhSYBuNjSsphXXbGvVXa_!!2819709291.jpg"));

            }
            funAdapter = new FunAdapter(list,getActivity());
            recyclerView.setAdapter(funAdapter);

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }

//        List<FunBean> list = new ArrayList<>();
//        list.add(new FunBean(title[0], url[0],icon[0]));
//        list.add(new FunBean(title[1],url[1],icon[1]));
//        list.add(new FunBean(title[2], url[2],icon[2]));
//        list.add(new FunBean(title[3], url[3],icon[3]));
//        list.add(new FunBean(title[4], url[4],icon[4]));
//        list.add(new FunBean(title[5], url[5],icon[5]));
//        list.add(new FunBean(title[6], url[6],icon[6]));
//        list.add(new FunBean(title[7],url[7],icon[7]));
//        list.add(new FunBean(title[8],url[8],icon[8]));
//        list.add(new FunBean(title[9],url[9],icon[9]));

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
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                } catch (Exception exception){
                    exception.printStackTrace();
                } finally {

                }
            }
        });
    }

}
