package com.shishic.cb.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.shishic.cb.bean.CommandResult;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


public class InstallUtil {

    private Activity mAct;
    private String mPath;//下载下来后文件的路径
    public static int UNKNOWN_CODE = 2018;

    public InstallUtil(Activity mAct, String mPath) {
        this.mAct = mAct;
        this.mPath = mPath;
    }

    public void install() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startInstallO();
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) startInstallN();
        else startInstall();
    }

    /**
     * android1.x-6.x
     */
    private void startInstall() {
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.parse("file://" + mPath), "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mAct.startActivity(install);
    }

    /**
     * android7.x
     */
    private void startInstallN() {
        //参数1 上下文, 参数2 在AndroidManifest中的android:authorities值, 参数3  共享的文件
        Uri apkUri = FileProvider.getUriForFile(mAct, "com.ssc.zx.fileProvider", new File(mPath));
        Intent install = new Intent(Intent.ACTION_VIEW);
        //由于没有在Activity环境下启动Activity,设置下面的标签
        install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        mAct.startActivity(install);
        LogUtil.e("my", "startInstallN  ------");
    }

    /**
     * android8.x
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallO() {
        boolean isGranted = mAct.getPackageManager().canRequestPackageInstalls();
        LogUtil.e("my", "startInstallO  isGranted:" + isGranted);
        if (isGranted) {
            startInstallN();//安装应用的逻辑(写自己的就可以)
        } else {
            startInstallN();
//            Uri packageURI = Uri.parse("package:" + mAct.getPackageName());
//            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
//            mAct.startActivityForResult(intent, UNKNOWN_CODE);
//            new AlertDialog.Builder(mAct)
//                    .setCancelable(false)
//                    .setTitle("安装应用需要打开未知来源权限，请去设置中开启权限")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface d, int w) {
//                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
//                            mAct.startActivityForResult(intent, UNKNOWN_CODE);
//                        }
//                    }).create()
//                    .show();
        }
    }



    //--------------------------------------------


    /**
     * 跳转到安装页面
     *
     * @param apkFile
     */
    public void install(File apkFile) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean b = mAct.getPackageManager().canRequestPackageInstalls();
            if (b) {
                startInstall(apkFile);
            } else {//跳转到应用授权安装第三方应用权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                mAct.startActivity(intent);
            }
        } else {
            startInstall(apkFile);
        }
    }

    /**
     * 安装
     * @param apkFile
     */
    private void startInstall(File apkFile) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(apkFile);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } else {
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            String authority = mAct.getPackageName() + ".provider";
            data = FileProvider.getUriForFile(mAct, authority, apkFile);
            intent1.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent1.setDataAndType(data, type);
        mAct.startActivity(intent1);
    }



    /**
     * 卸载应用成功&失败
     *
     * @param packageName
     * @param isKeepData
     * @return
     */
    public boolean uninstallSilent(String packageName, boolean isKeepData) {
        boolean isRoot = isRoot();
        String command = "LD_LIBRARY_PATH=/vendor/lib*:/system/lib* pm uninstall " + (isKeepData ? "-k" : "") + packageName;
        CommandResult commandResult = execCmd(new String[]{command}, isRoot, true);
        if (commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 提示卸载应用
     *
     * @param packcageName
     */
    public void normaluninstallSilent(String packcageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packcageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mAct.startActivity(intent);

    }

    /**
     * 是否root权限
     * @return
     */
    private boolean isRoot() {
        String su = "su";
        //手机本来已经有root权限（/system/bin/su已经存在，adb shell里面执行su就可以切换root权限下）
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;

    }


    private static final String LINE_SEP = System.getProperty("line.separator");

    /**
     * 执行动作
     * @param commands
     * @param isRoot
     * @param isNeedResultMsg
     * @return
     */
    private CommandResult execCmd(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (commands == null || commands.length == 0) {
            return new CommandResult(result, null, null);
        }
        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuffer successMsg = null;
        StringBuffer errorMsg = null;

        DataOutputStream os = null;
        try {
            //root过的手机上面获得root权限
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null)
                    continue;
                os.write(command.getBytes());
                os.writeBytes(LINE_SEP);
                os.flush();
            }
            os.writeBytes("exit" + LINE_SEP);
            os.flush();
            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuffer();
                errorMsg = new StringBuffer();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(),
                        "UTF-8"));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(),
                        "UTF-8"));
                String line;
                if ((line = successResult.readLine()) != null) {
                    successMsg.append(line);
                    while ((line = successResult.readLine()) != null) {
                        successMsg.append(LINE_SEP).append(line);
                    }
                }
                if ((line = errorResult.readLine()) != null) {
                    errorMsg.append(line);
                    while ((line = errorResult.readLine()) != null) {
                        errorMsg.append(LINE_SEP).append(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null)
                    os.close();
                if (successResult != null)
                    successResult.close();
                if (errorResult != null)
                    errorResult.close();
                if (process != null)
                    process.destroy();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new CommandResult(result, successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString());

    }
}

