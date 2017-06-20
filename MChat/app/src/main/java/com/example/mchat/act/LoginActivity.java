package com.example.mchat.act;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mchat.R;
import com.example.mchat.utils.SPUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ProgressBar pb;
    private EditText edit_login_username;
    private EditText edit_login_password;
    private Button login;
    private TextView register;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            pb.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void init() {
        pb = (ProgressBar) findViewById(R.id.pb);
        edit_login_username = (EditText) findViewById(R.id.edit_login_username);
        edit_login_password = (EditText) findViewById(R.id.edit_login_password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        edit_login_username.setText(SPUtil.getLastName(LoginActivity.this));
    }

    @Override
    public void onClick(View v) {
        pb.setVisibility(View.VISIBLE);
        switch (v.getId()) {
            case R.id.login:
                final String username = edit_login_username.getText().toString();
                String password = edit_login_password.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    EMClient.getInstance().login(username, password, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            handler.sendEmptyMessage(1);
                            SPUtil.setLastName(LoginActivity.this, username);
                            toMain();
                            finish();
                        }

                        @Override
                        public void onError(int i, String s) {
                        }

                        @Override
                        public void onProgress(int i, String s) {
                        }
                    });
                }
                break;
            case R.id.register:
                toRegister();
                break;
        }
    }
}
