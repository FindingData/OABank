package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/12/17.
 * Version: 1.0
 * Describe: 用户信息实体
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Token : -1755778283
     * UserId : 1164.0
     * CustomerId : 862.0
     * CustomerName : 长沙分行
     * SystemFlag : 11.0
     * RoleName : 公司超级管理员
     * UserName : 侯亮平
     * UserPhone : 15187654321
     * UserEmail : 252447793@qq.com
     * UserHeadPicture : 70686
     * IpAddress : 192.168.10.249
     * CreatedTime : 2019-12-17T13:33:37.5230376+08:00
     * CustomerPca : 430100
     */

    private String Token;
    private double UserId;
    private double CustomerId;
    private String CustomerName;
    private double SystemFlag;
    private String RoleName;
    private String UserName;
    private String UserPhone;
    private String UserEmail;
    private String UserHeadPicture;
    private String IpAddress;
    private String CreatedTime;
    private int CustomerPca;

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public double getUserId() {
        return UserId;
    }

    public void setUserId(double UserId) {
        this.UserId = UserId;
    }

    public double getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(double CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }

    public double getSystemFlag() {
        return SystemFlag;
    }

    public void setSystemFlag(double SystemFlag) {
        this.SystemFlag = SystemFlag;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String RoleName) {
        this.RoleName = RoleName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String UserPhone) {
        this.UserPhone = UserPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String UserEmail) {
        this.UserEmail = UserEmail;
    }

    public String getUserHeadPicture() {
        return UserHeadPicture;
    }

    public void setUserHeadPicture(String UserHeadPicture) {
        this.UserHeadPicture = UserHeadPicture;
    }

    public String getIpAddress() {
        return IpAddress;
    }

    public void setIpAddress(String IpAddress) {
        this.IpAddress = IpAddress;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public int getCustomerPca() {
        return CustomerPca;
    }

    public void setCustomerPca(int CustomerPca) {
        this.CustomerPca = CustomerPca;
    }
}
