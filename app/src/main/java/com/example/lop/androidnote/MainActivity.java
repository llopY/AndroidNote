package com.example.lop.androidnote;

import android.content.Intent;
import android.os.Bundle;

import com.example.lop.androidnote.animation.MainAnimationActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;
import com.example.lop.androidnote.custom.MainCustomActivity;
import com.example.lop.androidnote.file.MainFileActivity;
import com.example.lop.androidnote.intentservice.HandlerThreadActivity;
import com.example.lop.androidnote.js.ActivityJavaWithJs;
import com.example.lop.androidnote.lazyload.ActivityLazyLoad;
import com.example.lop.androidnote.net.MainNetActivity;
import com.example.lop.androidnote.tim.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BaseRVAdapter mAdapter;
    private List<String> list;
    private String uri = "http://hbimg.b0.upaiyun.com/357d23d074c2954d568d1a6f86a5be09d190a45116e95-0jh9Pg_fw658";
    private String uri2 = "http://5b0988e595225.cdn.sohucs.com/images/20180609/5b674fd57b0448b39eba198db37a7875.gif";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        recyclerView = findViewById(R.id.recyclerView222);
        initUI();
    }


    private void initUI() {
        list = new ArrayList<>();
        list.add("网络相关");
        list.add("动画相关");
        list.add("自定义控件");
        list.add("文件");
        list.add("IntentService原理");
        list.add("Fragment懒加载，setMaxLifecycle");
        list.add("activity进场动画");
        list.add("JsBridge");
        list.add("TIM");
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
                case 6:
                    launchActivity(ActivityEnterAnim.class);
                    break;
                case 7:
                    launchActivity(ActivityJavaWithJs.class);
                    break;
                case 8:
                    launchActivity(LoginActivity.class);
                    break;
            }
        });

    }

    private void launchActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
