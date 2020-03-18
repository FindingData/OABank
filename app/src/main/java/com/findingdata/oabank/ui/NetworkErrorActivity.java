package com.findingdata.oabank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import androidx.annotation.Nullable;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 网络异常页面
 */
@ContentView(R.layout.activity_network_error)
public class NetworkErrorActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.toolbar_btn_action)
    private ImageButton toolbar_btn_action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("网络不通");
        toolbar_btn_action.setVisibility(View.INVISIBLE);
    }

    @Event({R.id.toolbar_btn_back,R.id.btn_to_setting})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.btn_to_setting:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                finish();
                break;
        }
    }
}
