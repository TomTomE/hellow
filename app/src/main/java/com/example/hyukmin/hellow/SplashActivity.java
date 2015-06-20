package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by hyukmmin on 2015-06-01.
 */
public class SplashActivity extends Activity{

    Handler hd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        TextView splashTitle = (TextView) findViewById(R.id.splash_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "InterparkGothicBold.ttf"); //폰트 적용
        splashTitle.setTypeface(typeface); //폰트 적용
        hd = new Handler();
        hd.postDelayed(irun, 3500);
    }

    Runnable irun = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hd.removeCallbacks(irun);
    }

}
