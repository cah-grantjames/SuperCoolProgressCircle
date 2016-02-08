package com.example.grantjames.supercoolprogresscircle;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Timer timer;
    private SuperCoolProgressCircle superCoolProgressCircle1;
    private SuperCoolProgressCircle superCoolProgressCircle2;
    private SuperCoolProgressCircle superCoolProgressCircle3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superCoolProgressCircle1 = (SuperCoolProgressCircle) findViewById(R.id.supercool1);
        superCoolProgressCircle1.setProgress((float) Math.random());
        superCoolProgressCircle1.setLineColor(Color.GREEN);
        superCoolProgressCircle2 = (SuperCoolProgressCircle) findViewById(R.id.supercool2);
        superCoolProgressCircle2.setProgress((float) Math.random());
        superCoolProgressCircle2.setLineColor(Color.rgb(255, 0, 0));
        superCoolProgressCircle3 = (SuperCoolProgressCircle) findViewById(R.id.supercool3);
        superCoolProgressCircle3.setProgress((float) Math.random());
        superCoolProgressCircle3.setLineColor(Color.rgb(255, 140, 0));
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
                        superCoolProgressCircle1.inc(.001f);
                        superCoolProgressCircle2.inc(.001f);
                        superCoolProgressCircle3.inc(.001f);
                    }
                });
            }
        }, 0, 1000L/30L);
    }

    void stop() {
        timer.cancel();
    }
}
