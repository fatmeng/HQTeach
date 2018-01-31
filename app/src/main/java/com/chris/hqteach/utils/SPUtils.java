package com.chris.hqteach.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chris.hqteach.bean.Client;
import com.chris.hqteach.webview.HQConstant;
import com.google.gson.Gson;

/**
 * Created on 17/6/5.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class SPUtils {
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editor;
    private static Gson gson;
    private static final String NAME = "hq_sp";
    private static final String LAST_URL = "last_url" ;

    /**
     * 当前正在使用的电子班的用户信息
     */
    private static final String USER_INFO = "USER_INFO";

    public static final String USERINFO_DEFAULT = "";
    private SPUtils() {
    }


    private static volatile SPUtils instance = null;


    public static SPUtils getInstance(Context context) {
        if (instance == null) {
            instance = new SPUtils();
            sp = context.getApplicationContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
            editor = sp.edit();
            gson = new Gson();
        }
        return instance;
    }

    /**
     * 保存用户信息
     * @param client
     */
    public void saveUserInfo(Client client){
        String jsonString = gson.toJson(client);
        editor.putString(USER_INFO,jsonString);
        editor.commit();
    }

    /**
     * @return
     */
    public Client getUserInfo(){
        String jsonString = sp.getString(USER_INFO, USERINFO_DEFAULT);
        if (jsonString.equals(USERINFO_DEFAULT)) return null;
        Client client = gson.fromJson(jsonString, Client.class);
        return client;
    }


    public void saveLastUrl(String url){
        editor.putString(LAST_URL,url);
        editor.commit();
    }

    public String getLastUrl(){
        String url = sp.getString(LAST_URL, HQConstant.DEFAULT_URL);
        return url;
    }
}
