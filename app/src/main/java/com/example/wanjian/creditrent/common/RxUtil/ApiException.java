package com.example.wanjian.creditrent.common.RxUtil;


import com.example.wanjian.creditrent.base.C;

/**
 * Created by HugoXie on 16/5/20.
 *
 * Email: Hugo3641@gamil.com
 * GitHub: https://github.com/xcc3641
 * Info:
 */
public class ApiException extends RuntimeException {

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public ApiException(int resultCode, String msg) {
        this(handleErrorCode(resultCode,msg));
    }

    private static String handleErrorCode(int code, String s) {
        String msg;
        switch (code) {
            case -1:
                msg = C.UNLOGIN;
                break;
            default:
                msg = s;
                break;
        }
        return msg;
    }

}



//public class ApiException extends RuntimeException {
//
//
//    private int resultCode = 0;
//
//    public ApiException(int resultCode, String msg) {
//        super(msg);
//        this.resultCode = resultCode;
//    }
//
//    public int getResultCode() {
//        return resultCode;
//    }
//
//}

