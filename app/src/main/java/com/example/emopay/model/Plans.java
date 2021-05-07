package com.example.emopay.model;

public class Plans {
    String planname,plancode;
    public void addPlanName(String planname) {
        this.planname=planname;
    }

    public void addPlanCode(String plancode) {
        this.plancode=plancode;
    }
    public String getPlanname() {
        return planname;
    }

    public String getPlancode() {
        return plancode;
    }
}
