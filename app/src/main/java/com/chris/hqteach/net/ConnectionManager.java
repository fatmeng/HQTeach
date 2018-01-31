package com.chris.hqteach.net;

import android.content.Context;

import com.orhanobut.logger.Logger;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.SoftReference;
import java.net.InetSocketAddress;

/**
 * Created on 17/4/13.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class ConnectionManager {
    private static final String LOGGIN = "loggin";
    private static final String CODEC = "codec";
    private ConnectionConfig mConfig;
    private SoftReference<Context> mSoftContext;
    private NioSocketConnector mConnecter;
    private IoSession session;
    private InetSocketAddress mInetSocketAddress;
    private ConnectFuture connect;
    private HQHandler mHandler;

    public ConnectionManager(Context mContext,ConnectionConfig config){
        mSoftContext = new SoftReference<Context>(mContext);
        this.mConfig = config;
        init();
    }

    private void init() {
        mInetSocketAddress = new InetSocketAddress(mConfig.getAddress(),mConfig.getPort());
        mConnecter = new NioSocketConnector();
        mConnecter.setDefaultRemoteAddress(mInetSocketAddress);
        mConnecter.getSessionConfig().setKeepAlive(true);
        mConnecter.getSessionConfig().setReadBufferSize(mConfig.getReadBufferSize());
        mConnecter.getFilterChain().addLast(LOGGIN,new LoggingFilter());
        mConnecter.getFilterChain().addLast(CODEC,new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mHandler = new HQHandler(mSoftContext.get());
        mConnecter.setHandler(new HQHandler(mSoftContext.get()));
    }

    /**
     * 连接socket服务器
     * @return
     */
    public ConnectFuture connect(){
        try{
            connect = mConnecter.connect();
            if (connect != null){
                session = connect.getSession();
                return connect;
            }
        }catch (Exception e){
            Logger.e("异常:" + e.getMessage());
            e.printStackTrace();
            return null;
       }

        return null;
    }

    /**
     * 为啥要调用,长连接
     */
    public void disConnect(){

    }





}
