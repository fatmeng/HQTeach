package com.chris.hqteach;

import android.app.Application;

import com.chris.hqteach.bean.Client;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;


/**
 * Created on 17/3/23.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class HQApplication extends Application {

    public static Client info;

    private static Application mApplication = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        info = Client.defaultInfo(this);
    }

    public static Application getApplication(){
        return mApplication;
    }
}
