package com.example.lop.androidnote.tim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.example.lop.androidnote.R;
import com.example.lop.androidnote.utils.SpUtils;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

public class LoginActivity extends AppCompatActivity {

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText=findViewById(R.id.edit_name);
    }

    public void onClick(View view){
        if (TextUtils.isEmpty(editText.getText())){
            ToastUtils.showShort("请输入用户名");
            return;
        }
        String userID=editText.getText().toString();
        /**
         * 以下场景无需调用登录：
         * 用户的网络断开并重新连接后，不需要调用 login 函数，SDK 会自动上线。
         * 当一个登录过程在进行时，不需要进行重复登录。
         */
        V2TIMManager.getInstance().login(userID, GenerateTestUserSig.genTestUserSig(userID), new V2TIMCallback() {
            @Override
            public void onError(int i, String s) {
                ToastUtils.showShort("登录失败："+i+" "+s);
            }

            @Override
            public void onSuccess() {
                ToastUtils.showShort("登录成功");
                SpUtils.getInstance().setString(SpUtils.USER_ID,userID);
                jump();
            }
        });
    }

    private void jump() {
        Intent intent=new Intent(LoginActivity.this,MainTimActivity.class);
        startActivity(intent);
        finish();
    }

}
