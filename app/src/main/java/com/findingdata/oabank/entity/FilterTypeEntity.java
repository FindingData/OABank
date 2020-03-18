package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 项目过滤选择项实体
 */
public class FilterTypeEntity {

    private String type;
    private String chs;
    private boolean check;

    public FilterTypeEntity(String type, String chs, boolean check) {
        this.type = type;
        this.chs = chs;
        this.check = check;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChs() {
        return chs;
    }

    public void setChs(String chs) {
        this.chs = chs;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
