package com.chris.hqteach.net;

import org.apache.mina.core.session.IoSession;

/**
 * Created on 17/4/13.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */

public class SessionManager {

    private IoSession mSession = null;
    private SessionManager(){}
    private static SessionManager INSTNCE = null;

    public static SessionManager getInstance(){
        if (INSTNCE == null){
            synchronized (SessionManager.class){
                if (INSTNCE == null){
                    INSTNCE = new SessionManager();
                }
            }
        }
        return INSTNCE;
    }

    public void setSession(IoSession session){
        this.mSession = session;
    }

    public void write(String msg){
        if (mSession != null){
            mSession.write("my addresss");
        }
    }


    /** 需要返回一个类型,通过返回的类型做解析后,处理不同的业务逻辑
     * @return
     */
    public String read(){
        //TODO:need to implments
        return "";
    }
}
