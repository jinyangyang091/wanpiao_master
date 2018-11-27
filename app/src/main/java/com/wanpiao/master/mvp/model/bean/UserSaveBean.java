package com.wanpiao.master.mvp.model.bean;

import androidx.annotation.NonNull;

public class UserSaveBean {
    private String userAccount;
    private String userPassword;
    private String userName;
    private String id;
    private String userSex;

    public void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSex() {
        return userSex;
    }

    @NonNull
    @Override
    public String toString() {
        return "UserSaveBean [userAccount=" + userAccount + ", userPassword=" + userPassword + ", userName=" + userName
                + ", id=" + id + ", tel=" + userSex + ", sex=" + userSex
                + "]";
    }
}
