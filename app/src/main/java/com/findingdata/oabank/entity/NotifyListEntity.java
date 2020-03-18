package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息列表实体
 */
public class NotifyListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int data_count;
    private int page_count;
    private int page_no;
    private int page_size;
    private List<NotifyEntity> data;

    public int getData_count() {
        return data_count;
    }

    public void setData_count(int data_count) {
        this.data_count = data_count;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getPage_no() {
        return page_no;
    }

    public void setPage_no(int page_no) {
        this.page_no = page_no;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<NotifyEntity> getData() {
        return data;
    }

    public void setData(List<NotifyEntity> data) {
        this.data = data;
    }
}
