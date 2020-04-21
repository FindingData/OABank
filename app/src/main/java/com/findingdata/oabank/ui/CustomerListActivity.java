package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.CustomerListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.CustomerEntity;
import com.findingdata.oabank.entity.ProjectBusinessEntity;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.OA_BASE_URL;

@ContentView(R.layout.activity_customer_list)
public class CustomerListActivity extends BaseActivity {

    private RecyclerView mrv;

    private int project_id;
    private String[] business_commissionid;
    private List<CustomerEntity> mList;

    private CustomerListAdapter adapter;
    private ProjectEntity project;

    private int position;

    @ViewInject(R.id.submit)
    private TextView submit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        project_id=getIntent().getExtras().getInt("project_id");
        project = (ProjectEntity) getIntent().getExtras().get("project");

        mrv = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrv.setLayoutManager(layoutManager);
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mList = new ArrayList<CustomerEntity>();
        adapter = new CustomerListAdapter(mList);
        mrv.setAdapter(adapter);

        getBusinessList();
        //adapter = new CustomerListAdapter();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                manualDipatch();
            }
        });
    }

    private void addProjectToOA(Map<String,Object> projectMap){

        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(OA_BASE_URL+"/Project/BankAddProject");
        requestParam.setMethod(HttpMethod.PostJson);
//        Map<String,Object> requestMap=new HashMap<>();
//        requestMap.put("ProjectModel",projectMap);
        requestParam.setPostJsonRequest(projectMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                //BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    if(jsonobj.getBoolean("Status")){
                        LogUtil.d("增加至OA成功"+jsonobj.toString());
                        //add project to oa
                        showToast("派单成功");
                        CustomerListActivity.this.finish();

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

    private void manualDipatch(){

        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/ManualDipatch");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id);
        int[] customer_list = {mList.get(position).getCUSTOMER_ID()};
        requestMap.put("customer_list",customer_list);
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
                        LogUtil.d("派单成功"+jsonobj.toString());
                        //add project to oa
                        Map<String,Object> projectMap = new HashMap<>();
                        projectMap.put("PROJECT_NAME",project.getPROJECT_NAME());
                        projectMap.put("PRIORITY_LEVEL",40052001);
                        projectMap.put("PROJECT_TYPE","");
                        projectMap.put("APPRAISAL_PURPOSE",40005001);
                        projectMap.put("IS_FOLLOW",0);
                        projectMap.put("IS_REMINDER",0);
                        projectMap.put("DUE_DATE","");
                        projectMap.put("IS_APPROVED",0);
                        projectMap.put("BUSINESS_FORM_ID",project.getPROJECT_FORM_ID());
                        projectMap.put("PROPERTY_LIST",project.getPROPERTY_LIST());
                        projectMap.put("BUSINESS_LIST",project.getBUSINESS_LIST());
                        projectMap.put("REQUIRE_ESTIMATE",0);
                        projectMap.put("REQUIRED_AUDIT",0);
                        projectMap.put("CRM_CUSTOMER_ID","");
                        projectMap.put("CRM_CUSTOMER_NAME","");
                        projectMap.put("PROJECT_CODE","");
                        projectMap.put("CUSTOMER_ID",mList.get(position).getCUSTOMER_ID());
                        projectMap.put("BANK_PROJECT_ID",project.getPROJECT_ID());
                        addProjectToOA(projectMap);
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

    private void getCustomerList(){
        mList.clear();

        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Stats/GetRefCustomerStats");
        requestParam.setMethod(HttpMethod.Get);
        requestParam.setGetRequestMap(null);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                //BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    if(jsonobj.getBoolean("Status")){
                        LogUtil.d("评估公司列表"+jsonobj.toString());
                        JSONArray jsonArray = jsonobj.getJSONArray("Result");
                        for (int i = 0; i<jsonArray.length(); i++){
                            if(Arrays.asList(business_commissionid).contains(jsonArray.getJSONObject(i).getString("CUSTOMER_ID"))){
                                CustomerEntity customerEntity = new CustomerEntity();
                                customerEntity.setCUSTOMER_ID(jsonArray.getJSONObject(i).getInt("CUSTOMER_ID"));
                                customerEntity.setCUSTOMER_NAME(jsonArray.getJSONObject(i).getString("CUSTOMER_NAME"));
                                customerEntity.setBUSINESS_CONTACT(jsonArray.getJSONObject(i).getString("BUSINESS_CONTACT"));
                                customerEntity.setPHONE(jsonArray.getJSONObject(i).getString("PHONE"));
                                customerEntity.setPROCESSING(jsonArray.getJSONObject(i).getInt("PROCESSING"));
                                customerEntity.setTOTAL(jsonArray.getJSONObject(i).getInt("TOTAL"));
                                mList.add(customerEntity);
                            }
                        }

                        adapter.notifyDataSetChanged();

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

    private void getBusinessList(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/GetProjectBusiness");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id);
        requestParam.setGetRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                //BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                try {
                    JSONObject jsonobj = new JSONObject(result);
                    if(jsonobj.getBoolean("Status")){
                        LogUtil.d("事项列表"+jsonobj.toString());
                        Gson gson=new Gson();
                        Type type = new TypeToken<List<ProjectBusinessEntity>>() {}.getType();
                        List<ProjectBusinessEntity> business_list = gson.fromJson(jsonobj.getJSONArray("Result").toString(),type);
                        project.setBUSINESS_LIST(business_list);
                        JSONArray jsonArray = jsonobj.getJSONArray("Result");
                        business_commissionid = new String[jsonArray.length()];
                        for (int i = 0; i<jsonArray.length(); i++){
                            business_commissionid[i] = jsonArray.getJSONObject(i).getString("COMMISSIONED_ID");
                        }
                        getCustomerList();
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
