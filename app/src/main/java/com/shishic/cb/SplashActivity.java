package com.shishic.cb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;

import com.android.network.NFHttpResponseListener;
import com.android.network.RequestUtil;
import com.android.nfRequest.LogError;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.shishic.cb.bean.HtmlAVObject;
import com.shishic.cb.util.Constant;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.SharepreferenceUtil;
import com.shishic.cb.util.ToastUtils;
import com.xl.updatelib.UpgradeSilenceActivity;
import com.xl.updatelib.bean.UpgradeModel;
import com.xl.updatelib.dialog.UpgradeChoiceSilenceDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.shishic.cb.util.SharepreferenceUtil.S_FILE;
import static com.shishic.cb.util.SharepreferenceUtil.S_SHOW_HELP;

public class SplashActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            RequestUtil.httpGet(this, Constant.URL_FUNTION_LIST, new HashMap<String, String>(), new NFHttpResponseListener<String>() {
                @Override
                public void onErrorResponse(LogError error) {
                    ToastUtils.toastShow(SplashActivity.this, R.string.network_error);
                }

                @Override
                public void onResponse(String response) {
                    LogUtil.e("my","resonse:" + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject data = jsonObject.optJSONObject("data");
                        if(data != null){
                            JSONArray jsonArray = data.optJSONArray("list");
                            if(jsonArray != null && jsonArray.length() > 0){
                                SharepreferenceUtil.saveFun(jsonArray.toString());
                            }

                            Intent intent;
                            SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                            boolean isShow = sp.getBoolean(S_SHOW_HELP,false);
                            if(isShow){
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            } else {
                                intent = new Intent(SplashActivity.this, HelpActivity.class);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putBoolean(S_SHOW_HELP,true);
                                editor.commit();
                            }

                            //如果是配置网页，则只进入网页
                            String update = sp.getString(SharepreferenceUtil.S_UPDATE,"");
                            if(!TextUtils.isEmpty(update)){
                                JSONArray jsonArray1 = new JSONArray(update);
                                if(jsonArray1 != null && jsonArray1.length() > 0){
                                    JSONObject jsonObject2 = jsonArray1.optJSONObject(0);
                                    JSONObject serverData = jsonObject2.optJSONObject("serverData");
                                    String url = serverData.optString("url");
                                    String des = serverData.optString("des");
                                    intent.putExtra("url",url);
                                    UpgradeModel upgradeModel = new UpgradeModel();
                                    upgradeModel.setContent(des);
                                    upgradeModel.setTitle("升级提示");
                                    upgradeModel.setUpgradeFlag("2");
                                    upgradeModel.setDownloadUrl(url);
                                    //不退出当前页面
                                    upgradeBySilence(upgradeModel);

                                }
                                return;
                            }

                            //如果是配置网页，则只进入网页
                            String config_html = sp.getString(SharepreferenceUtil.S_HTML,"");
                            if(!TextUtils.isEmpty(config_html)){
                                JSONArray jsonArray1 = new JSONArray(config_html);
                                if(jsonArray1 != null && jsonArray1.length() > 0){
                                    JSONObject jsonObject2 = jsonArray1.optJSONObject(0);
                                    JSONObject serverData = jsonObject2.optJSONObject("serverData");
                                    String url = serverData.optString("url");
                                    String des = serverData.optString("des");
                                    intent.setClass(SplashActivity.this, HmtlActivity.class);
                                    intent.putExtra("url",url);
                                    intent.putExtra("canback",true);
                                }
                                List<AVObject> htmlList = new Gson().fromJson(config_html, new TypeToken<List<AVObject>>(){}.getType());
                                LogUtil.e("my","htmlList:" + htmlList.toString());
//                                AVObject avObject = htmlList.get(0);
//                                HtmlAVObject serverData = ((HtmlAVObject) avObject.getJSONObject());

                            }

                            //没有其他配置的情况下，进入首页
                            startActivity(intent);

                            finish();
                        }

                    } catch (Exception exception){
                        exception.printStackTrace();
                    } finally {

                    }
                }
            });

            AVQuery<AVObject> avQuery = new AVQuery<>("c_update");
            avQuery.orderByDescending("createdAt");
//            avQuery.include("owner");
            avQuery.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        LogUtil.e("my","avQuery list:" + list.toString());
                        SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(SharepreferenceUtil.S_UPDATE,list.toString());
                        editor.commit();

                    } else {
                        e.printStackTrace();
                    }
                }
            });

            AVQuery<AVObject> avQuery1 = new AVQuery<>("c_config_html");
            avQuery1.orderByDescending("createdAt");
//            avQuery.include("owner");
            avQuery1.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        LogUtil.e("my","avQuery1 list:" + list.toString());
                        SharedPreferences sp = getSharedPreferences(S_FILE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(SharepreferenceUtil.S_HTML,list.toString());
                        editor.commit();

                    } else {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception exception){
            exception.printStackTrace();
        } finally {

        }
        //注册广播
        initUpgradeSilenceReciver();
    }

    private UpgradeChoiceSilenceDialog upgradeChoiceSilenceDialog;
    public void upgradeBySilence(UpgradeModel upgradeModel) {
        upgradeChoiceSilenceDialog = new UpgradeChoiceSilenceDialog(this, upgradeModel);
        upgradeChoiceSilenceDialog.clickUpgrade();
    }

    public void dismissUpgradeChoiceSilenceDialog() {
        if (upgradeChoiceSilenceDialog != null && upgradeChoiceSilenceDialog.isShowing()) {
            upgradeChoiceSilenceDialog.dismiss();
        }
    }

    public void showUpgradeChoiceSilenceDialog() {
        if (upgradeChoiceSilenceDialog != null) {
            upgradeChoiceSilenceDialog.show();
        }
    }

    /**
     * 监听器
     */
    private void initUpgradeSilenceReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_ERROR);
        filter.addAction(UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_FINISHED);
        filter.addAction(UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_PROGRESS);
        filter.addAction(UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_START);
        registerReceiver(myBroadcastReciver, filter);
    }

    /**
     * 广播
     */
    private BroadcastReceiver myBroadcastReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_PROGRESS.equals(action)) {
                //更新进度
                int percent = (int) intent.getLongExtra(UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_PROGRESS, 0L);
                if (upgradeChoiceSilenceDialog != null) {
                    upgradeChoiceSilenceDialog.upgradeProcess(percent);
                }

            } else if (UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_FINISHED.equals(action)) {
                //完成
                if (upgradeChoiceSilenceDialog != null) {
                    upgradeChoiceSilenceDialog.upgradeFinish(100);
                    upgradeChoiceSilenceDialog.getDialog_upgrade_tv_upgrade_cp().getTextView()
                            .setText("无需下载，点击安装");
                    showUpgradeChoiceSilenceDialog();
                }

            } else if (UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_ERROR.equals(action)) {
                //错误
                if (upgradeChoiceSilenceDialog != null) {
                    upgradeChoiceSilenceDialog.upgradeError(0);
                }
            } else if (UpgradeSilenceActivity.ACTION_UPDATE_SILENCE_START.equals(action)) {
                Object object = intent.getSerializableExtra(UpgradeModel.TAG);
                Log.e("my","object:" + object);
                if (object != null) {
                    UpgradeModel upgradeModel = (UpgradeModel) object;
                    upgradeBySilence(upgradeModel);
                }
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissUpgradeChoiceSilenceDialog();
        unregisterReceiver(myBroadcastReciver);
    }



}
