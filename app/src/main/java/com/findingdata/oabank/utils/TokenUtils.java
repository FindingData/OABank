package com.findingdata.oabank.utils;

import com.findingdata.oabank.entity.BaseEntity;
import com.findingdata.oabank.entity.LoginEntity;
import com.findingdata.oabank.entity.TokenEntity;
import com.findingdata.oabank.utils.http.JsonParse;
import com.findingdata.oabank.utils.http.MyCallBack;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Date;

import static com.findingdata.oabank.base.Config.BASE_URL;

/**
 * Created by Loong on 2019/12/18.
 * Version: 1.0
 * Describe: Token维护工具类
 */
public class TokenUtils {
    /**
     * 判断token是否有效
     * @return
     */
    public static boolean isTokenValid(){
        TokenEntity tokenEntity = SharedPreferencesManage.getToken();
        if(tokenEntity!=null){
            return tokenEntity.getExpireTime()>new Date().getTime();

        }
        return false;
    }

    /**
     * 判断缓存的用户登录信息
     * @return
     */
    public static boolean isLoginInfoExist(){
        LoginEntity loginEntity=SharedPreferencesManage.getLoginInfo();
        if(loginEntity!=null){
            return !"".equals(loginEntity.getUserName())&&!"".equals(loginEntity.getPassword());
        }
        return false;
    }

    /**
     * 刷新token
     */
    public static boolean refreshToken() throws Throwable{
        LoginEntity loginEntity=SharedPreferencesManage.getLoginInfo();
        RequestParams params = new RequestParams(BASE_URL+"/api/Home/Login");
        params.addParameter("USER_NAME",loginEntity.getUserName());
        params.addBodyParameter("PASSWORD",loginEntity.getPassword());
        String result= x.http().postSync(params, String.class);
        BaseEntity<String> entity= JsonParse.parse(result,String.class);
        if(entity.isStatus()){
            SharedPreferencesManage.setToken(new TokenEntity(entity.getResult(),new Date().getTime()+10*60*1000));
            return true;
        }
        return false;
    }

    /**
     * 刷新token
     */
    public static void refreshToken(final RefreshTokenListener listener){
        LoginEntity loginEntity=SharedPreferencesManage.getLoginInfo();
        final RequestParams params = new RequestParams(BASE_URL+"/api/Home/Login");
        params.addParameter("USER_NAME",loginEntity.getUserName());
        params.addBodyParameter("PASSWORD",loginEntity.getPassword());
        x.http().post(params,new MyCallBack<String>(){
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                BaseEntity<String> entity= JsonParse.parse(result,String.class);
                if(entity.isStatus()){
                    SharedPreferencesManage.setToken(new TokenEntity(entity.getResult(),new Date().getTime()+10*60*1000));
                    listener.success();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                listener.fail(ex.getMessage());
            }
        });
    }

    /**
     * 获取token值
     * @return
     */
    public static String getToken(){
        if(SharedPreferencesManage.getToken()!=null)
            return SharedPreferencesManage.getToken().getToken();
        return "";
    }

    public interface RefreshTokenListener{
        void success();
        void fail(String errMsg);
    }

}
