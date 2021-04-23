package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.PropertyListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.ApplyBusiness;
import com.findingdata.oabank.entity.FormLabelData;
import com.findingdata.oabank.entity.LabelKeyValue;
import com.findingdata.oabank.entity.PropertyEntity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.OA_BASE_URL;
import static com.findingdata.oabank.base.Config.OA_COMPANY_ID;
import static com.findingdata.oabank.base.Config.OA_DYNAMIC_ID;

/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 新增项目Activity
 */
@ContentView(R.layout.activity_add_project)
public class AddProjectActivity extends BaseActivity {

    @ViewInject(R.id.project_content)
    private LinearLayout _project_content; 
    private TextView add_object;
    private TextView submit;
    private EditText loan_money;
    private EditText jiekuanren;
    private EditText jiekuanrendianhua;
    private EditText weituoren;
    private EditText weituorendianhua;

    private RecyclerView mrv;
    private List<PropertyEntity> mList;
    private PropertyListAdapter adapter;

    private String formid;
    private String project_form_id;
    private JSONArray formContentValue;
    private JSONArray formContents;
    private JSONObject project;
    private JSONArray property_list;
    private int project_id = 0;

    String project_type;
    String loan_type;

    String[] project_types = new String[0];
    String[] project_type_chs = new String[0];

    String[] loan_types = new String[0];
    String[] loan_type_chs = new String[0];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formContentValue = new JSONArray();
        getFormList();

        add_object = findViewById(R.id.add_object);
        add_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("project_id",project_id);
                bundle.putString("type","add");
                startActivity(AddObjectActivity.class,bundle);
            }
        });
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (property_list != null && property_list.length()>0){
                    if (weituoren.getText().toString() != null && !weituoren.getText().toString().equals("") && weituorendianhua.getText().toString()!= null && !weituorendianhua.getText().toString().equals("")){
                        if (formContentValue.length()>0){
                            try {
                                List<LabelKeyValue> jsonarray = new ArrayList<LabelKeyValue>();
                                for (int i = 0; i<formContentValue.length(); i++) {
                                    LabelKeyValue dic_obj  = new LabelKeyValue();

                                    dic_obj.setID(formContentValue.getJSONObject(i).getString("LABEL_ID"));
                                    if (i == 0) {
                                        dic_obj.setVALUE( project_type);
                                    } else if (i == 1) {
                                        dic_obj.setVALUE( loan_type);
                                    } else if (i == 2) {
                                        dic_obj.setVALUE( loan_money.getText().toString());
                                    } else if (i == 3) {
                                        dic_obj.setVALUE( jiekuanren.getText().toString());
                                    } else if (i == 4) {
                                        dic_obj.setVALUE( jiekuanrendianhua.getText().toString());
                                    } else if (i == 5) {
                                        dic_obj.setVALUE( weituoren.getText().toString());
                                    } else if (i == 6) {
                                        dic_obj.setVALUE( weituorendianhua.getText().toString());
                                    } else if (i == 7) {
                                        dic_obj.setVALUE( SharedPreferencesManage.getUserInfo().getUserId()+"");
                                    }
                                    jsonarray.add(dic_obj);
                                }
                                Map<String,Object> requestMap=new HashMap<>();
                                requestMap.put("labelKeyValueList",jsonarray);
                                requestMap.put("FORM_ID",formid);
                                requestMap.put("formStoreID",project.getString("PROJECT_FORM_ID"));
                                BankSaveFormData(requestMap);

                            }  catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }else{
                        showToast("请填写委托人和委托人电话");
                    }

                }else{
                    showToast("请添加抵押物");
                }

            }
        });

        loan_money = findViewById(R.id.loan_money);
        jiekuanren = findViewById(R.id.jiekuanren);
        jiekuanrendianhua = findViewById(R.id.jiekuanrendianhua);
        weituoren = findViewById(R.id.weituoren);
        weituorendianhua = findViewById(R.id.weituorendianhua);
    }

    @Override
    protected  void onStart(){
        super.onStart();
        if (project_id !=0){
            getProperty();
        }
    }

    @Event({R.id.toolbar_btn_back})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
        }
    }

    public void BankSaveFormData(Map<String,Object> param){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/BankSaveFormData");
        requestParam.setMethod(HttpMethod.PostJson);
        requestParam.setPostJsonRequest(param);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        project_form_id = jsonObject.getString("Result");
                        List<FormLabelData> jsonarray = new ArrayList<FormLabelData>();
                        FormLabelData jsonobj = new FormLabelData();
                        jsonobj.setKey("id");
                        jsonobj.setValue(project_id+"");
                        jsonarray.add(jsonobj);
                        jsonobj = new FormLabelData();
                        jsonobj.setKey("project_form_id");
                        jsonobj.setValue(project_form_id);
                        jsonarray.add(jsonobj);
                        jsonobj = new FormLabelData();
                        jsonobj.setKey("project_name");
                        jsonobj.setValue(property_list.getJSONObject(0).getString("ADDRESS"));
                        jsonarray.add(jsonobj);

                        for (int i = 0; i<formContentValue.length(); i++){
                            if (!formContents.getJSONObject(i).getString("BINDEXPANDFIELD").equals("") && formContents.getJSONObject(i).getString("BINDEXPANDFIELD") != null){
                                jsonobj = new FormLabelData();
                                jsonobj.setKey(formContents.getJSONObject(i).getString("BINDEXPANDFIELD"));
                                if (i == 0) {
                                    jsonobj.setValue( project_type);
                                } else if (i == 1) {
                                    jsonobj.setValue( loan_type);
                                } else if (i == 2) {
                                    jsonobj.setValue( loan_money.getText().toString());
                                } else if (i == 3) {
                                    jsonobj.setValue( jiekuanren.getText().toString());
                                } else if (i == 4) {
                                    jsonobj.setValue( jiekuanrendianhua.getText().toString());
                                } else if (i == 5) {
                                    jsonobj.setValue( weituoren.getText().toString());
                                } else if (i == 6) {
                                    jsonobj.setValue( weituorendianhua.getText().toString());
                                }else if (i == 7) {
                                    jsonobj.setValue( SharedPreferencesManage.getUserInfo().getUserId()+"");
                                }
                                jsonarray.add(jsonobj);
                            }
                        }
                        saveProject(jsonarray);

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

    private void saveProject(List<FormLabelData> jsonarray){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/SaveProject");
        requestParam.setMethod(HttpMethod.PostJson);
//        Map<String,Object> requestMap=new HashMap<>();
//        requestMap.put("labelValueList",jsonarray);
        requestParam.setPostJsonRequest(jsonarray);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        applyProject();
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

    private void applyProject(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/ApplyProject");
        requestParam.setMethod(HttpMethod.PostJson);
        ApplyBusiness jsonobj = new ApplyBusiness();

            jsonobj.setProject_id(project_id+"");
            jsonobj.setBusiness_list(new int[]{40004001, 40004002});


        requestParam.setPostJsonRequest(jsonobj);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        if (jsonObject.getJSONObject("Result").getBoolean("IS_AUTO_DISPATCH")){
                            Map<String,Object> jsonObject1 = new HashMap<>();
                            jsonObject1.put("PROJECT_NAME",property_list.getJSONObject(0).getString("ADDRESS"));
                            jsonObject1.put("PRIORITY_LEVEL",40052001);
                            jsonObject1.put("PROJECT_TYPE","");
                            jsonObject1.put("APPRAISAL_PURPOSE",40005001);
                            jsonObject1.put("IS_FOLLOW",0);
                            jsonObject1.put("IS_REMINDER",0);
                            jsonObject1.put("DUE_DATE","");
                            jsonObject1.put("IS_APPROVED",0);
                            jsonObject1.put("BUSINESS_FORM_ID",project_form_id);
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<PropertyEntity>>() {}.getType();
                            jsonObject1.put("PROPERTY_LIST",gson.fromJson(property_list.toString(),type));
                            jsonObject1.put("BUSINESS_LIST",new int[]{40004001, 40004002});
                            jsonObject1.put("REQUIRE_ESTIMATE",0);
                            jsonObject1.put("REQUIRED_AUDIT",0);
                            jsonObject1.put("CRM_CUSTOMER_ID","");
                            jsonObject1.put("CRM_SUCTOMER_NAME","");
                            jsonObject1.put("PROJECT_CODE","");
                            jsonObject1.put("CUSTOMER_ID",jsonObject.getJSONObject("Result").getJSONArray("DISPATCH_CUSTOMER_LIST").getJSONObject(0).getInt("CUSTOMER_ID"));
                            jsonObject1.put("BANK_PROJECT_ID",project.getInt("PROJECT_ID"));
                            addProjectToOA(jsonObject1,jsonObject.getJSONObject("Result").getJSONArray("DISPATCH_CUSTOMER_LIST").getJSONObject(0),jsonObject.getJSONObject("Result").getJSONObject("DISPATCH_RESULT").getString("CUSTOMER_NAME"));
                        }else{
                            AddProjectActivity.this.finish();
                        }


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

    private void addProjectToOA(Map<String,Object> dic, JSONObject customer, String projectCustomer){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/Project/BankAddProject");
        requestParam.setMethod(HttpMethod.PostJson);
        requestParam.setPostJsonRequest(dic);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        showToast("立项成功");
                        AddProjectActivity.this.finish();
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

    private void getProperty(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Property/GetPropertyListByProjectId");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id+"");
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        property_list = jsonObject.getJSONArray("Result");

                        initPropertyList(property_list);
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
        Gson gson=new Gson();
        Type type = new TypeToken<List<PropertyEntity>>() {}.getType();
        mrv = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrv.setLayoutManager(layoutManager);
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mList = new ArrayList<PropertyEntity>();
        mList = gson.fromJson(array.toString(),type);
        adapter = new PropertyListAdapter(mList);
        adapter.setOnItemClickListener(new PropertyListAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("project_id",project_id);
                bundle.putString("type","edit");
                try {
                    bundle.putInt("property_id",property_list.getJSONObject(position).getInt("PROPERTY_ID"));
                    bundle.putString("property",property_list.getJSONObject(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(AddObjectActivity.class,bundle);
            }
        });
        mrv.setAdapter(adapter);


        adapter.notifyDataSetChanged();
    }

    private void getFormList(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/BankGetFormListByCompanyId");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("formType",OA_DYNAMIC_ID);
        requestMap.put("companyID",OA_COMPANY_ID);
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
                            }else if (dataArray.getJSONObject(i).getString("LABEL_NAME").equals("bcm_user_id")){
                                value = SharedPreferencesManage.getUserInfo().getUserId()+"";
                            }
                            JSONObject jsonObject1 = new JSONObject();
                            jsonObject1.put("LABEL_ID",dataArray.getJSONObject(i).getString("FORM_LABEL_ID"));
                            jsonObject1.put("VALUE",value);
                            formContentValue.put(jsonObject1);
                            if (i == 0){
                                getProject_type(dataArray.getJSONObject(i).getString("DATA_DIC_SUB"));
                            }else if (i ==1){
                                getLoan_type(dataArray.getJSONObject(i).getString("DATA_DIC_SUB"));
                            }
                        }
                        //createProjectView(dataArray);
                        initProject();
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

    private void initProject(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(BASE_URL+"/api/Project/MInitProject");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONObject jsondata = jsonObject.getJSONObject("Result");
                        project_id = jsondata.getInt("PROJECT_ID");
                        project = jsondata;
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

    public void getProject_type(String dic_sub){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/getSelectDicData?DATA_DIC_SUB="+dic_sub);
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONArray jsonarray = jsonObject.getJSONArray("Result");
                        for (int i = 0; i<jsonarray.length();i++){
                            project_types = insert(project_types,jsonarray.getJSONObject(i).getString("DIC_PAR_ID"));
                            project_type_chs = insert(project_type_chs,jsonarray.getJSONObject(i).getString("DIC_PAR_NAME"));
                        }
                        if (project_types.length>0){
                            project_type = project_types[0];
                        }

                        initSpinner();

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

    private static String[] insert(String[] arr, String str)
    {
        int size = arr.length;
        String[] tmp = new String[size + 1];
        System.arraycopy(arr, 0, tmp, 0, size);
        tmp[size] = str;
        return tmp;
    }

    public void initSpinner(){
        Spinner project_type_spinner = findViewById(R.id.project_type);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, project_type_chs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        project_type_spinner .setAdapter(adapter);
        project_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                project_type = project_types[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public void getLoan_type(String dic_sub){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/DynamicForm/getSelectDicData?DATA_DIC_SUB="+dic_sub);
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        JSONArray jsonarray = jsonObject.getJSONArray("Result");
                        for (int i = 0; i<jsonarray.length();i++){
                            loan_types = insert(loan_types,jsonarray.getJSONObject(i).getString("DIC_PAR_ID"));
                            loan_type_chs = insert(loan_type_chs,jsonarray.getJSONObject(i).getString("DIC_PAR_NAME"));
                        }
                        if (loan_types.length>0){
                            loan_type = loan_types[0];
                        }

                        initSpinner1();

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

    public void initSpinner1(){
        Spinner loan_type_spinner = findViewById(R.id.loan_type);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, loan_type_chs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loan_type_spinner .setAdapter(adapter);
        loan_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                loan_type = loan_types[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }
}
