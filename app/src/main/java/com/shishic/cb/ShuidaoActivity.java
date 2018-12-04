package com.shishic.cb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.ShuidaoAdapter;
import com.shishic.cb.adapter.SpecailAdapter;
import com.shishic.cb.bean.Account;
import com.shishic.cb.bean.ShuidaoBean;
import com.shishic.cb.bean.SpecialBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 水稻计划
 */
public class ShuidaoActivity extends BaseActivity {
    private TextView tv_title;
    private LinearLayout ll_back;
    private RecyclerView recyclerView;
    private ShuidaoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        initView();
        initListener();
        requestData();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("专家计划");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        adapter = new ShuidaoAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initListener(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 请求新数据
     */
    private void requestData(){
        List<ShuidaoBean> list = new ArrayList<>();

        list.add(new ShuidaoBean("农家谷子带壳 喂鸡稻谷水稻谷子稻子鸡鸭鹅饲料 鸟食50斤25kg","https://gd1.alicdn.com/imgextra/i4/3538744321/O1CN011hn3s1Gea3L3sye_!!3538744321.png","https://item.taobao.com/item.htm?spm=a230r.1.14.73.49903be04LaGJd&id=580734513755&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("2018年新稻谷带壳 喂鸡吃的水稻 谷子饲料10斤","https://gd1.alicdn.com/imgextra/i1/2828857679/TB2OZeJl93PL1JjSZFtXXclRVXa_!!2828857679.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.53.49903be04LaGJd&id=550380579823&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("新品早籼米稻谷带壳水稻子谷子鸡鸭饲料鸟食50斤25kg","https://gd4.alicdn.com/imgextra/i4/139440035/O1CN011C84JK3EeliSs5O_!!139440035.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.69.49903be04LaGJd&id=574989913687&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("水稻稻谷带壳稻谷 优质稻谷 江浙沪皖包邮50斤","https://gd2.alicdn.com/imgextra/i2/2626276680/TB2zyaeoBNkpuFjy0FaXXbRCVXa_!!2626276680.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.86.49903be04LaGJd&id=553614599623&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("带壳稻谷鸡饲料 50斤谷子鸽子饲料鸽子粮食 鸽粮50斤","https://gd4.alicdn.com/imgextra/i4/2769359198/TB25KfGjHGYBuNjy0FoXXciBFXa_!!2769359198.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.110.49903be04LaGJd&id=571376180382&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("2018年新香稻谷内香型带壳大米家用碾米机专用10斤","https://gd4.alicdn.com/imgextra/i4/2800650001/TB2Y.2pd2NNTKJjSspcXXb4KVXa_!!2800650001.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.116.49903be04LaGJd&id=561176076284&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("几十年前野原生态浓香水稻谷老品种子传统自留秧苗大米非杂交旱粳","https://gd2.alicdn.com/imgextra/i3/902631815/TB250N7apXXXXX4XXXXXXXXXXXX_!!902631815.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.123.49903be04LaGJd&id=564278605912&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("农家自种9.5斤水稻谷子稻谷种子喂鸡鸭饲料鸟粮鸟食带壳谷子","","https://item.taobao.com/item.htm?spm=a230r.1.14.194.49903be04LaGJd&id=527830906498&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("宠物水稻带壳粮食饲料","https://gd3.alicdn.com/imgextra/i3/3395561922/O1CN01881Cfj1Q4JiPN4JMy_!!3395561922.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.129.49903be04LaGJd&id=581959848695&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("9.5斤农家稻谷喂鸡鸭鹅鸽子鸟仓鼠宠物水稻带壳谷子粮食饲料","https://gd3.alicdn.com/imgextra/i3/860135253/O1CN01pw9iUS1ofv0cLgp4B_!!860135253.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.141.49903be04LaGJd&id=580829686167&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("农家自种稻谷喂鸡鸽子鸟类飞鼠仓鼠宠物水稻带壳谷子粮食饲料","https://gd2.alicdn.com/imgextra/i2/1869511123/O1CN011KANEVLNUxFoB2y_!!1869511123.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.154.49903be04LaGJd&id=571320779687&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("8年新水稻鲜米机专用东北盘锦蟹田生态香稻谷带壳大米","https://gd2.alicdn.com/imgextra/i2/2800650001/TB25Y3wd5KO.eBjSZPhXXXqcpXa_!!2800650001.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.194.49903be04LaGJd&id=527830906498&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("2018农家自种带壳稻谷晚稻水稻粮食种子喂鸟鸡鸭鸽子仓鼠宠物饲料","https://gd2.alicdn.com/imgextra/i2/3409839018/O1CN012GUICDaD2VeKx6k_!!3409839018.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.244.49903be04LaGJd&id=581742793146&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("农家自种水稻稻子带壳糯米糯米稻谷糯米种子剥米水稻谷子","https://gd2.alicdn.com/imgextra/i3/2371295813/TB21PWjwbGYBuNjy0FoXXciBFXa_!!2371295813.jpg","https://item.taobao.com/item.htm?spm=a230r.1.14.262.49903be04LaGJd&id=571041880447&ns=1&abbucket=19#detail"));
        list.add(new ShuidaoBean("自种稻谷 水稻 谷子稻子 带壳大米仓鼠鸟类喂鸡鸭鸽子饲料","https://gd4.alicdn.com/imgextra/i4/2069788665/TB282UfcB9J.eBjy0FoXXXyvpXa_!!2069788665.png","https://item.taobao.com/item.htm?spm=a230r.1.14.282.49903be04LaGJd&id=533934274026&ns=1&abbucket=19#detail"));
//        list.add(new ShuidaoBean("","",""));
//        list.add(new ShuidaoBean("","",""));
//        list.add(new ShuidaoBean("","",""));
//        list.add(new ShuidaoBean("","",""));
        adapter.updateData(list);
        ToastUtils.toastShow(ShuidaoActivity.this,"没有更多数据了");
    }

}
