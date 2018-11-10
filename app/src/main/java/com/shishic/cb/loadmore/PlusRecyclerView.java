package com.shishic.cb.loadmore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by jingyu on 2017/4/20.
 */

public class PlusRecyclerView extends RecyclerView {
    public PlusRecyclerView(Context context) {
        super(context);
    }

    public PlusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private OnBottomListener onBottomListener;

    public interface OnBottomListener {
        void onBottom();
    }

    public void setOnBottomListener(OnBottomListener onBottomListener) {
        this.onBottomListener = onBottomListener;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        if (onBottomListener != null && isSlideToBottom()) {
            onBottomListener.onBottom();
        }
    }

    public boolean isSlideToBottom() {
        return this.computeVerticalScrollExtent() + this.computeVerticalScrollOffset()
                >= this.computeVerticalScrollRange();
    }
}
