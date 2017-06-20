package com.example.mchat.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 杜明 on 2017/4/27.
 */

public class SPUtil {
    private static String USER = "user";
    private static String NAME_KEY = "lastname";
    private  static final String CHAT_DEFF="chatdeff";

    static SharedPreferences sp;

    public static String getChatDeff(Context context){
        getSP(context);

        return sp.getString(CHAT_DEFF,"");
    }
    public static void setChatDeff(Context context,String json){
        getSP(context);
        sp.edit().putString(CHAT_DEFF,json).apply();
    }

    private static void getSP(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(USER, Context.MODE_PRIVATE);
        }
    }

    public static void setLastName(Context context, String name) {
        getSP(context);

        sp.edit().putString(NAME_KEY, name).apply();
    }

    public static String getLastName(Context context){
        getSP(context);

        return sp.getString(NAME_KEY,"");
    }
}
