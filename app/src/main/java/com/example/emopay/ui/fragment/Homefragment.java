package com.example.emopay.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.emopay.R;
import com.example.emopay.ui.activity.AepsActivity;
import com.example.emopay.ui.activity.DMTActivity;

import org.jetbrains.annotations.NotNull;

public class Homefragment extends Fragment implements View.OnClickListener {
    CardView cardView_aeps,cardView_dmt,cardView_mobilerechare,cardView_dthrecharge,cardView_electricity,cardView_water,cardView_gas;
     Context mContext;
    public Homefragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardView_aeps=root.findViewById(R.id.cv_aeps);
        cardView_mobilerechare=root.findViewById(R.id.cv_mobilerecherge);
        cardView_dthrecharge=root.findViewById(R.id.cv_dth);
        cardView_electricity=root.findViewById(R.id.cv_electricity);
        cardView_water=root.findViewById(R.id.cv_water);
        cardView_gas=root.findViewById(R.id.cv_gas);
        cardView_dmt=root.findViewById(R.id.cv_moneytransfer);

        cardView_mobilerechare.setOnClickListener(this);
        cardView_water.setOnClickListener(this);
        cardView_electricity.setOnClickListener(this);
        cardView_dthrecharge.setOnClickListener(this);
        cardView_aeps.setOnClickListener(this);
        cardView_gas.setOnClickListener(this);
        cardView_dmt.setOnClickListener(this);

     return root;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.cv_mobilerecherge)
        {
            MobileRechargeFragment mobileRechargeFragment=new MobileRechargeFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homecontainer,mobileRechargeFragment,"MobileRechargeFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.cv_dth)
        {
            DTHRechargeFragment dthRechargeFragment=new DTHRechargeFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homecontainer,dthRechargeFragment,"DTHRechargeFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.cv_water)
        {
            WaterBillPayFragment waterBillPayFragment=new WaterBillPayFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homecontainer,waterBillPayFragment,"WaterBillPayFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.cv_gas)
        {
            GasBillPayFragment gasBillPayFragment=new GasBillPayFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homecontainer,gasBillPayFragment,"GasBillPayFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.cv_electricity)
        {
            ElectricityBillPayFragment electricityBillPayFragment=new ElectricityBillPayFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.homecontainer,electricityBillPayFragment,"ElectricityBillPayFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(v.getId()==R.id.cv_aeps)
        {
            Intent intent=new Intent(getActivity(), AepsActivity.class);
            startActivity(intent);
        }
        if(v.getId()==R.id.cv_moneytransfer)
        {
            Intent intent=new Intent(getActivity(), DMTActivity.class);
            startActivity(intent);
        }
    }
}