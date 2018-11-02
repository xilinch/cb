package com.shishic.cb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shishic.cb.util.SharepreferenceUtil;

import static com.shishic.cb.util.SharepreferenceUtil.S_FILE;
import static com.shishic.cb.util.SharepreferenceUtil.S_SHOW_HELP;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(500);
            Intent intent;
            SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
            boolean isShow = sp.getBoolean(S_SHOW_HELP,false);
            if(isShow){
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, HelpActivity.class);
            }
            startActivity(intent);
            finish();
        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }

    }


}
