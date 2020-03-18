package com.findingdata.oabank.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.findingdata.oabank.R;
import com.findingdata.oabank.base.BaseActivity;
import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.EventBusMessage;
import com.findingdata.oabank.entity.ProjectCenterListType;
import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.http.HttpMethod;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;
import com.findingdata.oabank.utils.http.RequestParam;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.findingdata.oabank.base.Config.BASE_URL;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.main_tabs_iv_todo)
    private ImageView iv_todo;
    @ViewInject(R.id.main_tabs_iv_doing)
    private ImageView iv_doing;
    @ViewInject(R.id.main_tabs_iv_pause)
    private ImageView iv_pause;
    @ViewInject(R.id.main_tabs_iv_stop)
    private ImageView iv_stop;
    @ViewInject(R.id.main_tabs_tv_todo)
    private TextView tab_todo;
    @ViewInject(R.id.main_tabs_tv_doing)
    private TextView tab_doing;
    @ViewInject(R.id.main_tabs_tv_pause)
    private TextView tab_pause;
    @ViewInject(R.id.main_tabs_tv_stop)
    private TextView tab_stop;
    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawerLayout;
    @ViewInject(R.id.main_iv_notify_dot)
    private ImageView main_iv_notify_dot;

    private Context context;
    private TextView[] tabs = new TextView[4];
    private FragmentManager fragmentManager;
    private Fragment todo, doing, pause, stop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context=this;
        fragmentManager = getSupportFragmentManager();
        //关闭手势滑动
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //替换NavigationView
        fragmentManager.beginTransaction().replace(R.id.nav_view,
                new NavigationViewFragment()).commitAllowingStateLoss();
        initTabs();
        setTabSelection(0);
        //注册监听
        EventBus.getDefault().register(this);
        //获取未读消息
        getUnReadMessage();
    }

    private void getUnReadMessage(){
        RequestParam requestParam=new RequestParam();
        requestParam.setUrl(BASE_URL+"/api/Message/GetUnReadCount");
        requestParam.setMethod(HttpMethod.Get);
        requestParam.setCallback(new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                LogUtils.d("result",result);
                BaseEntity<Integer> entity= JsonParse.parse(result,Integer.class);
                if(entity.isStatus()){
                    if(entity.getResult()>0){
                        main_iv_notify_dot.setVisibility(View.VISIBLE);
                    }else{
                        main_iv_notify_dot.setVisibility(View.GONE);
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

    private void initTabs() {
        tabs[0] = tab_todo;
        tabs[1] = tab_doing;
        tabs[2] = tab_pause;
        tabs[3] = tab_stop;
    }

    @Event({R.id.main_tabs_ll_todo,R.id.main_tabs_ll_doing,R.id.main_tabs_ll_pause,R.id.main_tabs_ll_stop,
            R.id.main_tv_search,R.id.main_btn_filter,R.id.main_btn_person,R.id.main_btn_notify,R.id.main_btn_add})
    private void onClickEvent(View v){
        switch (v.getId()){
            case R.id.main_tabs_ll_todo:
                setTabSelection(0);
                break;
            case R.id.main_tabs_ll_doing:
                setTabSelection(1);
                break;
            case R.id.main_tabs_ll_pause:
                setTabSelection(2);
                break;
            case R.id.main_tabs_ll_stop:
                setTabSelection(3);
                break;
            case R.id.main_tv_search:
                startActivity(ProjectSearchActivity.class);
                break;
            case R.id.main_btn_filter:
                drawerLayout.openDrawer(GravityCompat.END);

                break;
            case R.id.main_btn_person:
                startActivity(PersonActivity.class,Transition.LeftIn);
                break;
            case R.id.main_btn_notify:
                startActivity(NotifyActivity.class);
                break;
            case R.id.main_btn_add:
                startActivity(AddProjectActivity.class);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {

        for (int i = 0; i < tabs.length; i++) {
            if (i == index) {
                tabs[i].setTextColor(ContextCompat.getColor(context, R.color.primary));
            } else {
                tabs[i].setTextColor(ContextCompat.getColor(context, R.color.text_desc));
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (todo == null) {
                    todo = ProjectCenterListFragment.newInstance(ProjectCenterListType.Todo);
                    transaction.add(R.id.main_fl_content, todo);
                } else {
                    transaction.show(todo);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_press));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 1:
                if (doing == null) {
                    doing = ProjectCenterListFragment.newInstance(ProjectCenterListType.Doing);
                    transaction.add(R.id.main_fl_content, doing);
                } else {
                    transaction.show(doing);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_press));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 2:
                if (pause == null) {
                    pause = ProjectCenterListFragment.newInstance(ProjectCenterListType.Pause);
                    transaction.add(R.id.main_fl_content, pause);
                } else {
                    transaction.show(pause);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_press));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_normal));
                break;
            case 3:
                if (stop == null) {
                    stop = ProjectCenterListFragment.newInstance(ProjectCenterListType.Stop);
                    transaction.add(R.id.main_fl_content, stop);
                } else {
                    transaction.show(stop);
                }
                iv_todo.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_todo_normal));
                iv_doing.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_doing_normal));
                iv_pause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_pause_normal));
                iv_stop.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_action_stop_press));
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (todo != null) {
            transaction.hide(todo);
        }
        if (doing != null) {
            transaction.hide(doing);
        }
        if (pause != null) {
            transaction.hide(pause);
        }
        if (stop != null) {
            transaction.hide(stop);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(EventBusMessage message){
        if("NavClose".equals(message.getMessage())){
            drawerLayout.closeDrawers();
        }else if("UnReadMessage".equals(message.getMessage())){
            getUnReadMessage();
        }
    }
}
