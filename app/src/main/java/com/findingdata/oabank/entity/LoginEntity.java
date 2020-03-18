package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/12/18.
 * Version: 1.0
 * Describe: 缓存的登录信息实体
 */
public class LoginEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userName;
    private String password;

    public LoginEntity(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
