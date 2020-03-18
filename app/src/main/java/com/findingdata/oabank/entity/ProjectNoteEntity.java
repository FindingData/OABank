package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 项目留言实体
 */
public class ProjectNoteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * PROJECT_NOTE_ID : 162
     * PROJECT_ID : 1615
     * PROJECT_NOTE_CONTENT : 这是一条留言
     * CUSTOMER_NAME : 长沙分行
     * CREATE_BY : 1164
     * CREATE_NAME : 侯亮平
     * CREATE_TIME : 2020-01-02T19:52:08
     * IS_DELETED : 0
     */

    private int PROJECT_NOTE_ID;
    private int PROJECT_ID;
    private String PROJECT_NOTE_CONTENT;
    private String CUSTOMER_NAME;
    private int CREATE_BY;
    private String CREATE_NAME;
    private String CREATE_TIME;
    private int IS_DELETED;

    public int getPROJECT_NOTE_ID() {
        return PROJECT_NOTE_ID;
    }

    public void setPROJECT_NOTE_ID(int PROJECT_NOTE_ID) {
        this.PROJECT_NOTE_ID = PROJECT_NOTE_ID;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPROJECT_NOTE_CONTENT() {
        return PROJECT_NOTE_CONTENT;
    }

    public void setPROJECT_NOTE_CONTENT(String PROJECT_NOTE_CONTENT) {
        this.PROJECT_NOTE_CONTENT = PROJECT_NOTE_CONTENT;
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

    public String getCREATE_NAME() {
        return CREATE_NAME;
    }

    public void setCREATE_NAME(String CREATE_NAME) {
        this.CREATE_NAME = CREATE_NAME;
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
