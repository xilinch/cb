package com.shishic.cb.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.headerfooter.songhang.library.RecyclerViewAdapterWrapper;


/**
 * Created by zhangzhihao on 2018/5/15 15:43.
 * describe
 */

public class CustomHeadAdapter extends RecyclerViewAdapterWrapper {

    private static final int HEADER_TYPE = 515;
    private View mHeader;
    private PlusRecyclerAdapter mInnerAdapter;
    private boolean isAddCustomHead;
    private int scrollY;      //滑动的距离
    private int minHeight;   //滑动隐藏或显示的最小值 px

    public CustomHeadAdapter(RecyclerView.Adapter wrapped) {
        super(wrapped);
        mInnerAdapter= (PlusRecyclerAdapter) wrapped;
        minHeight = 5;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            return new RecyclerView.ViewHolder(mHeader) {
            };
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!isHeaderPos(position)) {
            mInnerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPos(position)) {
            return HEADER_TYPE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    /**
     * 设置头部
     *
     * @param view
     */
    public void setCustomHeaderView(View view) {
        mHeader = view;
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(-1, -2);
        mHeader.setLayoutParams(params);
    }


    /**
     * 显示头部
     */
    public void showCustomHeader() {
        mInnerAdapter.addHeadItem();
        isAddCustomHead = true;
        getWrappedAdapter().notifyItemRangeInserted(0, 1);
    }

    /**
     * 隐藏头部
     */
    public void hideCustomHeader() {
        mInnerAdapter.getList().remove(0);
        isAddCustomHead = false;
        getWrappedAdapter().notifyItemRangeRemoved(0, 1);
        scrollY = 0;
    }

    private boolean isHeaderPos(int position) {
        return isAddCustomHead && position == 0;
    }

    public boolean isAddCustomHead() {
        return isAddCustomHead;
    }

    public void setAddCustomHead(boolean addCustomHead) {
        isAddCustomHead = addCustomHead;
    }

    public int getScrollY() {
        return scrollY;
    }

    public void setScrollY(int scrollY) {
        this.scrollY = scrollY;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void resetParams() {
        isAddCustomHead = false;
        scrollY = 0;
    }

    public PlusRecyclerAdapter getmInnerAdapter() {
        return mInnerAdapter;
    }
}
