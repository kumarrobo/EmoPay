package com.example.emopay.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.emopay.R;
import com.example.emopay.model.Bank;
import com.example.emopay.ui.activity.BimetricDeviceSelectionActivity;
import com.example.emopay.viewmodel.URLs;
import com.example.emopay.viewmodel.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.emopay.viewmodel.SharedPrefManager.KEY_TOKEN;
import static com.example.emopay.viewmodel.SharedPrefManager.KEY_TOKENTYPE;
import static com.example.emopay.viewmodel.SharedPrefManager.SHARED_PREF_NAME;

public class BankListFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    String token,tokentype;
    //the bank list where we will store all the hero objects after parsing json
    List<Bank> bankList;
    List<String>bankNames;
    TextInputEditText textInputEditText_adharno,textInputEditText_mobno,textInputEditText_amount;
    AppCompatButton button_continue;
    Spinner spinner;
    String transactiontype,selectedbank;
    public BankListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bank_list, container, false);
        // this = your fragment
        SharedPreferences preferences = getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        textInputEditText_adharno=view.findViewById(R.id.ti_adharno);
        textInputEditText_mobno=view.findViewById(R.id.ti_mobno);
        textInputEditText_amount=view.findViewById(R.id.ti_amount);
        button_continue=view.findViewById(R.id.btn_continue);
        spinner=view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        transactiontype=mBundle.getString("transactiontype");  // key must be same which was given in first fragment

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //first getting the values
                 String adharno = textInputEditText_adharno.getText().toString();
                 String mobno = textInputEditText_mobno.getText().toString();
                 String amount = textInputEditText_amount.getText().toString();

                //validating inputs
                if (TextUtils.isEmpty(adharno)) {
                    textInputEditText_adharno.setError("Please enter your adharno");
                    textInputEditText_adharno.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(mobno)) {
                    textInputEditText_mobno.setError("Please enter your mobileno");
                    textInputEditText_mobno.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(amount)) {
                    textInputEditText_amount.setError("Please enter amount");
                    textInputEditText_amount.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(selectedbank)){

                    TextView errorText = (TextView)spinner.getSelectedView();
                    errorText.setError("select bank");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("select bank");//changes the selected item text to this*/
                }
                if (selectedbank.equalsIgnoreCase("select bank"))
                {
                    ((TextView)spinner.getChildAt(0)).setError("select momething");
                }
                /*if (spinner.getSelectedItem().toString().trim().equals("select bank")) {
                    ((TextView)spinner.getChildAt(0)).setError("select bank");
                    Toast.makeText(getActivity(), "Select bank", Toast.LENGTH_SHORT).show();
                }*/

                if (!TextUtils.isEmpty(adharno) && !TextUtils.isEmpty(mobno)&&!selectedbank.equalsIgnoreCase("select bank")) {

                    Intent intent=new Intent(getActivity(), BimetricDeviceSelectionActivity.class);
                    intent.putExtra("transactiontype",transactiontype);
                    intent.putExtra("adharno",adharno);
                    intent.putExtra("mobno",mobno);
                    intent.putExtra("amount",amount);
                    intent.putExtra("selectedbank",selectedbank);
                    startActivity(intent);
                   // startActivity(new Intent(getActivity(), BimetricDeviceSelectionActivity.class));
                }
            }
        });
        bankList = new ArrayList<>();
        bankNames = new ArrayList<>();
        loadBankList(this);
        return  view;
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        token=preferences.getString(KEY_TOKEN,"");
        tokentype=preferences.getString(KEY_TOKENTYPE,"");
    }

    private void loadBankList(BankListFragment bankListFragment) {
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_BANKLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("BankListFragment",response);
                            JSONArray jsonArray=jsonObject.getJSONArray("codeValues");
                            //now looping through all the elements of the json array
                            for (int i = 0; i < jsonArray.length(); i++) {
                                //getting the json object of the particular index inside the array
                                JSONObject bankObject = jsonArray.getJSONObject(i);

                                 String code=bankObject.getString("code");
                                 String bankname=bankObject.getString("value");
                                //creating a bank object and giving them the values from json object
                                Bank bank = new Bank();
                                bank.setCode(code);
                                bank.setBankName(bankname);
                                //adding the bank to banklist
                                bankList.add(bank);
                            }
                            for (int i = 0; i < bankList.size(); i++){
                                bankNames.add(bankList.get(i).getBankName().toString());
                            }
                            bankNames.add(0,"select bank");

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, bankNames);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinner.setAdapter(spinnerArrayAdapter);

                         } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = tokentype+" "+token;
                headers.put("Authorization",credentials );
                return headers;}
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String scannerType1=parent.getItemAtPosition(position).toString();
          selectedbank=parent.getItemAtPosition(position).toString();
        Log.e("selectedbank",selectedbank);
        Log.e("selectedbank1",scannerType1);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}