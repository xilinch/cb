package com.shishic.cb.loadmore;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class PlusRecyclerAdapter<T> extends RecyclerView.Adapter {

    private List<T> list;
    private boolean isShowCustomHead;

    public PlusRecyclerAdapter(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public int getItemCount() {
        if (getList() == null) {
            return 0;
        }
        return getList().size();
    }

    public void addHeadItem() {
        list.add(0, (T) new Object());
    }

    public boolean isShowCustomHead() {
        return isShowCustomHead;
    }

    public void setShowCustomHead(boolean showCustomHead) {
        isShowCustomHead = showCustomHead;
    }

}
