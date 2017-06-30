package com.hengda.smart.xhnyw.d.callback;

import com.hengda.smart.xhnyw.d.model.FileLoadEvent;
import com.hengda.smart.xhnyw.d.tools.RxBusUtil;
import com.hengda.smart.xhnyw.d.tools.ZipUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/4/5 08:52
 * 邮箱：tailyou@163.com
 * 描述：Retrofit 文件下载回调
 */
public abstract class FileCallback implements Callback<ResponseBody> {

    /**
     * 订阅下载进度
     */
    private Subscription rxSubscriptions;
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress();
    }

    public abstract void onSuccess(File file);

    public abstract void progress(long progress, long total);

    @Override
    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileCallback.this.saveFile(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 保存
     *
     * @param response
     * @return
     * @throws IOException
     */
    public void saveFile(Response<ResponseBody> response) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = response.body().byteStream();
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
            unsubscribe();
            if (destFileName.endsWith(".zip"))
                unzipFile(file);
            else
                onSuccess(file);
        } finally {
            try {
                if (is != null) is.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Logger.e(e.getMessage());
            }
        }
    }

    /**
     * 订阅文件下载进度
     */
    private void subscribeLoadProgress() {
        rxSubscriptions = RxBusUtil.subscribeEvent(FileLoadEvent.class,
                new Action1<FileLoadEvent>() {
                    @Override
                    public void call(FileLoadEvent fileLoadEvent) {
                        FileCallback.this.progress(fileLoadEvent.getProgress(), fileLoadEvent.getTotal());
                    }
                });
    }

    /**
     * 取消订阅，防止内存泄漏
     */
    private void unsubscribe() {
        RxBusUtil.unsubscribe(rxSubscriptions);
        rxSubscriptions = null;
    }

    /**
     * 解压文件
     *
     * @param file
     */
    private void unzipFile(final File file) {
        try {
            ZipUtil.unzipFolder(file, destFileDir, new ZipUtil.IUnzipCallback() {
                @Override
                public void completed() {
                    FileCallback.this.onSuccess(file);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
