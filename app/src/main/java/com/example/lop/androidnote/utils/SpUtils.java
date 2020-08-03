package com.example.lop.androidnote.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.lop.androidnote.MyApplication;

/**
 * @author: lop
 * @createTime: 2020-08-03
 * @desc:
 */
public class SpUtils {
    public static final String USER_ID="userID";
    private static SpUtils spUtils;
    private SharedPreferences mSharedPreferences;
    private SpUtils() {
        mSharedPreferences= MyApplication.getInstance().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance(){
        if (spUtils==null){
            synchronized (SpUtils.class){
                spUtils=new SpUtils();
            }
        }
        return spUtils;
    }
    public void setString(String key,String value){
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)){
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }
    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }
}
