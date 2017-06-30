package com.hengda.smart.xhnyw.d.http;

import com.hengda.platform.easyhttp.api.BaseResponse;

import java.util.List;

/**
 * Created by shiwei on 2017/5/12.
 */

public class ApiListTest<T> extends BaseResponse {
    private int  code;
    private String msg;
    private List<T> info;

    public void setError_code(int error_code) {
        this.code = error_code;
    }

    public void setReason(String reason) {
        this.msg = reason;
    }

    public void setResult(List<T> result) {
        this.info = result;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public List<T> getDatas() {
        return info;
    }
}
