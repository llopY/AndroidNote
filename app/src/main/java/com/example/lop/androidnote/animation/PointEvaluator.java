package com.example.lop.androidnote.animation;

import android.animation.TypeEvaluator;

/**
 * @author: lop
 * @createTime: 2020-06-03
 * @desc:
 */
public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point();
        point.setX(x);
        point.setY(y);
        return point;
    }
}
