package com.example.lop.androidnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;

public class ActivityEnterAnim extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.text).setOnClickListener(v -> {
            finish();
            overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
    }
}
