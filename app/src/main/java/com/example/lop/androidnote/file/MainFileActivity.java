package com.example.lop.androidnote.file;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.utils.XPermission;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFileActivity extends BaseActivity {

    @BindView(R.id.btn_create_directory)
    Button btnCreateDirectory;
    @BindView(R.id.btn_create_file)
    Button btnCreateFile;

    @Override
    protected void initUI() {
        setTitle("File");
        showBack();
        XPermission.requestPermissions(this, 200, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {

            }

            @Override
            public void onPermissionDenied() {

            }
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_create_directory,R.id.btn_create_file})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_directory:
                File file=new File("/test/sdcard/flie/file.txt");
                if (file.exists()){
                    file.delete();
                    Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_SHORT).show();
                }else {
                    File fileDirectory=new File("/test/sdcard/file");
                    fileDirectory.mkdir();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"创建成功",Toast.LENGTH_SHORT).show();
                    Log.i("___----------->",file.getAbsolutePath());
                    Log.i("___----------->",fileDirectory.getAbsolutePath());
                }
                break;
            case R.id.btn_create_file:

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        XPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
