package com.example.lop.androidnote.js;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.lop.androidnote.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

public class ActivityJavaWithJs extends AppCompatActivity {

    private TextView textView;
    private BridgeWebView webView;
    private WebSettings settings;
    private ProgressBar progressBar;
    private String baseUri = "http://i.crazyming.cn/web/sync.html?channel=inLive";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_with_js);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.result);

        webView = findViewById(R.id.webView);
        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        //初始化jsbridge，和js建立连接
        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.loadUrl("file:///android_asset/javascript.html");

        //注册函数让js调用
        webView.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("this is come from js：", data);
                Toast.makeText(ActivityJavaWithJs.this, data, Toast.LENGTH_LONG).show();
                //对数据处理后，回传给js
                String substring = data.substring(0, 5);
                if (function != null) {
                    function.onCallBack(substring);
                }

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.java_send_js_msg:
                mJavaSendToMessage();
                break;
            case R.id.java_to_js:
                mJavaCallJs();
                break;
        }
    }
    private void mJavaSendToMessage() {
        //java 发送给 js 的数据
//                webView.send("hello");
        webView.send("Hello", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("----------->", "java 发送给 js 数据后的 回调数据：\n" + data);
            }
        });
    }

    private void mJavaCallJs() {
        webView.callHandler("functionInJs", "调用js方法并发送数据", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.e("----------->", "回调数据来自 js =:\n" + data);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
