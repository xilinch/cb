package com.shishic.cb.loadmore;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.util.DateUtils;
import com.shishic.cb.view.NfProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by xilinch on 2017/4/27.
 * 参考 https://github.com/fengjingyu/Android-Utils
 */

public class PtrNFDefaultHeader extends LinearLayout implements PtrUIHandler{
    private NfProgressBar mProgressBar;
    private static final String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mRotateAniTime = 150;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private View header;
    private TextView mTitleTextView;
    private long mLastUpdateTime = 0L;
//    private TextView mLastUpdateTextView;
    private String mLastUpdateTimeKey;
    private boolean mShouldShowLastUpdate;
//    private PtrNFDefaultHeader.LastUpdateTimeUpdater mLastUpdateTimeUpdater = new PtrNFDefaultHeader.LastUpdateTimeUpdater();

    public PtrNFDefaultHeader(Context context) {
        super(context);
        this.initViews(null);
    }

    public PtrNFDefaultHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initViews(attrs);
    }

    public PtrNFDefaultHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        TypedArray arr = this.getContext().obtainStyledAttributes(attrs,R.styleable.PtrClassicHeader, 0, 0);
        if(arr != null) {
            mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime);
        }

        this.buildAnimation();
        header = LayoutInflater.from(this.getContext()).inflate(R.layout.list_item_footer_view_text, this);
        mTitleTextView = (TextView) header.findViewById(R.id.footer_view_title);
        mProgressBar = (NfProgressBar) header.findViewById(R.id.footer_view_progress);
        resetView();
    }

    public View getHeader() {
        return header;
    }

    public void setRotateAniTime(int time) {
        if(time != mRotateAniTime && time != 0) {
            this.mRotateAniTime = time;
            this.buildAnimation();
        }
    }

    public void setLastUpdateTimeKey(String key) {
        if(!TextUtils.isEmpty(key)) {
            this.mLastUpdateTimeKey = key;
        }
    }

    public void setLastUpdateTimeRelateObject(Object object) {
         setLastUpdateTimeKey(object.getClass().getName());
    }

    private void buildAnimation() {
        this.mFlipAnimation = new RotateAnimation(0.0F, -180.0F, 1, 0.5F, 1, 0.5F);
        this.mFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mFlipAnimation.setDuration((long)this.mRotateAniTime);
        this.mFlipAnimation.setFillAfter(true);
        this.mReverseFlipAnimation = new RotateAnimation(-180.0F, 0.0F, 1, 0.5F, 1, 0.5F);
        this.mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        this.mReverseFlipAnimation.setDuration((long)this.mRotateAniTime);
        this.mReverseFlipAnimation.setFillAfter(true);
    }

    private void resetView() {
        this.hideRotateView();
        this.mProgressBar.setVisibility(View.GONE);
    }

    private void hideRotateView() {
    }

    public void onUIReset(PtrFrameLayout frame) {
        this.resetView();
        this.mShouldShowLastUpdate = true;
        this.tryUpdateLastUpdateTime();
    }

    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = true;
        this.tryUpdateLastUpdateTime();
//        this.mLastUpdateTimeUpdater.start();
        this.mProgressBar.setVisibility(View.GONE);
        this.mTitleTextView.setVisibility(VISIBLE);
        if(frame.isPullToRefresh()) {
            mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        }

    }

    public void onUIRefreshBegin(PtrFrameLayout frame) {
        this.mShouldShowLastUpdate = true;
//        setBackgroundColor(getResources().getColor(R.color.white));
//        this.mTitleTextView.setTextColor(getResources().getColor(R.color.black));
        this.hideRotateView();
        this.mProgressBar.setVisibility(VISIBLE);
        this.mTitleTextView.setVisibility(VISIBLE);
        this.mTitleTextView.setText(R.string.cube_ptr_refreshing);
        this.tryUpdateLastUpdateTime();
//        this.mLastUpdateTimeUpdater.stop();
    }

    public void onUIRefreshComplete(PtrFrameLayout frame) {
        this.hideRotateView();
        this.mProgressBar.setVisibility(GONE);
        this.mTitleTextView.setVisibility(VISIBLE);
        //不显示更新完成字样
//        this.mTitleTextView.setDescription(this.getResources().getString(in.srain.cube.views.ptr.R.string.cube_ptr_refresh_complete));
        SharedPreferences sharedPreferences = this.getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if(!TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTime = (new Date()).getTime();
            sharedPreferences.edit().putLong(this.mLastUpdateTimeKey, this.mLastUpdateTime).commit();
        }

    }

    private void tryUpdateLastUpdateTime() {
        if(!TextUtils.isEmpty(this.mLastUpdateTimeKey) && this.mShouldShowLastUpdate) {
            String time = this.getLastUpdateTime();
            if(TextUtils.isEmpty(time)) {
                this.mTitleTextView.setVisibility(GONE);
            } else {
                this.mTitleTextView.setVisibility(VISIBLE);
                this.mTitleTextView.setText(time);
            }
        } else {
            this.mTitleTextView.setVisibility(GONE);
        }

    }

    private String getLastUpdateTime() {
        String lastUpdateTime ;
        if(this.mLastUpdateTime == 0L && !TextUtils.isEmpty(this.mLastUpdateTimeKey)) {
            this.mLastUpdateTime = this.getContext().getSharedPreferences("cube_ptr_classic_last_update", 0).getLong(this.mLastUpdateTimeKey, -1L);
        }
        lastUpdateTime = DateUtils.compressData(new Date().getTime(), mLastUpdateTime) + "更新";
//        LogUtil.e("my", "lastUpdateTime:" + lastUpdateTime);
        return lastUpdateTime;
    }

    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffsetToRefresh = frame.getOffsetToRefresh();
        int currentPos = ptrIndicator.getCurrentPosY();
        int lastPos = ptrIndicator.getLastPosY();
        if(currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if(isUnderTouch && status == 2) {
                this.crossRotateLineFromBottomUnderTouch(frame);

            }
        } else if(currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh && isUnderTouch && status == 2) {
            this.crossRotateLineFromTopUnderTouch(frame);

        }

    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if(!frame.isPullToRefresh()) {
            this.mTitleTextView.setVisibility(VISIBLE);
            this.mTitleTextView.setText(R.string.cube_ptr_release_to_refresh);
        }

    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        this.mTitleTextView.setVisibility(VISIBLE);
        if(frame.isPullToRefresh()) {
            this.mTitleTextView.setText(getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        } else {
            //不显示下拉刷新
            this.mTitleTextView.setText(this.getResources().getString(R.string.cube_ptr_pull_down_to_refresh));
        }

    }

    private class LastUpdateTimeUpdater implements Runnable {
        private boolean mRunning;

        private LastUpdateTimeUpdater() {
            this.mRunning = false;
        }

        private void start() {
            if(!TextUtils.isEmpty(PtrNFDefaultHeader.this.mLastUpdateTimeKey)) {
                this.mRunning = true;
                this.run();
            }
        }

        private void stop() {
            this.mRunning = false;
            PtrNFDefaultHeader.this.removeCallbacks(this);
        }

        public void run() {
            PtrNFDefaultHeader.this.tryUpdateLastUpdateTime();
            if(this.mRunning) {
                PtrNFDefaultHeader.this.postDelayed(this, 1000L);
            }

        }
    }
}
