package com.chris.hqteach.net;

import android.content.Context;

/**
 * Created on 17/4/13.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail : 配置文件信息
 */
public class ConnectionConfig {

    private Context mContext = null;
    //private String address = "124.65.152.34";//
    private String address = "1.1.1.1";//
//    private int port = 9988;
    private int port = 9988;
    private int readBufferSize = 1024 * 10;//10KB
    private int connectedTimeout = 10 * 1000;//10秒

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public void setReadBufferSize(int readBufferSize) {
        this.readBufferSize = readBufferSize;
    }

    public int getConnectedTimeout() {
        return connectedTimeout;
    }

    public void setConnectedTimeout(int connectedTimeout) {
        this.connectedTimeout = connectedTimeout;
    }


    public static class Builder{
        private ConnectionConfig mConfig;

        public Builder(Context context){
            mConfig = new ConnectionConfig();
            mConfig.setContext(context);
        }

        public Builder setAddress(String address) {
            if (mConfig != null)
                mConfig.setAddress(address);
            return this;
        }

        public Builder setPort(int port) {
            if (mConfig != null)
                mConfig.setPort(port);
            return this;
        }

        public Builder setReadBufferSize(int readBufferSize) {
            if (mConfig != null)
                mConfig.setReadBufferSize(readBufferSize);
            return this;
        }

        public Builder setConnectedTimeout(int connectedTimeout) {
            if (mConfig != null)
                mConfig.setConnectedTimeout(connectedTimeout);
            return this;
        }

        public ConnectionConfig build(){
            if (mConfig != null)
                return mConfig;
            return null;
        }

    }
}
