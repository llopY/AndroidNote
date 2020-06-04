package com.example.lop.androidnote.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.lop.androidnote.R;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-06-04
 * @desc:
 */
public class PointCircle extends View {
    private Paint paint;
    private float radius = 50f;
    private Point currentPoint;
    private ValueAnimator valueAnimator;

    public PointCircle(Context context) {
        this(context, null);
    }

    public PointCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(context.getResources().getColor(R.color.chart_color_03));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point();
            currentPoint.setX(radius);
            currentPoint.setY(radius);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void startAnimation() {
        Point startPoint = new Point();
        startPoint.setX(radius);
        startPoint.setY(radius);
        Point endPoint = new Point();
        endPoint.setX(radius);
        endPoint.setY(getHeight() - 300);
        valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(animation -> {
            currentPoint = (Point) animation.getAnimatedValue();
            invalidate();
        });
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), radius, paint);
    }
}
