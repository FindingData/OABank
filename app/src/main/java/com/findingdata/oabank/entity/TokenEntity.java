package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/12/18.
 * Version: 1.0
 * Describe: Token缓存实体
 */
public class TokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private long expireTime;

    public TokenEntity(String token, long expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
