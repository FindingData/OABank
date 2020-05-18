package com.findingdata.oabank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.SearchEditAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.SearchEntity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.Utils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.findingdata.oabank.base.Config.OA_BASE_URL;

public class SearchEditActivity extends BaseActivity {

    private TextView tv_title;
    private EditText ed_search;
    private RecyclerView mrv;
    private SearchEditAdapter adapter;
    private TextView toolbar_btn_back;
    private List<SearchEntity> mList;
    private String type;
    private String purpose_id;
    private TextView sure_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_edit);
        tv_title = findViewById(R.id.toolbar_tv_title);

        toolbar_btn_back = findViewById(R.id.toolbar_btn_back);
        toolbar_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchEditActivity.this.finish();
            }
        });
        ed_search = findViewById(R.id.search_txt);
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (type.equals("1")){
                    searchContent();
                }else if (type.equals("2")){
                    searchContent2();
                }else if (type.equals("3")){
                    searchContent3();
                }

            }
        });

        mrv = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrv.setLayoutManager(layoutManager);
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mList = new ArrayList<SearchEntity>();
        adapter = new SearchEditAdapter(mList);
        adapter.setOnItemClickListener(new SearchEditAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                if (type.equals("1")){
                    Intent intent = new Intent();
                    intent.putExtra("type","1");
                    intent.putExtra("construction_name",mList.get(position).getCONSTRUCTION_NAME());
                    intent.putExtra("construction_code",mList.get(position).getCONSTRUCTION_CODE());
                    setResult(RESULT_OK,intent);
                    finish();
                }else if (type.equals("2")){
                    Intent intent = new Intent();
                    intent.putExtra("type","2");
                    intent.putExtra("building_name",mList.get(position).getBUILDING_NAME());
                    intent.putExtra("building_code",mList.get(position).getBUILDING_CODE());
                    setResult(RESULT_OK,intent);
                    finish();
                }else if (type.equals("3")){
                    Intent intent = new Intent();
                    intent.putExtra("type","3");
                    intent.putExtra("house_code",mList.get(position).getHOUSE_CODE());
                    intent.putExtra("house_name",mList.get(position).getHOUSE_NAME());
                    intent.putExtra("building_area",mList.get(position).getBUILDING_AREA());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });

        mrv.setAdapter(adapter);
        type = getIntent().getExtras().getString("type");
        if (type.equals("1")){
            tv_title.setText("楼盘检索");
        }else if (type.equals("2")){
            tv_title.setText("楼栋检索");
        }else if (type.equals("3")){
            tv_title.setText("户检索");
        }
        purpose_id = getIntent().getExtras().getString("purpose_id");

        sure_btn = findViewById(R.id.sure_btn);
        sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("1")){
                    Intent intent = new Intent();
                    intent.putExtra("type","1");
                    intent.putExtra("construction_name",ed_search.getText().toString());
                    intent.putExtra("construction_code","");
                    setResult(RESULT_OK,intent);
                    finish();
                }else if (type.equals("2")){
                    Intent intent = new Intent();
                    intent.putExtra("type","2");
                    intent.putExtra("building_name",ed_search.getText().toString());
                    intent.putExtra("building_code","");
                    setResult(RESULT_OK,intent);
                    finish();
                }else if (type.equals("3")){
                    Intent intent = new Intent();
                    intent.putExtra("type","3");
                    intent.putExtra("house_code","");
                    intent.putExtra("house_name",ed_search.getText().toString());
                    intent.putExtra("building_area","");
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    public void searchContent(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/api/BaseData/GetConstructions");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("customer_id","");
        requestMap.put("new_purpose_id",purpose_id);
        requestMap.put("pca_code","430100");
        requestMap.put("keyword",ed_search.getText().toString());
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
                        mList.clear();
                        for (int i = 0; i<jsonarray.length();i++){
                            SearchEntity entity = new SearchEntity();
                            entity.setType("1");
                            entity.setPROMOTION_NAME1(jsonarray.getJSONObject(i).getString("PROMOTION_NAME1"));
                            entity.setCONSTRUCTION_NAME(jsonarray.getJSONObject(i).getString("CONSTRUCTION_NAME"));
                            entity.setCONSTRUCTION_CODE(jsonarray.getJSONObject(i).getString("CONSTRUCTION_CODE"));
                            mList.add(entity);
                        }
                        adapter.notifyDataSetChanged();

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
        sendRequest(requestParam,false);
    }

    public void searchContent2(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/api/BaseData/GetBuildings");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("customer_id","");
        requestMap.put("new_purpose_id",purpose_id);
        requestMap.put("pca_code","430100");
        requestMap.put("keyword",ed_search.getText().toString());
        String construction_code = getIntent().getExtras().getString("construction_code");
        requestMap.put("construction_code",construction_code);
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
                        mList.clear();
                        for (int i = 0; i<jsonarray.length();i++){
                            SearchEntity entity = new SearchEntity();
                            entity.setType("2");
                            entity.setBUILDING_NAME(jsonarray.getJSONObject(i).getString("BUILDING_NAME"));
                            entity.setBUILDING_CODE(jsonarray.getJSONObject(i).getString("BUILDING_CODE"));

                            mList.add(entity);
                        }
                        adapter.notifyDataSetChanged();

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
        sendRequest(requestParam,false);
    }

    public void searchContent3(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(OA_BASE_URL+"/api/BaseData/GetHouses");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("customer_id","");
        requestMap.put("new_purpose_id",purpose_id);
        requestMap.put("pca_code","430100");
        requestMap.put("keyword",ed_search.getText().toString());
        String building_code = getIntent().getExtras().getString("building_code");
        requestMap.put("building_code",building_code);
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
                        mList.clear();
                        for (int i = 0; i<jsonarray.length();i++){
                            SearchEntity entity = new SearchEntity();
                            entity.setType("3");
                            entity.setHOUSE_CODE(jsonarray.getJSONObject(i).getString("HOUSE_CODE"));
                            entity.setHOUSE_NAME(jsonarray.getJSONObject(i).getString("HOUSE_NAME"));
                            entity.setBUILDING_AREA(Utils.dealwithNull(jsonarray.getJSONObject(i).getString("BUILD_AREA")));
                            mList.add(entity);
                        }
                        adapter.notifyDataSetChanged();

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
        sendRequest(requestParam,false);
    }
}
