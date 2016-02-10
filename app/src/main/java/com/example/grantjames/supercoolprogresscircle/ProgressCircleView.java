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
    public static final float ARC_SIZE_PERCENT_OF_WIDTH = .9f;
    public static final float PERCENT_LITTLE_CIRCLE_RADIUS_OF_FULL_RADIUS = .084f;
    public static final float PERCENT_LITTLE_INNER_CIRCLE_RADIUS_OF_LITTLE_CIRCLE_RADIUS = .7f;
    //
    public static final float MIN_SPEED = 0.0005f;
    public static final float MAX_SPEED = 0.03f;
    public static final float START_SPEED = MAX_SPEED;
    public static final float ACCEL = -0.13f;
    public static final int START_ANGLE = -90;
    public static final float PERCENT_STROKE_SIZE_OF_RADIUS = .05f;
    float acceleration = ACCEL;
    float speed = START_SPEED;
    RectF bounds;
    PointF center;
    Paint paint;
    float radius;
    float actualProgress = 0f;

    float currentProgress = 0f;
    Point littleCirclePoint;
    Paint circlePaint;
    Paint innerCirclePaint;
    int lineColor = Color.GREEN;
    int backgroundColor = Color.WHITE;
    private int savedWidth = -1;
    private int savedHeight = -1;
    private float littleRadius;
    private float innerLittleCircleRadius;

    public ProgressCircleView(Context context, Paint paint, Paint circlePaint, Paint innerCirclePaint) {
        super(context);
        this.paint = paint;
        this.circlePaint = circlePaint;
        this.innerCirclePaint = innerCirclePaint;
        bounds = new RectF();
        center = new PointF(1, 1);
        littleCirclePoint = new Point(0, 0);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(6f);
        this.paint.setTextSize(55f);
        reset();
    }

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
        littleCirclePoint = new Point(0, 0);
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
        canvas.drawColor(backgroundColor);
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        boolean dimmensionsHaveChanged = width != savedWidth || height != savedHeight;
        if (dimmensionsHaveChanged) {
            calculateCenter(width, height);
            calculateRadiusAndBounds();
            handleEditMode();
            paint.setStrokeWidth(radius * PERCENT_STROKE_SIZE_OF_RADIUS);
            littleRadius = this.radius * PERCENT_LITTLE_CIRCLE_RADIUS_OF_FULL_RADIUS;
            innerLittleCircleRadius = littleRadius *
                    PERCENT_LITTLE_INNER_CIRCLE_RADIUS_OF_LITTLE_CIRCLE_RADIUS;
            savedWidth = width;
            savedHeight = height;
        }

        setPaintColors();
        //Little circle calculate every time
        calculateLittleCirclePoint();

        canvas.drawArc(bounds, START_ANGLE, (360f * currentProgress), false, paint);
        canvas.drawCircle(littleCirclePoint.x, littleCirclePoint.y, littleRadius, circlePaint);
        canvas.drawCircle(littleCirclePoint.x, littleCirclePoint.y, innerLittleCircleRadius, innerCirclePaint);
    }

    private void calculateRadiusAndBounds() {
        radius = center.x * ARC_SIZE_PERCENT_OF_WIDTH;
        bounds.set(center.x - radius, center.y - radius, center.x + radius, center.y + radius);
    }

    private void calculateCenter(float width, float height) {
        center.x = width / 2f;
        center.y = height / 2f;
    }

    private void handleEditMode() {
        if (isInEditMode()) {
            int width = (int) (310 / .75f);
            int height = (int) (310 / .75f);
            calculateCenter(width, height);
            currentProgress = .7f;
            actualProgress = currentProgress;
            lineColor = Color.MAGENTA;
            backgroundColor = Color.argb(40, 200, 0, 0);
            calculateRadiusAndBounds();
        }
    }

    private void setPaintColors() {
        paint.setColor(lineColor);
        circlePaint.setColor(lineColor);
        if (isInEditMode()) {
            innerCirclePaint.setColor(Color.CYAN);
        } else {
            innerCirclePaint.setColor(backgroundColor);
        }
    }

    private void calculateLittleCirclePoint() {
        double circlePointAngleRad = getCirclePointAngleRad();
        littleCirclePoint.x = calculateCirclePointX(circlePointAngleRad);
        littleCirclePoint.y = calculateCirclePointY(circlePointAngleRad);
    }

    int calculateCirclePointX(double circlePointAngleRad) {
        return (int) (center.x + (radius * Math.cos(circlePointAngleRad)));
    }

    int calculateCirclePointY(double circlePointAngleRad) {
        return (int) (center.y - (radius * Math.sin(circlePointAngleRad)));
    }

    double getCirclePointAngleRad() {
        float circlePointAngle = 90 + (-360f * currentProgress);
        return circlePointAngle * Math.PI / 180;
    }

    public void reset() {
        currentProgress = 0f;
        speed = START_SPEED;
    }

    public void onUpdate() {
        increment(speed);
        if ((currentProgress/actualProgress) > 0.8f) {
            speed *= (1 + acceleration);
        }
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
