package com.findingdata.oabank.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2019/11/21.
 * Version: 1.0
 * Describe: 通用实体，用于JSON解析
 */
public class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * code : 200
     * data : {"f_id":"44e21750-0c56-11ea-8003-9f39673eebae"}
     * msg : 上传附件成功
     */

    private boolean Status;
    private T Result;
    private String Message;

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
