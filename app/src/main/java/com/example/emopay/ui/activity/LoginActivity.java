package com.example.emopay.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.emopay.ui.util.GPSTracker;
import com.example.emopay.ui.util.Logger;
import com.example.emopay.R;
import com.example.emopay.model.User;
import com.example.emopay.viewmodel.SharedPrefManager;
import com.example.emopay.viewmodel.URLs;
import com.example.emopay.viewmodel.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    TextInputEditText editTextEmail,editTextPassword;
    TextView textView_forgotpassword,textView_register;
    private String latitude;
    private  String longitude;
     GPSTracker gps;
     CircularProgressButton cirLoginButton;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //show the icon
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
           // getSupportActionBar().setHomeAsUpIndicator(R.drawable.edtemo);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); //hide back icon in actionbar
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ef5f11")));
        }

      /*  //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //11CFC5
        }*/
        
       // SharedPrefManager.getInstance(getApplicationContext()).checkLogin();

        // create class object
        gps = new GPSTracker(LoginActivity.this);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            double latitudel = gps.getLatitude();
            double longitudel = gps.getLongitude();

            latitude= String.valueOf(latitudel);
            longitude= String.valueOf(longitudel);

            Log.e("Latitude:" , String.valueOf(latitudel));
            Log.e("longitude:" , String.valueOf(longitudel));
            Logger.e(LoginActivity.this,"latitude",latitude);
            Logger.e(LoginActivity.this,"longitude",longitude);

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network i
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please allow location")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                            //do things
/*
                            if (ActivityCompat.checkSelfPermission(LoginActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{
                                        android.Manifest.permission.ACCESS_FINE_LOCATION
                                }, 10);
                            }*/
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
           // Toast.makeText(LoginActivity.this,"not enabled",Toast.LENGTH_LONG).show();
        }

        editTextEmail=findViewById(R.id.inputEmail);
        editTextPassword=findViewById(R.id.inputPassword);
        editTextPassword=findViewById(R.id.inputPassword);
        textView_forgotpassword=findViewById(R.id.tv_forgotPassword);
        textView_register=findViewById(R.id.tv_gotoRegister);
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }
        });
        cirLoginButton = findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

       /* //calling the method userLogin() for login the user
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 1:
                    //Toast.makeText(LoginActivity.this,"onactivityresult",Toast.LENGTH_LONG).show();
                    getGpsLocation();
                    break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onresume");
        //getGpsLocation();
    }

    private void getGpsLocation() {
        Log.e("insidemethod","getGpsLocation");

        // create class object
        gps = new GPSTracker(LoginActivity.this);

        if (gps.canGetLocation()) {
            double latitudel = gps.getLatitude();
            double longitudel = gps.getLongitude();

            latitude= String.valueOf(latitudel);
            longitude= String.valueOf(longitudel);

            Log.e("Latitudefromsetting:" , String.valueOf(latitudel));
            Log.e("longitudefromsetting:" , String.valueOf(longitudel));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions, grantResults);
        if (requestCode == 10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this,
                        " Permission granted",
                        Toast.LENGTH_SHORT)
                        .show();
                //Permission Granted
                //Do your work here
                //Perform operations here only which requires permission
                finish();
                startActivity(getIntent());
            }
            else {
                Toast.makeText(LoginActivity.this,
                        " Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            cirLoginButton.startAnimation();

            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            cirLoginButton.stopAnimation();

                            try {
                                //converting response to json object
                                JSONObject jsonObject = new JSONObject(response);
                                Log.i("response", response);

                                String token = jsonObject.getString("token");
                                String tokentype = jsonObject.getString("token_type");
                                String member_id = jsonObject.getString("member_id");
                                String phone = jsonObject.getString("phone");

                                User user = new User();
                                user.setToken(token);
                                user.setTokentype(tokentype);
                                user.setMemberId(member_id);
                                user.setPhoneNumber(phone);

                                // user.setLastInteractionTime(System.currentTimeMillis());
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the Dashboard activity
                                finish();
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

                            } catch (JSONException e) {
                                cirLoginButton.stopAnimation();
                                 cirLoginButton.revertAnimation();
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            cirLoginButton.stopAnimation();
                            cirLoginButton.revertAnimation();

                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("member_id", username);
                    params.put("latitude", latitude);
                    params.put("longitude", longitude);
                    params.put("password", password);
                    return params;
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

}