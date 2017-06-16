package com.happy.shoppingmall.app;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.happy.shoppingmall.R;


public class WelcomeActivity extends AppCompatActivity {
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(){
            @Override
            public void run() {
                super.run();
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }.start();
    }
}
