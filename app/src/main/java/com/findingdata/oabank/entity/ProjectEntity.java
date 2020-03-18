package com.findingdata.oabank.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 项目实体
 */
public class ProjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * PROJECT_ID : 1562
     * PROJECT_NAME : 北辰三角洲1栋101
     * PROJECT_STATUS : 40001001
     * PROJECT_STATUS_CHS : 进行中
     * CONTACT_PERSON : 张三
     * CONTACT_PHONE : 13112345678
     * BCM_USER_ID : 1165
     * BCM_NAME : 戚东卫
     * BCM_PHONE : 15912345679
     * LOAN_TYPE : 40047001
     * LOAN_TYPE_CHS : 小微助业贷款
     * LOAN_AMOUNT : 80
     * BORROWER : 张三
     * BORROWER_PHONE : 13112345678
     * BORROWER_ID_CARD :
     * REMARK :
     * TERMINATION_REASON :
     * CREATE_BY : 1165
     * CREATE_NAME : 戚东卫
     * CREATE_PHONE : 15912345679
     * MODIFY_BY : 0
     * MODIFY_NAME :
     * CREATE_TIME : 2019-12-23T08:57:57
     * IS_DELETED : 0
     * CUSTOMER_ID : 863
     * MODIFY_CUSTOMER_NAME : 湘江新区支行
     * CUSTOMER_NAME : 湘江新区支行
     * IS_QUICK_MODE :
     * DISPATCH_CUSTOMER_ID : 862
     * DISPATCH_CUSTOMER_NAME : 长沙分行
     * DISPATCH_BY : 1164
     * DISPATCH_NAME : 侯亮平
     * DISPATCH_PHONE : 15187654321
     * DISPATCH_TIME : 2019-12-23T17:59:05
     * PROJECT_FORM_ID : 7066
     * CONFIRM_TIME : 2019-12-23T09:23:28
     * IS_AUTO_DISPATCH : 0
     * IS_DRAFT : 0
     * OWNER_CUSTOMER_ID : 862
     * PROPERTY_LIST:[]
     * BUSINESS:[]
     * NOTE_LIST:[]
     * ACT_LIST:[]
     */

    private int PROJECT_ID;
    private String PROJECT_NAME;
    private int PROJECT_STATUS;
    private String PROJECT_STATUS_CHS;
    private String CONTACT_PERSON;
    private String CONTACT_PHONE;
    private int BCM_USER_ID;
    private String BCM_NAME;
    private String BCM_PHONE;
    private int LOAN_TYPE;
    private String LOAN_TYPE_CHS;
    private int LOAN_AMOUNT;
    private String BORROWER;
    private String BORROWER_PHONE;
    private String BORROWER_ID_CARD;
    private String REMARK;
    private String TERMINATION_REASON;
    private int CREATE_BY;
    private String CREATE_NAME;
    private String CREATE_PHONE;
    private int MODIFY_BY;
    private String MODIFY_NAME;
    private String CREATE_TIME;
    private int IS_DELETED;
    private int CUSTOMER_ID;
    private String MODIFY_CUSTOMER_NAME;
    private String CUSTOMER_NAME;
    private String IS_QUICK_MODE;
    private int DISPATCH_CUSTOMER_ID;
    private String DISPATCH_CUSTOMER_NAME;
    private int DISPATCH_BY;
    private String DISPATCH_NAME;
    private String DISPATCH_PHONE;
    private String DISPATCH_TIME;
    private int PROJECT_FORM_ID;
    private String CONFIRM_TIME;
    private int IS_AUTO_DISPATCH;
    private int IS_DRAFT;
    private int OWNER_CUSTOMER_ID;
    private List<PropertyEntity> PROPERTY_LIST;
    private ProjectBusinessEntity BUSINESS;
    private List<ProjectNoteEntity> NOTE_LIST;
    private List<ProjectActionEntity> ACT_LIST;


    public int getPROJECT_ID() {
        return PROJECT_ID;
    }

    public void setPROJECT_ID(int PROJECT_ID) {
        this.PROJECT_ID = PROJECT_ID;
    }

    public String getPROJECT_NAME() {
        return PROJECT_NAME;
    }

    public void setPROJECT_NAME(String PROJECT_NAME) {
        this.PROJECT_NAME = PROJECT_NAME;
    }

    public int getPROJECT_STATUS() {
        return PROJECT_STATUS;
    }

    public void setPROJECT_STATUS(int PROJECT_STATUS) {
        this.PROJECT_STATUS = PROJECT_STATUS;
    }

    public String getPROJECT_STATUS_CHS() {
        return PROJECT_STATUS_CHS;
    }

    public void setPROJECT_STATUS_CHS(String PROJECT_STATUS_CHS) {
        this.PROJECT_STATUS_CHS = PROJECT_STATUS_CHS;
    }

    public String getCONTACT_PERSON() {
        return CONTACT_PERSON;
    }

    public void setCONTACT_PERSON(String CONTACT_PERSON) {
        this.CONTACT_PERSON = CONTACT_PERSON;
    }

    public String getCONTACT_PHONE() {
        return CONTACT_PHONE;
    }

    public void setCONTACT_PHONE(String CONTACT_PHONE) {
        this.CONTACT_PHONE = CONTACT_PHONE;
    }

    public int getBCM_USER_ID() {
        return BCM_USER_ID;
    }

    public void setBCM_USER_ID(int BCM_USER_ID) {
        this.BCM_USER_ID = BCM_USER_ID;
    }

    public String getBCM_NAME() {
        return BCM_NAME;
    }

    public void setBCM_NAME(String BCM_NAME) {
        this.BCM_NAME = BCM_NAME;
    }

    public String getBCM_PHONE() {
        return BCM_PHONE;
    }

    public void setBCM_PHONE(String BCM_PHONE) {
        this.BCM_PHONE = BCM_PHONE;
    }

    public int getLOAN_TYPE() {
        return LOAN_TYPE;
    }

    public void setLOAN_TYPE(int LOAN_TYPE) {
        this.LOAN_TYPE = LOAN_TYPE;
    }

    public String getLOAN_TYPE_CHS() {
        return LOAN_TYPE_CHS;
    }

    public void setLOAN_TYPE_CHS(String LOAN_TYPE_CHS) {
        this.LOAN_TYPE_CHS = LOAN_TYPE_CHS;
    }

    public int getLOAN_AMOUNT() {
        return LOAN_AMOUNT;
    }

    public void setLOAN_AMOUNT(int LOAN_AMOUNT) {
        this.LOAN_AMOUNT = LOAN_AMOUNT;
    }

    public String getBORROWER() {
        return BORROWER;
    }

    public void setBORROWER(String BORROWER) {
        this.BORROWER = BORROWER;
    }

    public String getBORROWER_PHONE() {
        return BORROWER_PHONE;
    }

    public void setBORROWER_PHONE(String BORROWER_PHONE) {
        this.BORROWER_PHONE = BORROWER_PHONE;
    }

    public String getBORROWER_ID_CARD() {
        return BORROWER_ID_CARD;
    }

    public void setBORROWER_ID_CARD(String BORROWER_ID_CARD) {
        this.BORROWER_ID_CARD = BORROWER_ID_CARD;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getTERMINATION_REASON() {
        return TERMINATION_REASON;
    }

    public void setTERMINATION_REASON(String TERMINATION_REASON) {
        this.TERMINATION_REASON = TERMINATION_REASON;
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

    public String getCREATE_PHONE() {
        return CREATE_PHONE;
    }

    public void setCREATE_PHONE(String CREATE_PHONE) {
        this.CREATE_PHONE = CREATE_PHONE;
    }

    public int getMODIFY_BY() {
        return MODIFY_BY;
    }

    public void setMODIFY_BY(int MODIFY_BY) {
        this.MODIFY_BY = MODIFY_BY;
    }

    public String getMODIFY_NAME() {
        return MODIFY_NAME;
    }

    public void setMODIFY_NAME(String MODIFY_NAME) {
        this.MODIFY_NAME = MODIFY_NAME;
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

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(int CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getMODIFY_CUSTOMER_NAME() {
        return MODIFY_CUSTOMER_NAME;
    }

    public void setMODIFY_CUSTOMER_NAME(String MODIFY_CUSTOMER_NAME) {
        this.MODIFY_CUSTOMER_NAME = MODIFY_CUSTOMER_NAME;
    }

    public String getCUSTOMER_NAME() {
        return CUSTOMER_NAME;
    }

    public void setCUSTOMER_NAME(String CUSTOMER_NAME) {
        this.CUSTOMER_NAME = CUSTOMER_NAME;
    }

    public String getIS_QUICK_MODE() {
        return IS_QUICK_MODE;
    }

    public void setIS_QUICK_MODE(String IS_QUICK_MODE) {
        this.IS_QUICK_MODE = IS_QUICK_MODE;
    }

    public int getDISPATCH_CUSTOMER_ID() {
        return DISPATCH_CUSTOMER_ID;
    }

    public void setDISPATCH_CUSTOMER_ID(int DISPATCH_CUSTOMER_ID) {
        this.DISPATCH_CUSTOMER_ID = DISPATCH_CUSTOMER_ID;
    }

    public String getDISPATCH_CUSTOMER_NAME() {
        return DISPATCH_CUSTOMER_NAME;
    }

    public void setDISPATCH_CUSTOMER_NAME(String DISPATCH_CUSTOMER_NAME) {
        this.DISPATCH_CUSTOMER_NAME = DISPATCH_CUSTOMER_NAME;
    }

    public int getDISPATCH_BY() {
        return DISPATCH_BY;
    }

    public void setDISPATCH_BY(int DISPATCH_BY) {
        this.DISPATCH_BY = DISPATCH_BY;
    }

    public String getDISPATCH_NAME() {
        return DISPATCH_NAME;
    }

    public void setDISPATCH_NAME(String DISPATCH_NAME) {
        this.DISPATCH_NAME = DISPATCH_NAME;
    }

    public String getDISPATCH_PHONE() {
        return DISPATCH_PHONE;
    }

    public void setDISPATCH_PHONE(String DISPATCH_PHONE) {
        this.DISPATCH_PHONE = DISPATCH_PHONE;
    }

    public String getDISPATCH_TIME() {
        return DISPATCH_TIME;
    }

    public void setDISPATCH_TIME(String DISPATCH_TIME) {
        this.DISPATCH_TIME = DISPATCH_TIME;
    }

    public int getPROJECT_FORM_ID() {
        return PROJECT_FORM_ID;
    }

    public void setPROJECT_FORM_ID(int PROJECT_FORM_ID) {
        this.PROJECT_FORM_ID = PROJECT_FORM_ID;
    }

    public String getCONFIRM_TIME() {
        return CONFIRM_TIME;
    }

    public void setCONFIRM_TIME(String CONFIRM_TIME) {
        this.CONFIRM_TIME = CONFIRM_TIME;
    }

    public int getIS_AUTO_DISPATCH() {
        return IS_AUTO_DISPATCH;
    }

    public void setIS_AUTO_DISPATCH(int IS_AUTO_DISPATCH) {
        this.IS_AUTO_DISPATCH = IS_AUTO_DISPATCH;
    }

    public int getIS_DRAFT() {
        return IS_DRAFT;
    }

    public void setIS_DRAFT(int IS_DRAFT) {
        this.IS_DRAFT = IS_DRAFT;
    }

    public int getOWNER_CUSTOMER_ID() {
        return OWNER_CUSTOMER_ID;
    }

    public void setOWNER_CUSTOMER_ID(int OWNER_CUSTOMER_ID) {
        this.OWNER_CUSTOMER_ID = OWNER_CUSTOMER_ID;
    }

    public List<PropertyEntity> getPROPERTY_LIST() {
        return PROPERTY_LIST;
    }

    public void setPROPERTY_LIST(List<PropertyEntity> PROPERTY_LIST) {
        this.PROPERTY_LIST = PROPERTY_LIST;
    }

    public ProjectBusinessEntity getBUSINESS() {
        return BUSINESS;
    }

    public void setBUSINESS(ProjectBusinessEntity BUSINESS) {
        this.BUSINESS = BUSINESS;
    }

    public List<ProjectNoteEntity> getNOTE_LIST() {
        return NOTE_LIST;
    }

    public void setNOTE_LIST(List<ProjectNoteEntity> NOTE_LIST) {
        this.NOTE_LIST = NOTE_LIST;
    }

    public List<ProjectActionEntity> getACT_LIST() {
        return ACT_LIST;
    }

    public void setACT_LIST(List<ProjectActionEntity> ACT_LIST) {
        this.ACT_LIST = ACT_LIST;
    }
}
