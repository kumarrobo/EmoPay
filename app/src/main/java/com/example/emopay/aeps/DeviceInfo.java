package com.example.emopay.aeps;

public class DeviceInfo {
    public String dpId;     // Unique code assigned to registered device provider
    public String rdsId;    //Unique ID of the certified registered device service.
    public String rdsVer;   //Registered devices service version
    public String dc;       //Unique Registered device code.
    public String mi;       //Registered device model ID
    public String mc;       //This attribute holds registered device public key certificate.   This is signed with device provider key


    public DeviceInfo(){}

    public DeviceInfo(String dpId, String rdsId, String rdsVer, String dc, String mi, String mc) {
        this.dpId = dpId;
        this.rdsId = rdsId;
        this.rdsVer = rdsVer;
        this.dc = dc;
        this.mi = mi;
        this.mc = mc;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getRdsId() {
        return rdsId;
    }

    public void setRdsId(String rdsId) {
        this.rdsId = rdsId;
    }

    public String getRdsVer() {
        return rdsVer;
    }

    public void setRdsVer(String rdsVer) {
        this.rdsVer = rdsVer;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }
}
