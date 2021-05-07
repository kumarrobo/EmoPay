package com.example.emopay.model;

public class User {
    String token;
    String tokentype;
    long currentTimeMillis;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokentype() {
        return tokentype;
    }

    public void setTokentype(String tokentype) {
        this.tokentype = tokentype;
    }

    public void setLastInteractionTime(long currentTimeMillis) {
        this.currentTimeMillis=currentTimeMillis;
    }

    public void setMemberId(String member_id) {
    }

    public void setPhoneNumber(String phone) {
    }
}
