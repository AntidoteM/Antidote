package com.example.mchat;

import android.app.Application;

import com.example.mchat.utils.StringUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by 杜明 on 2017/4/20.
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initEMdk();
        StringUtil.list2Map();
    }

    private void initEMdk(){
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }
}
