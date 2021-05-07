package com.example.emopay.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.emopay.R;

import java.util.ArrayList;
import java.util.List;

public class BimetricDeviceSelectionActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
     Spinner spinner;
     List<String> bankNames;
     Button button_capture;
     String scannerType;
     String adharno,mobno,amount,transactiontype,selectedbank;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometricselection);

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
        } /*else {
            // handle case
        }*/

        spinner=findViewById(R.id.spinner);
        button_capture=findViewById(R.id.btn_capture);
        bankNames=new ArrayList<>();
        bankNames.add("select device");
        bankNames.add("Mantra MFS100");
        bankNames.add("Morpho");

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, bankNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);

        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(scannerType)){

                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setError("select device");
                    errorText.setTextColor(Color.RED);      //just to highlight that this is an error
                    errorText.setText("select device");     //changes the selected item text to this
                }
                if (scannerType.equalsIgnoreCase("select device"))
                {
                    ((TextView)spinner.getChildAt(0)).setError("select device");
                }

                if(!TextUtils.isEmpty(scannerType)&&!scannerType.equalsIgnoreCase("select device")) {
                    Intent intent = new Intent(BimetricDeviceSelectionActivity.this, AepsTransaction.class);
                    intent.putExtra("scannertype", scannerType);
                    intent.putExtra("transactiontype", transactiontype);
                    intent.putExtra("adharno", adharno);
                    intent.putExtra("mobno", mobno);
                    intent.putExtra("amount", amount);
                    intent.putExtra("selectedbank", selectedbank);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         scannerType=spinner.getSelectedItem().toString();
        String scannerType1=parent.getItemAtPosition(position).toString();
        Log.e("selected",scannerType);
        Log.e("selected1",scannerType1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}