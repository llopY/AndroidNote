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
public class Fragment2 extends Fragment {
    private final String TAG = "----___>";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "fragment2 onCreate");
    }

    public static Fragment2 newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("str", str);
        Fragment2 fragment1 = new Fragment2();
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        Log.e(TAG, "fragment2 onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "fragment2 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "fragment2 onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "fragment2 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "fragment2 onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "fragment2 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "fragment2 onDestroy");
    }
}
