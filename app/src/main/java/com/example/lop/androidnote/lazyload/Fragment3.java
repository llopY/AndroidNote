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
public class Fragment3 extends Fragment {
    private final String TAG = "----___>";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "fragment3 onCreate");
    }

    public static Fragment3 newInstance(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("str", str);
        Fragment3 fragment1 = new Fragment3();
        fragment1.setArguments(bundle);
        return fragment1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);
        Log.i(TAG, "fragment3 onCreateView");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "fragment3 onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "fragment3 onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "fragment3 onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "fragment3 onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "fragment3 onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "fragment3 onDestroy");
    }
}
