package com.example.lop.androidnote.intentservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HandlerThreadActivity extends BaseActivity {

    @BindView(R.id.btn_start_intent_service)
    Button btnStartIntentService;
    @BindView(R.id.btn_star_handler_thread)
    Button btnStarHandlerThread;

    private final String TAG = "-------->";
    private UIHandler uiHandler;
    private Handler mHandler;
    private HandlerThread handlerThread;
    private Looper looper;
    private Bundle bundle;
    private ServiceHandler serviceHandler;

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        setTitle("HandlerThread");
        showBack();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_handler_thread;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Log.e(TAG, "main threadID==" + Thread.currentThread().getId());
        MyIntentService myIntentService = new MyIntentService("HnadlerThreadTest");
        bundle = new Bundle();
        if (uiHandler == null) {
            uiHandler = new UIHandler(this);
        }
        if (serviceHandler == null) {
            /**
             * quit方法会将消息队列中的所有消息移除（延迟消息和非延迟消息）。
             * quitSafely会将消息队列所有的延迟消息移除，非延迟消息派发出去让Handler去处理。quitSafely相比于quit方法安全之处在于清空消息之前会派发所有的非延迟消息
             */
            handlerThread = new HandlerThread("handlerThreadExam");
            handlerThread.start();//注：这里必须先开始线程再去获取looper否则报错，因为looper是在handlerThread的run方法中才被赋值的
            looper = handlerThread.getLooper();
            serviceHandler = new ServiceHandler(looper, this);
        }

//        if (mHandler==null || handlerThread==null){
//            handlerThread=new HandlerThread("handlerThreadExam");
//            handlerThread.start();
//            looper=handlerThread.getLooper();
//            mHandler=new Handler(looper){
//                @Override
//                public void handleMessage(@NonNull Message msg) {
//                    Log.e(TAG,"mHandler id："+Thread.currentThread().getId());
//                    try {
//                        Thread.sleep(3*1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (uiHandler!=null){
//                        msg=Message.obtain();
//                        bundle.putString("msg","测试测试测试测试测试");
//                        msg.setData(bundle);
//                        uiHandler.sendMessageAtTime(msg,0);
//                    }
//
//                }
//            };
//        }

    }

    @OnClick({R.id.btn_start_intent_service, R.id.btn_star_handler_thread})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_intent_service:
                Intent intent = new Intent(HandlerThreadActivity.this, MyIntentService.class);
                intent.putExtra("test", "intentService" + 1);
                startService(intent);

                Intent intent2 = new Intent(HandlerThreadActivity.this, MyIntentService.class);
                intent2.putExtra("test", "intentService" + 2);
                startService(intent2);

                Intent intent3 = new Intent(HandlerThreadActivity.this, MyIntentService.class);
                intent3.putExtra("test", "intentService" + 3);
                startService(intent3);
                break;
            case R.id.btn_star_handler_thread:
//                mHandler.sendEmptyMessage(0);
                serviceHandler.sendEmptyMessage(0);
                break;
        }
    }

    /**
     * ui线程的handler
     */
    private class UIHandler extends Handler {
        private HandlerThreadActivity mActivity;

        public UIHandler(HandlerThreadActivity activity) {
            mActivity = new WeakReference<>(activity).get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.e(TAG, "UI handleMessage：" + msg.getData().getString("msg") + "thread id==" + Thread.currentThread().getId());
        }

        private void release() {
            mActivity = null;
        }
    }

    /**
     * 新开线程handler
     */
    private class ServiceHandler extends Handler {
        private HandlerThreadActivity mActivity;

        public ServiceHandler(Looper looper, HandlerThreadActivity activity) {
            /**
             * 这里通过传入的looper就可以把该handler和looper对应的线程绑定
             * 因为looper的prepare方法会创建一个消息队列(MessageQueue)，这样就把当前handler和looper和messageQueue绑定在一起了
             * 则在使用handler发送消息的时候就会存放在这个消息队列当中，而这个消息队列又是和HandlerThread中的线程绑定的。。。。。。。
             */
            super(looper);
            mActivity = new WeakReference<>(activity).get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.e(TAG, "thread handleMessage id：" + Thread.currentThread().getId());
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (uiHandler != null) {
                msg = Message.obtain();
                bundle.putString("msg", "测试测试测试测试测试");
                msg.setData(bundle);
                uiHandler.sendMessageAtTime(msg, 0);
            }
        }

        private void release() {
            mActivity = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uiHandler != null) {
            uiHandler.removeCallbacksAndMessages(null);
            uiHandler.release();
            uiHandler = null;
        }

        /**
         * 这里通过serviceHandler和mHandler的两种写法，不用弱引用直接清空handler里面的消息并把handler置空也不会出现内存泄露
         * 要是既不想造成内存泄露也想让消息全部执行就把UIHandler设为静态内部类
         */
        if (serviceHandler != null) {
            serviceHandler.removeCallbacksAndMessages(null);
            serviceHandler.release();
            serviceHandler = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }
}
