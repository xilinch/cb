package com.shishic.cb.loadmore;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @description 封装了上下拉 ， 分页 ，无数据背景
 */
public class ListRefreshLayout extends RefreshLayout {
    private PtrNFDefaultHeader mPtrClassicHeader;

    public ListRefreshLayout(Context context) {
        super(context);
    }

    public ListRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLastUpdateTimeKey(String lastUpdateTimeKey) {
        mPtrClassicHeader.setLastUpdateTimeKey(lastUpdateTimeKey);
    }


    @Override
    public void initHeadStyle() {
//        PtrClassicDefaultHeader mPtrClassicHeader = new PtrClassicDefaultHeader(getContext());
        mPtrClassicHeader = new PtrNFDefaultHeader(getContext());
        mPtrRefreshLayout.setHeaderView(mPtrClassicHeader);
        mPtrRefreshLayout.addPtrUIHandler(mPtrClassicHeader);
    }
}

