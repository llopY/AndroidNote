package com.example.lop.androidnote.net.okhttp;

import android.os.Bundle;
import android.util.Half;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;
import com.example.lop.androidnote.base.Constants;
import com.example.lop.androidnote.net.bean.FoodAllClassifyBean;
import com.example.lop.androidnote.net.bean.FoodListByClassifyIDBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainOkhttpActivity extends BaseActivity {


    @BindView(R.id.btn_about_get)
    Button btnAboutGet;
    @BindView(R.id.btn_about_post_form)
    Button btnAboutPost;
    @BindView(R.id.btn_about_post_string)
    Button btnAboutString;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private FoodAllClassifyBean foodAllClassifyBean;
    @Override
    protected void initUI() {
        setTitle("OkHttp");
        showBack();
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main_okhttp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_about_get, R.id.btn_about_post_form,R.id.btn_about_post_string})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_about_get:
                doGet();
                break;
            case R.id.btn_about_post_form:
                doPostforForm();
                break;
            case R.id.btn_about_post_string:
                doPostForString();
                break;
        }
    }

    /**
     * post用RequestBody提交字符串
     */
    private void doPostForString() {
        //FormBody是RequestBody的子类
        OkHttpClient httpClient=new OkHttpClient();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("keyword","白菜");
            jsonObject.put("start",0);
            jsonObject.put("num",10);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("---------->json",jsonObject.toString());
        RequestBody requestBody=RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),jsonObject.toString());
        Request request=new Request.Builder()
                .url(Constants.getUrl("search"))
                .post(requestBody)
                .build();
        Log.e("---------->request",request.toString());
        Call call=httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                runOnUiThread(() -> {
                    try {
                        tvContent.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }

    /**
     * post请求FormBody提交表单(键值对)
     */
    private void doPostforForm() {
        OkHttpClient httpClient = new OkHttpClient();
        //FromBody用于提交表单键值对,key-value形式
        FormBody formBody = new FormBody.Builder()
                .add("classid", String.valueOf(2))
                .add("start", String.valueOf(0))
                .add("num", String.valueOf(10))
                .add("appkey", Constants.APP_KEY)
                .build();

        Request request = new Request.Builder()
                .url(Constants.getUrl("byclass"))
                .post(formBody)
                .build();
        Log.e("---------->request",request.toString());
        Call call = httpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) {
//                runOnUiThread(() -> {
//                    try {
//                        tvContent.setText(response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//        });
        call.enqueue(new ResponseCallBack<FoodListByClassifyIDBean>() {
            @Override
            protected void onSuccess(FoodListByClassifyIDBean cls) {
                Log.e("--------->success",new Gson().toJson(cls));
            }
        });
    }

    /**
     * OkHttp get请求
     * 参考极速api(菜谱分类)-->FoodAllClassifyBean
     */
    private void doGet() {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.jisuapi.com/recipe/class?appkey=ba8f5e734fa442cd")
                .get()
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                runOnUiThread(() -> {
                    try {
                        tvContent.setText(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        });
    }
}
