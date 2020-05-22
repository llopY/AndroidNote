package com.example.lop.androidnote.custom.tools;

import android.content.Context;
import android.graphics.Canvas;
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
public class CanvasViewByPath extends View {
    private Paint paint;
    private Path path;

    public CanvasViewByPath(Context context) {
        this(context, null);
    }

    public CanvasViewByPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
//        path.addCircle(150,150,100, Path.Direction.CW);//cw顺时针，ccw逆时针
        /**
         * 线操作 lineTo：x、y表示当前位置的绝对位置  rLineTo:表示当前位置的相对位置
         * 当前位置：最后一次调用path的方法的终点位置，初始值为(0,0)
         * 点操作 moveTo:改变后面操作的起始点位置,和线操作原理一样
         */
//        path.moveTo(50,100);
//        path.setLastPoint(0,0);//改变前面操作中最后点的位置
//        path.lineTo(100,300);

//        path.moveTo(300,0);
//        path.lineTo(300,300);
//        path.rLineTo(300,0);
//        path.close();

        /**
         * FillType.EVEN_ODD交叉填充 even(偶) odd(奇)
         * even-odd rule 奇偶原则：对于平面中的任意一点向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算
         * 如果是奇数则这个点被认为在图形内部是要被涂色的区域；如果是偶数则这个点被认为在图形外部是不被涂色的区域
         * FillType.WINDING全填充：也是从平面任意一点发出一条射线，查看交点，初始值为0顺时针交点加一逆时针交点减一，最终结果为0的话则认为这个点在图形外部是不被涂色的区域
         */
        path.setFillType(Path.FillType.WINDING);
        path.addCircle(150, 150, 100, Path.Direction.CW);
        path.addCircle(270, 150, 100, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }
}
