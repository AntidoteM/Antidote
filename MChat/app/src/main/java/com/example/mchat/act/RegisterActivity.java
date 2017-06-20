package com.example.mchat.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mchat.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by 杜明 on 2017/5/3.
 */

public class RegisterActivity extends BaseActivity {
    private EditText register_username;
    private EditText register_password;
    private Button register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = register_username.getText().toString();
                String password = register_password.getText().toString();
                try {
                    EMClient.getInstance().createAccount(username, password);//同步方法
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        register_username = (EditText) findViewById(R.id.register_username);
        register_password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.register);
    }
}
