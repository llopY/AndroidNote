package com.example.lop.androidnote;


import android.app.Application;
import android.util.Log;

import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author: lop
 * @createTime: 2020-08-02
 * @desc:
 */
public class MyApplication extends Application {
    public static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        init();
    }

    /**
     * 这里的初始化针对imsdk不包含ui
     *
     * 类 V2TIMManager 是 IM SDK 主核心类，负责 IM SDK 的初始化、登录、消息收发，建群退群等功能，
     * 是 IM SDK 的入口类。调用 initSDK 接口即可完成初始化：
     */
    private void init() {
        // 1. 从 IM 控制台获取应用 SDKAppID，详情请参考 SDKAppID。
// 2. 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
// 3. 指定 log 输出级别，详情请参考 SDKConfig。
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);
// 4. 初始化 SDK 并设置 V2TIMSDKListener 的监听对象。
// initSDK 后 SDK 会自动连接网络，网络连接状态可以在 V2TIMSDKListener 回调里面监听。
        V2TIMManager.getInstance().initSDK(this, Integer.parseInt("1400408083"), config, new V2TIMSDKListener() {
            // 5. 监听 V2TIMSDKListener 回调
            @Override
            public void onConnecting() {
                // 正在连接到腾讯云服务器 适合在 UI 上展示“正在连接”状态。
                Log.e("----------->","onConnecting");
            }

            @Override
            public void onConnectSuccess() {
                // 已经成功连接到腾讯云服务器
                Log.e("----------->","onConnectSuccess");
            }

            @Override
            public void onConnectFailed(int code, String error) {
                // 连接腾讯云服务器失败 可以提示用户当前网络连接不可用。
                Log.e("----------->","onConnectFailed");
            }

            @Override
            public void onUserSigExpired() {
                super.onUserSigExpired();
                //登录票据已经过期
                Log.e("----------->","onUserSigExpired");
            }
        });

    }

}
