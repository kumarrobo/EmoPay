package com.example.emopay.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.emopay.ui.util.Logger;
import com.example.emopay.aeps.AepsCaptureResponseModel;
import com.example.emopay.aeps.CaptureResponse;
import com.example.emopay.aeps.DeviceInfo;
import com.example.emopay.aeps.RDServiceInfo;
import com.example.emopay.aeps.SplitXML;

public class AepsTransaction extends BaseActivity {
    public  static final String TAG=AepsTransaction.class.getSimpleName();
    public static final String CUSTOM_ACTION_INFO_FINGERPRINT = "in.gov.uidai.rdservice.fp.INFO";
    public static final String CUSTOM_ACTION_CAPTURE_FINGERPRINT = "in.gov.uidai.rdservice.fp.CAPTURE";

    private static final int INFO_REQUEST = 0;
    private static final int CAPTURE_REQUEST = 1;

    private static final String DeviceINFO_KEY = "DEVICE_INFO";
    private static final String RD_SERVICE_INFO = "RD_SERVICE_INFO";
    private static final String PID_DATA = "PID_DATA";
    private static final String PID_OPTIONS = "PID_OPTIONS";

    protected DeviceInfo deviceInfo = new DeviceInfo();
    protected RDServiceInfo rdServiceInfo = new RDServiceInfo();
     boolean isPreProduction=true;
    // String adharno="SXXps5837B";
    String adharno,mobno,amount,transactiontype,selectedbank,scannertype;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //show the icon
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.edtemo);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);    //hide back icon in actionbar
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ef5f11")));
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            transactiontype = extras.getString("transactiontype");
            adharno = extras.getString("adharno");
            mobno = extras.getString("mobno");
            amount = extras.getString("amount");
            selectedbank = extras.getString("selectedbank");
            scannertype = extras.getString("scannertype");
        }

        openFPSensor();
    }

    public void openFPSensor() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(this.CUSTOM_ACTION_INFO_FINGERPRINT);
            this.startActivityForResult(sendIntent, INFO_REQUEST);
        } catch (ActivityNotFoundException var4) {
            var4.printStackTrace();
           // if (getContext() != null)
                Toast.makeText(AepsTransaction.this, "Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String piddataxml;
        String text;
        String captureRequestXML;
        String s;
        try {
            if (data != null) {
                if (requestCode == 0) {
                    piddataxml = data.getStringExtra("DNC");
                    text = data.getStringExtra("DNR");
                    String strDeviceInfo = data.getStringExtra(this.DeviceINFO_KEY);
                    String strRDServiceInfo = data.getStringExtra(this.RD_SERVICE_INFO);

                    // String appName = data.getComponent().flattenToShortString();
                    //  Log.e("Shetty App Name", appName);
                    Log.e("Shetty D Info", strDeviceInfo);
                    Logger.e(AepsTransaction.this,"deviceinfo",strDeviceInfo);
                    Logger.e(AepsTransaction.this,"rdserviceInfo",strRDServiceInfo);
                    if(!TextUtils.isEmpty(piddataxml)) {
                        Logger.e(AepsTransaction.this, "DNC", piddataxml);
                    }

                    if (piddataxml != null) {
                        showToast("Device is not registered");
                    } else if (text != null) {
                        showToast("Device is not registered");
                    } else if (strRDServiceInfo != null && !strRDServiceInfo.isEmpty()) {
                        this.rdServiceInfo = (new SplitXML()).SplitRDServiceInfo(strRDServiceInfo);
                        if (this.rdServiceInfo != null) {
                            if (this.rdServiceInfo.status.equalsIgnoreCase("Ready")) {
                                if (isValidString(strDeviceInfo)) {
                                    this.deviceInfo = (new SplitXML()).SplitDeviceInfo(strDeviceInfo);
                                    String enviroment = "P";
                                    if (isPreProduction) {
                                        enviroment = "PP";
                                    }

                                    switch (getScannerType(strDeviceInfo)) {
                                        case 0:
                                            //device is next biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" /><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        case 1:
                                            //device is Precision  biometric
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\" wadh=\"\" posh=\"UNKNOWN\" /><Demo></Demo><CustOpts><Param name=\"\" value=\"\" /></CustOpts></PidOptions>";
                                            break;
                                        case 2:
                                            //device is Tatvik  biometric
                                            captureRequestXML = "<PidOptions ver=\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" iCount=\"0\" iType=\"\" pCount=\"0\" pType=\"\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\" wadh=\"\" posh=\"UNKNOWN\" /><Demo></Demo><CustOpts><Param name=\"\" value=\"\" /></CustOpts></PidOptions>";
                                            break;
                                        case 3:
                                            //device is Mantra  biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        case 4:
                                            //device is Morpho  biometric
                                            captureRequestXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><PidOptions ver =\"1.0\"><Opts env=\"" + enviroment + "\" fCount=\"1\" fType=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"5000\"/><Demo></Demo><CustOpts></CustOpts></PidOptions>";
                                            break;
                                        default:
                                            captureRequestXML = null;
                                            break;
                                    }
                                    Log.d(TAG, "onActivityResult: " + captureRequestXML);
                                    if (captureRequestXML != null && !captureRequestXML.isEmpty()) {
                                        Intent sendIntent = new Intent();
                                        sendIntent.setAction(this.CUSTOM_ACTION_CAPTURE_FINGERPRINT);
                                        sendIntent.putExtra(this.PID_OPTIONS, captureRequestXML);
                                        this.startActivityForResult(sendIntent, this.CAPTURE_REQUEST);
                                    }
                                } else {
                                    //Toast.makeText(mContext, "dev_info_null", Toast.LENGTH_SHORT).show();
                                    showToast("Device information not found");
                                }
                            } else {
                                //Toast.makeText(mContext, "dev_not_ready", Toast.LENGTH_SHORT).show();
                                showToast("Device is not ready");
                            }
                        }
                    } else {
                        //Toast.makeText(mContext, "rd_info_empty", Toast.LENGTH_SHORT).show();
                        showToast("RD information is null");
                    }
                } else {
                    String rdsId;
                    String rdsVer;
                    String dc;
                    String mi;
                    String mc;
                    String dpId;
                    if (requestCode == 1) {

                        piddataxml = data.getStringExtra(this.PID_DATA);

                        int maxLogSize = 4000;
                        for(int i = 0; i <= piddataxml.length() / maxLogSize; i++) {
                            int start = i * maxLogSize;
                            int end = (i+1) * maxLogSize;
                            end = Math.min(end, piddataxml.length());
                           // end = end > piddataxml.length() ? piddataxml.length() : end;
                           Logger.e(AepsTransaction.this,"PID_DATA", piddataxml.substring(start, end));
                        }

                        Logger.e(AepsTransaction.this,"PID_DATA",piddataxml);

                        if (isValidString(piddataxml)) {

                            SplitXML splitXML = new SplitXML();
                            CaptureResponse response = splitXML.SplitCaptureResponse(piddataxml);
                            String captureres= String.valueOf(response);

                            int maxLogSize1 = 4000;
                            for(int i = 0; i <= captureres.length() / maxLogSize1; i++) {
                                int start = i * maxLogSize1;
                                int end = (i+1) * maxLogSize1;
                                end = Math.min(end, captureres.length());
                                // end = end > piddataxml.length() ? piddataxml.length() : end;
                                Logger.e(AepsTransaction.this,"CaptureResponse", captureres.substring(start, end));
                            }

                           // Logger.e(AepsTransaction.this,"CaptureResponse", String.valueOf(response));

                            AepsCaptureResponseModel aepsCaptureResponseModel = splitXML.SplitAepsCaptureResponseModel(piddataxml);
                            Log.d(TAG, "onActivityResult: " + aepsCaptureResponseModel.getJSON(adharno));
                            if (aepsCaptureResponseModel.getJSON(adharno) != null) {
                                if (aepsCaptureResponseModel.getJSON(adharno).getString("errCode").equalsIgnoreCase("0") || aepsCaptureResponseModel.getJSON(adharno).getString("errCode").equalsIgnoreCase("00")) {
                                    Log.d(TAG, "onActivityResult: send");
                                    Toast.makeText(getApplicationContext(),"response from capture",Toast.LENGTH_LONG).show();
                                    //TODO : You can code your logic here.
                                    //TODO : You will get fingerprint data as output in piddataxml.

                                } else {
                                    Log.d(TAG, "onActivityResult: not send");
                                   // showDialog("Device Capture Failed", aepsCaptureResponseModel.getJSON(adharno).getString("errInfo"), true);
                                }
                            }

                        } else {
                            showToast("captured_xml_null");
                        }
                    }
                }
            }
        } catch (Exception e) {
            showToast("");
            e.printStackTrace();
        }
    }

    private int getScannerType(String strDeviceInfo) {
        return 3;
    }

    private boolean isValidString(String strDeviceInfo) {
        return true;
    }

    private void showToast(String device_is_not_registered) {
        Toast.makeText(AepsTransaction.this,device_is_not_registered,Toast.LENGTH_LONG).show();
    }
}