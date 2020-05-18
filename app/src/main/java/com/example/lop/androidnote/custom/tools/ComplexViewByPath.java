package com.example.lop.androidnote.custom.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * @author: lop
 * @createTime: 2020-05-18
 * @desc:
 */
public class ComplexViewByPath extends View {
    private Paint paint;
    private Path path;

    public ComplexViewByPath(Context context) {
        this(context, null);
    }

    public ComplexViewByPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        path.addCircle(150,150,100, Path.Direction.CW);//cw顺时针，ccw逆时针
        /**
         * lineTo：x、y表示当前位置的绝对位置  rLineTo:表示当前位置的相对位置
         * 当前位置：最后一次调用path的方法的终点位置，初始值为(0,0)
         */
        path.lineTo(100, 300);
        path.rLineTo(100, 100);
        canvas.drawPath(path, paint);
    }
}
