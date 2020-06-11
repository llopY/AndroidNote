package com.example.lop.androidnote.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;


import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;


public class XPermission {

    private static int mRequestCode = -1;

    private static OnPermissionListener mOnPermissionListener;

    public interface OnPermissionListener {

        void onPermissionGranted();

        void onPermissionDenied();
    }

    /**
     * 请求权限
     *
     * @param context
     * @param requestCode
     * @param permissions
     * @param listener
     */
    public static void requestPermissions(Context context, int requestCode
            , String[] permissions, OnPermissionListener listener) {
        mOnPermissionListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
            if (context instanceof Activity) {
                List<String> deniedPermissions = getDeniedPermissions(context, permissions);
                if (deniedPermissions.size() > 0) {
                    mRequestCode = requestCode;
                    ((Activity) context).requestPermissions(deniedPermissions
                            .toArray(new String[deniedPermissions.size()]), requestCode);

                } else {
                    if (mOnPermissionListener != null)
                        mOnPermissionListener.onPermissionGranted();
                }
            } else {
                throw new RuntimeException("Context must be an Activity");
            }
        } else {
            if (mOnPermissionListener != null)
                mOnPermissionListener.onPermissionGranted();
        }
    }

    /**
     * 获取请求权限中需要授权的权限
     */
    public static List<String> getDeniedPermissions(Context context, String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 请求权限结果，对应Activity中onRequestPermissionsResult()方法。
     */
    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mRequestCode != -1 && requestCode == mRequestCode) {
            if (mOnPermissionListener != null) {
                if (verifyPermissions(grantResults)) {
                    mOnPermissionListener.onPermissionGranted();
                } else {
                    mOnPermissionListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 验证所有权限是否都已经授权
     */
    private static boolean verifyPermissions(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 显示提示对话框
     */
    public static void showTipsDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage("当前应用缺少必要权限，该功能暂时无法使用。如若需要，请单击【确定】按钮前往设置中心进行权限授权。")
                .setPositiveButton("确定", null).show();
    }
}

