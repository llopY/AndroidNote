package com.example.lop.androidnote.custom;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;
import com.example.lop.androidnote.custom.customview.CustomView;
import com.example.lop.androidnote.custom.customview.CustomView2;
import com.example.lop.androidnote.custom.tools.CustomViewToolsActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainCustomActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.custom_view)
    CustomView customView;
    @BindView(R.id.custom_view2)
    CustomView2 customView2;
    @BindView(R.id.btn_start)
    Button btnStart;
    private List<String> list = new ArrayList<>();
    private BaseRVAdapter mAdapter;
    private TextView textView;
    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("CustomView");
        showBack();
        list.add("Paint、Canvas");
        list.add("TypedValue");
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new BaseRVAdapter(R.layout.view_common_rv_item);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewInstance(list);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position) {
                case 0:
                    launchActivity(CustomViewToolsActivity.class);
                    break;
                case 1:
                    break;
            }
        });

        btnStart.setOnClickListener(v -> {
            new Thread(customView2).start();
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_custom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView=findViewById(R.id.view_post);
        Log.e("----------------->","width=="+textView.getWidth());
        Log.e("----------------->","height=="+textView.getHeight());
        Log.e("----------------->",Thread.currentThread().getName()+" "+Thread.currentThread().getId());
        textView.post(() -> {
            Log.e("----------------->post","width=="+textView.getWidth());
            Log.e("----------------->post","height=="+textView.getHeight());
            Log.e("----------------->post",Thread.currentThread().getName()+" "+Thread.currentThread().getId());
        });
    }
}
