package com.example.emopay.model;

public class CircleCode {
    String circlename,circlecode;

    public void addCircleName(String circlename) {
        this.circlename=circlename;
    }

    public void addCircleCode(String circlecode) {
        this.circlecode=circlecode;
    }

    public String getCirclename() {
        return circlename;
    }

    public String getCirclecode() {
        return circlecode;
    }
}
