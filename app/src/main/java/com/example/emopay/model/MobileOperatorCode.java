package com.example.emopay.model;

public class MobileOperatorCode {
    String operatorname;
    String operatorcode;
    public void addMobileOperatorName(String operatorname) {
        this.operatorname=operatorname;
    }

    public void addMobileOperatorCode(String operatorcode) {
        this.operatorcode=operatorcode;
    }

    public String getOperatorname() {
        return operatorname;
    }

    public String getOperatorcode() {
        return operatorcode;
    }
}
