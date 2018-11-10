package com.shishic.cb.loadmore;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by xilinch on 2017/4/28.
 * 参考 https://github.com/fengjingyu/Android-Utils
 */

public class PtrNFClassicFrameLayout extends PtrFrameLayout {

//    private PtrClassicDefaultHeader mPtrClassicHeader;
    private PtrNFDefaultHeader mPtrClassicHeader;


    public PtrNFClassicFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public PtrNFClassicFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrNFClassicFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }
    private void initViews() {
        this.mPtrClassicHeader = new PtrNFDefaultHeader(this.getContext());
        this.setHeaderView(this.mPtrClassicHeader);
        this.addPtrUIHandler(this.mPtrClassicHeader);
    }

    public PtrNFDefaultHeader getHeader() {
        return this.mPtrClassicHeader;
    }

    public void setLastUpdateTimeKey(String key) {
        if(this.mPtrClassicHeader != null) {
            this.mPtrClassicHeader.setLastUpdateTimeKey(key);
        }

    }

    public void setLastUpdateTimeRelateObject(Object object) {
        if(this.mPtrClassicHeader != null) {
            this.mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }

    }

}
