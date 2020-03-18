package com.findingdata.oabank.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.utils.KeyBoardUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import static com.findingdata.oabank.base.Config.BASE_URL;

@ContentView(R.layout.activity_add_note)
public class AddNoteActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.add_note_et_content)
    private EditText add_note_et_content;

    private int project_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_tv_title.setText("备注");
        project_id=getIntent().getExtras().getInt("project_id");
    }

    @Event({R.id.toolbar_btn_back,R.id.add_note_btn_save})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.add_note_btn_save:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                if(TextUtils.isEmpty(add_note_et_content.getText().toString().trim())){
                    showToast("留言内容不能为空");
                    return;
                }
                addNote();
                break;
        }
    }
    private void addNote(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/AddProjectNote");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("PROJECT_NOTE_CONTENT",add_note_et_content.getText());
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                if(entity.isStatus()){
                    LogUtil.d("留言ID"+entity.getResult());
                    EventBus.getDefault().post(new EventBusMessage<>("AddNote"));
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

            @Override
            public void onFinished() {
                super.onFinished();
                stopProgressDialog();
            }
        });
        sendRequest(requestParam,true);
    }
}
