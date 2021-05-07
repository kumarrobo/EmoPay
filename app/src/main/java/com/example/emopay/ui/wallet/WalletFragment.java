package com.example.emopay.ui.wallet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emopay.ui.fragment.DMTAddBalanceFragment;
import com.example.emopay.ui.fragment.DMTWithdrawBalanceFragment;
import com.example.emopay.R;

public class WalletFragment extends Fragment implements View.OnClickListener {
    AppCompatButton button_walletbal,button_addbal,button_withdraw;

    public static WalletFragment newInstance() {
        return new WalletFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        button_walletbal=root.findViewById(R.id.btn_walletbalance);
        button_addbal=root.findViewById(R.id.btn_addbalance);
        button_withdraw=root.findViewById(R.id.btn_withdraw);

        button_walletbal.setOnClickListener(this);
        button_addbal.setOnClickListener(this);
        button_withdraw.setOnClickListener(this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        int viewid=v.getId();
        if(viewid==R.id.btn_walletbalance)
        {

        }
        if(viewid==R.id.btn_addbalance)
        {
            DMTAddBalanceFragment dmtAddBalanceFragment=new DMTAddBalanceFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dmtrelativeLayout,dmtAddBalanceFragment,"DMTAddBalanceFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        if(viewid==R.id.btn_withdraw)
        {
            DMTWithdrawBalanceFragment dmtWithdrawBalanceFragment=new DMTWithdrawBalanceFragment();
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.dmtrelativeLayout,dmtWithdrawBalanceFragment,"DMTWithdrawBalanceFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}