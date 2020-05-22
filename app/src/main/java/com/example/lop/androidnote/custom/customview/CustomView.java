package com.example.lop.androidnote.custom.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-05-19
 * @desc:
 */
public class CustomView extends View {
    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 在setMeasuredDimension()方法调用之后，我们才能使用getMeasuredWidth()和getMeasuredHeight()来获取视图测量出的宽高
         * 以此之前调用这两个方法得到的值都会是0
         */
        setMeasuredDimension(100, 100);

    }

    /**
     * 在measure结束以后就执行layout，源码中可以看到这是一个空方法
     * 因为onlayout是为了确定view在布局中的位置所以应该由布局去决定，即父视图决定子视图得位置
     * 所以在自定义view中主要是onMeasure和onDraw
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
