package com.example.wanjian.creditrent.common;

/**
 * Created by zk on 2015/12/19.
 * 所有请求返回的结果
 */
public class Result<T> {
    public String msg;
    public int code;
    public T data;
}
