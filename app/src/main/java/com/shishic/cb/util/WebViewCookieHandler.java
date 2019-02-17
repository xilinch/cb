package com.shishic.cb.util;

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class WebViewCookieHandler implements CookieJar {
    private CookieManager mCookieManager = CookieManager.getInstance();

    public Context context;

    public WebViewCookieHandler(Context context) {
        this.context = context;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        mCookieManager.setAcceptCookie(true);
        String urlString = url.toString();
        for (Cookie cookie : cookies) {
            mCookieManager.setCookie(urlString, cookie.toString());
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.getInstance().sync();
        } else {
            mCookieManager.flush();
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String urlString = url.toString();
        String cookiesString = mCookieManager.getCookie(urlString);
        if (cookiesString != null && !cookiesString.isEmpty()) {
            String[] cookieHeaders = cookiesString.split(";");
            List<Cookie> cookies = new ArrayList<>(cookieHeaders.length);
            for (String header : cookieHeaders) {
                cookies.add(Cookie.parse(url, header));
            }
            return cookies;
        }
        return Collections.emptyList();
    }
}
