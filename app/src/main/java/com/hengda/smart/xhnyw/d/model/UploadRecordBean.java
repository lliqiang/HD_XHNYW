package com.hengda.smart.xhnyw.d.model;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/27.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class UploadRecordBean {

    /**
     * isSuccess : 1
     * url : data/upload/audio/20170627/5951bfa231819.voice
     */

    private int isSuccess;
    private String url;

    public int getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(int isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
