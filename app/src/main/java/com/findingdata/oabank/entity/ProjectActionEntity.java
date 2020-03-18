package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 项目动态实体
 */
public class ProjectActionEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * PROJECT_ACT_ID : 426
     * PROJECT_ID : 1623
     * ACT_CONTENT : 系统派评 没有符合自动派评的公司 项目上报至 长沙分行
     * CUSTOMER_NAME : 长沙分行
     * CREATE_BY : 1164
     * CREATE_TIME : 2020-01-02T17:49:18
     * IS_DELETED : 0
     */

    private int PROJECT_ACT_ID;
    private int PROJECT_ID;
    private String ACT_CONTENT;
    private String CUSTOMER_NAME;
    private int CREATE_BY;
    private String CREATE_TIME;
    private int IS_DELETED;

    public int getPROJECT_ACT_ID() {
        return PROJECT_ACT_ID;
    }

    public void setPROJECT_ACT_ID(int PROJECT_ACT_ID) {
        this.PROJECT_ACT_ID = PROJECT_ACT_ID;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getACT_CONTENT() {
        return ACT_CONTENT;
    }

    public void setACT_CONTENT(String ACT_CONTENT) {
        this.ACT_CONTENT = ACT_CONTENT;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public int getCREATE_BY() {
        return CREATE_BY;
    }

    public void setCREATE_BY(int CREATE_BY) {
        this.CREATE_BY = CREATE_BY;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public int getIS_DELETED() {
        return IS_DELETED;
    }

    public void setIS_DELETED(int IS_DELETED) {
        this.IS_DELETED = IS_DELETED;
    }
}
