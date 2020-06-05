package com.example.lop.androidnote.custom.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.lop.androidnote.R;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-05-18
 * @desc:
 */
public class CanvasSimpleView extends View {
    private Paint paint;

    public CanvasSimpleView(Context context) {
        this(context, null);
    }

    public CanvasSimpleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint = new Paint();
        /**
         * 锯齿现象实际上就是像素太低导致人眼看出来像素颗粒，所以开不开抗锯齿图像的边缘已经是最完美的了
         * 开启抗锯齿实际上就是修改图像边缘的颜色让图像看起更平滑
         */
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics()));
        paint.setColor(getContext().getResources().getColor(R.color.blue));
        /**
         * canvas.drawXXX里面的参数除了paint都是它的独有参数，如这里的圆心坐标和半径 drawText就没有
         * 而设置颜色这些叫公有信息是通过paint去设置的
         */
//        canvas.drawCircle(150,150,140,paint);
//        canvas.drawOval(100,100,300,200,paint);//椭圆
//        canvas.drawLine(100,100,500,300,paint);//因为线不是封闭图形所以setStyle对它没影响

        //ROUND 画出来是圆形的点，SQUARE 或 BUTT 画出来是方形的点，实际上画单个点和drawCircle、drawRect是一样的效果
        paint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawPoint(100,100,paint);
        canvas.drawPoints(new float[]{100, 100, 100, 50, 100, 100}, 2, 4, paint);//2：跳过前两个数，4：一共绘制4个数(即两个点)

        //画圆角矩形
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            canvas.drawRoundRect(100,100,300,200,
//                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getContext().getResources().getDisplayMetrics()),
//                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getContext().getResources().getDisplayMetrics()),paint);
//        }
        //画扇形或者圆弧,userCenter表示是否连接圆心，true表示连接则画的是扇形，false画的是弧线
//        canvas.drawArc(100, 100, 400, 200, 0, -90, true, paint);

    }
}
