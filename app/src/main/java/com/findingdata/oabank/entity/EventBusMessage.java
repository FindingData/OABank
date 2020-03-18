package com.findingdata.oabank.entity;

/**
 * Created by Loong on 2019/11/26.
 * Version: 1.0
 * Describe: 通用EventBusMessage
 */
public class EventBusMessage<T> {
    private String message;
    private T data;

    public EventBusMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
