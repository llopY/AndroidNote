package com.example.lop.androidnote;

import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

/**
 * @author: lop
 * @createTime: 2020-06-29
 * @desc:
 */
public class MyLinkedMovementMethod extends LinkMovementMethod {
    private static MyLinkedMovementMethod sInstance;
    private long startTime;
    private long endTime;
    public static MyLinkedMovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new MyLinkedMovementMethod();
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
//        return super.onTouchEvent(widget, buffer, event);
        int action = event.getAction();
        if (action==MotionEvent.ACTION_DOWN){
            startTime=System.currentTimeMillis();
        }

        TextView tv = widget;
        CharSequence text = tv.getText();
        if (text instanceof SpannableString) {
            if (action == MotionEvent.ACTION_UP) {
                endTime=System.currentTimeMillis();
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= tv.getTotalPaddingLeft();
                y -= tv.getTotalPaddingTop();

                x += tv.getScrollX();
                y += tv.getScrollY();

                Layout layout = tv.getLayout();
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = ((SpannableString)text).getSpans(off, off, ClickableSpan.class);
                if (endTime-startTime>1000){
                    ToastUtils.showShort("长按");
                }else {
                    if (link.length != 0) {
                        link[0].onClick(tv);
                    } else {
                        //do textview click event
                        ToastUtils.showShort("onClick");
                    }
                }
            }
        }

        return true;
    }
}
