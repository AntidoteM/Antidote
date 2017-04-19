package com.example.mchat;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager vp;
    private LinearLayout ll;
    private int[] image = {R.drawable.c,R.drawable.v,R.drawable.x};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        vp =(ViewPager) findViewById(R.id.vp);
        ll =(LinearLayout) findViewById(R.id.ll);
        initdata();
    }
}
