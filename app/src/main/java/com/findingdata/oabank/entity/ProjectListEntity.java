package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/12/17.
 * Version: 1.0
 * Describe: 项目列表实体
 */
public class ProjectListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * data_count : 219
     * page_count : 22
     * page_no : 1
     * page_size : 10
     * data : []
     */

    private int data_count;
    private int page_count;
    private int page_no;
    private int page_size;
    private List<ProjectEntity> data;

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

    public List<ProjectEntity> getData() {
        return data;
    }

    public void setData(List<ProjectEntity> data) {
        this.data = data;
    }
}
