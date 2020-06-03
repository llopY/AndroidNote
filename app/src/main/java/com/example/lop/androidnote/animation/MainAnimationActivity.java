package com.example.lop.androidnote.animation;

import android.os.Bundle;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.BaseRVAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAnimationActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BaseRVAdapter mAdapter;
    private List<String>list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("动画相关");
        showBack();
        list.add("补间动画");
        list.add("属性动画");
        mAdapter=new BaseRVAdapter(R.layout.view_common_rv_item);
        mAdapter.setNewInstance(list);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position==0){
                launchActivity(TweenAnimationActivity.class);
            }else {
                launchActivity(ObjectAnimationActivity.class);
            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_animation;
    }
}
