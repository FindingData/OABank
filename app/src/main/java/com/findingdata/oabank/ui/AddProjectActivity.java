package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.OA_BASE_URL;

/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 新增项目Activity
 */
@ContentView(R.layout.activity_add_project)
public class AddProjectActivity extends BaseActivity {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_title;
    @ViewInject(R.id.project_content)
    private LinearLayout _project_content;

    private String formid;
    private JSONArray formContentValue;
    private JSONArray formContents;
    private String project_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("新增项目");
        formContentValue = new JSONArray();
        getFormList();
    }

    @Override
    protected  void onStart(){
        super.onStart();
        getProperty();
    }

    @Event({R.id.toolbar_btn_back})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

    private void getProperty(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Property/GetPropertyListByProjectId");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id);
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONArray dataArray = jsonObject.getJSONArray("Result");
                        initPropertyList(dataArray);
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

    private void initPropertyList(JSONArray array){

    }

    private void getFormList(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/BankGetFormListByCompanyId");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("formType","1");
        requestMap.put("companyID","3");
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONArray dataArray = jsonObject.getJSONArray("Result");
                        formid = dataArray.getJSONObject(0).getString("value");
                        getDynamicFormLabels(formid);
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

    private void getDynamicFormLabels(String formID){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/GetFormLabelsForMobile");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("formId",formID);
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONArray dataArray = jsonObject.getJSONArray("Result");
                        formContents = dataArray;
                        for (int i = 0 ; i < dataArray.length(); i++){
                            String value = "";
                            if (dataArray.getJSONObject(i).getString("LABEL_NAME").equals("pca_code")){
                                value = "430100";
                            }else if (dataArray.getJSONObject(i).getString("LABEL_NAME").equals("customer_id")){
                                value = "3";
                            }
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("LABEL_ID",dataArray.getJSONObject(i).getString("FORM_LABEL_ID"));
                            jsonObject1.put("VALUE",value);
                            formContentValue.put(jsonObject1);
                        }
                        createProjectView(dataArray);
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

    private void createProjectView(JSONArray param){
        for (int i = 0; i<param.length();i++){
            LinearLayout current_layout = new LinearLayout(this);
            current_layout.setOrientation(LinearLayout.HORIZONTAL);
            current_layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            current_layout.setTop(5);

            try {
                TextView tv_name = new TextView(this);
                tv_name.setText(param.getJSONObject(i).getString("LABEL_NAME_CHS"));
                tv_name.setWidth(40);
                EditText ed_label = new EditText(this);
                ed_label.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
                ed_label.setLeft(5);
                ed_label.setRight(5);
                current_layout.addView(tv_name);
                current_layout.addView(ed_label);
                _project_content.addView(current_layout);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
