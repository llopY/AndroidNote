package com.example.lop.androidnote.custom.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.lop.androidnote.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-06-11
 * @desc:
 */
public class CustomView2 extends View implements Runnable {
    private Paint basePaint;
    private Paint progressPaint;
    private int circleWidth;

    private int baseColor;
    private int progressColor;
    private float centerX;
    private float centerY;
    private int radius;
    private int strokeWidth;
    private int progress;

    public CustomView2(Context context) {
        this(context, null);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customView2);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.customView2_circle_base_color:
                    baseColor = typedArray.getColor(index, Color.GRAY);
                    break;
                case R.styleable.customView2_circle_progress_color:
                    progressColor = typedArray.getColor(index, Color.RED);
                    break;
                case R.styleable.customView2_centerX:
                    centerX = UIUtil.dip2px(context, typedArray.getDimension(index, 0));
                    break;
                case R.styleable.customView2_centerY:
                    centerY = UIUtil.dip2px(context, typedArray.getDimension(index, 0));
                    break;
                case R.styleable.customView2_radius:
                    radius = UIUtil.dip2px(context, typedArray.getDimension(index, 0));
                    break;
                case R.styleable.customView2_stroke_width:
                    strokeWidth = UIUtil.dip2px(context, typedArray.getDimension(index, 0));
                    break;

            }
        }
        initPaint();
    }

    private void initPaint() {
        basePaint = new Paint();
        basePaint.setAntiAlias(true);
        basePaint.setColor(baseColor);
        basePaint.setStyle(Paint.Style.STROKE);
        basePaint.setStrokeWidth(strokeWidth != 0 ? strokeWidth : TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getContext().getResources().getDisplayMetrics()));

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth != 0 ? strokeWidth : TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getContext().getResources().getDisplayMetrics()));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(centerX,centerY,radius,basePaint);
        int offset = UIUtil.dip2px(getContext(), 10);
        RectF rect = new RectF(offset, offset, canvas.getWidth() - offset, canvas.getHeight() - offset);
        canvas.drawArc(rect, 0, 360, false, basePaint);
        canvas.drawArc(rect, -90, progress, false, progressPaint);
    }

    @Override
    public void run() {
        while (true) {
            if (progress < 360) {
                progress += 1;
            } else {
                progress = 0;
            }
            postInvalidate();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
