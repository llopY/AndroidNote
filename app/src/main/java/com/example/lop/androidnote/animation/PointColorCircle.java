package com.example.lop.androidnote.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;

import com.example.lop.androidnote.R;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-06-04
 * @desc:
 */
public class PointColorCircle extends View {
    private Paint mPaint;
    private String currentColor;
    private float radius = 50f;
    private Point currentPoint;
    private ObjectAnimator objectAnimator;
    private ValueAnimator valueAnimator;
    private AnimatorSet animationSet;

    public String getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(String currentColor) {
        this.currentColor = currentColor;
        mPaint.setColor(Color.parseColor(currentColor));
        invalidate();
    }

    public PointColorCircle(Context context) {
        this(context, null);
    }

    public PointColorCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(radius, radius);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
    }

    private void startAnimation() {
        Point startPoint = new Point(radius, radius);
        Point endPoint = new Point(radius, getHeight() - 300);
        valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.addUpdateListener(animation -> {
            currentPoint = (Point) animation.getAnimatedValue();
            invalidate();
        });
//        valueAnimator.setInterpolator(new AccelerateInterpolator(2f));
        valueAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator = ObjectAnimator.ofObject(this, "currentColor", new PointColorEvaluator(), "#0000FF", "#FF0000");
        animationSet = new AnimatorSet();
        animationSet.play(valueAnimator).with(objectAnimator);
        animationSet.setDuration(3000);
        animationSet.start();
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(), currentPoint.getY(), radius, mPaint);
    }
}
