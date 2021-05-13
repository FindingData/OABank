package com.findingdata.oabank.ui;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.utils.KeyBoardUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.OA_BASE_URL;

@ContentView(R.layout.activity_add_note)
public class AddNoteActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_tv_title;
    @ViewInject(R.id.add_note_et_content)
    private EditText add_note_et_content;
    @ViewInject(R.id.add_note_btn_save)
    private Button add_note_btn_save;

    @ViewInject(R.id.add_btn_note)
    private LinearLayout add_btn_note;

    private int project_id;
    private int actionid;
    private int commissioned_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project_id=getIntent().getExtras().getInt("project_id");
        actionid = getIntent().getExtras().getInt("actionid");
        commissioned_id = getIntent().getExtras().getInt("commissioned_id");
        if (actionid == 1){
            toolbar_tv_title.setText("备注");
            add_note_btn_save.setVisibility(View.GONE);
            add_btn_note.setVisibility(View.VISIBLE);

        }else if (actionid == 2){
            toolbar_tv_title.setText("暂停项目");
            add_btn_note.setVisibility(View.GONE);
            add_note_btn_save.setVisibility(View.VISIBLE);
        }else if (actionid == 3){
            toolbar_tv_title.setText("终止项目");
            add_btn_note.setVisibility(View.GONE);
            add_note_btn_save.setVisibility(View.VISIBLE);
        }

    }

    @Event({R.id.toolbar_btn_back,R.id.add_note_btn_save,R.id.add_to_yh,R.id.add_to_pg})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.add_note_btn_save:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                if(TextUtils.isEmpty(add_note_et_content.getText().toString().trim())){
                    showToast("内容不能为空");
                    return;
                }
                if (actionid == 2){
                    modifyProjectStatus("40001005");
                }else if (actionid ==3){
                    modifyProjectStatus("40001003");
                }

                break;

            case R.id.add_to_yh:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                if(TextUtils.isEmpty(add_note_et_content.getText().toString().trim())){
                    showToast("内容不能为空");
                    return;
                }
                addNote("0");
                break;
            case R.id.add_to_pg:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                if(TextUtils.isEmpty(add_note_et_content.getText().toString().trim())){
                    showToast("内容不能为空");
                    return;
                }
                addNote("1");
                break;

        }
    }

    private void addNote(final String is_outside){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/AddProjectNote");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("PROJECT_NOTE_CONTENT",add_note_et_content.getText());
        requestMap.put("IS_OUTSIDE",is_outside);
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
                    //AddNoteActivity.this.finish();
//                    finish();
                    if(is_outside.equals("1")){
                        addNoteToOA();
                    }else{
                        finish();
                    }

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

    private void addNoteToOA(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/project/BankProjectNote");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("REMARK",add_note_et_content.getText());
        requestMap.put("COMMISSIONED_ID",commissioned_id);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                if(entity.isStatus()){
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

    private void modifyProjectStatus(final String statusVal){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/ModifyProjectStatus");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("project_status",statusVal);
        requestMap.put("TERMINATION_REASON",add_note_et_content.getText());
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                //BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    if(jsonobj.getBoolean("Status")){
                        LogUtil.d("暂停项目"+jsonobj.toString());
                        //EventBus.getDefault().post(new EventBusMessage<>("AddNote"));
                        //AddNoteActivity.this.finish();
                        //finish();
                        modifyProjectStatusToOA(statusVal);
                    }else{
                        showToast(jsonobj.getString("Message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void modifyProjectStatusToOA(String statusVal){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/project/BankUpdateProjectStatus");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("project_status",statusVal);
        requestMap.put("TERMINATION_REASON",add_note_et_content.getText());
        requestMap.put("COMMISSIONED_ID",commissioned_id);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                //BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    if(jsonobj.getBoolean("Status")){
                        LogUtil.d("暂停项目"+jsonobj.toString());
                        //EventBus.getDefault().post(new EventBusMessage<>("AddNote"));
                        //AddNoteActivity.this.finish();
                        finish();
                    }else{
                        showToast(jsonobj.getString("Message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
