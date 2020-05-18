package com.findingdata.oabank.entity;

public class AchievementEntity {

    private String FILE_ID;
    private String FILE_NAME;
    private String BUSINESS_TYPE_CHS;
    private String COMPLETED_TIME;
    private String TOTAL_PRICE;

    public String getFILE_ID() {
        return FILE_ID;
    }

    public void setFILE_ID(String FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }

    public String getBUSINESS_TYPE_CHS() {
        return BUSINESS_TYPE_CHS;
    }

    public void setBUSINESS_TYPE_CHS(String BUSINESS_TYPE_CHS) {
        this.BUSINESS_TYPE_CHS = BUSINESS_TYPE_CHS;
    }

    public String getCOMPLETED_TIME() {
        return COMPLETED_TIME;
    }

    public void setCOMPLETED_TIME(String COMPLETED_TIME) {
        this.COMPLETED_TIME = COMPLETED_TIME;
    }

    public String getTOTAL_PRICE() {
        return TOTAL_PRICE;
    }

    public void setTOTAL_PRICE(String TOTAL_PRICE) {
        this.TOTAL_PRICE = TOTAL_PRICE;
    }
}
