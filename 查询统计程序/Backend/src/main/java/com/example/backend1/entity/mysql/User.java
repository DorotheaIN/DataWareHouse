package com.example.backend1.entity.mysql;

import lombok.Data;
import org.apache.ibatis.type.Alias;

public class User {

    private String USER_EMAIL;

    private String USER_NAME;

    private String PASSWORD;

    private String INTRODUCTION;

    private String AVATOR;

    public String getUSER_EMAIL() {
        return USER_EMAIL;
    }

    public void setUSER_EMAIL(String USER_EMAIL) {
        this.USER_EMAIL = USER_EMAIL;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getINTRODUCTION() {
        return INTRODUCTION;
    }

    public void setINTRODUCTION(String INTRODUCTION) {
        this.INTRODUCTION = INTRODUCTION;
    }

    public String getAVATOR() {
        return AVATOR;
    }

    public void setAVATOR(String AVATOR) {
        this.AVATOR = AVATOR;
    }

    public User(String USER_EMAIL, String PASSWORD) {
        this.USER_EMAIL = USER_EMAIL;
        this.PASSWORD = PASSWORD;
    }
}

