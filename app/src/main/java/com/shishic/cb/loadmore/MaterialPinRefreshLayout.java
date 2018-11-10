package com.shishic.cb.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import com.shishic.cb.R;
import com.shishic.cb.util.DensityUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * @description 封装了上下拉 ， 分页 ，无数据背景
 */
public class MaterialPinRefreshLayout extends RefreshLayout {

    public MaterialPinRefreshLayout(Context context) {
        super(context);
    }

    public MaterialPinRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialPinRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initHeadStyle() {
        final MaterialHeader header = new MaterialHeader(getContext());
        int[] colors = getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        PtrFrameLayout.LayoutParams ptrFrameLayout = new PtrFrameLayout.LayoutParams(PtrFrameLayout.LayoutParams.MATCH_PARENT,PtrFrameLayout.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(ptrFrameLayout);
        header.setPadding(0, DensityUtils.dipTopx(getContext(), 15), 0, DensityUtils.dipTopx(getContext(), 10));
        header.setPtrFrameLayout(mPtrRefreshLayout);
        mPtrRefreshLayout.setHeaderView(header);
        mPtrRefreshLayout.addPtrUIHandler(header);
        mPtrRefreshLayout.setPinContent(true);
    }
}
