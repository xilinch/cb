package com.shishic.cb.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhangzkai on 2017/6/28.
 */

public class VerticaltemDecoration extends RecyclerView.ItemDecoration{

    private int paddingHor = 0;
    private int firstPaddingHor = 0;
    private int lastPaddingHor = 0;
    public VerticaltemDecoration(int paddingHor) {
        this.paddingHor = paddingHor;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        if (parent.getAdapter().getItemCount() == 1) {
            outRect.top  = firstPaddingHor != 0 ? firstPaddingHor : paddingHor ;
            outRect.bottom  = lastPaddingHor != 0 ? lastPaddingHor : paddingHor ;
            return;
        }

        outRect.bottom = paddingHor;
        outRect.top = paddingHor;
        // last position
//        if (isLastPosition(view, parent)) {
//            outRect.bottom = paddingHor;
//        }else if( isFirstPosition(view, parent) ){
//            outRect.top = firstPaddingHor;
//            outRect.bottom = paddingHor;
//        }else{
//            outRect.bottom = paddingHor;
//            outRect.top = paddingHor;
//        }
    }

    private boolean isFirstPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == 0;
    }

    private boolean isLastPosition(View view, RecyclerView parent) {
        return parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1;
    }
}
