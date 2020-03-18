package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/12/27.
 * Version: 1.0
 * Describe: 项目列表查询条件实体
 */
public class QueryItem<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private List<T> value;

    public QueryItem(String key, List<T> value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<T> getValue() {
        return value;
    }

    public void setValue(List<T> value) {
        this.value = value;
    }
}
