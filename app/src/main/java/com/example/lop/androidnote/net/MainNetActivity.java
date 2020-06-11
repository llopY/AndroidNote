package com.example.lop.androidnote.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.net.okhttp.MainOkhttpActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainNetActivity extends BaseActivity {

    @BindView(R.id.btn_about_okhttp)
    Button btnAboutOkhttp;
    @BindView(R.id.btn_about_retrofit)
    Button btnAboutRetrofit;
    @BindView(R.id.btn_about_rxjava)
    Button btnAboutRxjava;
    @BindView(R.id.btn_about_rxandroid)
    Button btnAboutRxandroid;
    @BindView(R.id.btn_http_url_connect_get)
    Button btnHttpUrlConnectGet;
    @BindView(R.id.image)
    ImageView image;

    private String imageURL="http://img5.imgtn.bdimg.com/it/u=3238317745,514710292&fm=26&gp=0.jpg";
    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("网络");
        showBack();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_net;
    }

    @OnClick({R.id.btn_about_okhttp, R.id.btn_about_retrofit, R.id.btn_about_rxjava, R.id.btn_about_rxandroid
    ,R.id.btn_http_url_connect_get})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_about_okhttp:
                launchActivity(MainOkhttpActivity.class);
                break;
            case R.id.btn_about_retrofit:
                break;
            case R.id.btn_about_rxjava:
                break;
            case R.id.btn_about_rxandroid:
                break;
            case R.id.btn_http_url_connect_get:
                doHttpUrlConnectGet();
                break;
        }
    }

    /**
     * httpurlconnection get请求
     */
    private void doHttpUrlConnectGet() {
       new Thread(() -> {
           try {
               URL url =new URL(imageURL);
               HttpURLConnection connection= (HttpURLConnection) url.openConnection();
               connection.setReadTimeout(5000);
               connection.setRequestMethod("GET");
               InputStream inputStream=connection.getInputStream();
               BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
               Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
               runOnUiThread(() -> {
                   image.setImageBitmap(bitmap);
               });
           } catch (Exception e) {
               e.printStackTrace();
           }
       }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
