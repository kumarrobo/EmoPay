package com.example.emopay.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.emopay.R;

public class SplashScreen extends AppCompatActivity {
   ImageView imageView_splashicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView_splashicon=findViewById(R.id.iv_splashicon);

        Animation animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        imageView_splashicon.startAnimation(animFadeIn);
        animFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(i);
                finish();
               // startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //imageView_splashicon.animate().translationX(1400).setStartDelay(4000);
    }
}