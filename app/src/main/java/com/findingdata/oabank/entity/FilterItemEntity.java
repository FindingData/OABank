package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/12/23.
 * Version: 1.0
 * Describe: 项目过滤项实体
 */
public class FilterItemEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * key :
     * value : 不限
     */

    private String key;
    private String value;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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
