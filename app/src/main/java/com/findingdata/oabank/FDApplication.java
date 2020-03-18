package com.findingdata.oabank;

import android.app.Application;
import android.content.Context;

import com.findingdata.oabank.entity.Transition;
import com.findingdata.oabank.receiver.DemoPushService;
import com.findingdata.oabank.receiver.GeTuiIntentService;
import com.igexin.sdk.PushManager;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengx on 2019/11/14.
 * Describe:
 */
public class FDApplication extends Application {

    public static FDApplication getInstance = null;
    private Context mContext;

    public static Map<Class, Transition> activityTrans=new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance = this;
        mContext = getApplicationContext();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        //蒲公英crash收集注册
        PgyCrashManager.register();
        //初始化个推推送
        com.igexin.sdk.PushManager.getInstance().initialize(getApplicationContext(), DemoPushService.class);
        //注册个推推送服务事件接收类
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), GeTuiIntentService.class);
    }


    public static FDApplication getInstance() {
        return getInstance;
    }

    public static Context getAppContext() {
        return FDApplication.getInstance.mContext;
    }


}
