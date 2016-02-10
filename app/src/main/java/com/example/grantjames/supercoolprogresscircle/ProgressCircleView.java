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

public class ProgressCircleView extends View {
    //Size
    public static final float SIZE_PERCENT_OF_WIDTH = 90f;
    public static final float PERCENT_CIRCLE_RADIUS_OF_FULL_RADIUS = 8.33f;
    public static final float PERCENT_INNER_CIRCLE_RADIUS_OF_CIRCLE_RADIUS = 70f;
    //
    public static final float MIN_SPEED = 0.0025f;
    public static final float MAX_SPEED = 0.03f;
    public static final float START_SPEED = MAX_SPEED;
    public static final float ACCEL = -0.03f;
    private float acceleration = ACCEL;
    private float speed = START_SPEED;
    private RectF bounds;
    private PointF center;
    private Paint paint;
    private float radius;
    private float actualProgress = 0f;
    private float currentProgress = 0f;

    private Point circlePoint;
    private Paint circlePaint;
    private Paint innerCirclePaint;
    private int lineColor = Color.GREEN;
    private int backgroundColor = Color.WHITE;

    public ProgressCircleView(Context context) {
        super(context);
        init();
    }

    public ProgressCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        reset();
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

        radius = center.x * SIZE_PERCENT_OF_WIDTH/100f;
        float strokeWidth = radius * .05f;
        paint.setStrokeWidth(strokeWidth);

        bounds.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
        int startAngle = -90;
        int sweepAngle = (int) (360f * currentProgress);

        canvas.drawArc(bounds, startAngle, sweepAngle, false, paint);
        //~~
        float circlePointAngle = 90 + (-360f * currentProgress);
        double circlePointAngleRad = circlePointAngle * Math.PI / 180;
        circlePoint.x = (int) (center.x + (radius * Math.cos(circlePointAngleRad)));
        circlePoint.y = (int) (center.y - (radius * Math.sin(circlePointAngleRad)));

        float circleRadius = this.radius * PERCENT_CIRCLE_RADIUS_OF_FULL_RADIUS/100f;
        canvas.drawCircle(circlePoint.x, circlePoint.y, circleRadius, circlePaint);
        canvas.drawCircle(circlePoint.x, circlePoint.y, circleRadius *
                PERCENT_INNER_CIRCLE_RADIUS_OF_CIRCLE_RADIUS/100f, innerCirclePaint);
        //~~
        if(isInEditMode()){
            canvas.drawColor(Color.MAGENTA);
        }
    }

    public void reset() {
        currentProgress = 0f;
        speed = START_SPEED;
    }

    public void onUpdate() {
        increment(speed);
        speed *= (1 + acceleration);
        speed = speed > MAX_SPEED ? MAX_SPEED : speed;
        speed = speed < MIN_SPEED ? MIN_SPEED : speed;
    }

    public void increment(float delta) {
        float p = currentProgress + delta;
        setCurrentProgress(p);
        invalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }


    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress >= actualProgress ? actualProgress : currentProgress;
        invalidate();
    }

    public float getActualProgress() {
        return actualProgress;
    }

    public void setActualProgress(float actualProgress) {
        reset();
        this.actualProgress = actualProgress;
        this.acceleration = ACCEL / actualProgress;
        invalidate();
    }


}
