package com.shishic.cb.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangzkai on 2017/6/28.
 */

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration{

    private int paddingHor = 0;
    private int firstPaddingHor = 0;
    private int lastPaddingHor = 0;
    public HorizontalItemDecoration(int paddingHor) {
        this.paddingHor = paddingHor;
    }

    public HorizontalItemDecoration(int paddingHor, int firstPaddingHor, int lastPaddingHor ) {
        this.paddingHor = paddingHor;
        this.firstPaddingHor = firstPaddingHor;
        this.lastPaddingHor = lastPaddingHor;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        if (parent.getAdapter().getItemCount() == 1) {
            outRect.left  = firstPaddingHor != 0 ? firstPaddingHor : paddingHor ;
            outRect.right  = lastPaddingHor != 0 ? lastPaddingHor : paddingHor ;
            return;
        }

        // last position
        if (isLastPosition(view, parent)) {
            outRect.right = paddingHor;
            outRect.left = paddingHor;
        }else if( isFirstPosition(view, parent) ){
            outRect.left = paddingHor;
            outRect.right = paddingHor;
        }else{
            outRect.left = paddingHor;
            outRect.right = paddingHor;
        }
    }

    private boolean isFirstPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == 0;
    }

    private boolean isLastPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1;
    }
}
