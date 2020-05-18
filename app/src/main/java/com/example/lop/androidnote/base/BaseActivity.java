package com.example.lop.androidnote.base;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.example.lop.androidnote.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: lop
 * @createTime: 2020-05-14
 * @desc:
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected LayoutInflater inflater;
    protected View headView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        inflater=LayoutInflater.from(this);
        headView= findViewById(R.id.view_title);
        if (headView!=null){
            BarUtils.addMarginTopEqualStatusBarHeight(headView);
        }
        BarUtils.setStatusBarLightMode(this,false);
        BarUtils.setStatusBarColor(this,getResources().getColor(R.color.blue));
        initUI();
    }

    protected abstract void initUI();

    public abstract int getLayoutID();

    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            ((TextView)findViewById(R.id.tv_title)).setText(title);
        }
    }
    public void  launchActivity(Class<?>cls){
        Intent intent=new Intent(this,cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void showBack(){
        ImageView imageView=findViewById(R.id.image_left);
        imageView.setImageResource(R.mipmap.ic_withe_back);
        imageView.setOnClickListener(v -> finish());
    }

}
