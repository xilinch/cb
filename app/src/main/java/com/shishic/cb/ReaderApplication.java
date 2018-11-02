package com.shishic.cb;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;

import com.bumptech.glide.Glide;

public class ReaderApplication extends Application {

    private static ReaderApplication applicationContext;

    public static ReaderApplication getInstace(){
        if(applicationContext == null){
            applicationContext = new ReaderApplication();
        }
        return applicationContext;
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (Build.VERSION.SDK_INT >= 19) {
            Glide.get(this).trimMemory(level);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //Glide中的MemorySizeCalculator调用了isLowRamDevice是新的SDK方法
        if (Build.VERSION.SDK_INT >= 19) {
            Glide.get(this).clearMemory();
        }
    }
}
