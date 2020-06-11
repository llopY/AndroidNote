package com.example.lop.androidnote.net.okhttp;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author: lop
 * @createTime: 2020-06-08
 * @desc:
 */
public abstract class ResponseCallBack<T> implements Callback {
    private Class<T> cls;

    public ResponseCallBack() {
    }

    public ResponseCallBack(Class<T> cls) {
        this.cls = cls;
    }

    protected abstract void onSuccess(T cls);

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e) {

    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//        String conetnt=response.body().string();
        ResponseBody body = response.body();
        if (body == null) return;

        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        data = gson.fromJson(jsonReader, type);
        onSuccess(data);
    }
}
