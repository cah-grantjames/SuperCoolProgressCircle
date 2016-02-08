package com.example.grantjames.supercoolprogresscircle;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

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
        superCoolProgressCircle1.reset();
        superCoolProgressCircle1.setActualProgress((float).3);
        superCoolProgressCircle1.setLineColor(Color.GREEN);

        superCoolProgressCircle2 = (SuperCoolProgressCircle) findViewById(R.id.supercool2);
        superCoolProgressCircle2.reset();
        superCoolProgressCircle2.setActualProgress((float).6);
        superCoolProgressCircle2.setLineColor(Color.rgb(255, 0, 0));

        superCoolProgressCircle3 = (SuperCoolProgressCircle) findViewById(R.id.supercool3);
        superCoolProgressCircle3.reset();
        superCoolProgressCircle3.setActualProgress((float) 1);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            superCoolProgressCircle1.reset();
            superCoolProgressCircle2.reset();
            superCoolProgressCircle3.reset();
        }
        return super.onTouchEvent(event);
    }

    void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        superCoolProgressCircle1.onTimePassed();
                        superCoolProgressCircle2.onTimePassed();
                        superCoolProgressCircle3.onTimePassed();
                    }
                });
            }
        }, 0, 1000L / 30L);
    }

    void stop() {
        timer.cancel();
    }
}
