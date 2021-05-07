package com.example.emopay.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.emopay.R;
import com.example.emopay.ui.activity.MainActivity2;
import com.google.android.material.textfield.TextInputEditText;

public class ElectricityBillPayFragment extends Fragment implements View.OnClickListener {
    TextInputEditText editText_accountid;
    AppCompatButton button_recharge;
    Spinner spinner_operator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmrnt_electricitybill,container,false);

        editText_accountid=view.findViewById(R.id.ti_account);
        button_recharge=view.findViewById(R.id.btn_recharge);
        spinner_operator=view.findViewById(R.id.spinner_operator);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity2.hideBottomNav();
        ((MainActivity2) getActivity()).setActionBarTitle("Electricity");
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity2.showBottomNav();
        ((MainActivity2) getActivity()).setActionBarTitle(getResources().getString(R.string.app_name));
    }

    @SuppressLint("NewApi")
    @Override
    public void onClick(View v) {
    }
}