package com.example.lop.androidnote;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-06-22
 * @desc:
 */
public class View_PostTest extends TextView {
    public View_PostTest(Context context) {
        this(context, null);
    }

    public View_PostTest(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200, 100);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.e("----------------->", "before super onAttachedToWindow");
        super.onAttachedToWindow();
        Log.e("----------------->", "after super onAttachedToWindow");
    }
}
