package com.example.lop.androidnote.lazyload;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lop.androidnote.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author: lop
 * @createTime: 2020-06-17
 * @desc:
 */
public class FragmentLazyLoad extends Fragment {
    private String content;
    private final String TAG = "------>";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        content = getArguments().getString("content");
        Log.e(TAG + content, "onCreate");
    }

    public static FragmentLazyLoad newInstance(String content) {
        Bundle bundle = new Bundle();
        bundle.putString("content", content);
        FragmentLazyLoad fragmentLazyLoad = new FragmentLazyLoad();
        fragmentLazyLoad.setArguments(bundle);
        return fragmentLazyLoad;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG + content, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_lazy_load, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        ((TextView) view.findViewById(R.id.text)).setText(content);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            Log.e(TAG + content, "onHiddenChanged==true");
        } else {
            Log.e(TAG + content, "onHiddenChanged==false");
        }
    }

    /**
     * isVisibleToUser 会执行两次一次false一次true，false表示上一个fragment不可见了
     * true表示当前fragment可见
     * 【注：setUserVisibleHint的生命周期在onCreate之前】
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.e(TAG + content, "setUserVisibleHint true");
        } else {
            Log.e(TAG + content, "setUserVisibleHint false");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getContext(), content + " onResume", Toast.LENGTH_SHORT).show();
        Log.e(TAG + content, "onResume");
    }
}
