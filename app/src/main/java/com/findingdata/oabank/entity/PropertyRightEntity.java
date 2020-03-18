package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/1/2.
 * Version: 1.0
 * Describe: 标的物产权实体
 */
public class PropertyRightEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * PROPERTY_RIGHT_ID : 1679
     * PROPERTY_ID : 1838
     * PROPERTY_OWNER :
     * PROPERTY_OWNER_PHONE :
     * PROPERTY_OWNER_ID :
     * PROPERTY_CERTIFICATE :
     */

    private int PROPERTY_RIGHT_ID;
    private int PROPERTY_ID;
    private String PROPERTY_OWNER;
    private String PROPERTY_OWNER_PHONE;
    private String PROPERTY_OWNER_ID;
    private String PROPERTY_CERTIFICATE;

    public int getPROPERTY_RIGHT_ID() {
        return PROPERTY_RIGHT_ID;
    }

    public void setPROPERTY_RIGHT_ID(int PROPERTY_RIGHT_ID) {
        this.PROPERTY_RIGHT_ID = PROPERTY_RIGHT_ID;
    }

    public int getPROPERTY_ID() {
        return PROPERTY_ID;
    }

    public void setPROPERTY_ID(int PROPERTY_ID) {
        this.PROPERTY_ID = PROPERTY_ID;
    }

    public String getPROPERTY_OWNER() {
        return PROPERTY_OWNER;
    }

    public void setPROPERTY_OWNER(String PROPERTY_OWNER) {
        this.PROPERTY_OWNER = PROPERTY_OWNER;
    }

    public String getPROPERTY_OWNER_PHONE() {
        return PROPERTY_OWNER_PHONE;
    }

    public void setPROPERTY_OWNER_PHONE(String PROPERTY_OWNER_PHONE) {
        this.PROPERTY_OWNER_PHONE = PROPERTY_OWNER_PHONE;
    }

    public String getPROPERTY_OWNER_ID() {
        return PROPERTY_OWNER_ID;
    }

    public void setPROPERTY_OWNER_ID(String PROPERTY_OWNER_ID) {
        this.PROPERTY_OWNER_ID = PROPERTY_OWNER_ID;
    }

    public String getPROPERTY_CERTIFICATE() {
        return PROPERTY_CERTIFICATE;
    }

    public void setPROPERTY_CERTIFICATE(String PROPERTY_CERTIFICATE) {
        this.PROPERTY_CERTIFICATE = PROPERTY_CERTIFICATE;
    }
}
