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
    private ProgressCircleView progressCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressCircleView = (ProgressCircleView) findViewById(R.id.supercool3);
        progressCircleView.reset();
        progressCircleView.setActualProgress((float) 1);
        progressCircleView.setLineColor(Color.rgb(0, 0, 0));
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
            progressCircleView.reset();
            progressCircleView.setActualProgress((float) Math.random());
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
                        progressCircleView.onUpdate();
                    }
                });
            }
        }, 0, 1000L / 30L);
    }

    void stop() {
        timer.cancel();
    }
}
