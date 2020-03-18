package com.findingdata.oabank.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.LoginEntity;
import com.findingdata.oabank.entity.TokenEntity;
import com.findingdata.oabank.entity.UserInfo;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.utils.http.XHttp;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

import static com.findingdata.oabank.base.Config.BASE_URL;

/**
 * Created by zengx on 2019/11/16.
 * Describe: 登录Activity
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    @ViewInject(R.id.login_et_tel)
    private EditText login_et_tel;
    @ViewInject(R.id.login_et_password)
    private EditText login_et_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
}


    @Event({R.id.login_btn_submit})
    private void eventOnClick(View v){
        switch (v.getId()){
            case R.id.login_btn_submit:
                login();
                break;
        }
    }
    //登录
    private void login(){
        startProgressDialog();
        Map<String,Object> params=new HashMap<>();
        params.put("USER_NAME",login_et_tel.getText().toString());
        params.put("PASSWORD",login_et_password.getText().toString());
        XHttp.Post(BASE_URL+"/api/Home/Login",params,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<String> entity= JsonParse.parse(result,String.class);
                if(entity.isStatus()){
                    SharedPreferencesManage.setLoginInfo(new LoginEntity(login_et_tel.getText().toString(),login_et_password.getText().toString()));
                    SharedPreferencesManage.setToken(new TokenEntity(entity.getResult(),new Date().getTime()+10*60*1000));
                    getUserInfo();
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
    }
    //获取当前用户信息并缓存
    private void getUserInfo(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Home/GetUserInfo");
        requestParam.setMethod(HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<UserInfo> entity= JsonParse.parse(result,UserInfo.class);
                if(entity.isStatus()){
                    SharedPreferencesManage.setUserInfo(entity.getResult());
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast(ex.getMessage());
            }
        });
        sendRequest(requestParam,false);
    }
}
