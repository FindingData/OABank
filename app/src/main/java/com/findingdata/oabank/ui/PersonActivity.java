package com.findingdata.oabank.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.LoginEntity;
import com.findingdata.oabank.entity.TokenEntity;
import com.findingdata.oabank.entity.UserInfo;
import com.findingdata.oabank.utils.ExitAppUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.Utils;
import com.pgyersdk.update.DownloadFileListener;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe: 个人中心Activity
 */
@ContentView(R.layout.acitvity_person)
public class PersonActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    TextView toolbar_title;
    @ViewInject(R.id.toolbar_btn_action)
    private ImageButton toolbar_btn_action;
    @ViewInject(R.id.person_ll_logout_layer)
    LinearLayout logout_layer;

    @ViewInject(R.id.person_tv_customer)
    private TextView person_tv_customer;
    @ViewInject(R.id.person_tv_name)
    private TextView person_tv_name;
    @ViewInject(R.id.person_tv_email)
    private TextView person_tv_email;
    @ViewInject(R.id.person_tv_tel)
    private TextView person_tv_tel;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("个人中心");
        toolbar_btn_action.setVisibility(View.INVISIBLE);
        UserInfo userInfo=SharedPreferencesManage.getUserInfo();
        person_tv_customer.setText(userInfo.getCustomerName());
        person_tv_name.setText(userInfo.getUserName());
        person_tv_tel.setText(userInfo.getUserPhone().substring(0,3)+"****"+userInfo.getUserPhone().substring(7));
        person_tv_email.setText(userInfo.getUserEmail());
    }

    @Event({R.id.toolbar_btn_back, R.id.person_ll_version,R.id.person_ll_logout,R.id.person_ll_logout_layer,R.id.person_ll_logout_layer_change,
            R.id.person_ll_logout_out, R.id.person_ll_logout_cancel,R.id.person_ll_modify_password})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.person_ll_modify_password:
                startActivity(ModifyPasswordActivity.class);
                break;
            case R.id.person_ll_version:
                checkVersion();
                break;
            case R.id.person_ll_logout:
                logout_layer.setVisibility(View.VISIBLE);
                logout_layer.startAnimation(alphaAnimation());
                break;
            case R.id.person_ll_logout_layer_change:
                SharedPreferencesManage.setLoginInfo(new LoginEntity("",""));
                TokenEntity tokenEntity=SharedPreferencesManage.getToken();
                tokenEntity.setExpireTime(-1);
                SharedPreferencesManage.setToken(tokenEntity);
                startActivity(new Intent(PersonActivity.this, LoginActivity.class));
                ExitAppUtils.getInstance().exit();
                break;
            case R.id.person_ll_logout_out:
                ExitAppUtils.getInstance().exit();
                break;
                default:
                    logout_layer.setVisibility(View.GONE);
                    break;
        }
    }

    /**
     * 退出ActionSheet动画
     * @return
     */
    private AlphaAnimation alphaAnimation(){
        AlphaAnimation animation=new AlphaAnimation(0,1);
        animation.setDuration(200);
        return animation;
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
                        showToast("已经是最新版本");
                    }

                    @Override
                    public void onUpdateAvailable(final AppBean appBean) {
                        //有更新回调此方法
                        Log.d("pgyer", "there is new version can update"
                                + " new versionCode is " + appBean.getVersionCode()
                                + " new versionName is " + appBean.getVersionName());
                        //调用以下方法，DownloadFileListener 才有效；
                        //如果完全使用自己的下载方法，不需要设置DownloadFileListener
                        if(Utils.getCurrentVersion(PersonActivity.this).equals(appBean.getVersionName())){
                            new AlertDialog.Builder(PersonActivity.this)
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
                                            finish();
                                            startActivity(new Intent(PersonActivity.this,LoginActivity.class));
                                        }
                                    }).show();
                        }else{
                            new AlertDialog.Builder(PersonActivity.this)
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
                        showToast("检查最新版异常");
                    }
                })
                .setDownloadFileListener(new DownloadFileListener() {
                    @Override
                    public void downloadFailed() {
                        //下载失败
                        Log.e("pgyer", "download apk failed");
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
}
