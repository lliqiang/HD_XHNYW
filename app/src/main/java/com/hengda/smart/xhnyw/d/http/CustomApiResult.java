package com.hengda.smart.xhnyw.d.http;

import com.zhouyou.http.model.ApiResult;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/24.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class CustomApiResult<T> extends ApiResult<T> {

    public static final int HTTP_ERROR_CODE = 0;

    private int status;

    @Override
    public int getCode() {
        return status;
    }

    @Override
    public void setCode(int code) {
        status = code;
    }

    @Override
    public boolean isOk() {
        return status == 1;
    }

}