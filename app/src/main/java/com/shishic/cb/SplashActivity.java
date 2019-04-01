package com.shishic.cb;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.DownloadUtil;
import com.shishic.cb.util.InstallUtil;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.SharepreferenceUtil;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.NfProgressBar;
import com.xl.updatelib.bean.UpgradeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static com.shishic.cb.util.SharepreferenceUtil.S_FILE;
import static com.shishic.cb.util.SharepreferenceUtil.S_SHOW_HELP;

public class SplashActivity extends BaseActivity {
    private NfProgressBar progressBar;

    private InstallUtil mInstallUtil;

    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int grantedResult = ContextCompat.checkSelfPermission(this, permissions[0]);
            if (grantedResult == PackageManager.PERMISSION_GRANTED) {
                //申请权限
                init();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 321);
            }
        } else {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean granted = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!granted) {
                        finish();
                    } else {
                        ActivityCompat.requestPermissions(this, permissions, 321);
                    }
                } else {
                    init();
                }
            }
        }

    }

    private void init() {
        try {
            RequestUtil.httpGet(this, Constant.URL_FUNTION_LIST, new HashMap<String, String>(), new NFHttpResponseListener<String>() {
                @Override
                public void onErrorResponse(LogError error) {
                    ToastUtils.toastShow(SplashActivity.this, R.string.network_error);
                }

                @Override
                public void onResponse(String response) {
                    LogUtil.e("my", "resonse:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            JSONArray jsonArray = data.optJSONArray("list");
                            if (jsonArray != null && jsonArray.length() > 0) {
                                SharepreferenceUtil.saveFun(jsonArray.toString());
                            }

                            Intent intent;
                            SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                            boolean isShow = sp.getBoolean(S_SHOW_HELP, false);
                            if (isShow) {
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            } else {
                                intent = new Intent(SplashActivity.this, HelpActivity.class);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean(S_SHOW_HELP, true);
                                editor.commit();
                            }
                            //如果是配置网页，则只进入网页
                            String config_html = sp.getString(SharepreferenceUtil.S_HTML, "");
                            if (!TextUtils.isEmpty(config_html)) {
                                JSONArray jsonArray1 = new JSONArray(config_html);
                                if (jsonArray1 != null && jsonArray1.length() > 0) {
                                    JSONObject jsonObject2 = jsonArray1.optJSONObject(0);
                                    JSONObject serverData = jsonObject2.optJSONObject("serverData");
                                    String url = serverData.optString("url");
                                    String des = serverData.optString("des");
                                    String type = serverData.optString("type");
                                    if("1".equals(type)){
                                        //如果是配置网页，则只进入网页
                                        intent.setClass(SplashActivity.this, HmtlActivity.class);
                                        intent.putExtra("url", url);
                                        intent.putExtra("canback", true);
                                    } else if("2".equals(type)){
                                        //升级
                                        download(url);
                                        return;
                                    } else {
                                        //
                                    }
                                }
                            }
                            //没有其他配置的情况下，进入首页
                            startActivity(intent);
                            finish();
                        }

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    } finally {

                    }
                }
            });
            AVQuery<AVObject> avQuery1 = new AVQuery<>("c_config_html");
            avQuery1.orderByDescending("createdAt");
            avQuery1.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        LogUtil.e("my", "avQuery1 list:" + list.toString());
                        SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(SharepreferenceUtil.S_HTML, list.toString());
                        editor.commit();

                    } else {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {

        }
    }

    /**
     * 下载应用
     * @param url
     */
    private void download(String url) {
        progressBar.setVisibility(View.VISIBLE);
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "kuoke.apk";
        LogUtil.e("my","filePath:" + filePath);
        mInstallUtil = new InstallUtil(this, filePath);
        DownloadUtil.get().download(url, Environment.getExternalStorageDirectory().getAbsolutePath(), "kuoke.apk", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                //下载完成进行相关逻辑操作
                openFile(file);
            }

            @Override
            public void onDownloading(int progress) {
//                LogUtil.e("my", "下载百分之" + progress + "%。。。。");
            }

            @Override
            public void onDownloadFailed(Exception e) {
                //下载异常进行相关提示操作
            }
        });

    }

    private void openFile(final File file) {
        // TODO Auto-generated method stub
        mInstallUtil.install();//再次执行安装流程，包含权限判等
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == InstallUtil.UNKNOWN_CODE) {
            mInstallUtil.install();//再次执行安装流程，包含权限判等
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //返回键不可返回
            if(progressBar.getVisibility() == View.VISIBLE){
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
