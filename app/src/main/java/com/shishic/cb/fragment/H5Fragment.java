package com.shishic.cb.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shishic.cb.R;
import com.shishic.cb.bean.ADTextBean;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.view.NfWebView;

import java.io.Serializable;

public class H5Fragment extends BaseFragment {

    private View view;

    private LinearLayout ll_content;

    private RelativeLayout rl_header;

    private LinearLayout ll_back;

    private NfWebView nfWebView;

    private boolean canback;

//    private ADTextBean adTextBean;
    private String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_h5, container, false);
            ll_content = view.findViewById(R.id.ll_content);
            rl_header = view.findViewById(R.id.rl_header);
            ll_back = view.findViewById(R.id.ll_back);
            nfWebView = new NfWebView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll_content.addView(nfWebView,layoutParams);
            url = getArguments().getString("url", "");
            canback = getArguments().getBoolean("canback");
            if(canback){
                rl_header.setVisibility(View.GONE);
            }
            initWebview();

            nfWebView.loadUrl(url);
        }
        return view;
    }


    private void initWebview(){
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canback){
                    if(nfWebView.canGoBack()){
                        nfWebView.goBack();
                    }
                } else {
                    getActivity().finish();
                }
            }
        });

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

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
                LogUtil.e("my", "onShowCustomView ");
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                LogUtil.e("my", "onHideCustomView ");
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                LogUtil.e("my", "onCreateWindow ");
                if (nfWebView == null) {
                    nfWebView = new NfWebView(getActivity());
                }
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(nfWebView);
                resultMsg.sendToTarget();
                return true;
            }
        });

        nfWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.e("my","shouldOverrideUrlLoading:" + url);
                if(URLUtil.isValidUrl(url)){
                    view.loadUrl(url);

                }
                return true;
            }

        });
    }

    /**
     * 返回
     */
    public void goback(){
        if(canback){
            if(nfWebView.canGoBack()){
                nfWebView.goBack();
            }
        } else {
            getActivity().finish();
        }
    }


}
