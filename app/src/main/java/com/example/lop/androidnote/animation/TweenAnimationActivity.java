package com.example.lop.androidnote.animation;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweenAnimationActivity extends BaseActivity {
    @BindView(R.id.view_test)
    View viewTest;
    @BindView(R.id.view_test2)
    View viewTest2;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("ScaleAnimation");
        showBack();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_tween;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.anim_scale1);
                viewTest.startAnimation(animation1);
                break;
            case R.id.btn_2:
                Animation animation2 = AnimationUtils.loadAnimation(context, R.anim.anim_scale2);
                viewTest.startAnimation(animation2);
                break;
            case R.id.btn_3:
                Animation animation3 = AnimationUtils.loadAnimation(context, R.anim.anim_scale3);
                viewTest.startAnimation(animation3);
                break;
            case R.id.btn_4:
                Animation animation4 = AnimationUtils.loadAnimation(context, R.anim.anim_scale4);
                viewTest.startAnimation(animation4);
                break;
            case R.id.btn_rotate1:
                Animation rotateAnim1 = AnimationUtils.loadAnimation(context, R.anim.anim_rotate1);
                viewTest2.startAnimation(rotateAnim1);
                break;
            case R.id.btn_rotate2:
                Animation rotateAnim2 = AnimationUtils.loadAnimation(context, R.anim.anim_rotate2);
                viewTest2.startAnimation(rotateAnim2);
                break;
            case R.id.btn_translate:
                Animation translateAnim = AnimationUtils.loadAnimation(context, R.anim.aim_translate);
                viewTest2.startAnimation(translateAnim);
                break;
            case R.id.btn_set:
                break;
        }
    }
}
