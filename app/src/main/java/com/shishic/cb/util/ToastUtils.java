package com.shishic.cb.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.shishic.cb.BuildConfig;
import com.shishic.cb.ReaderApplication;

public class ToastUtils {

    private static Toast toast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    // toast重复显示不消失问题
    public static void toastShow(Context context, final int arg) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ReaderApplication.getInstace(), arg, Toast.LENGTH_SHORT);
                } else {
                    // toast.cancel(); //取消后只会显示一次
                    toast.setText(arg);
                }
                if (toast != null && ReaderApplication.getInstace() != null) {
                    toast.show();
                }
            }
        });
    }

    // toast重复显示不消失问题
    public static void toastShowLong(Context context, final String arg) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ReaderApplication.getInstace(), arg, Toast.LENGTH_LONG);
                } else {
                    // toast.cancel(); //取消后只会显示一次
                    toast.setText(arg);
                }
                if (toast != null && ReaderApplication.getInstace() != null) {
                    toast.show();
                }
            }
        });
    }

    // toast重复显示不消失问题
    public static void toastShowLong(Context context, final int arg) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ReaderApplication.getInstace(), arg, Toast.LENGTH_LONG);
                } else {
                    // toast.cancel(); //取消后只会显示一次
                    toast.setText(arg);
                }
                if (toast != null && ReaderApplication.getInstace() != null) {
                    toast.show();
                }
            }
        });
    }

    // toast重复显示不消失问题
    public static void toastShow(Context context, final String arg) {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(ReaderApplication.getInstace(), arg, Toast.LENGTH_SHORT);
                } else {
                    // toast.cancel(); //取消后只会显示一次
                    toast.setText(arg);
                }
                if (toast != null && ReaderApplication.getInstace() != null) {
                    toast.show();
                }
            }
        });
    }

    /**
     * 调试toast
     */
    public static void dShortToastShow(Context context, String msg) {
        if (BuildConfig.DEBUG) {
            if (toast == null) {
                toast = Toast.makeText(ReaderApplication.getInstace(), msg, Toast.LENGTH_SHORT);
            } else {
                // toast.cancel(); //取消后只会显示一次
                toast.setText(msg);
            }
            if (toast != null && ReaderApplication.getInstace() != null) {
                toast.show();
            }
        } else {
            //do nothing
        }
    }
}
