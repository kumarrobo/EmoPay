package com.example.emopay.ui.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.emopay.R;
import com.example.emopay.model.CircleCode;
import com.example.emopay.model.MobileOperatorCode;
import com.example.emopay.model.Plans;
import com.example.emopay.ui.activity.MainActivity2;
import com.example.emopay.viewmodel.URLs;
import com.example.emopay.viewmodel.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static com.example.emopay.viewmodel.URLs.payrechargeapikey;
import static com.example.emopay.viewmodel.URLs.payrechargeusername;

public class MobileRechargeFragment extends Fragment implements View.OnClickListener, TextWatcher {
    TextInputEditText editText_mobno,editText_amount;
    AppCompatButton button_recharge,button_viewoffer;
    Spinner spinner_operator,spinner_circle;
    RadioButton radioButton_prepaid,radioButton_postpaid;
    LinearLayout linearLayout_buttons;
    List<String> circleList,mobileOperatorList,planList;
    ArrayList<MobileOperatorCode> mobileOperatorCodeList;
    ArrayList<CircleCode>circleCodeList;
    ArrayList<Plans>plansList;
    ArrayList<String> mobileOperatorNames,circleNames,planNames;
     AlertDialog dialog;
     String selectedcirclecode,selectedoperatorcode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmrnt_mobilerecharge,container,false);
        circleList =new ArrayList<>();
        mobileOperatorList =new ArrayList<>();
        planList =new ArrayList<>();

        mobileOperatorCodeList=new ArrayList<>();
        circleCodeList=new ArrayList<>();
        plansList=new ArrayList<>();

        mobileOperatorNames=new ArrayList<>();
        circleNames=new ArrayList<>();
        planNames=new ArrayList<>();

        radioButton_prepaid=view.findViewById(R.id.rb_prepaid);
        radioButton_postpaid=view.findViewById(R.id.rb_postpaid);
        editText_mobno=view.findViewById(R.id.ti_mobno);
        editText_amount=view.findViewById(R.id.ti_amount);
        button_recharge=view.findViewById(R.id.btn_recharge);
        button_viewoffer=view.findViewById(R.id.btn_viewoffer);
        spinner_operator=view.findViewById(R.id.spinner_operator);
        spinner_circle=view.findViewById(R.id.spinner_circle);
        linearLayout_buttons=view.findViewById(R.id.activemobilerechargebutton);
        radioButton_postpaid.setOnClickListener(this);
        radioButton_prepaid.setOnClickListener(this);
        editText_mobno.addTextChangedListener(this);
        button_recharge.setOnClickListener(this);
        button_viewoffer.setOnClickListener(this);

        mobileOperatorList = Arrays.asList(getResources().getStringArray(R.array.mobileoperator));
        circleList = Arrays.asList(getResources().getStringArray(R.array.circlecode));
        planList = Arrays.asList(getResources().getStringArray(R.array.mobileplans));

        getMobileOperatorCode(mobileOperatorList);
        getCircleCode(circleList);
        getPlans(planList);

        spinner_operator.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String operatorname=spinner_operator.getSelectedItem().toString();
                for (int i=0;i<mobileOperatorCodeList.size();i++)
                {
                    if(mobileOperatorCodeList.get(i).getOperatorname().contains(operatorname))
                    {
                        selectedoperatorcode=mobileOperatorCodeList.get(i).getOperatorcode();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_circle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String circlename=spinner_circle.getSelectedItem().toString();
                for (int i=0;i<circleCodeList.size();i++)
                {
                    if(circleCodeList.get(i).getCirclename().contains(circlename))
                    {
                        selectedcirclecode=circleCodeList.get(i).getCirclecode();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void getPlans(List<String> planList) {
        for (int i = 0; i < planList.size(); i++) {
            String singleRow = planList.get(i);

            StringTokenizer tokens = new StringTokenizer(singleRow, ",");
            String planname = tokens.nextToken();
            String plancode = tokens.nextToken();


            Plans plans=new Plans();
            plans.addPlanName(planname);
            plans.addPlanCode(plancode);
            plansList.add(plans);

        }

        for (int i = 0; i < plansList.size(); i++) {
            String singleRow = plansList.get(i).getPlanname();
            planNames.add(singleRow);
        }

        planNames.add(0,"Select Type");
    }

    private void getCircleCode(List<String> circleList) {
        for (int i = 0; i < circleList.size(); i++) {
            String singleRow = circleList.get(i);

            StringTokenizer tokens = new StringTokenizer(singleRow, ",");
            String circlename = tokens.nextToken();
            String circlecode = tokens.nextToken();

            CircleCode circleCode=new CircleCode();
            circleCode.addCircleName(circlename);
            circleCode.addCircleCode(circlecode);
            circleCodeList.add(circleCode);
        }

        for (int i = 0; i < circleCodeList.size(); i++) {
            String singleRow = circleCodeList.get(i).getCirclename();
            circleNames.add(singleRow);
        }

        circleNames.add(0,"Select Circle");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, circleNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_circle.setAdapter(spinnerArrayAdapter);
    }

    private void getMobileOperatorCode(List<String> mobileOperatorList) {
        for (int i = 0; i < mobileOperatorList.size(); i++) {
            String singleRow = mobileOperatorList.get(i);

            StringTokenizer tokens = new StringTokenizer(singleRow, ",");
            String first = tokens.nextToken();// this will contain "12345"
            String second = tokens.nextToken();// this will contain "mari@123"


            MobileOperatorCode mobileOperatorCode=new MobileOperatorCode();
            mobileOperatorCode.addMobileOperatorName(first);
            mobileOperatorCode.addMobileOperatorCode(second);
            mobileOperatorCodeList.add(mobileOperatorCode);
        }

        for (int i = 0; i < mobileOperatorCodeList.size(); i++) {
            String singleRow = mobileOperatorCodeList.get(i).getOperatorname();
            mobileOperatorNames.add(singleRow);
        }

        mobileOperatorNames.add(0,"Select Operator");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, mobileOperatorNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner_operator.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity2.hideBottomNav();
        ((MainActivity2) getActivity()).setActionBarTitle("Mobile");
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
        if(v.getId()==R.id.rb_postpaid)
        {
            spinner_circle.setVisibility(View.GONE);
            ColorStateList myColorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{getResources().getColor(R.color.colorAccent)}
                    },
                    new int[]{getResources().getColor(R.color.throughoutcolor)}
            );

            radioButton_postpaid.setButtonTintList(myColorStateList);
        }
        if(v.getId()==R.id.btn_recharge)
        {
            doMobileRecharge();
         }

        if(v.getId()==R.id.rb_prepaid)
        {
            spinner_circle.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.btn_viewoffer)
        {
            LayoutInflater inflater = getLayoutInflater();
            View alertLayout = inflater.inflate(R.layout.layout_custom_dialog, null);
             ImageButton imageButton_close = alertLayout.findViewById(R.id.imgbtn);
             Spinner spinnermobplans = alertLayout.findViewById(R.id.spinner_mobileplans);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, planNames);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spinnermobplans.setAdapter(spinnerArrayAdapter);
             imageButton_close.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     dialog.dismiss();
                 }
             });

            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            //alert.setTitle("Plans");
            // this is set the view from XML inside AlertDialog
            alert.setView(alertLayout);
            // disallow cancel of AlertDialog on click of back button and outside touch
            alert.setCancelable(false);

             dialog = alert.create();
             dialog.show();
        }
    }

    private void doMobileRecharge() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MOBILERECHARGE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("mobilerecharge",response);
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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("username",payrechargeusername);
                headers.put("apikey",payrechargeapikey );
                headers.put("no","7387318638" );
                headers.put("type","1" );
                headers.put("amount","1" );
                headers.put("operator","AT" );
                headers.put("circle","1" );
                headers.put("user","AT12334304" );
                headers.put("format","json" );
                Log.e("request", String.valueOf(headers));
                // Log.e("request", stringRequest);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length()==0)
        {
         linearLayout_buttons.setVisibility(View.INVISIBLE);
        }
        else {
            linearLayout_buttons.setVisibility(View.VISIBLE);
        }
        if(s.length()==10)
        {
            String mobno= editText_mobno.getText().toString();
            getMobileNumberInfo(mobno);
        }
    }

    private void getMobileNumberInfo(String mobno) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MOBILEINFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("mobileinfo",response);
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
             protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("username",payrechargeusername );
                headers.put("apikey",payrechargeapikey );
                headers.put("no",mobno );
                headers.put("format","json" );
                Log.e("request", String.valueOf(headers));
               // Log.e("request", stringRequest);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}