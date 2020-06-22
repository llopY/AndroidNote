package com.example.lop.androidnote.custom;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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
        Log.e("----------------->","after post");
        /**
         * 通过以上日志(after post之前)可以知道在onCreate执行的时候onAttachedToWindow还没有执行，即view.post里面用到的mAttachInfo为空，则会走getRunQueue().post(action)方法
         *
         * getRunQueue().post(action) 会通过HandlerActionQueue类中的HandlerAction数组把当前的runnable缓存起来（具体去看该类的源码）
         * 最后通过该类的executeActions方法就可以执行这些runnable，而executeActions是在view的dispatchAttachedToWindow方法中调用的，onAttachedToWindow也是在这个方法中调用
         *
         * 到处又知道了，要是view.post里面用到的mAttachInfo不为空则直接把当前的runnable放到handler对应的消息队列中，为空就把当前的runnable缓存起来（保存在HandlerActionQueue类的一个数组中）
         * 最后当在执行dispatchAttachedToWindow的时候会调用mRunQueue.executeActions(info.mHandler)方法把所有缓存的runnable放入对应的消息队列中
         *
         * 最终回到了dispatchAttachedToWindow到底在哪里调用的？
         * 在viewGroup中有调用该方法，它会遍历所有子view并把自己的mAttachInfo传给子类，而viewGroup的mAttachInfo又是哪来的？
         * ViewRootImpl.performTraversals()是通知activity中的view开始测量、布局、绘制等
         * 其中有 View host=mView; host.dispatchAttachedToWindow(mAttachInfo,0)，其中mView是activity的decorView，decorView继承FrameLayout
         * 也是ViewGroup，这样就知道了ViewGroup的mAttachInfo的由来。
         *
         * 而viewImpl的mAttachInfo则是直接通过new View.AttachInfo(...,mHandler)得到的，这里传了很多参数，我们只需要关注mHandler，这里的mHandler在viewImpl是
         * 直接new出来的且调用的是无参构造方法，则mHandler是跟主线程绑定的，所以在view.post里面我们可以跟新UI
         *
         * 根据源码可以知道performTraversals()是在performMeasure()、performLayout()、performDraw()之前调用的假设按照顺序执行我们得到的宽高还是0
         * 解释
         * Android是消息驱动，所以它执行performTraversals()其实是由另一个消息去驱动执行了，而这个消息是TraversalRunnable，具体可以看ViewRootImpl的源码，里面有TraversalRunnable的定义。
         * 因此，这个时候Handler正在执行着TraversalRunnable这个Runnable，而我们post的Runnable要等待TraversalRunnable执行完才会去执行，
         * 而TraversalRunnable这里面又会进行measure,layout和draw流程，所以等到执行我们的Runnable时，此时的View就已经被measure过了，所以获取到的宽高就是measure过后的宽高。
         *
         * https://www.cnblogs.com/dasusu/p/8047172.html
         */
    }
}
