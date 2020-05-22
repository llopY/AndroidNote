package com.example.lop.androidnote.animation;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

import java.io.IOException;

public class MainAnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUI() {
        setTitle("动画相关");
        showBack();
        GifImageView gifImageView=findViewById(R.id.gif_image_view);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.ic_gif_finish_success);
            gifImageView.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_animation;
    }
}
