package com.shishic.cb.loadmore;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/10/13.
 */

public class ListRefreshReverseLayout extends RefreshReverseLayout {
    private PtrNFDefaultHeader mPtrClassicHeader;

    public ListRefreshReverseLayout(Context context) {
        super(context);
    }

    public ListRefreshReverseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListRefreshReverseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
