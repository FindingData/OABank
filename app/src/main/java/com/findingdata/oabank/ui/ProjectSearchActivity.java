package com.findingdata.oabank.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.ProjectListAdapter;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.ProjectEntity;
import com.findingdata.oabank.entity.ProjectListEntity;
import com.findingdata.oabank.entity.QueryItem;
import com.findingdata.oabank.utils.KeyBoardUtils;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.findingdata.oabank.weidgt.RecyclerViewDivider;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.findingdata.oabank.base.Config.BASE_URL;

/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 项目查询Activity
 */
@ContentView(R.layout.activity_project_search)
public class ProjectSearchActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @ViewInject(R.id.toolbar_tv_title)
    private TextView toolbar_title;
    @ViewInject(R.id.project_search_et)
    private EditText project_search_et;
    @ViewInject(R.id.project_search_srl_fresh)
    private SwipeRefreshLayout msf;
    @ViewInject(R.id.project_search_rl_list)
    private RecyclerView mrv;

    private int pageIndex = 1;
    private int pageSize = 10;
    private List<ProjectEntity> dataList=new ArrayList<>();
    private ProjectListAdapter adapter;
    private int loadStatus=2;//1为刷新，2为加载更多

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar_title.setText("项目搜索");
            project_search_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) project_search_et.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(ProjectSearchActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    KeyBoardUtils.hideSoftInputMode(ProjectSearchActivity.this,getWindow().peekDecorView());
                    // 搜索，进行自己要的操作...
                    showToast(project_search_et.getText().toString());
                    return true;
                }
                return false;
            }
        });
        //注册下拉刷新
        msf.setOnRefreshListener(this);
        //设置圈圈的颜色
        msf.setColorSchemeColors(Color.rgb(47, 223, 189));

        mrv.setLayoutManager(new LinearLayoutManager(this));
        mrv.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.HORIZONTAL));
        //注册RecyclerView点击
        mrv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putInt("project_id",dataList.get(position).getPROJECT_ID());
                startActivity(ProjectDetailActivity.class,bundle);
            }
        });

        //初始化adapter
        adapter = new ProjectListAdapter(R.layout.item_project_list, dataList);
        adapter.setOnLoadMoreListener(this,mrv);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.setLoadMoreView(new SimpleLoadMoreView());

        mrv.setAdapter(adapter);
        getData();
    }

    /**
     * 获取项目列表
     */
    private void getData(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/ProjectList");
        requestParam.setMethod(HttpMethod.PostJson);
        Map<String,Object> param=new HashMap<>();
        param.put("page_num",pageIndex);
        param.put("page_size",pageSize);
        List<QueryItem> queryItemList=new ArrayList<>();
        List<String> _value=new ArrayList<>();
        _value.add(project_search_et.getText().toString());
        queryItemList.add(new QueryItem("1005",_value));
        param.put("query_item_list",queryItemList);
        requestParam.setPostJsonRequest(param);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<ProjectListEntity> entity= JsonParse.parse(result, ProjectListEntity.class);
                if(entity.isStatus()){
                    if(loadStatus==1){
                        dataList.clear();
                    }
                    dataList.addAll(entity.getResult().getData());
                    adapter.notifyDataSetChanged();
                    if(loadStatus==1){
                        msf.setRefreshing(false);
                        adapter.setEnableLoadMore(true);
                    }else{
                        if(pageIndex*pageSize>=entity.getResult().getData_count()){
                            adapter.loadMoreEnd();
                        }else{
                            adapter.loadMoreComplete();
                        }
                    }
                }else{
                    if(loadStatus==1){
                        msf.setRefreshing(false);
                        adapter.setEnableLoadMore(true);
                    }else{
                        pageIndex--;
                        adapter.loadMoreFail();
                    }
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

    @Event({R.id.toolbar_btn_back,R.id.project_search_btn_submit})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.toolbar_btn_back:
                finish();
                break;
            case R.id.project_search_btn_submit:
                KeyBoardUtils.hideSoftInputMode(this, getWindow().peekDecorView());
                onRefresh();
                break;
        }
    }

    @Override
    public void onRefresh() {
        loadStatus=1;
        pageIndex = 1;
        adapter.setEnableLoadMore(false);
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadStatus=2;
        pageIndex++;
        getData();
    }
}
