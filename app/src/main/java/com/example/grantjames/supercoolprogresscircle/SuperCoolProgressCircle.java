package com.example.grantjames.supercoolprogresscircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SuperCoolProgressCircle extends View {

    private RectF bounds;
    private PointF center;
    private Paint paint;
    private float radius;
    private float progress = 0f;
    private Point circlePoint;
    private Paint circlePaint;
    private Paint innerCirclePaint;
    private int lineColor = Color.GREEN;
    private int backgroundColor = Color.WHITE;

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
        center = new PointF(1, 1);
        circlePoint = new Point(0, 0);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6f);
        paint.setTextSize(55f);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(lineColor);
        circlePaint.setColor(lineColor);
        innerCirclePaint.setColor(backgroundColor);
        canvas.drawColor(backgroundColor);
        center.x = canvas.getWidth() / 2f;
        center.y = canvas.getHeight() / 2f;
        radius = center.x * .9f;
        float strokeWidth = radius * .05f;
        paint.setStrokeWidth(strokeWidth);

        bounds.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
        int startAngle = -90;
        int sweepAngle = (int) (360f * progress);

        canvas.drawArc(bounds, startAngle, sweepAngle, false, paint);
        //~~
        float circlePointAngle = 90 + (-360f * progress);
        double circlePointAngleRad = circlePointAngle * Math.PI / 180;
        circlePoint.x = (int) (center.x + (radius * Math.cos(circlePointAngleRad)));
        circlePoint.y = (int) (center.y - (radius * Math.sin(circlePointAngleRad)));
        canvas.drawCircle(circlePoint.x, circlePoint.y, 1.1f * strokeWidth, circlePaint);
        canvas.drawCircle(circlePoint.x, circlePoint.y, strokeWidth * .7f, innerCirclePaint);
        //~~
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void inc(float v) {
        float p = progress + v;

        setProgress(p);
        invalidate();
    }

    public void setProgress(float progress) {
        this.progress = progress > 1 ? 0 : progress;
    }
}
