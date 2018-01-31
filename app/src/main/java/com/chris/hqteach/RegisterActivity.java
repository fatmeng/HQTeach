package com.chris.hqteach;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chris.hqteach.bean.Client;
import com.chris.hqteach.notification.NotificationActivity;
import com.chris.hqteach.service.HQService;
import com.chris.hqteach.utils.SPUtils;
import com.chris.hqteach.utils.SystemUtils;
import com.chris.hqteach.webview.WebViewActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    Button submit;
    EditText gralde,cls;
    TextView mac,serialNum;
    Context mContext;
    private static final int MAC_MSG = 1;
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MAC_MSG:
                    mac.setText((String)msg.obj);
                    break;
                default:
                    break;

            }
        }
    };
    private HQService.RegisterBiner delegate = null;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            delegate = (HQService.RegisterBiner)service;
            submit.setClickable(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            delegate = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        getDeviceInfoForMac();
        bindService(new Intent(this,HQService.class),conn,Context.BIND_AUTO_CREATE);

    }

    private void getDeviceInfoForMac() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mac = SystemUtils.getMac(mContext);
                Message msg = mHandler.obtainMessage();
                msg.what = MAC_MSG;
                msg.obj = mac;
                msg.sendToTarget();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView() {
        submit = (Button)findViewById(R.id.commit);
        gralde = (EditText) findViewById(R.id.gradle);
        cls = (EditText)findViewById(R.id.cls);
        mac = (TextView)findViewById(R.id.mac);
        serialNum = (TextView)findViewById(R.id.serial_num);
        serialNum.setText(Build.SERIAL);
        submit.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println("~~~~~~~~~~~~ "+keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commit:
                if (gralde.getText()!= null && cls.getText()!= null && mac.getText() != null
                        && serialNum.getText() != null){
                    Client.Builder builder = new Client.Builder();
                    Client client = builder.setGradle(gralde.getText().toString())
                            .setCls(cls.getText().toString())
                            .setMac(mac.getText().toString())
                            .setSerialNum(serialNum.getText().toString())
                            .setKey("client_bind")
                            .setOrderId(0)
                            .setTimeStamp(System.currentTimeMillis())
                            .setUrl("")
                            .build();
                    //将收集的clinet信息保存到本地
                    SPUtils.getInstance(mContext).saveUserInfo(client);
                    //调用HQService,去注册该用户
                    delegate.register(client);

                }
                break;

            default:
                break;
        }
    }

    private void goToNoficationView(String notification) {
        Intent intent = new Intent(RegisterActivity.this, NotificationActivity.class);
        intent.putExtra(NotificationActivity.NOTIFICATION_CONTENT,notification);
        String notificaitonBg = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491151824434&di=ef4c44aeb8615069a5912dc548f68232&imgtype=0&src=http%3A%2F%2Fa.ikafan.com%2Fattachment%2Fforum%2F201308%2F01%2F064309n8srirsl37usr438.jpg";
        intent.putExtra(NotificationActivity.NOTIFICATION_BG,notificaitonBg);
        startActivity(intent);
    }

    private void goToWebView(String url){
        Intent intent = new Intent(RegisterActivity.this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.WEB_URL,url);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
