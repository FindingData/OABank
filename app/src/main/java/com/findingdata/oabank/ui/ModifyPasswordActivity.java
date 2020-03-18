package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.LoginEntity;
import com.findingdata.oabank.utils.KeyBoardUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import static com.findingdata.oabank.base.Config.BASE_URL;

@ContentView(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    TextView toolbar_title;
    @ViewInject(R.id.toolbar_btn_action)
    ImageButton toolbar_action;
    @ViewInject(R.id.setting_et_old_password)
    EditText old_password;
    @ViewInject(R.id.setting_et_new_password)
    EditText new_password;
    @ViewInject(R.id.setting_et_new_password_confirm)
    EditText getNew_password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("修改密码");
        toolbar_action.setBackgroundResource(R.drawable.ic_action_submit);
        toolbar_action.setVisibility(View.VISIBLE);
    }
    @Event(value = {R.id.toolbar_btn_back,R.id.toolbar_btn_action})
    private void onClickEvent(View v){
        switch (v.getId()) {
            case R.id.toolbar_btn_back:
                finish();
                break;

            case R.id.toolbar_btn_action:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                modifyCommit();
                break;
        }
    }

    private void modifyCommit() {
        String old_pwd=old_password.getText().toString().trim();
        final String new_pwd=new_password.getText().toString().trim();
        String new_pwd_confirm=getNew_password_confirm.getText().toString().trim();
        if (TextUtils.isEmpty(old_pwd)) {
            showToast("原始密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(new_pwd)) {
            showToast("新密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(new_pwd_confirm)) {
            showToast("确认新密码不能为空");
            return;
        }
        if (!new_pwd.equals(new_pwd_confirm)) {
            showToast("前后两次输入的新密码不一致,请重新输入");
            return;
        }
        if(new_pwd.equals(old_pwd)){
            showToast("新密码不能和旧密码一样");
            return;
        }

        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetQueryItems");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> params=new HashMap<>();
        params.put("OldPassword", old_pwd);
        params.put("NewPassword", new_pwd);
        requestParam.setPostRequestMap(params);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<String> entity= JsonParse.parse(result,String.class);
                if(entity.isStatus()){
                    showToast("更新成功");
                    LoginEntity loginEntity= SharedPreferencesManage.getLoginInfo();
                    loginEntity.setPassword(new_pwd);
                    SharedPreferencesManage.setLoginInfo(loginEntity);
                    finish();
                }else{
                    showToast(entity.getMessage());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                showToast("出现错误,修改用户密码失败");
            }

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }
}
