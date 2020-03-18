package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/22.
 * Version: 1.0
 * Describe: 消息实体
 */
public class NotifyEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * RID : 1
     * MESSAGE_ID : 394
     * CUSTOMER_ID : 862
     * READED_TIME :
     * RFROM : 长沙县经典
     * RTO : 862
     * MESSAGE_TITLE :
     * MESSAGE_CONTENT : 项目 凤凰城 评估公司已立项.
     * MESSAGE_STATUS : 0
     * CREATED_TIME : 2019-12-23T11:19:48
     * MESSAGE_TYPE : 1
     * MESSAGE_TYPE_CHS :
     * IS_SYSTEM_MESSAGE :
     */

    private int RID;
    private int MESSAGE_ID;
    private int CUSTOMER_ID;
    private String READED_TIME;
    private String RFROM;
    private int RTO;
    private String MESSAGE_TITLE;
    private String MESSAGE_CONTENT;
    private int MESSAGE_STATUS;
    private String CREATED_TIME;
    private int MESSAGE_TYPE;
    private String MESSAGE_TYPE_CHS;
    private String IS_SYSTEM_MESSAGE;

    public int getRID() {
        return RID;
    }

    public void setRID(int RID) {
        this.RID = RID;
    }

    public int getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(int MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getREADED_TIME() {
        return READED_TIME;
    }

    public void setREADED_TIME(String READED_TIME) {
        this.READED_TIME = READED_TIME;
    }

    public String getRFROM() {
        return RFROM;
    }

    public void setRFROM(String RFROM) {
        this.RFROM = RFROM;
    }

    public int getRTO() {
        return RTO;
    }

    public void setRTO(int RTO) {
        this.RTO = RTO;
    }

    public String getMESSAGE_TITLE() {
        return MESSAGE_TITLE;
    }

    public void setMESSAGE_TITLE(String MESSAGE_TITLE) {
        this.MESSAGE_TITLE = MESSAGE_TITLE;
    }

    public String getMESSAGE_CONTENT() {
        return MESSAGE_CONTENT;
    }

    public void setMESSAGE_CONTENT(String MESSAGE_CONTENT) {
        this.MESSAGE_CONTENT = MESSAGE_CONTENT;
    }

    public int getMESSAGE_STATUS() {
        return MESSAGE_STATUS;
    }

    public void setMESSAGE_STATUS(int MESSAGE_STATUS) {
        this.MESSAGE_STATUS = MESSAGE_STATUS;
    }

    public String getCREATED_TIME() {
        return CREATED_TIME;
    }

    public void setCREATED_TIME(String CREATED_TIME) {
        this.CREATED_TIME = CREATED_TIME;
    }

    public int getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public void setMESSAGE_TYPE(int MESSAGE_TYPE) {
        this.MESSAGE_TYPE = MESSAGE_TYPE;
    }

    public String getMESSAGE_TYPE_CHS() {
        return MESSAGE_TYPE_CHS;
    }

    public void setMESSAGE_TYPE_CHS(String MESSAGE_TYPE_CHS) {
        this.MESSAGE_TYPE_CHS = MESSAGE_TYPE_CHS;
    }

    public String getIS_SYSTEM_MESSAGE() {
        return IS_SYSTEM_MESSAGE;
    }

    public void setIS_SYSTEM_MESSAGE(String IS_SYSTEM_MESSAGE) {
        this.IS_SYSTEM_MESSAGE = IS_SYSTEM_MESSAGE;
    }
}
