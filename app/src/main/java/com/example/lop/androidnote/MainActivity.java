package com.example.lop.androidnote;

import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.example.lop.androidnote.animation.MainAnimationActivity;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;
import com.example.lop.androidnote.custom.MainCustomActivity;
import com.example.lop.androidnote.file.MainFileActivity;
import com.example.lop.androidnote.intentservice.HandlerThreadActivity;
import com.example.lop.androidnote.intentservice.MyIntentService;
import com.example.lop.androidnote.lazyload.ActivityLazyLoad;
import com.example.lop.androidnote.net.MainNetActivity;
import com.example.lop.androidnote.utils.VersionUtils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BaseRVAdapter mAdapter;
    private List<String> list;
    private String uri = "http://hbimg.b0.upaiyun.com/357d23d074c2954d568d1a6f86a5be09d190a45116e95-0jh9Pg_fw658";
    private String uri2="http://5b0988e595225.cdn.sohucs.com/images/20180609/5b674fd57b0448b39eba198db37a7875.gif";
    private GifImageView gifImageView;
    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("首页");

        list = new ArrayList<>();
        list.add("网络相关");
        list.add("动画相关");
        list.add("自定义控件");
        list.add("文件");
        list.add("IntentService原理");
        list.add("Fragment懒加载，setMaxLifecycle");
        mAdapter = new BaseRVAdapter(R.layout.view_main_rv_item);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewInstance(list);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0://网络相关
                    launchActivity(MainNetActivity.class);
                    break;
                case 1://动画
                    launchActivity(MainAnimationActivity.class);
                    break;
                case 2://自定义控件
                    launchActivity(MainCustomActivity.class);
                    break;
                case 3:
                    launchActivity(MainFileActivity.class);
                    break;
                case 4:
                    launchActivity(HandlerThreadActivity.class);
                    break;
                case 5:
                    launchActivity(ActivityLazyLoad.class);
                    break;
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


        gifImageView=findViewById(R.id.gifImageView);
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(uri2)
                .get()
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                byte[]bytes=response.body().bytes();
                runOnUiThread(() -> {
                    GifDrawable gifDrawable= null;
                    try {
                        gifDrawable = new GifDrawable(bytes);
                        gifDrawable.setLoopCount(Character.MAX_VALUE);
                        gifImageView.setImageDrawable(gifDrawable);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
            }
        });
    }

}
