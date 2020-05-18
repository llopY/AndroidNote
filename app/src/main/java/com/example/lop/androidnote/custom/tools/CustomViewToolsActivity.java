package com.example.lop.androidnote.custom.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

/**
 * 一些canvas、paint的用法
 */
public class CustomViewToolsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initUI() {
        BarUtils.addMarginTopEqualStatusBarHeight(findViewById(R.id.view_head));
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_custom_view_tools;
    }
}
