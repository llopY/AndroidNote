package com.example.lop.androidnote.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;
import com.example.lop.androidnote.custom.customview.CustomView;
import com.example.lop.androidnote.custom.customview.CustomView2;
import com.example.lop.androidnote.custom.tools.CustomViewToolsActivity;

import java.util.ArrayList;
import java.util.List;

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

}
