package com.example.lop.androidnote;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.JsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.lop.androidnote.animation.MainAnimationActivity;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;
import com.example.lop.androidnote.custom.MainCustomActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BaseRVAdapter mAdapter;
    private List<String> list;
    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }
    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("首页");

        list=new ArrayList<>();
        list.add("网络相关");
        list.add("动画相关");
        list.add("自定义控件");
        mAdapter=new BaseRVAdapter(R.layout.view_main_rv_item);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setNewInstance(list);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position){
                case 0://网络相关

                    break;
                case 1://动画
                    launchActivity(MainAnimationActivity.class);
                    break;
                case 2://自定义控件
                    launchActivity(MainCustomActivity.class);
                    break;
            }
        });

    }
}
