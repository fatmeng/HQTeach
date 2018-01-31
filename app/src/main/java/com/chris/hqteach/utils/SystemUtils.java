package com.chris.hqteach.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created on 17/3/23.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class SystemUtils {

    private static final String TAG = SystemUtils.class.getSimpleName();
    /**
     * 用来设置延时关机操作
     * @param time
     */
    public static void shutDownAlarm(long time){
        try {

            //获得ServiceManager类
            Class<?> ServiceManager = Class
                    .forName("android.os.ServiceManager");

            //获得ServiceManager的getService方法
            Method getService = ServiceManager.getMethod("getService", java.lang.String.class);

            //调用getService获取RemoteService
            Object oRemoteService = getService.invoke(null,Context.POWER_SERVICE);

            //获得IPowerManager.Stub类
            Class<?> cStub = Class
                    .forName("android.os.IPowerManager$Stub");
            //获得asInterface方法
            Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
            //调用asInterface方法获取IPowerManager对象
            Object oIPowerManager = asInterface.invoke(null, oRemoteService);
            //获得shutdown()方法
            Method shutdown = oIPowerManager.getClass().getMethod("shutdown",boolean.class,boolean.class);
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager,false,true);

        } catch (Exception e) {
            Log.e(TAG, e.toString(), e);
        }

    }

    public static String getMac(Context mContext){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getMacBySystemInterface(mContext);
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            return getMacByFileAndJavaAPI();
        } else {
            return getMacByJavaAPI();
        }
    }
    @SuppressLint("HardwareIds")
    private static String getMacBySystemInterface(Context context) {
        if (context == null) return "00:00:00:00:00:00";
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Throwable e) {
            return "00:00:00:00:00:00";
        }
    }

    private static String getMacByFileAndJavaAPI() {
        String mac = getMacShell();
        return !TextUtils.isEmpty(mac) ? mac : getMacByJavaAPI();
    }

    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        continue;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return "00:00:00:00:00:00";
    }
    private static String getMacShell() {
        try {
            String[] urls = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address",
                    "/sys/devices/virtual/net/wlan0/address"};
            String mc;
            for (int i = 0; i < urls.length; i++) {
                try {
                    mc = reaMac(urls[i]);
                    if (mc != null) {
                        return mc;
                    }
                } catch (Throwable e) {
                }
            }
        } catch (Throwable e) {
        }

        return null;
    }

    private static String reaMac(String url) {
        String macInfo = null;
        try {
            FileReader fstream = new FileReader(url);
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    macInfo = in.readLine();
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (Throwable e) {

                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (Throwable e) {

                        }
                    }
                }
            }
        } catch (Throwable e) {
        }
        return macInfo;
    }

}
