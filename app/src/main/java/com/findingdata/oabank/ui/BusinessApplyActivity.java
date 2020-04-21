package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.Utils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.Map;

import static com.findingdata.oabank.base.Config.BASE_URL;

@ContentView(R.layout.activity_business_apply)
public class BusinessApplyActivity extends BaseActivity {

    @ViewInject(R.id.business_bg)
    private TextView tx_business_bg;
    @ViewInject(R.id.borrower)
    private TextView tx_borrower;
    @ViewInject(R.id.borrower_telephone)
    private TextView tx_borrower_telephone;
    @ViewInject(R.id.client)
    private TextView tx_client;
    @ViewInject(R.id.client_telephone)
    private TextView tx_client_telephone;
    @ViewInject(R.id.object)
    private TextView tx_object;

    @ViewInject(R.id.property_list)
    private LinearLayout ll_property_list;
    @ViewInject(R.id.submit)
    private TextView submit;

    private int project_id;

    private JSONObject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        project_id=getIntent().getExtras().getInt("project_id");
        getProjectInfo();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyProject();
            }
        });
    }

    private void getProjectInfo(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/GetProjectInfo");
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
                        LogUtil.d("申请报告界面详情"+jsonobj.toString());
                        project = jsonobj.getJSONObject("Result");
                        initView(jsonobj.getJSONObject("Result"));
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

    private void initView(JSONObject jsonobj){
        try{
            tx_business_bg.setText("报告");
            String borrower = jsonobj.getString("BORROWER");
            tx_borrower.setText(Utils.dealwithNull(borrower));
            String borrower_telephone = jsonobj.getString("BORROWER_PHONE");
            tx_borrower_telephone.setText(Utils.dealwithNull(borrower_telephone));
            String client = jsonobj.getString("CONTACT_PERSON");
            tx_client.setText(Utils.dealwithNull(client));
            String client_telephone = jsonobj.getString("CONTACT_PHONE");
            tx_client_telephone.setText(Utils.dealwithNull(client_telephone));

            JSONArray property_list = jsonobj.getJSONArray("PROPERTY_LIST");
            for (int i = 0; i<property_list.length(); i++){
                String property_name = property_list.getJSONObject(i).getString("PROPERTY_NAME");
                TextView tx_property_name = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                tx_property_name.setTop(10);
                tx_property_name.setGravity(Gravity.LEFT);
                tx_property_name.setText("抵押物："+property_name);
                tx_property_name.setLayoutParams(layoutParams);
                JSONArray property_prices = property_list.getJSONObject(i).getJSONArray("PROPERTY_PRICES");
                for (int j = 0; j<property_prices.length(); j++){
                    if (property_prices.getJSONObject(j).getString("PRICE_TYPE").equals("40004002")){
                        String property_area = "面积："+property_prices.getJSONObject(j).getString("AREA");
                        String property_total_price = "总价：" + property_prices.getJSONObject(j).getString("TOTAL_PRICE");
                        TextView tx_property_price = new TextView(this);
                        tx_property_price.setGravity(Gravity.LEFT);
                        tx_property_price.setTop(10);
                        tx_property_price.setText(property_area+"  "+property_total_price);
                        tx_property_price.setLayoutParams(layoutParams);
                    }
                }
            }

        }catch (Exception exp){
            exp.printStackTrace();
        }

    }

    private void applyProject(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/project/ApplyProjectBusiness");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("project_id",project_id);
        int[] arr = {40004003};
        requestMap.put("business_list",arr);
        try{
            requestMap.put("commissioned_id",project.getJSONObject("BUSINESS").getString("COMMISSIONED_ID"));
        }catch (Exception ex){
            ex.printStackTrace();
        }

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
                        LogUtil.d("申请报告成功"+jsonobj.toString());
                        finish();
                    }else{
                        showToast("申请失败");
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
