package com.example.grantjames.supercoolprogresscircle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Timer timer;
    private SuperCoolProgressCircle superCoolProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superCoolProgressCircle = (SuperCoolProgressCircle) findViewById(R.id.supercool);
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }


    void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        superCoolProgressCircle.inc();
                    }
                });
            }
        }, 0, 500);
    }

    void stop() {
        timer.cancel();
    }
}
