package com.example.grantjames.supercoolprogresscircle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class SuperCoolProgressCircle extends View {

    private Path myPath;
    private RectF oval;
    private PointF arcPoint;
    private Paint paint;

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
        myPath = new Path();
        oval = new RectF();
        arcPoint = new PointF(0, 0);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        float startAngle = 0;
        float radius = 20;
        float sweepAngle = 30;
        oval.set(arcPoint.x - radius, arcPoint.y - radius, arcPoint.x + radius, arcPoint.y + radius);
        myPath.arcTo(oval, startAngle, -sweepAngle, true);
        canvas.drawPath(myPath, paint);
    }
}
