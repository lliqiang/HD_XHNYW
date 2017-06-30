package com.hengda.smart.xhnyw.d.http;

import com.hengda.smart.xhnyw.d.model.ResUpdate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/5/26 18:46
 * 邮箱：tailyou@163.com
 * 描述：Retrofit文件下载接口
 */
public interface IFileService {

    /**
     * 下载数据库、资源
     *
     * @param fileName
     * @return
     */
    @Streaming
    @GET("{fileName}")
    Call<ResponseBody> loadFile(@Path("fileName") String fileName);

    /**
     * 导览机资源更新
     * @param version
     * @return
     */
    @GET("index.php?g=mapi&m=Resource&a=update_zip")
    Observable<ResUpdate> getRes(@Query("version") int version);
}
