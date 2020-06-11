package com.example.lop.androidnote.custom.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.util.MeasureUnit;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.lop.androidnote.R;

import org.w3c.dom.Text;

import androidx.annotation.Nullable;

/**
 * @author: lop
 * @createTime: 2020-05-19
 * @desc:
 */
public class CustomView extends View {
    private int radius = 50;
    private TextPaint paint;
    private Paint.FontMetrics fontMetrics;
    private int baseX;
    private int baseY;
    private Rect rect;

    private String conetnt;
    private int paintColor;
    private int width;
    private int height;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.customView);

//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.customView, defStyleAttr, 0);
        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, context.getResources().getDisplayMetrics()));
        paint.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/DINOT-Bold.ttf"));
        paint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.SOLID));
        fontMetrics = paint.getFontMetrics();

        int itemCount = typedArray.getIndexCount();
        for (int i = 0; i < itemCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.customView_paint_color:
                    paintColor = typedArray.getColor(index, Color.RED);
                    break;
                case R.styleable.customView_tv_content:
                    conetnt = typedArray.getString(index);
                    break;
                case R.styleable.customView_height:
                    height = typedArray.getDimensionPixelSize(index, 100);
                    break;
                case R.styleable.customView_width:
                    width = typedArray.getDimensionPixelSize(index, 100);
                    break;
            }
        }
        typedArray.recycle();
        paint.setColor(paintColor);
    }

    /**
     * 在setMeasuredDimension()方法调用之后，我们才能使用getMeasuredWidth()和getMeasuredHeight()来获取视图测量出的宽高
     * 以此之前调用这两个方法得到的值都会是0
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {//EXACTLY代表一个确定的宽度或者match_parent
            width = widthSize;
        } else {
            width = (int) (paint.measureText(conetnt) + getPaddingLeft() + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            if (rect == null) {
                rect = new Rect();
            }
            paint.getTextBounds(conetnt, 0, conetnt.length(), rect);
            height = rect.height() + getPaddingBottom() + getPaddingTop();
        }
        setMeasuredDimension(width, height);

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
        /**
         * paint.measureText只能获取宽度而getTextBounds还可以获取高度因为它传入了Rect
         * paint.measureText获取的宽度略大于getTextBounds的宽度，measureText会在两边加上额外的宽度(类似于字间距)
         */
        baseX = (int) (canvas.getWidth() - paint.measureText(conetnt)) / 2;
        /**
         * 平时接触的文字在绘制出来的时候ascent的绝对值都比descent的绝对值大，而绘制是以基线为基准所以在drawText的时候设置y为0的话只能看到文字下半部的一点
         * 所以要想文字居中baseline不能在view的中心，而是在更下面点
         * 所以offset=ascent和descent的差值再除以2，此时offset为负，由于baseline需要在比view中间更靠下的位置，所以再用baseY=canvas.getHeight/2-offset
         */
        baseY = (int) (canvas.getHeight() - (fontMetrics.descent + fontMetrics.ascent)) / 2;
        canvas.drawLine(0, canvas.getHeight() / 2, canvas.getWidth(), canvas.getHeight() / 2, paint);
        /**
         * drawText中x、y所代表的坐标决定了文字的位置，这个坐标不是文字的左上角而是在左下角
         * y还表示了基线的位置，设置为0的话表示基线就会在自定义view的最上方这样绘制出来的文字就在view外部了结果就是看不到文字
         * x表示了文字最左边的位置，实际上这个位置比最左边文字的位置还要再左边一点，因为字符与字符之间需要一点间距所以绝大多数字符它的宽度都要略大于显示的宽度
         *
         * paint.setTextAlign();需要测试去体会。。。
         */
        //FontMetrics是我们在绘制文本之前就获取了，它跟文字内容无关而是跟paint的size和typeface有关
        canvas.drawText(conetnt, baseX, baseY, paint);

    }
}
