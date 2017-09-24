package com.snapgroup.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.snapgroup.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent i=new Intent(SplashScreenActivity.this,SignInActivity.class);
                    i.putExtra("splash","splash");
                    startActivity(i);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
