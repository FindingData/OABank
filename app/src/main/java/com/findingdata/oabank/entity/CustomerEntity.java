package com.findingdata.oabank.entity;

import java.io.Serializable;

public class CustomerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getBUSINESS_CONTACT() {
        return BUSINESS_CONTACT;
    }

    public void setBUSINESS_CONTACT(String BUSINESS_CONTACT) {
        this.BUSINESS_CONTACT = BUSINESS_CONTACT;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public int getPROCESSING() {
        return PROCESSING;
    }

    public void setPROCESSING(int PROCESSING) {
        this.PROCESSING = PROCESSING;
    }

    public int getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(int TOTAL) {
        this.TOTAL = TOTAL;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    private int CUSTOMER_ID;
    private String CUSTOMER_NAME;
    private String BUSINESS_CONTACT;
    private String PHONE;
    private int PROCESSING;
    private int TOTAL;


}
