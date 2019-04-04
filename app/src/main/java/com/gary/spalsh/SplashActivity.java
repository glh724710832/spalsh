package com.gary.spalsh;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private int count = 5;
    private TextView jump;
    private SplashHandler splashHandler;
    private boolean isClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jump = findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isClick = true;
                jumpToMain();
            }
        });
        splashHandler = new SplashHandler();
        jump.setText("跳过" + 5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new SplashThread().start();
    }

    private void jumpToMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class SplashThread extends Thread {

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 5 && !isClick; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.arg1 = i;
                splashHandler.sendMessage(message);
            }
        }
    }

    class SplashHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            jump.setText((5 - msg.arg1) + "");
            Log.e("跳过的值", msg.arg1 + "");
            if (5 - msg.arg1 == 1) {
                Log.e("执行了没", "ok");
                jumpToMain();
            }

        }
    }
}
