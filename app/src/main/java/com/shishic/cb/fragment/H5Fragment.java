package com.shishic.cb.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.shishic.cb.R;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.view.NfWebView;

import java.io.Serializable;

public class H5Fragment extends BaseFragment {

    private View view;

    private LinearLayout ll_content;

    private NfWebView nfWebView;

//    private ADTextBean adTextBean;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_h5, container, false);
            ll_content = view.findViewById(R.id.ll_content);
            nfWebView = new NfWebView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll_content.addView(nfWebView,layoutParams);
            url = getArguments().getString("url", "");
            initWebview();

            nfWebView.loadUrl(url);
        }
        return view;
    }


    private void initWebview(){
        nfWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtil.e("my","onProgressChanged:" + newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                LogUtil.e("my","onReceivedTitle:" + title);
            }
        });

        nfWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("my","shouldOverrideUrlLoading:" + url);
                view.loadUrl(url);
                return true;
            }
        });
    }


}
