package com.example.emopay.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.emopay.R;

public class MainActivity extends BaseActivity {
    CardView cardView_aeps;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //show the icon
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
           // getSupportActionBar().setHomeAsUpIndicator(R.drawable.edtemo);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);    //hide back icon in actionbar
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ef5f11")));

            //display custom layout in android
            LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
            @SuppressLint("InflateParams") View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
            TextView walletbal = (TextView) mCustomView.findViewById(R.id.title_walletbal);
            walletbal.setText("1000");

            actionBar.setCustomView(mCustomView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        cardView_aeps=findViewById(R.id.cv_aeps);
        cardView_aeps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AepsActivity.class);
                startActivity(intent);
            }
        });
    }
}