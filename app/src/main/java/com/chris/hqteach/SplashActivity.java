package com.chris.hqteach;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chris.hqteach.service.DemonsService;
import com.chris.hqteach.service.HQService;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService();
//        finish();
    }

    private void startService(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            Intent intent = new Intent(this, DemonsService.class);
            startService(intent);
        }else {
            Intent intent = new Intent(this, HQService.class);
            startService(intent);
        }

    }

}
