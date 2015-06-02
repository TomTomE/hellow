package com.example.hyukmin.hellow;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by hyukmmin on 2015-06-01.
 */
public class DetailActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
    }
}
