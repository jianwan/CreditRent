package com.example.wanjian.creditrent.base;

/**
 * Created by zk on 2015/12/19.
 * 所有请求返回的结果
 */
public class Result<T> {
    public String info;
    public int code;
//    public T data;


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }




}
