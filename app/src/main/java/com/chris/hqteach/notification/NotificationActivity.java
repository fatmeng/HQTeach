package com.chris.hqteach.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chris.hqteach.BaseActivity;
import com.chris.hqteach.R;
import com.dalong.marqueeview.MarqueeView;

public class NotificationActivity extends BaseActivity {

    public static final String NOTIFICATION_CONTENT = "notificaiton_key";
    public static final String NOTIFICATION_BG = "notification_bg";
    MarqueeView notificaitonView;
    FrameLayout container;
    ImageView notificaitonBg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        String notificationBg = getIntent().getStringExtra(NOTIFICATION_BG);
        String notfication = getIntent().getStringExtra(NOTIFICATION_CONTENT);
        initView(notificationBg,notfication);
    }

    private void initView(String url,String notificaiton){
        container = (FrameLayout)findViewById(R.id.activity_notification);
        notificaitonBg = (ImageView)findViewById(R.id.notification_bg);
        Glide.with(this).load(url).centerCrop().into(notificaitonBg);
        notificaitonView = (MarqueeView)findViewById(R.id.marquee_view);
        notificaitonView.setText(notificaiton);
        notificaitonView.startScroll();
    }

    public void refreshView(String url,String notification){
        if (notificaitonBg != null){
            Glide.with(this).load(url).centerCrop().into(notificaitonBg);
        }
        if (notificaitonView != null){
            notificaitonView.setText(notification);
            notificaitonView.startScroll();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String notificationBg = getIntent().getStringExtra(NOTIFICATION_BG);
        String notification = getIntent().getStringExtra(NOTIFICATION_CONTENT);

        refreshView(notificationBg,notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        notificaitonView.stopScroll();
    }


    public static void startAction(Context mContext, String notification,String notificationBg) {
        Intent intent = new Intent(mContext,NotificationActivity.class);
        intent.putExtra(NOTIFICATION_CONTENT,notification);
        intent.putExtra(NOTIFICATION_BG,notificationBg);
        mContext.startActivity(intent);
    }
}
