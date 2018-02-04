package com.chris.hqteach.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.chris.hqteach.bean.Client;
import com.chris.hqteach.net.ConnectionConfig;
import com.chris.hqteach.net.ConnectionManager;
import com.chris.hqteach.webview.WebViewActivity;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.apache.mina.core.future.ConnectFuture;

import java.util.List;

public class HQService extends Service {

    private static final String TAG = "HQService";
    //与Socket服务器建立连接后,保持通信的Thread
    private ConntectThread minaThread;
    //和服务器通信的对象
    private ConnectFuture conn = null;
    private RegisterBiner biner = new RegisterBiner();

    @Override
    public void onCreate() {
        super.onCreate();
        minaThread = new ConntectThread("mianConnectionThread",getApplicationContext());
        minaThread.start();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return biner;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //当HQService存活时,再次调起,此方法会再次调用.之前的onCreate. onStart不会被调用


        return START_STICKY;
    }

    private boolean isTopApp() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (null == tasks || tasks.isEmpty() || !tasks.get(0).topActivity.getPackageName().equals(getPackageName())){
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(),HQService.class);
        startService(intent);
    }

    //负责保障socket连接的线程
    private class ConntectThread extends Thread{

        private static final long INTERVAL_TIME = 3 * 1000L;
        private Context mContext;
        private int count = 0;
        private ConnectionManager manager;
        boolean isQuit = false;
        Intent intent = null;
        public ConntectThread(String name, Context context) {
            super(name);
            this.mContext = context;
            intent = new Intent(mContext, WebViewActivity.class);
            //
            manager = new ConnectionManager(mContext, new ConnectionConfig.Builder(mContext).setAddress("1.1.1.1").setPort(9988).build());
        }

        @Override
        public void run() {

           while (!isQuit){
               conn = manager.connect();
                if (conn != null){
                    isQuit = true;
                }
                try {
                    Thread.currentThread().sleep(INTERVAL_TIME);
                    count++;
                    if (count >= 5) {
                        Logger.e("请检测与服务器的网络连接是否正常!");
                        Toast.makeText(mContext, "请检测与服务器的网络连接是否正常!", Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               //如果应用未在前台,则将其放至前台
               if(!isTopApp()){
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
               }
            }
        }
    }

    public class RegisterBiner extends Binder {
        /**
         * 用来将设备信息(Clinet)通过注册界面收集到服务,上传到服务器的方法
         */
        public boolean register(final Client client){

            final Gson gson = new Gson();
            if (conn.getSession() == null){
                Logger.d("正在常试与服务器建议连接!!!");
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (conn.getSession() == null){

                    }
                    conn.getSession().write(gson.toJson(client));
                }
            }).start();
            return false;
        }

    }
}
