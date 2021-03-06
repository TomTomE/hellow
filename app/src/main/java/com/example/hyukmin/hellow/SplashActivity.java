package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by hyukmmin on 2015-06-01.
 */
public class SplashActivity extends Activity {

    Handler hd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        hd = new Handler();
        hd.postDelayed(irun, 3500);
    }

    Runnable irun = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hd.removeCallbacks(irun);
    }

}
