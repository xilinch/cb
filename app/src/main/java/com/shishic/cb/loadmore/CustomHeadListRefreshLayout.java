package com.shishic.cb.loadmore;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.headerfooter.songhang.library.SmartRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;


/**
 * Created by zhangzhihao on 2018/5/15 15:42.
 * describe 下拉显示自定义头部的recyclerView
 */

public class CustomHeadListRefreshLayout extends RefreshLayout {
    private PtrNFDefaultHeader mPtrClassicHeader;
    private CustomHeadAdapter customAdapter;

    public CustomHeadListRefreshLayout(Context context) {
        super(context);
    }

    public CustomHeadListRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomHeadListRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLastUpdateTimeKey(String lastUpdateTimeKey) {
        mPtrClassicHeader.setLastUpdateTimeKey(lastUpdateTimeKey);
    }

    @Override
    public void initHeadStyle() {
        mPtrClassicHeader = new PtrNFDefaultHeader(getContext());
        mPtrRefreshLayout.setHeaderView(mPtrClassicHeader);
        mPtrRefreshLayout.addPtrUIHandler(mPtrClassicHeader);
    }

    @Override
    public void registerRefreshHandler() {
        mPtrRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 这个refreshing()只有在checkCanDoRefresh()返回为true时才会调用
                refreshing();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mRefreshHandler.canRefresh()) {
                    if (customAdapter != null && adapter.isShowCustomHead()) {
                        return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header) && customAdapter.isAddCustomHead();
                    }
                    /**
                     * 如果是可下拉刷新的listview，这句必须调用，否则下拉与listview滑动会有冲突
                     */
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                } else {
                    return false;
                }
            }
        });
    }

    @Override
    protected synchronized void updateList(boolean append, List list, PlusRecyclerAdapter adapter) {
        if (list == null) {
            list = new ArrayList();
        }
        synchronized (adapter) {
            if (!append) {
                adapter.getList().clear();
            } else {
                clearWhenPageOne();
            }
            adapter.getList().addAll(list);
            if (!append && customAdapter != null && customAdapter.isAddCustomHead()) {
                adapter.notifyDataSetChanged();
                customAdapter.resetParams();
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setRecyclerAdapter(final PlusRecyclerAdapter adapter) {
        this.adapter = adapter;
        customAdapter = new CustomHeadAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (adapter.isShowCustomHead()) {
                    LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (!recyclerView.canScrollVertically(-1) && !customAdapter.isAddCustomHead() && newState == SCROLL_STATE_DRAGGING && customAdapter.getScrollY() <= 0) {
                        customAdapter.showCustomHeader();
                    }
                    if (newState == SCROLL_STATE_IDLE) {
                        if (customAdapter.isAddCustomHead()) {
                            int first = manager.findFirstVisibleItemPosition();
                            if (first == 0) {
                                View view = manager.findViewByPosition(first);
                                if (view != null) {
                                    if (customAdapter.getScrollY() > 0) {
                                        if (Math.abs(view.getTop()) > customAdapter.getMinHeight()) {
                                            customAdapter.hideCustomHeader();
                                        } else {
                                            manager.scrollToPositionWithOffset(0, 0);
                                            recyclerView.smoothScrollBy(0, view.getTop());
                                        }
                                    } else {
                                        recyclerView.smoothScrollBy(0, view.getTop());
                                    }
                                } else {
                                    customAdapter.hideCustomHeader();
                                }
                            } else {
                                customAdapter.hideCustomHeader();
                            }
                        }
                        customAdapter.setScrollY(0);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.isShowCustomHead() && dy != 0) {
                    customAdapter.setScrollY(dy + customAdapter.getScrollY());
                }
            }
        });
        this.smartAdapter = new SmartRecyclerAdapter(customAdapter);
        this.smartAdapter.setFooterView(loadingLayout);
        if (recyclerView != null) {
            recyclerView.setAdapter(smartAdapter);
        }
    }

    /**
     * 设置头部
     *
     * @param header
     */
    public void setCustomHeaderView(View header) {
        if (customAdapter != null) {
            customAdapter.setCustomHeaderView(header);
            customAdapter.getmInnerAdapter().setShowCustomHead(true);
        }
    }


}
