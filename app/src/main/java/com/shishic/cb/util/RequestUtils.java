package com.shishic.cb.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.network.RequestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class RequestUtils {

    public static OkHttpClient get(Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cookieJar(new WebViewCookieHandler(context))
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        return builder.build();
    }


    public static void httpget(Context context, String url, HashMap<String,String> params,NFCallback callback){

        OkHttpClient okHttpClient = new OkHttpClient();
        StringBuilder url_builder = new StringBuilder(url);
        if (params != null && params.size() > 0) {
            url_builder.append(RequestUtil.encodeParameters(params, url.indexOf("?") != -1));
        }
        String saveSessionId = SharepreferenceUtil.getSessionid();
        LogUtil.e("my"," saveSessionId:" + saveSessionId);
        final Request request = new Request.Builder()
                .url(url_builder.toString())
                .header("Cookie",saveSessionId)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        LogUtil.e("my","request.headers：" + request.headers());
        call.enqueue(callback);
    }

}
