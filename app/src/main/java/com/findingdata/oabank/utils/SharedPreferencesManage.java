package com.findingdata.oabank.utils;

import com.findingdata.oabank.FDApplication;
import com.findingdata.oabank.entity.FilterValueEntity;
import com.findingdata.oabank.entity.LoginEntity;
import com.findingdata.oabank.entity.TokenEntity;
import com.findingdata.oabank.entity.UserInfo;

import java.util.List;

/**
 * Created by Loong on 2019/11/20.
 * Version: 1.0
 * Describe: SharedPreferences管理类
 */
public class SharedPreferencesManage {
    public static void setLoginInfo(LoginEntity loginInfo){
        SharedPreferencesUtil.getInstance().saveObject(FDApplication.getAppContext(),"fd","loginInfo",loginInfo);
    }

    public static LoginEntity getLoginInfo(){
        return (LoginEntity) SharedPreferencesUtil.getInstance().getObject(FDApplication.getAppContext(),"fd","loginInfo");
    }

    public static void setToken(TokenEntity token){
        SharedPreferencesUtil.getInstance().saveObject(FDApplication.getAppContext(),"fd","Token",token);
    }
    public static TokenEntity getToken(){
        return (TokenEntity) SharedPreferencesUtil.getInstance().getObject(FDApplication.getAppContext(),"fd","Token");
    }


    public static void setFilterValueEntity(List<FilterValueEntity> list){
        SharedPreferencesUtil.getInstance().saveObject(FDApplication.getAppContext(),"fd","FilterValue",list);
    }
    public static List<FilterValueEntity> getFilterValueEntity(){
        return (List<FilterValueEntity>) SharedPreferencesUtil.getInstance().getObject(FDApplication.getAppContext(),"fd","FilterValue");
    }

    public static void setUserInfo(UserInfo userInfo){
        SharedPreferencesUtil.getInstance().saveObject(FDApplication.getAppContext(),"fd","userInfo",userInfo);
    }

    public static UserInfo getUserInfo(){
        return (UserInfo) SharedPreferencesUtil.getInstance().getObject(FDApplication.getAppContext(),"fd","userInfo");
    }
}
