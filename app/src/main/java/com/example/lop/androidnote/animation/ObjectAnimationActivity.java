package com.example.lop.androidnote.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObjectAnimationActivity extends BaseActivity {

    @BindView(R.id.btn_valueAnimator)
    Button btnValueAnimator;
    private int screemWidth;//手机屏幕宽度
    private int originalWidth;//动画开始前的宽
    private int endWidth;//动画结束后的宽
    private FrameLayout.LayoutParams layoutParams;
    private ValueAnimator valueAnimator;
    private boolean isNeedMagnify;
    private int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("属性动画");
        showBack();
        screemWidth = getResources().getDisplayMetrics().widthPixels;
        originalWidth = screemWidth - UIUtil.dip2px(this, 20);
        endWidth = originalWidth - UIUtil.dip2px(this, 60);
        offset = UIUtil.dip2px(this, 60);
        layoutParams = (FrameLayout.LayoutParams) btnValueAnimator.getLayoutParams();
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            if (isNeedMagnify) {//放大
                layoutParams.width = (int) (endWidth + ((float) animation.getAnimatedValue() * offset));
                btnValueAnimator.setLayoutParams(layoutParams);
            } else {//缩小
                layoutParams.width = (int) (originalWidth - ((float) animation.getAnimatedValue() * offset));
                btnValueAnimator.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isNeedMagnify) {
                    isNeedMagnify = false;
                } else {
                    isNeedMagnify = true;
                }
            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_object_animation;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_valueAnimator:
                if (!valueAnimator.isRunning()) {
                    valueAnimator.start();
                }
                break;
        }
    }
}
