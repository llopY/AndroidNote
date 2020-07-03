package com.example.lop.androidnote.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author: lop
 * @createTime: 2020-07-03
 * @desc:
 */
public class VersionUtils {
    public static int getVersionCode(Context context) {
        int mVersionCode =0;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            mVersionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mVersionCode;
    }
}
