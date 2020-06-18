package com.example.lop.androidnote.lazyload;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lop.androidnote.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author: lop
 * @createTime: 2020-06-18
 * @desc:
 */
public class Fragment1 extends Fragment {
    private final String TAG = "----___>";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "fragment1 onCreate");
    }

    public static Fragment1 newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("str", str);
        Fragment1 fragment1 = new Fragment1();
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1_layout, container, false);
        Log.d(TAG, "fragment1 onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "fragment1 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "fragment1 onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "fragment1 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "fragment1 onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "fragment1 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "fragment1 onDestroy");
    }
}
