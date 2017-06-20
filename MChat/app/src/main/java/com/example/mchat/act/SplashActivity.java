package com.example.mchat.act;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.example.mchat.R;
import com.hyphenate.chat.EMClient;


public class SplashActivity extends BaseActivity {
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            toNext();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    public void onBackPressed() {
    }

    private void toNext() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            toMain();
        } else {
            toLogin();
        }
        SplashActivity.this.finish();
    }
}
