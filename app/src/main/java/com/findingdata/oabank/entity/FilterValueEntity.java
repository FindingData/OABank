package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 项目过滤选择项的值
 */
public class FilterValueEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String value;

    public FilterValueEntity() {
    }

    public FilterValueEntity(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
