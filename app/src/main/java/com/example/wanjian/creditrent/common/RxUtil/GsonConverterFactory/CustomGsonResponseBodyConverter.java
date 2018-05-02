package com.example.wanjian.creditrent.common.RxUtil.GsonConverterFactory;

import android.util.Log;

import com.example.wanjian.creditrent.base.Result;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by wanjian on 2018/4/24.
 */

class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    CustomGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            Log.d("Network", "response>>" + response);
            //ResultResponse 只解析result字段
            Result resultResponse = gson.fromJson(response, Result.class);
            if (resultResponse.code == 200){
                //result==200表示成功返回，继续用本来的Model类解析
                Log.d("TAG","here convert");
                return gson.fromJson(response, type);
            } else {
                //ErrResponse 将msg解析为异常消息文本
                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);
                throw new com.example.wanjian.creditrent.common.RxUtil.ApiException(resultResponse.code, errResponse.getInfo());
            }
        } finally {
        }
    }
}
