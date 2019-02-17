package com.shishic.cb.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.net.CookieStore;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.Response;

public abstract class  NFCallback implements Callback {
    private static final String TAG = "nfokhttp";
    private static final String HEADETAG = "Set-Cookie";
    private static final String SESSIONTAG = "JSESSIONID=";


    @Override
    public void onFailure(Call call, IOException e){
        LogUtil.d(TAG, "onFailure: ");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException{
        Headers headers = response.headers();
        List<String> cookies = headers.values("Set-Cookie");
        LogUtil.e("my","onResponse header cookies:" + cookies + "  response:" + response);
        if(cookies !=null && cookies.size() > 0){
            String sessionId = cookies.get(0);
            sessionId = sessionId.substring(0, sessionId.indexOf(";"));
            SharepreferenceUtil.setSessionid(sessionId);
        }

    }
}
