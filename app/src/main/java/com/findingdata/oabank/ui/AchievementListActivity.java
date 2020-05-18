package com.findingdata.oabank.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.AchievementAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.base.Config;
import com.findingdata.oabank.entity.AchievementEntity;
import com.findingdata.oabank.utils.FilePathUtil;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.TokenUtils;
import com.findingdata.oabank.utils.Utils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.findingdata.oabank.base.Config.BASE_URL;
import static com.findingdata.oabank.base.Config.SD_APP_DIR_NAME;

public class AchievementListActivity extends BaseActivity {
    private int project_id;
    private RecyclerView mrv;
    private AchievementAdapter adapter;
    private TextView toolbar_btn_back;
    private List<AchievementEntity> mList;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);
        mrv = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mrv.setLayoutManager(layoutManager);
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        mList = new ArrayList<AchievementEntity>();
        adapter = new AchievementAdapter(mList);
        adapter.setOnItemClickListener(new AchievementAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                Bundle bundle=new Bundle();
                String url = BASE_URL+"/api/project/GetFile?file_id="+(int)Float.parseFloat(mList.get(position).getFILE_ID());
                bundle.putString("web_url",url);
//                startActivity(AchievementDetailActivity.class,bundle);
                downLoad(url,mList.get(position).getFILE_NAME());
            }
        });

        mrv.setAdapter(adapter);

        toolbar_btn_back = findViewById(R.id.toolbar_btn_back);
        toolbar_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AchievementListActivity.this.finish();
            }
        });
        project_id = getIntent().getExtras().getInt("project_id");
        getAchievementList();
    }

    private void downLoad(String url,String filename) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mProgressDialog = new ProgressDialog(AchievementListActivity.this);
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/OA_BANK/"+filename;
            String path = FilePathUtil.createPathIfNotExist("/" + SD_APP_DIR_NAME + "/" + "Doc/")+filename;
            // mDownloadUrl为JSON从服务器端解析出来的下载地址
            RequestParams requestParams = new RequestParams(url);
            // 为RequestParams设置文件下载后的保存路径
            requestParams.setSaveFilePath(path);
            StringBuilder sbCookie = new StringBuilder();
            sbCookie.append(Config.COOKIE_NAME).append("=")
                    .append(TokenUtils.getToken());
            requestParams.addHeader("Cookie",sbCookie.toString());
            requestParams.setUseCookie(false);
            // 下载完成后自动为文件命名
            requestParams.setAutoRename(true);

            x.http().get(requestParams, new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {

                    mProgressDialog.dismiss();
                    openFile(AchievementListActivity.this, result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                    mProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                    mProgressDialog.dismiss();
                }

                @Override
                public void onFinished() {

                    mProgressDialog.dismiss();
                }

                @Override
                public void onWaiting() {
                    // 网络请求开始的时候调用

                }

                @Override
                public void onStarted() {
                    // 下载的时候不断回调的方法

                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {
                    // 当前的下载进度和文件总大小

                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setMessage("正在下载中......");
                    mProgressDialog.show();
                    mProgressDialog.setMax((int) total);
                    mProgressDialog.setProgress((int) current);
                }
            });
        }
    }

    private static void openFile(Context context, File f) {
        Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        myIntent.setAction(Intent.ACTION_VIEW);//动作，查看
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(f).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        myIntent.setDataAndType(Uri.fromFile(f),mimetype);
        context.startActivity(myIntent);

    }


    private void getAchievementList(){
        mList.clear();

        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetProjectBusiness?project_id="+project_id);
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
                        LogUtil.d("成果列表"+jsonobj.toString());
                        JSONArray jsonArray = jsonobj.getJSONArray("Result");
                        for (int i = 0; i<jsonArray.length(); i++){
                            AchievementEntity customerEntity = new AchievementEntity();
                            customerEntity.setFILE_NAME(Utils.dealwithNull(jsonArray.getJSONObject(i).getString("FILE_NAME")));
                            customerEntity.setFILE_ID(Utils.dealwithNull(jsonArray.getJSONObject(i).getString("FILE_ID")));
                            customerEntity.setBUSINESS_TYPE_CHS(Utils.dealwithNull(jsonArray.getJSONObject(i).getString("BUSINESS_TYPE_CHS")));
                            customerEntity.setTOTAL_PRICE(Utils.dealwithNull(jsonArray.getJSONObject(i).getString("TOTAL_PRICE")));
                            customerEntity.setCOMPLETED_TIME(Utils.dealwithNull(jsonArray.getJSONObject(i).getString("COMPLETED_TIME")));
                            if (customerEntity.getFILE_ID()!= null && !customerEntity.getFILE_ID().equals("")){
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
}
