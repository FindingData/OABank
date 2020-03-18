package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 标的回价实体
 */
public class PropertyPriceEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * INQUIRY_ID : 317
     * PROPERTY_ID : 1838
     * PRICE : 12580
     * TOTAL_PRICE : 161.02
     * CUSTOMER_ID : 863
     * COMMISSIONED_NAME : 长沙县经典
     * PROJECT_ID : 1562
     * PRICE_TYPE_CHS : 预估函
     * PROPERTY_NAME : 北辰三角洲1栋101
     * AREA : 128
     * IS_CONFIRM : 1
     * REPLY_TIME : 2019-12-23T18:13:45
     * REPLY_BY : 1184
     * REPLY_NAME : 管理员
     * REPLY_PHONE : 15512345678
     * CREATE_BY : 1184
     * CREATE_TIME : 2019-12-23T18:13:45
     * IS_DELETED : 0
     * PRICE_TYPE : 40004002
     * COMMISSIONED_ID : 46
     * RN : 1
     */

    private int INQUIRY_ID;
    private int PROPERTY_ID;
    private int PRICE;
    private double TOTAL_PRICE;
    private int CUSTOMER_ID;
    private String COMMISSIONED_NAME;
    private int PROJECT_ID;
    private String PRICE_TYPE_CHS;
    private String PROPERTY_NAME;
    private int AREA;
    private int IS_CONFIRM;
    private String REPLY_TIME;
    private int REPLY_BY;
    private String REPLY_NAME;
    private String REPLY_PHONE;
    private int CREATE_BY;
    private String CREATE_TIME;
    private int IS_DELETED;
    private int PRICE_TYPE;
    private int COMMISSIONED_ID;
    private int RN;

    public int getINQUIRY_ID() {
        return INQUIRY_ID;
    }

    public void setINQUIRY_ID(int INQUIRY_ID) {
        this.INQUIRY_ID = INQUIRY_ID;
    }

    public int getPROPERTY_ID() {
        return PROPERTY_ID;
    }

    public void setPROPERTY_ID(int PROPERTY_ID) {
        this.PROPERTY_ID = PROPERTY_ID;
    }

    public int getPRICE() {
        return PRICE;
    }

    public void setPRICE(int PRICE) {
        this.PRICE = PRICE;
    }

    public double getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(double TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getCOMMISSIONED_NAME() {
        return COMMISSIONED_NAME;
    }

    public void setCOMMISSIONED_NAME(String COMMISSIONED_NAME) {
        this.COMMISSIONED_NAME = COMMISSIONED_NAME;
    }

    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPRICE_TYPE_CHS() {
        return PRICE_TYPE_CHS;
    }

    public void setPRICE_TYPE_CHS(String PRICE_TYPE_CHS) {
        this.PRICE_TYPE_CHS = PRICE_TYPE_CHS;
    }

    public String getPROPERTY_NAME() {
        return PROPERTY_NAME;
    }

    public void setPROPERTY_NAME(String PROPERTY_NAME) {
        this.PROPERTY_NAME = PROPERTY_NAME;
    }

    public int getAREA() {
        return AREA;
    }

    public void setAREA(int AREA) {
        this.AREA = AREA;
    }

    public int getIS_CONFIRM() {
        return IS_CONFIRM;
    }

    public void setIS_CONFIRM(int IS_CONFIRM) {
        this.IS_CONFIRM = IS_CONFIRM;
    }

    public String getREPLY_TIME() {
        return REPLY_TIME;
    }

    public void setREPLY_TIME(String REPLY_TIME) {
        this.REPLY_TIME = REPLY_TIME;
    }

    public int getREPLY_BY() {
        return REPLY_BY;
    }

    public void setREPLY_BY(int REPLY_BY) {
        this.REPLY_BY = REPLY_BY;
    }

    public String getREPLY_NAME() {
        return REPLY_NAME;
    }

    public void setREPLY_NAME(String REPLY_NAME) {
        this.REPLY_NAME = REPLY_NAME;
    }

    public String getREPLY_PHONE() {
        return REPLY_PHONE;
    }

    public void setREPLY_PHONE(String REPLY_PHONE) {
        this.REPLY_PHONE = REPLY_PHONE;
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

    public int getPRICE_TYPE() {
        return PRICE_TYPE;
    }

    public void setPRICE_TYPE(int PRICE_TYPE) {
        this.PRICE_TYPE = PRICE_TYPE;
    }

    public int getCOMMISSIONED_ID() {
        return COMMISSIONED_ID;
    }

    public void setCOMMISSIONED_ID(int COMMISSIONED_ID) {
        this.COMMISSIONED_ID = COMMISSIONED_ID;
    }

    public int getRN() {
        return RN;
    }

    public void setRN(int RN) {
        this.RN = RN;
    }
}
