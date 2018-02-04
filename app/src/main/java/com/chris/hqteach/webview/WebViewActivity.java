package com.chris.hqteach.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chris.hqteach.BaseActivity;
import com.chris.hqteach.R;
import com.chris.hqteach.utils.SPUtils;
import com.chris.hqteach.utils.SystemUtils;
import com.orhanobut.logger.Logger;

public class WebViewActivity extends BaseActivity {


    public static final String WEB_URL = "web_url";
    String mac = "";
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra(WEB_URL);
        if (TextUtils.isEmpty(url)){
            url = SPUtils.getInstance(this).getLastUrl();
        }
        initView(url);
        mac = SystemUtils.getMac(this);
    }




    private void initView(String url){
        mWebView = (WebView)findViewById(R.id.web_view);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                StringBuilder sb = new StringBuilder(url);

                if (url.contains("ecbIndex")){
                    sb = url.contains("?")?sb.append("&addresss=" + mac):sb.append("?address="+mac);
                    url = sb.toString();
                }
                Logger.d("发到服务器的url:" + url);
                return super.shouldInterceptRequest(view, url);

            }
        });
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()){
            mWebView.goBack();
        }else {
            super.onBackPressed();
        }
    }


    public static void startAction(Context mContext,String url) {
        Intent intent = new Intent(mContext,WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WEB_URL,url);
        mContext.startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url =  intent.getStringExtra(WEB_URL);
        if (!TextUtils.isEmpty(url));
            mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.destroy();
        mWebView = null;
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
}
