package com.example.lop.androidnote;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * @author: lop
 * @createTime: 2020-06-28
 * @desc:
 */
public class MyClickSpan extends ClickableSpan {
    @Override
    public void onClick(@NonNull View widget) {
        Toast.makeText(widget.getContext(),"测试",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
//        super.updateDrawState(ds);
        ds.setColor(Color.BLUE);
        ds.setUnderlineText(false);
    }
}
