package com.example.mchat.act;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

/*
 * Created by 杜明 on 2017/4/20.
 */

public class BaseActivity extends AppCompatActivity {
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void toRegister(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void toMain() {
        EMClient.getInstance().chatManager().loadAllConversations();
        EMClient.getInstance().groupManager().loadAllGroups();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
