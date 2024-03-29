package com.findingdata.oabank.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.Utils;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import static com.findingdata.oabank.base.BaseHandler.CHECK_LOGIN;

/**
 * Created by zengx on 2019/11/17.
 * Describe: 应用启动页
 */
@ContentView(R.layout.activity_launch)
public class LaunchActivity extends BaseActivity {
    @ViewInject(R.id.tv_loading_version)
    private TextView tv_loading_version;

    private ProgressDialog progressDialog;
    private boolean hasPermission = false;
    public static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setFullScreen(false);
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            hasPermission=true;
        }
        tv_loading_version.setText("v-"+ Utils.getCurrentVersion(this)+" : "+Utils.getCurrentBuild(this));
        checkVersion();   
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        int hasReadSDPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> permissions = new ArrayList<>();
        if (hasReadSDPermission != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            hasPermission = true;
        }
        if (!permissions.isEmpty()) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
        }
    }


    /**
     * 检查版本
     */
    private void checkVersion(){
        new PgyUpdateManager.Builder()
                .setForced(false)                //设置是否强制提示更新,非自定义回调更新接口此方法有用
                .setUserCanRetry(false)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                .setDeleteHistroyApk(false)     // 检查更新前是否删除本地历史 Apk， 默认为true
                .setUpdateManagerListener(new UpdateManagerListener() {
                    @Override
                    public void onNoUpdateAvailable() {
                        //没有更新是回调此方法
                        Log.d("pgyer", "there is no new version");
//                        finish();
                        checkAutoLogin();
                    }
                    @Override
                    public void onUpdateAvailable(final AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + " new versionCode is " + appBean.getVersionCode()
                                + " new versionName is " + appBean.getVersionName());
                        //调用以下方法，DownloadFileListener 才有效；
                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                        if(Utils.getCurrentVersion(LaunchActivity.this).equals(appBean.getVersionName())){
                            new AlertDialog.Builder(LaunchActivity.this)
                                    .setTitle("更新")
                                    .setMessage("发现新版本"+appBean.getVersionName()+"\n"+appBean.getReleaseNote())
                                    .setCancelable(false)
                                    .setNegativeButton("确认更新",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                        }
                                    }).
                                    setNeutralButton("下次再说", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
//                                            finish();
                                            checkAutoLogin();
                                        }
                                    }).show();
                        }else{
                            new AlertDialog.Builder(LaunchActivity.this)
                                    .setTitle("强制更新")
                                    .setMessage("发现新版本"+appBean.getVersionName()+"\n"+appBean.getReleaseNote())
                                    .setCancelable(false)
                                    .setNegativeButton(
                                            "确认更新",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                                                }
                                            }).show();
                        }
                    }

                    @Override
                    public void checkUpdateFailed(Exception e) {
                        //更新检测失败回调
                        //更新拒绝（应用被下架，过期，不在安装有效期，下载次数用尽）以及无网络情况会调用此接口
                        Log.e("pgyer", "check update failed ", e);
//                        finish();
                        checkAutoLogin();
                    }
                })
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");
//                        finish();
                        checkAutoLogin();
                    }

                    @Override
                    public void downloadSuccessful(File file) {
                        PgyUpdateManager.installApk(file);
                    }
                    @Override
                    public void onProgressUpdate(Integer... integers) {
                        Log.e("pgyer", "update download apk progress" + integers[0]);
                        initProgressDialog(integers[0]);
                    }
                })
                .register();
    }

    /**
     * 下载最新版进度
     * @param progress
     */
    private void initProgressDialog(int progress){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.setTitle("下载最新版");
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }else{
            progressDialog.setProgress(progress);
            if(progress==100){
                progressDialog.dismiss();
            }
        }
    }

    /**
     * 检查是否自动登录
     */
    private void checkAutoLogin(){
        List<FilterValueEntity> filterValueEntities=new ArrayList<>();
        //清空项目过滤的值
        SharedPreferencesManage.setFilterValueEntity(filterValueEntities);
        handler.sendEmptyMessage(CHECK_LOGIN);
    }
}
