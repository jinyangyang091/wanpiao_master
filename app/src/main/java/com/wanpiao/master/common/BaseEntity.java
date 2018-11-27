package com.wanpiao.master.common;

import java.io.Serializable;

/**
 * 描述: 基础的实体类
 * --------------------------------------------
 * 工程:
 *
 * @author Administrator
 */

public class BaseEntity<T> implements Serializable {
    private T data;
    private String messageId;
    private String error;
    private String message;
    private String timestamp;
    private T allData;
    private String token;
    private String token2;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken2(String token2) {
        this.token2 = token2;
    }

    public String getToken2() {
        return token2;
    }

    public void setAllData(T allData){
        this.allData = allData;
    }


    public T getAllData(){
        return  allData;
    }


}
