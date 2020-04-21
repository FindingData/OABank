package com.findingdata.oabank.entity;

public class ApplyBusiness {

    private String project_id;
    private int[] business_list;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public int[] getBusiness_list() {
        return business_list;
    }

    public void setBusiness_list(int[] business_list) {
        this.business_list = business_list;
    }
}
