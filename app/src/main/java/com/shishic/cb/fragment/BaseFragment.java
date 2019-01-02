package com.shishic.cb.fragment;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment {

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getContext());
    }


}
