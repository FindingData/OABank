package com.findingdata.oabank.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.findingdata.oabank.R;
import com.findingdata.oabank.adapter.FilterListAdapter;
import com.findingdata.oabank.base.BaseFragment;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.FilterEntity;
import com.findingdata.oabank.entity.FilterItemEntity;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.SharedPreferencesManage;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.findingdata.oabank.base.Config.BASE_URL;


/**
 * Created by Loong on 2019/11/25.
 * Version: 1.0
 * Describe: 自定义NavigationView
 */
@ContentView(R.layout.navigation_view)
public class NavigationViewFragment extends BaseFragment {
    @ViewInject(R.id.nav_ll_filter_view)
    private LinearLayout nav_ll_filter_view;

    private List<FilterListAdapter> adaptersList=new ArrayList<>();
    private List<FilterEntity> listData;
    private Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getQueryData();

    }

    //加载配置的查询条件
    private void getQueryData(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Project/GetQueryItems");
        requestParam.setMethod(HttpMethod.Get);
        Map<String,String> map=new HashMap<>();
        map.put("RESOURCEID", "9001001");
        requestParam.setGetRequestMap(map);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<String> entity= JsonParse.parse(result,String.class);
                if(entity.isStatus()){
                    try {
                        JSONObject jsonObject=new JSONObject(entity.getResult());
                        Gson gson=new Gson();
                        Type type = new TypeToken<List<FilterEntity>>() {}.getType();
                        listData=gson.fromJson(jsonObject.getString("filters"),type);
                        List<FilterValueEntity> filterValue=new ArrayList<>();
                        for (int i = 0; i < listData.size(); i++) {
                            listData.get(i).getFilterItems().get(0).setCheck(true);
                            filterValue.add(new FilterValueEntity(listData.get(i).getFilterKey(),listData.get(i).getFilterItems().get(0).getKey()));
                        }
                        SharedPreferencesManage.setFilterValueEntity(filterValue);
                        initView();
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        });
        sendRequest(requestParam,false);

    }
    //初始化视图
    private void initView(){
        for (int i = 0; i <listData.size() ; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin=50;
            TextView tv=new TextView(context);
            tv.setText(listData.get(i).getFilterName());
            tv.setTextColor(getResources().getColor(R.color.text_primary));
            tv.setTextSize(16);
            tv.setLayoutParams(lp);
            nav_ll_filter_view.addView(tv);
            RecyclerView rv=new RecyclerView(context);
            rv.setBackgroundColor(getResources().getColor(R.color.body_background));
            rv.setLayoutManager(new GridLayoutManager(context,3));
            //注册RecyclerView点击
            final int _i = i;
            rv.addOnItemTouchListener(new OnItemClickListener() {
                @Override
                public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                    refreshData(_i,position);
                }
            });
            FilterListAdapter adapter=new FilterListAdapter(context,R.layout.item_filter_list,listData.get(i).getFilterItems());
            adaptersList.add(adapter);
            rv.setAdapter(adapter);
            LinearLayout.LayoutParams _lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            _lp.topMargin=20;
            rv.setLayoutParams(_lp);
            nav_ll_filter_view.addView(rv);
        }

    }
    //刷新项目过滤
    private void refreshData(int type,int position){
        LogUtil.d(type+"_"+position);
        for (int i = 0; i <listData.size() ; i++) {
            if(type==i){
               List<FilterItemEntity> filter= listData.get(i).getFilterItems();
                for (int j = 0; j < filter.size(); j++) {
                    if(position==j){
                        filter.get(j).setCheck(true);
                    }else{
                        filter.get(j).setCheck(false);
                    }
                }
                adaptersList.get(type).notifyDataSetChanged();
            }
        }
    }
    //获取项目过滤选择的值
    private List<FilterValueEntity> getFilterValueEntity(){
        List<FilterValueEntity> list=new ArrayList<>();
        for (int i = 0; i <listData.size() ; i++) {
            FilterValueEntity filterValueEntity=new FilterValueEntity();
            filterValueEntity.setKey(listData.get(i).getFilterKey());
            List<FilterItemEntity> filter= listData.get(i).getFilterItems();
            for (int j = 0; j < filter.size(); j++) {
                if(filter.get(j).isCheck()){
                    filterValueEntity.setValue(filter.get(j).getKey());
                }
            }
            list.add(filterValueEntity);
        }
        return list;
    }
    //还原项目过滤
    private void resetData(){
        List<FilterValueEntity> list=SharedPreferencesManage.getFilterValueEntity();
        for (int i = 0; i <listData.size() ; i++) {
            for (int j = 0; j < list.size(); j++) {
                if(listData.get(i).getFilterKey().equals(list.get(j).getKey())){
                    List<FilterItemEntity> filter= listData.get(i).getFilterItems();
                    for (int k = 0; k < filter.size(); k++) {
                        if(filter.get(k).getKey().equals(list.get(j).getValue())){
                            filter.get(k).setCheck(true);
                        }else{
                            filter.get(k).setCheck(false);
                        }
                    }
                }
            }
            adaptersList.get(i).notifyDataSetChanged();
        }
    }

    @Event({R.id.nav_tv_cancel,R.id.nav_tv_submit})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.nav_tv_cancel:
                //通知Main页面关闭项目过滤抽屉
                EventBus.getDefault().post(new EventBusMessage<>("NavClose"));
                resetData();
                break;
            case R.id.nav_tv_submit:
                //通知Main页面关闭项目过滤抽屉
                EventBus.getDefault().post(new EventBusMessage<>("NavClose"));
                //存储项目过滤的值
                SharedPreferencesManage.setFilterValueEntity(getFilterValueEntity());
                //通知列表页面刷新
                EventBus.getDefault().post(new EventBusMessage<>("ProjectFilter"));
                break;
        }
    }
}
