package com.chris.hqteach.net;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created on 17/4/2.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class SocketManager {
    private static final String TAG = "SocketManager";

    static Socket client;
    static String socketAddress = "124.65.152.34";
    static int socketPort = 9988;
    public static Socket connction(){
        try {
            client = new Socket(socketAddress,socketPort);
            client.setKeepAlive(true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }

    public static void resetSocket(){
        while (isServiceColse()){
            try {
                client = new Socket(socketAddress,socketPort);
            } catch (IOException e) {
                Log.d(TAG,"socket正在重连中........");
            }
        }
    }

    public static boolean isServiceColse(){
        if (client == null)
            return true;
        try {
            client.sendUrgentData(0);
            return false;
        }catch (Exception e){
            return true;
        }
    }



}
