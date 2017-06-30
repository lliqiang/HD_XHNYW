package com.hengda.smart.xhnyw.d.http;

import com.hengda.smart.xhnyw.d.model.UploadRecordBean;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/26.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public interface UploadApi {

    @Multipart
    @POST("index.php?g=mapi&m=Friend&a=upload_audio")
    Observable<CustomApiResult<UploadRecordBean>> uploadAvatar(@Part("avatar\"; filename=\"avatar.voice") RequestBody file, @Query("device_id") String device_id,
                                                               @Query("language") String language, @Query("length") String length);
}
