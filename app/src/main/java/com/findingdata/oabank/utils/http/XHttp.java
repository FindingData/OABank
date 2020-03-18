package com.findingdata.oabank.utils.http;


import com.findingdata.oabank.base.Config;
import com.findingdata.oabank.utils.LogUtils;
import com.findingdata.oabank.utils.TokenUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import static com.findingdata.oabank.base.Config.BASE_URL;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: 通用请求封装
 */
public class XHttp {
    /**
     * 发送get请求
     * @param <T>
     */
    public static <T> Callback.Cancelable Get(String url, Map<String,String> map, Callback.CommonCallback<T> callback){
        LogUtil.d(url);
        RequestParams params=new RequestParams(url);
        StringBuilder sbCookie = new StringBuilder();
//        sbCookie.append(Config.COOKIE_NAME).append("=")
//                .append(TokenUtils.getToken()).append("; path=/; domain=")
//                .append(Config.BASE_URL);
        sbCookie.append(Config.COOKIE_NAME).append("=")
                .append(TokenUtils.getToken());
        params.addHeader("Cookie",sbCookie.toString());
        params.setUseCookie(false);
        LogUtils.d("Cookie",sbCookie);
        if(null!=map){
            for(Map.Entry<String, String> entry : map.entrySet()){
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().get(params, callback);
    }

    /**
     * 发送异步post请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(String url, Map<String, Object> map, Callback.CommonCallback<T> callback) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        if(!url.equals(BASE_URL+"/api/Home/Login")){
            StringBuilder sbCookie = new StringBuilder();
//            sbCookie.append(Config.COOKIE_NAME).append("=")
//                    .append(TokenUtils.getToken()).append("; path=/; domain=")
//                    .append(Config.BASE_URL);
            sbCookie.append(Config.COOKIE_NAME).append("=")
                    .append(TokenUtils.getToken());
            params.addHeader("Cookie",sbCookie.toString());
            params.setUseCookie(false);
            LogUtils.d("Cookie",sbCookie);
        }
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().post(params, callback);
    }

    /**
     * 发送异步post请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable PostJson(String url, Object object, Callback.CommonCallback<T> callback) {
        LogUtil.d(url);
        RequestParams params = new RequestParams(url);
        if(!url.equals(BASE_URL+"/api/Home/Login")){
            StringBuilder sbCookie = new StringBuilder();
//            sbCookie.append(Config.COOKIE_NAME).append("=")
//                    .append(TokenUtils.getToken()).append("; path=/; domain=")
//                    .append(Config.BASE_URL);
            sbCookie.append(Config.COOKIE_NAME).append("=")
                    .append(TokenUtils.getToken());
            params.addHeader("Cookie",sbCookie.toString());
            params.setUseCookie(false);
            LogUtils.d("Cookie",sbCookie);
        }
        if (null != object) {
            Gson gson=new Gson();
            LogUtil.d(gson.toJson(object));
            params.setAsJsonContent(true);
            params.setBodyContent(gson.toJson(object));

        }
        return x.http().post(params, callback);
    }

    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(String url, Map<String, Object> map, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        return x.http().post(params, callback);
    }

    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(String url, String filepath, Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        return x.http().get(params, callback);
    }

}
