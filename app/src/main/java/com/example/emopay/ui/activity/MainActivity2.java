package com.example.emopay.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.emopay.ui.fragment.Homefragment;
import com.example.emopay.R;
import com.example.emopay.ui.history.HistoryFragment;
import com.example.emopay.ui.profile.ProfileFragment;
import com.example.emopay.ui.wallet.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends BaseActivity {

     String selectedFragmentName;
    static BottomNavigationView bottomNav;
     ColorStateList ColorStateList2;
    private TextView textView_actionbattitle;

    public static void hideBottomNav() {
        bottomNav.setVisibility(View.GONE);
    }

    public static void showBottomNav() {
        bottomNav.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //show the icon
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.edtemo);// set drawable icon
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);    //hide back icon in actionbar
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ef5f11")));

            //display custom layout in android
            LayoutInflater mInflater = LayoutInflater.from(MainActivity2.this);
            @SuppressLint("InflateParams") View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
            TextView walletbal = (TextView) mCustomView.findViewById(R.id.title_walletbal);
             textView_actionbattitle = (TextView) mCustomView.findViewById(R.id.title_appname);
            walletbal.setText("1000");
            walletbal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new WalletFragment()).addToBackStack(Homefragment.class.getSimpleName()).commit();
                }
            });

            actionBar.setCustomView(mCustomView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_enabled}, // disabled
                new int[] {android.R.attr.state_enabled}, // enabled
                new int[] {-android.R.attr.state_checked}, // unchecked
                new int[] { android.R.attr.state_pressed}  // pressed

        };

        int[] colors = new int[] {
                Color.WHITE,
                Color.BLUE,
                Color.WHITE,
                Color.WHITE
        };

         ColorStateList2 = new ColorStateList(states, colors);

         bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Homefragment()).commit();
        }
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                       /* case R.id.navigation_move_to_bank:
                            selectedFragment = new MoveToBankFragment();
                            break;*/
                        case R.id.navigation_home:
                            selectedFragment = new Homefragment();
                            selectedFragmentName=Homefragment.class.getSimpleName();
                            //  bottomNav.setItemIconTintList(ContextCompat.getColorStateList(MainActivity2.this,R.color.throughoutcolor));
                            break;
                        case R.id.navigation_profile:
                            selectedFragment = new ProfileFragment();
                            selectedFragmentName=ProfileFragment.class.getSimpleName();
                            //bottomNav.setItemIconTintList(ContextCompat.getColorStateList(MainActivity2.this,R.color.throughoutcolor));

                            break;
                        /*case R.id.navigation_qrcode:
                            selectedFragment = new QRCodeFragment();
                            break;*/
                         case R.id.navigation_wallet:
                            selectedFragment = new WalletFragment();
                             selectedFragmentName=WalletFragment.class.getSimpleName();
                             break;
                        case R.id.navigation_history:
                            selectedFragment = new HistoryFragment();
                            selectedFragmentName=HistoryFragment.class.getSimpleName();
                            break;
                    }
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment,selectedFragmentName).addToBackStack(selectedFragmentName).commit();
                    }
                    return true;
                }
            };

    @Override
    public void onBackPressed()
    {
        Log.e("MainActivity2","onBackPressed");
      /* // Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(selectedFragmentName);

        if(fragment instanceof Homefragment&&fragment.isVisible())
        {
            Toast.makeText(MainActivity2.this,"Homefragment",Toast.LENGTH_LONG).show();
            //bottomNav.setItemIconTintList(ColorStateList2 );
        }*/
            super.onBackPressed();
    }

    public void setActionBarTitle(String actionBarTitle) {
        //getSupportActionBar().setTitle(actionBarTitle);
        textView_actionbattitle.setText(actionBarTitle);
    }
}