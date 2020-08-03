package com.example.lop.androidnote.tim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.lop.androidnote.R;
import com.example.lop.androidnote.utils.SpUtils;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageManager;
import com.tencent.imsdk.v2.V2TIMSendCallback;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;

public class MainTimActivity extends AppCompatActivity {
    private EditText editText;
    private TextView textView;
    private V2TIMSimpleMsgListener simpleMsgListener;
    private String userID;
    private TextView tvName;
    private EditText edit_to_userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tim);
        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        userID = SpUtils.getInstance().getString(SpUtils.USER_ID);
        tvName = findViewById(R.id.user_id);
        tvName.setText("当前账号id：" + userID);
        edit_to_userID = findViewById(R.id.editText2);
        init();
    }

    private void init() {
        if (simpleMsgListener == null) {
            simpleMsgListener = new V2TIMSimpleMsgListener() {
                @Override
                public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                    super.onRecvC2CTextMessage(msgID, sender, text);
                    ToastUtils.showShort("接受到来自" + sender.getUserID() + "的消息");
                    textView.setText(text);
                }
            };
        }
        V2TIMManager.getInstance().addSimpleMsgListener(simpleMsgListener);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send_msg:
                if (TextUtils.isEmpty(editText.getText())) {
                    ToastUtils.showShort("内容不能为空");
                    return;
                }
                if (TextUtils.isEmpty(edit_to_userID.getText())) {
                    ToastUtils.showShort("接收人id不能为空");
                    return;
                }
                V2TIMManager.getInstance().sendC2CTextMessage(editText.getText().toString(), edit_to_userID.getText().toString(), new V2TIMSendCallback<V2TIMMessage>() {
                    @Override
                    public void onProgress(int i) {

                    }

                    @Override
                    public void onError(int i, String s) {
                        ToastUtils.showShort("发送消息失败：" + i + " " + s);
                    }

                    @Override
                    public void onSuccess(V2TIMMessage v2TIMMessage) {
                        ToastUtils.showShort("发送消息成功");
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (V2TIMManager.getInstance() != null) {
            V2TIMManager.getInstance().logout(null);//退出登录
            V2TIMManager.getInstance().removeSimpleMsgListener(simpleMsgListener);
        }
    }
}
