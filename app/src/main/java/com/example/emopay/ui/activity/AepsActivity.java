package com.example.emopay.ui.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emopay.ui.fragment.BankListFragment;
import com.example.emopay.R;

public class AepsActivity extends BaseActivity {
    AppCompatButton button_withdrawl;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeps);

        //show the icon
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.edtemo);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);  //hide back arrow from left corner
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ef5f11")));
        }

        button_withdrawl=findViewById(R.id.btn_withdrawl);
        relativeLayout=findViewById(R.id.relativeLayout);
        button_withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transactiontype=button_withdrawl.getText().toString();
                FragmentManager fragmentManager = getSupportFragmentManager ();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction ();

                BankListFragment myfragment = new BankListFragment();  //your fragment
                Bundle args = new Bundle();
                args.putString("transactiontype",transactiontype);
                myfragment.setArguments(args);
                // work here to add, remove, etc
                fragmentTransaction.add (R.id.fragment_container, myfragment);
                fragmentTransaction.commit ();

               /* relativeLayout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new BankListFragment()).addToBackStack(null).commit();*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm=getSupportFragmentManager();
       /* if (fm.getBackStackEntryCount() > 0) {
            relativeLayout.setVisibility(View.VISIBLE);
        }*/
        super.onBackPressed();
    }
}