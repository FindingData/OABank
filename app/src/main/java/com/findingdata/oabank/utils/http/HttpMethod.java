package com.findingdata.oabank.utils.http;

/**
 * Created by Loong on 2019/11/23.
 * Version: 1.0
 * Describe: 请求方法枚举
 */
public enum HttpMethod {
    Post(0x01),
    Get(0x02),
    Upload(0x03),
    Download(0x04),
    PostJson(0x05);
    private final int type;

    HttpMethod(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "HttpMethod{" +
                "type=" + type +
                '}';
    }
}
