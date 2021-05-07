package com.example.emopay.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.emopay.R;
import com.example.emopay.ui.activity.MainActivity2;
import com.google.android.material.textfield.TextInputEditText;

public class DTHRechargeFragment extends Fragment implements View.OnClickListener {
    TextInputEditText editText_mobno,editText_amount;
    AppCompatButton button_save,button_viewoffer;
    Spinner spinner_operator,spinner_circle;
    LinearLayout linearLayout_buttons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmrnt_dthrecharge,container,false);

        editText_mobno=view.findViewById(R.id.ti_mobno);
        editText_amount=view.findViewById(R.id.ti_amount);
        button_save=view.findViewById(R.id.btn_save);
        button_viewoffer=view.findViewById(R.id.btn_viewoffer);
        spinner_operator=view.findViewById(R.id.spinner_operator);
        spinner_circle=view.findViewById(R.id.spinner_dthcircle);
        linearLayout_buttons=view.findViewById(R.id.activemobilerechargebutton);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity2.hideBottomNav();
        ((MainActivity2) getActivity()).setActionBarTitle("DTH");
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