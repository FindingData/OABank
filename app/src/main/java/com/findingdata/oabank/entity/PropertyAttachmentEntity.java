package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 标的物附件实体
 */
public class PropertyAttachmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ATTACHMENT_ID : 712
     * PROJECT_ID : 0
     * PROPERTY_ID : 1890
     * FILE_ID : 71250
     * FILE_NAME : 11
     * FILE_ORIGIN_NAME : 11
     * LABEL_DATA :
     * IS_DELETED : 0
     */

    private int ATTACHMENT_ID;
    private int PROJECT_ID;
    private int PROPERTY_ID;
    private int FILE_ID;
    private String FILE_NAME;
    private String FILE_ORIGIN_NAME;
    private String LABEL_DATA;
    private int IS_DELETED;

    public int getATTACHMENT_ID() {
        return ATTACHMENT_ID;
    }

    public void setATTACHMENT_ID(int ATTACHMENT_ID) {
        this.ATTACHMENT_ID = ATTACHMENT_ID;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public int getPROPERTY_ID() {
        return PROPERTY_ID;
    }

    public void setPROPERTY_ID(int PROPERTY_ID) {
        this.PROPERTY_ID = PROPERTY_ID;
    }

    public int getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(int FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getFILE_ORIGIN_NAME() {
        return FILE_ORIGIN_NAME;
    }

    public void setFILE_ORIGIN_NAME(String FILE_ORIGIN_NAME) {
        this.FILE_ORIGIN_NAME = FILE_ORIGIN_NAME;
    }

    public String getLABEL_DATA() {
        return LABEL_DATA;
    }

    public void setLABEL_DATA(String LABEL_DATA) {
        this.LABEL_DATA = LABEL_DATA;
    }

    public int getIS_DELETED() {
        return IS_DELETED;
    }

    public void setIS_DELETED(int IS_DELETED) {
        this.IS_DELETED = IS_DELETED;
    }
}
