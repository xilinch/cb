package com.shishic.cb.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shishic.cb.R;
import com.shishic.cb.adapter.FreePlanAdapter;
import com.shishic.cb.bean.FreePlanBean;
import com.shishic.cb.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class FreePlanFragment extends BaseFragment {
    private View view;

    //刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    //
    private RecyclerView recyclerView;

    private FreePlanAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_freeplan, container, false);
            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            recyclerView = view.findViewById(R.id.recyclerView);
            initListener();
        }
        return view;
    }

    private void initListener(){
        List<FreePlanBean> list = new ArrayList<>();
        list.add(new FreePlanBean("096-098期 搶眼人工【02356】 等待中[1]"));
        list.add(new FreePlanBean("095-097期 搶眼人工【02468】 095期 73650(0) 中"));
        list.add(new FreePlanBean("094-096期 搶眼人工【23589】 094期 61093(3) 中"));
        list.add(new FreePlanBean("093-095期 搶眼人工【12356】 093期 26372(2) 中"));
        list.add(new FreePlanBean("091-093期 搶眼人工【23678】 092期 38406(6) 中"));
        list.add(new FreePlanBean("090-092期 搶眼人工【01579】 090期 76310(0) 中"));
        list.add(new FreePlanBean("089-091期 搶眼人工【02369】 089期 00359(9) 中"));
        list.add(new FreePlanBean("086-088期 搶眼人工【12368】 088期 26930(0) 错"));
        list.add(new FreePlanBean("084-086期 搶眼人工【24578】 085期 79337(7) 中"));
        list.add(new FreePlanBean("083-085期 搶眼人工【01279】 083期 15032(2) 中"));
        list.add(new FreePlanBean("080-082期 搶眼人工【02389】 082期 05457(7) 错"));
        list.add(new FreePlanBean("079-081期 搶眼人工【02568】 079期 77232(2) 中"));
        list.add(new FreePlanBean("078-080期 搶眼人工【01278】 078期 72751(1) 中"));
        list.add(new FreePlanBean("076-078期 搶眼人工【23589】 077期 54533(3) 中"));
        list.add(new FreePlanBean("073-075期 搶眼人工【12789】 075期 81375(5) 错"));
        list.add(new FreePlanBean("071-073期 搶眼人工【02348】 072期 05628(8) 中"));
        list.add(new FreePlanBean("070-072期 搶眼人工【23689】 070期 56626(6) 中"));
        adapter = new FreePlanAdapter(list,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<FreePlanBean> list = new ArrayList<>();
                        for(int i = 0; i< 20 ; i++){
                            list.add(new FreePlanBean("096-098期 搶眼人工【02356】 等待中[1]-" + i));
                        }
                            adapter.changeData(list);
                        //刷新完成
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtils.toastShow(getContext(),"更新了 "+list.size()+" 条数据");
                    }

                }, 2000);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem ;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1== adapter.getItemCount()){
//                    if(!isShow){
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                List<FreePlanBean> list = new ArrayList<>();
                                for(int i = 20 ; i< 40 ;i++){
                                    list.add(new FreePlanBean("011-013期 搶眼人工【02356】 等待中[1]-" + i));
                                }
                                adapter.addData(list);
                                ToastUtils.toastShow(getContext(),"更新了 "+list.size()+" 条数据");
                            }
                        }, 2000);
                        isShow = true;
//                    } else {
//                        Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
//
//                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //最后一个可见的ITEM
                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });
    }
    private boolean isShow = false;
}
