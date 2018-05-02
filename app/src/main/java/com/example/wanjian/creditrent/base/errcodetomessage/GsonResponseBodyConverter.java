//package com.example.wanjian.creditrent.base.errcodetomessage;
//
//import com.example.wanjian.creditrent.base.Result;
//import com.example.wanjian.creditrent.common.RxUtil.ApiException;
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//
//import java.io.IOException;
//import java.io.StringReader;
//
//import okhttp3.ResponseBody;
//import retrofit2.Converter;
//
///**
// * Created by wanjian on 2017/12/13.
// */
//
//public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
//    private static final int FAILURE = 0;       // 失败 提示失败msg
//    private static final int SUCCESS = 1;       // 成功
//    private static final int TOKEN_EXPIRE = 2;  // token过期
//    private static final int SERVER_EXCEPTION = 3;  // 服务器异常
//
//    private final Gson gson;
//    private final TypeAdapter<T> adapter;
//
//     GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
//        this.gson = gson;
//        this.adapter = adapter;
//    }
//
//    @Override
//    public T convert(ResponseBody value) throws IOException {
//        String json = value.string();
//        try {
//            verify(json);
//            return adapter.read(gson.newJsonReader(new StringReader(json)));
//        } finally {
//            value.close();
//        }
//    }
//
//    private void verify(String json) {
//        Result result = gson.fromJson(json, Result.class);
//        if (result.getCode() != 200) {
//            switch (result.getCode()) {
//                case FAILURE:
//                case SERVER_EXCEPTION:
//                    throw new ApiException(result.getInfo());
//                default:
//                    throw new UndefinedStateException();
//            }
//        }
//    }
//}
