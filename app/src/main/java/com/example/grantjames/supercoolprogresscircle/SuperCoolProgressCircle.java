package com.example.grantjames.supercoolprogresscircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class SuperCoolProgressCircle extends View {

    private RectF bounds;
    private PointF center;
    private Paint paint;
    float radius = 100;
    private float progress = 0f;
    private Handler handler = new Handler();

    public SuperCoolProgressCircle(Context context) {
        super(context);
        init();
    }

    public SuperCoolProgressCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperCoolProgressCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        bounds = new RectF();
        center = new PointF(200, 200);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6f);
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        SuperCoolProgressCircle.this.inc();
                    }
                });
            }
        }, 30L, 30L);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);
        center.x = canvas.getWidth() / 2f;
        center.y = canvas.getHeight() / 2f;
        radius = 100;
        bounds.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
        int startAngle = -90;
        int sweepAngle = (int) (360f * getProgress());
        canvas.drawArc(bounds, startAngle, sweepAngle, false, paint);
    }

    private float getProgress() {
        return progress;
    }

    public void inc() {
        float p = progress + .01f;
        p = p > 1 ? 0 : p;
        progress = p;
        invalidate();
    }
}
