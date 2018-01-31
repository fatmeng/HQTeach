package com.chris.hqteach.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.chris.hqteach.RegisterActivity;
import com.chris.hqteach.bean.Client;
import com.chris.hqteach.bean.OrderList;
import com.chris.hqteach.notification.NotificationActivity;
import com.chris.hqteach.utils.SPUtils;
import com.chris.hqteach.webview.HQConstant;
import com.chris.hqteach.webview.WebViewActivity;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;

/**
 * Created on 17/4/13.
 * Author : chris
 * Email  : mengqi@analysys.com.cn
 * Detail :
 */
public class HQHandler extends IoHandlerAdapter {
    private String order;
    private Context mContext;
    private String HEART = "hb_request";
    public HQHandler(Context context){
        this.mContext = context;
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        Logger.d("给服务器发送的数据:" + message);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        Logger.d("收到服务器传来的数据:" + message);

        try {
            if (HEART.equals(message)){
                session.write("hb_response");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            JSONObject jsonObject = new JSONObject((String)message);
            int sessionOrder = (int)jsonObject.get("orderId");

            switch (sessionOrder){
                case OrderList.ORDER_WEB:
                    gotoWeb((String)jsonObject.get("url"));
                    break;
                case OrderList.ORDER_NOTIFICATION:
                    gotoNotification();
                    break;
                default:
                    Toast.makeText(mContext,"未知命令",Toast.LENGTH_SHORT).show();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            Logger.d("接收异常参数:" + message);
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        Logger.d("session已经建立,与服务器" + session.getServiceAddress() + "建立连接");
        Gson gson = new Gson();
        Client client = SPUtils.getInstance(mContext).getUserInfo();
        if (client != null){
            String clinetString = gson.toJson(client);
            session.write(clinetString);
        }else {
            startRegsiterActivity();
        }

    }

    private void startRegsiterActivity(){
        Intent intent = new Intent(mContext, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void gotoWeb(String url){
        if (url == null || url.equals("")){
            String lastUrl = SPUtils.getInstance(mContext).getLastUrl();
            WebViewActivity.startAction(mContext, lastUrl);

        }else {
            WebViewActivity.startAction(mContext,url);
            SPUtils.getInstance(mContext).saveLastUrl(url);
        }

    }

    private void gotoNotification(){

        NotificationActivity.startAction(mContext,HQConstant.MESSAGE_DEFAULT,HQConstant.MESSAGE_BG);
    }
}
