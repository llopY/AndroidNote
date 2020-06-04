package com.example.lop.androidnote.animation;

import android.animation.TypeEvaluator;

/**
 * @author: lop
 * @createTime: 2020-06-04
 * @desc:
 */
public class PointColorEvaluator implements TypeEvaluator {
    private int currentRed = -1;
    private int currentGreen = -1;
    private int currentBlue = -1;
    private int redOffset;
    private int greenOffset;
    private int blueOffset;
    private int colorOffset;
    private String resultColor;

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        /**
         * parseInt(String s,int radix)就是求    “int radix”进制数“String s”的十进制数是多少
         * 而我们给的rgb就是一个十六进制表示的
         */
        int startRed = Integer.parseInt(((String) startValue).substring(1, 3), 16);
        int startGreen = Integer.parseInt(((String) startValue).substring(3, 5), 16);
        int startBlue = Integer.parseInt(((String) startValue).substring(5, 7), 16);
        int endRed = Integer.parseInt(((String) endValue).substring(1, 3), 16);
        int endGreen = Integer.parseInt(((String) endValue).substring(3, 5), 16);
        int endBlue = Integer.parseInt(((String) endValue).substring(5, 7), 16);

        //初始化颜色值
        if (currentRed == -1) {
            currentRed = startRed;
        }
        if (currentGreen == -1) {
            currentGreen = startGreen;
        }
        if (currentBlue == -1) {
            currentBlue = startBlue;
        }
        //计算出开始到结束颜色的差值(一个绝对值)
        redOffset = Math.abs(startRed - endRed);
        greenOffset = Math.abs(startGreen - endGreen);
        blueOffset = Math.abs(startBlue - endBlue);
        colorOffset = redOffset + greenOffset + blueOffset;

        if (startRed != endRed && currentRed != endRed) {
            currentRed = getCurrentColor(startRed, endRed, fraction, colorOffset, 0);
        }
        if (startGreen != endGreen && currentGreen != endGreen) {
            currentGreen = getCurrentColor(startGreen, endGreen, fraction, colorOffset, redOffset);
        }
        if (startBlue != endBlue && currentBlue != endBlue) {
            currentBlue = getCurrentColor(startBlue, endBlue, fraction, colorOffset, redOffset + greenOffset);
        }

        resultColor = "#" + getResultColor(currentRed) + getResultColor(currentGreen) + getResultColor(blueOffset);
        return resultColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getResultColor(int value) {
        String hexString = Integer.toHexString(value);
        //因为红绿蓝都是长度为2的string，长度为1的话就在前面加0
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }

    private int getCurrentColor(int startColor, int endColor, float fraction, int colorTotalDiff, int offset) {
        //因为颜色值会随意传所以需要判断大小
        int currentColor;
        if (startColor < endColor) {
            currentColor = (int) (startColor + fraction * colorTotalDiff - offset);
        } else {
            currentColor = (int) (startColor - fraction * colorTotalDiff - offset);
        }
        return currentColor;
    }
}
