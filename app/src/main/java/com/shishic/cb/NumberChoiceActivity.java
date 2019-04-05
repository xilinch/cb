package com.shishic.cb;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.adapter.NumberChoiceAdapter;
import com.shishic.cb.bean.HistoryBean;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NumberChoiceActivity extends BaseActivity {

    private TextView tv_title;
    private LinearLayout ll_back;
    private RecyclerView recyclerView;
    private NumberChoiceAdapter adapter;

    private Button btn_confirm;
    private Button btn_copy;

    private LinearLayout ll_bai,ll_shi,ll_ge;
    private RadioGroup tg_tab;
    private RadioButton rb_2, rb_3;

    private ArrayList<CheckBox> textViewBai = new ArrayList<>();
    private ArrayList<CheckBox> textViewShi = new ArrayList<>();
    private ArrayList<CheckBox> textViewGe = new ArrayList<>();

    private View indi1,indi2;

    private int[] id_bai = new int[]{R.id.bai_tv_0,R.id.bai_tv_1,R.id.bai_tv_2,
            R.id.bai_tv_3,R.id.bai_tv_4,R.id.bai_tv_5,R.id.bai_tv_6,
            R.id.bai_tv_7,R.id.bai_tv_8,R.id.bai_tv_9};
    private int[] id_shi = new int[]{R.id.shi_tv_0,R.id.shi_tv_1,R.id.shi_tv_2,
            R.id.shi_tv_3,R.id.shi_tv_4,R.id.shi_tv_5,R.id.shi_tv_6,
            R.id.shi_tv_7,R.id.shi_tv_8,R.id.shi_tv_9};
    private int[] id_ge = new int[]{R.id.ge_tv_0,R.id.ge_tv_1,R.id.ge_tv_2,
            R.id.ge_tv_3,R.id.ge_tv_4,R.id.ge_tv_5,R.id.ge_tv_6,
            R.id.ge_tv_7,R.id.ge_tv_8,R.id.ge_tv_9};

    private ArrayList<Integer> bai = new ArrayList<>();
    private ArrayList<Integer> shi = new ArrayList<>();
    private ArrayList<Integer> ge = new ArrayList<>();

    private ArrayList<String> result = new ArrayList<>();
    private List<HashMap<String,String>> resultLost = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_choice);
        initView();
        initListener();
    }
    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        ll_back = findViewById(R.id.ll_back);
        indi1 = findViewById(R.id.indi1);
        indi2 = findViewById(R.id.indi2);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("号码直选工具");
        recyclerView = findViewById(R.id.listRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List list = new ArrayList();
        adapter = new NumberChoiceAdapter(list,this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        for(int i = 0; i < id_bai.length ; i++){
            textViewBai.add((CheckBox) findViewById(id_bai[i]));
        }
        for(int i = 0; i < id_shi.length ; i++){
            textViewShi.add((CheckBox) findViewById(id_shi[i]));
        }
        for(int i = 0; i < id_ge.length ; i++){
            textViewGe.add((CheckBox) findViewById(id_ge[i]));
        }

        btn_copy = findViewById(R.id.btn_copy);
        btn_confirm = findViewById(R.id.btn_confirm);
        ll_bai = findViewById(R.id.ll_bai);
        ll_shi = findViewById(R.id.ll_shi);
        ll_ge = findViewById(R.id.ll_ge);
        tg_tab = findViewById(R.id.tg_tab);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        tg_tab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_2:
                        ll_bai.setVisibility(View.GONE);
                        ll_shi.setVisibility(View.VISIBLE);
                        ll_ge.setVisibility(View.VISIBLE);
                        indi1.setVisibility(View.VISIBLE);
                        indi2.setVisibility(View.GONE);

                        break;
                    case R.id.rb_3:
                        ll_bai.setVisibility(View.VISIBLE);
                        ll_shi.setVisibility(View.VISIBLE);
                        ll_ge.setVisibility(View.VISIBLE);
                        indi1.setVisibility(View.GONE);
                        indi2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        rb_2.setChecked(true);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                caclu2();
            }
        });

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(adapter != null){
                    String numbers = adapter.getChoiceNumber();
                    // 11以后使用content.Clipboardmanager,之前使用text.ClipboardManager
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(numbers);
                }
                ToastUtils.toastShow(NumberChoiceActivity.this,"已复制。");
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

    /**
     * 2星
     */
    private void caclu2(){
        bai.clear();
        shi.clear();
        ge.clear();
        result.clear();
        resultLost.clear();
        for(int i = 0 ; i < 10 ; i++ ){
            if(textViewShi.get(i).isChecked()){
                shi.add(i);
            }
            if(textViewBai.get(i).isChecked()){
                bai.add(i);
            }
            if(textViewGe.get(i).isChecked()){
                ge.add(i);
            }
        }

        if(rb_2.isChecked()){
            if(shi.size() <= 0){
                ToastUtils.toastShow(this,"请选择十位");
            }
            if(ge.size() <= 0){
                ToastUtils.toastShow(this,"请选择个位");
            }
            //计算出2所有的组合
            for(int i =0 ; i < shi.size() ;i++){
                int shi_num = shi.get(i);
                for(int j = 0 ;j < ge.size();j++){
                    int ge_num = ge.get(j);
                    String numbers = shi_num + "" + ge_num ;
                    LogUtil.e("my","numbers:" + numbers);
                    result.add(numbers);
                }
            }
        } else if(rb_3.isChecked()){
            if(bai.size() <= 0){
                ToastUtils.toastShow(this,"请选择百位");
            }
            if(shi.size() <= 0){
                ToastUtils.toastShow(this,"请选择十位");
            }
            if(ge.size() <= 0){
                ToastUtils.toastShow(this,"请选择个位");
            }
            //计算出3所有的组合
            for(int i =0 ; i < bai.size() ;i++){
                int bai_num = bai.get(i);
                for(int j = 0 ;j < shi.size();j++){
                    int shi_num = shi.get(j);
                    for(int k = 0 ;k < ge.size();k++){
                        int ge_num = ge.get(k);
                        String numbers = bai_num + "" + shi_num + "" + ge_num ;
                        LogUtil.e("my","numbers:" + numbers);
                        result.add(numbers);
                    }
                }
            }
        }
        LogUtil.e("my","result:" + result.toString());
        requestData();
    }

    /**
     * 对照数据
     */
    private void requestData(){
        HashMap<String,String> params = new HashMap<>();
        params.put("pageNum",String.valueOf(1));
        params.put("pageSize","30");
        RequestUtil.httpGet(this, Constant.URL_HISTORY, params, new NFHttpResponseListener<String>() {
            @Override
            public void onErrorResponse(LogError logError) {
                LogUtil.e("my","URL_HISTORY logError");
            }

            @Override
            public void onResponse(String response) {
                LogUtil.e("my","URL_HISTORY response:" + response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONArray listData = data.optJSONArray("list");
                    boolean success = jsonObject.optBoolean("success");
                    if(success && listData != null && listData.length() > 0 ){
                        List<HistoryBean> list = new Gson().fromJson(listData.toString(), new TypeToken<List<HistoryBean>>(){}.getType());
                        //进行遗漏和热点分析
                        analy(list);

                    }
                } catch (Exception exception){
                    exception.printStackTrace();

                }
            }
        });
    }

    /**
     * 分析
     */
    private void analy(List<HistoryBean> list){
        if(list != null){
            for(int i = 0 ; i < result.size() ;i++){
                String number = result.get(i);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("number", number);
                boolean has = false;
                for(int j = 0 ; j < list.size() && !has; j++){
                    HistoryBean bean = list.get(j);
                    String luckyNumber = "";
                    if(rb_2.isChecked()){
                        luckyNumber = bean.getN2() + "" + bean.getN1();
                    } else if(rb_3.isChecked()){
                        luckyNumber = bean.getN3() + "" +bean.getN2() + "" + bean.getN1();
                    }
                    if(!TextUtils.isEmpty(luckyNumber) && luckyNumber.equals(number)){
                        hashMap.put("lost", String.valueOf(j));
                        has = true;
                    }
                }
                if(!has){
                    hashMap.put("lost", String.valueOf(list.size()));
                }
                resultLost.add(hashMap);
                LogUtil.e("my","lost map:" + hashMap);
            }
            HashMap<String,String> first = new HashMap<>();
            first.put("number","号码");
            first.put("lost","当前遗漏期数");
            first.put("type","1");
            resultLost.add(0,first);
            adapter.updateData(resultLost);
        }


    }

}
