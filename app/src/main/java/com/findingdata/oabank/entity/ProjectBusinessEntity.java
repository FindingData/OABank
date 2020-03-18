package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 项目事项实体
 */
public class ProjectBusinessEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * PROJECT_BUSINESS_ID : 135373
     * PROJECT_ID : 1615
     * COMMISSIONED_ID : 46
     * COMMISSIONED_NAME : 长沙县经典
     * COMMISSIONED_PHONE :
     * BUSINESS_TYPE : 40004001
     * BUSINESS_TYPE_CHS : 项目反馈
     * EXECUTE_BY : 0
     * EXECUTE_NAME :
     * EXECUTE_PHONE :
     * EXECUTE_TIME :
     * COMPLETED_TIME :
     * FILE_ID : 0
     * IS_RETURN : 1
     * RETURN_REASON : 项目超期重派
     * CREATE_NAME : 戚东卫
     * CREATE_BY : 1165
     * CREATE_TIME : 2020-01-02T16:21:41
     * IS_DELETED : 0
     * IS_AUTO_DISPATCH : 1
     * TOTAL_PRICE : 0.0
     * FILE_CODE :
     * FILE_NAME :
     */

    private int PROJECT_BUSINESS_ID;
    private int PROJECT_ID;
    private int COMMISSIONED_ID;
    private String COMMISSIONED_NAME;
    private String COMMISSIONED_PHONE;
    private int BUSINESS_TYPE;
    private String BUSINESS_TYPE_CHS;
    private int EXECUTE_BY;
    private String EXECUTE_NAME;
    private String EXECUTE_PHONE;
    private String EXECUTE_TIME;
    private String COMPLETED_TIME;
    private int FILE_ID;
    private int IS_RETURN;
    private String RETURN_REASON;
    private String CREATE_NAME;
    private int CREATE_BY;
    private String CREATE_TIME;
    private int IS_DELETED;
    private int IS_AUTO_DISPATCH;
    private double TOTAL_PRICE;
    private String FILE_CODE;
    private String FILE_NAME;

    public int getPROJECT_BUSINESS_ID() {
        return PROJECT_BUSINESS_ID;
    }

    public void setPROJECT_BUSINESS_ID(int PROJECT_BUSINESS_ID) {
        this.PROJECT_BUSINESS_ID = PROJECT_BUSINESS_ID;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public int getCOMMISSIONED_ID() {
        return COMMISSIONED_ID;
    }

    public void setCOMMISSIONED_ID(int COMMISSIONED_ID) {
        this.COMMISSIONED_ID = COMMISSIONED_ID;
    }

    public String getCOMMISSIONED_NAME() {
        return COMMISSIONED_NAME;
    }

    public void setCOMMISSIONED_NAME(String COMMISSIONED_NAME) {
        this.COMMISSIONED_NAME = COMMISSIONED_NAME;
    }

    public String getCOMMISSIONED_PHONE() {
        return COMMISSIONED_PHONE;
    }

    public void setCOMMISSIONED_PHONE(String COMMISSIONED_PHONE) {
        this.COMMISSIONED_PHONE = COMMISSIONED_PHONE;
    }

    public int getBUSINESS_TYPE() {
        return BUSINESS_TYPE;
    }

    public void setBUSINESS_TYPE(int BUSINESS_TYPE) {
        this.BUSINESS_TYPE = BUSINESS_TYPE;
    }

    public String getBUSINESS_TYPE_CHS() {
        return BUSINESS_TYPE_CHS;
    }

    public void setBUSINESS_TYPE_CHS(String BUSINESS_TYPE_CHS) {
        this.BUSINESS_TYPE_CHS = BUSINESS_TYPE_CHS;
    }

    public int getEXECUTE_BY() {
        return EXECUTE_BY;
    }

    public void setEXECUTE_BY(int EXECUTE_BY) {
        this.EXECUTE_BY = EXECUTE_BY;
    }

    public String getEXECUTE_NAME() {
        return EXECUTE_NAME;
    }

    public void setEXECUTE_NAME(String EXECUTE_NAME) {
        this.EXECUTE_NAME = EXECUTE_NAME;
    }

    public String getEXECUTE_PHONE() {
        return EXECUTE_PHONE;
    }

    public void setEXECUTE_PHONE(String EXECUTE_PHONE) {
        this.EXECUTE_PHONE = EXECUTE_PHONE;
    }

    public String getEXECUTE_TIME() {
        return EXECUTE_TIME;
    }

    public void setEXECUTE_TIME(String EXECUTE_TIME) {
        this.EXECUTE_TIME = EXECUTE_TIME;
    }

    public String getCOMPLETED_TIME() {
        return COMPLETED_TIME;
    }

    public void setCOMPLETED_TIME(String COMPLETED_TIME) {
        this.COMPLETED_TIME = COMPLETED_TIME;
    }

    public int getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(int FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public int getIS_RETURN() {
        return IS_RETURN;
    }

    public void setIS_RETURN(int IS_RETURN) {
        this.IS_RETURN = IS_RETURN;
    }

    public String getRETURN_REASON() {
        return RETURN_REASON;
    }

    public void setRETURN_REASON(String RETURN_REASON) {
        this.RETURN_REASON = RETURN_REASON;
    }

    public String getCREATE_NAME() {
        return CREATE_NAME;
    }

    public void setCREATE_NAME(String CREATE_NAME) {
        this.CREATE_NAME = CREATE_NAME;
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

    public int getIS_AUTO_DISPATCH() {
        return IS_AUTO_DISPATCH;
    }

    public void setIS_AUTO_DISPATCH(int IS_AUTO_DISPATCH) {
        this.IS_AUTO_DISPATCH = IS_AUTO_DISPATCH;
    }

    public double getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(double TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public String getFILE_CODE() {
        return FILE_CODE;
    }

    public void setFILE_CODE(String FILE_CODE) {
        this.FILE_CODE = FILE_CODE;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }
}
