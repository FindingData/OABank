package com.findingdata.oabank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

import static com.findingdata.oabank.base.Config.BASE_URL;

public class AddObjectActivity extends BaseActivity {

    private TextView save_object;
    private TextView delete_object;
    private String property_id;
    private JSONObject property;

    private EditText ed_property_name;
    private EditText ed_property_address;
    private EditText ed_property_area;
    private EditText ed_inspection_contact;
    private EditText ed_inspection_contact_phone;
    private EditText ed_property_owner;
    private EditText ed_property_owner_telephone;
    private EditText ed_property_owner_idcard;
    private EditText ed_property_owner_code;

    String pca_code;
    String property_type;

    String[] pca_codes = new String[0];
    String[] pca_codes_chs = new String[0];

    String[] property_types = new String[0];
    String[] property_types_chs = new String[0];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        save_object = findViewById(R.id.save_object);
        save_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = getIntent().getExtras().getString("type");
                if (type.equals("add")){
                    initObject();
                }else{
                    try {
                        addProperty(property.getString("PROPERTY_ID"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        delete_object = findViewById(R.id.delete_object);
        delete_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = getIntent().getExtras().getString("type");
                if (type.equals("add")){
                    AddObjectActivity.this.finish();
                }else{
                    deleteProperty();
                }
            }
        });

        ed_property_name = findViewById(R.id.property_name);
        ed_property_area = findViewById(R.id.property_area);
        ed_property_address = findViewById(R.id.property_address);
        ed_inspection_contact = findViewById(R.id.inspection_contact);
        ed_inspection_contact_phone = findViewById(R.id.inspection_contact_phone);

        ed_property_owner = findViewById(R.id.property_owner);
        ed_property_owner_code = findViewById(R.id.property_owner_code);
        ed_property_owner_idcard = findViewById(R.id.property_owner_idcard);
        ed_property_owner_telephone = findViewById(R.id.property_owner_telephone);

        String type = getIntent().getExtras().getString("type");
        if (type.equals("add")){

        }else {
            initProperty();
        }
        getPCA();
        getPropertys();
    }

    public void getPCA(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(BASE_URL+"/api/Common/GetSubPcas?pca_code=4301");
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
                            pca_codes = insert(pca_codes,jsonarray.getJSONObject(i).getString("VALUE"));

                            pca_codes_chs = insert(pca_codes_chs,jsonarray.getJSONObject(i).getString("NAME"));

                        }
                        if (pca_codes.length>0){
                            pca_code = pca_codes[0];
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
        Spinner pca_code_spinier = findViewById(R.id.property_pca);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, pca_codes_chs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pca_code_spinier .setAdapter(adapter);
        pca_code_spinier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
               pca_code = pca_codes[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }

    public void getPropertys(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(BASE_URL+"/api/Common/GetDictionaries?type_id=40002");
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
                            property_types = insert(property_types,jsonarray.getJSONObject(i).getString("VALUE"));

                            property_types_chs = insert(property_types_chs,jsonarray.getJSONObject(i).getString("NAME"));

                        }
                        if (property_types.length>0){
                            property_type = property_types[0];
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
        Spinner pca_code_spinier = findViewById(R.id.property_type);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, property_types_chs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pca_code_spinier .setAdapter(adapter);
        pca_code_spinier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                property_type = property_types[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    public void initProperty(){

        try {
            property = new JSONObject( getIntent().getExtras().getString("property"));
            ed_property_name.setText(property.getString("PROPERTY_NAME"));
            ed_property_address.setText(property.getString("ADDRESS"));
            ed_property_area.setText(property.getString("AREA"));
            ed_inspection_contact.setText(property.getString("INSPECTION_CONTACT"));
            ed_inspection_contact_phone.setText(property.getString("INSPECTION_CONTACT_PHONE"));
            ed_property_owner.setText(property.getJSONArray("PROPERTY_RIGHTS").getJSONObject(0).getString("PROPERTY_OWNER"));
            ed_property_owner_telephone.setText(property.getJSONArray("PROPERTY_RIGHTS").getJSONObject(0).getString("PROPERTY_OWNER_PHONE"));
            ed_property_owner_idcard.setText(property.getJSONArray("PROPERTY_RIGHTS").getJSONObject(0).getString("PROPERTY_OWNER_ID"));
            ed_property_owner_code.setText(property.getJSONArray("PROPERTY_RIGHTS").getJSONObject(0).getString("PROPERTY_CERTIFICATE"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void deleteProperty()  {
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(BASE_URL+"/api/Property/DeleteProperty");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        String property_id = "";
        try {
            property_id = property.getString("PROPERTY_ID");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestMap.put("property_id",property_id);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        AddObjectActivity.this.finish();
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

    public void initObject(){
        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        String project_id = getIntent().getExtras().getString("project_id");
        requestParam.setUrl(BASE_URL+"/api/Property/InitProjectProperty?project_id="+project_id);
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                    super.onSuccess(result);
                    LogUtils.d("result",result);
                    try {
                        JSONObject jsonObject=new JSONObject(result);
                        if (jsonObject.getBoolean("Status")){
                            property = jsonObject.getJSONObject("Result");
                            property_id = property.getString("PROPERTY_ID");
                            addProperty(property_id);
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

    public void addProperty(final String property_id){
        String property_name = ed_property_name.getText().toString();
        String property_address = ed_property_address.getText().toString();
        String property_area = ed_property_area.getText().toString();
        String inspection_contact = ed_inspection_contact.getText().toString();
        String inspection_contact_phone = ed_inspection_contact_phone.getText().toString();

        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        int project_id = getIntent().getExtras().getInt("project_id");
        requestParam.setUrl(BASE_URL+"/api/Property/UpdateProperty");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROPERTY_ID",property_id);
        requestMap.put("PROJECT_ID",project_id);
        requestMap.put("PROPERTY_NAME",property_name);
        requestMap.put("PROPERTY_TYPE",property_type);
        requestMap.put("PCA_CODE",pca_code);
        requestMap.put("ADDRESS",property_address);
        requestMap.put("INSPECTION_CONTACT",inspection_contact);
        requestMap.put("INSPECTION_CONTACT_PHONE",inspection_contact_phone);
        requestMap.put("AREA",property_area);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        String property_right_id = property.getJSONArray("PROPERTY_RIGHTS").getJSONObject(0).getString("PROPERTY_RIGHT_ID");
                        addPropertyRight(property_id,property_right_id);
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

    public void addPropertyRight(String property_id, String property_right_id){
        String property_owner = ed_property_owner.getText().toString();
        String property_owner_idcard = ed_property_owner_idcard.getText().toString();
        String property_owner_telephone = ed_property_owner_telephone.getText().toString();
        String property_owner_code = ed_property_owner_code.getText().toString();

        RequestParam requestParam=new RequestParam();
//        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectInfo");
        requestParam.setUrl(BASE_URL+"/api/property/updatePropertyRight");
        requestParam.setMethod(HttpMethod.Post);
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("PROPERTY_RIGHT_ID",property_right_id);
        requestMap.put("PROPERTY_ID",property_id);
        requestMap.put("PROPERTY_OWNER",property_owner);
        requestMap.put("PROPERTY_OWNER_PHONE",property_owner_telephone);
        requestMap.put("PROPERTY_OWNER_ID",property_owner_idcard);
        requestMap.put("PROPERTY_CERTIFICATE",property_owner_code);
        requestParam.setPostRequestMap(requestMap);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("Status")){
                        showToast("立项成功");
                       AddObjectActivity.this.finish();
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
