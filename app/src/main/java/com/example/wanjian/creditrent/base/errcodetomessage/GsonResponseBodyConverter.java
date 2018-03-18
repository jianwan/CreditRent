package com.example.wanjian.creditrent.base.errcodetomessage;

/**
 * Created by wanjian on 2017/12/13.
 */

//public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
//    private final Gson gson;
//    private final Type type;
//
//    GsonResponseBodyConverter(Gson gson, Type type) {
//        this.gson = gson;
//        this.type = type;
//    }
//
//    @Override
//    public T convert(ResponseBody value) throws IOException {
//        String response = value.string();
//        try {
//            Log.d("Network", "response>>" + response);
//            //ResultResponse 只解析result字段
//            Result result = gson.fromJson(response, Result.class);
//            if (result.getCode()==200){
//                //result==0表示成功返回，继续用本来的Model类解析
//                return gson.fromJson(response, type);
//            } else {
//                //ErrResponse 将msg解析为异常消息文本
////                ErrResponse errResponse = gson.fromJson(response, ErrResponse.class);
////                throw new ResultException(resultResponse.getResult(), errResponse.getMsg());
//            }
//        } finally {
//        }
//    }
//}
