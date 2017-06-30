package com.hengda.smart.xhnyw.d.ui;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hengda.platform.easyhttp.HttpManager;
import com.hengda.platform.easyhttp.callback.OnResultCallBack;
import com.hengda.platform.easyhttp.model.CacheResult;
import com.hengda.platform.easyhttp.subscriber.HttpSubscriber;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.app.HdConstant;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.callback.CheckCallback;
import com.hengda.smart.xhnyw.d.callback.FileCallback;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.http.FileApi;
import com.hengda.smart.xhnyw.d.model.AppBean;
import com.hengda.smart.xhnyw.d.model.CheckResponse;
import com.hengda.smart.xhnyw.d.model.DataBaseBean;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.Exhibition;
import com.hengda.smart.xhnyw.d.model.MapBean;
import com.hengda.smart.xhnyw.d.model.ResUpdate;
import com.hengda.smart.xhnyw.d.tools.AppUtil;
import com.hengda.smart.xhnyw.d.tools.DataManager;
import com.hengda.smart.xhnyw.d.tools.FileUtils;
import com.hengda.smart.xhnyw.d.tools.GlideCacheUtil;
import com.hengda.smart.xhnyw.d.tools.NetUtil;
import com.hengda.smart.xhnyw.d.tools.ViewUtil;
import com.hengda.smart.xhnyw.d.tools.ZipUtil;
import com.hengda.smart.xhnyw.d.view.DialogCenter;
import com.hengda.smart.xhnyw.d.view.DialogClickListener;
import com.orhanobut.logger.Logger;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.cache.model.CacheMode;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.model.ApiResult;
import com.zhouyou.http.model.HttpParams;
import com.zhouyou.http.subsciber.BaseSubscriber;

import org.litepal.tablemanager.Connector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zlc.season.rxdownload.RxDownload;
import zlc.season.rxdownload.entity.DownloadStatus;

/**
 * @author lenovo.
 * @explain
 * @time 2017/6/17 9:20.
 */
public class CheckUpdateActivity extends BaseActivity {
    TextView txtProgress;
    TextView txtUpdateLog;
    SQLiteDatabase db;
    private final int update_db_success = 1000;
    private final int update_res_success = 2000;
    private final int update_res_loading = 3000;
    private Subscription apkDownloader;
    private Subscription resDownloader;
    private AppBean mAppBean;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case update_db_success:
                    DialogCenter.hideProgressDialog();
                    showSuccessInfoDialog("更新数据库成功");
                    break;
                case update_res_success:
                    DialogCenter.hideProgressDialog();
                    showSuccessInfoDialog("更新资源成功");
                    break;
                case update_res_loading:
                    txtProgress.setText(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update);
    }


    public void showResInfoDialog(String resId) {
        DialogCenter.showDialog(CheckUpdateActivity.this, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
            }
        }, new String[]{"资源更新", "当前已是最新版，版本号：" + resId, "取消"});
    }

    public void showSuccessInfoDialog(String string) {
        DialogCenter.showDialog(CheckUpdateActivity.this, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
            }
        }, new String[]{"提示", string, "确定"});
    }

    private void loadAndInstall() {
        String apkUrl = mAppBean.getVersionInfo().getVersionUrl();
        String baseUrl = apkUrl.substring(0, apkUrl.lastIndexOf("/") + 1);
        String fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
        String fileStoreDir = Hd_AppConfig.getDefaultFileDir();

        apkDownloader = RxDownload.getInstance().download(apkUrl, fileName, fileStoreDir)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DownloadStatus>() {
                    @Override
                    public void call(DownloadStatus downloadStatus) {
                        txtProgress.setText(String.format("正在下载(%s/%s)",
                                DataManager.getFormatSize(downloadStatus.getDownloadSize()),
                                DataManager.getFormatSize(downloadStatus.getTotalSize())));
                    }
                }, throwable -> {
                    Logger.e(throwable.getMessage());
                    Toast.makeText(CheckUpdateActivity.this, "下载出错", Toast.LENGTH_SHORT).show();
                    DialogCenter.hideDialog();
                }, () -> {
                    Logger.e("下载成功");
                    DialogCenter.hideDialog();
                    AppUtil.installApk(CheckUpdateActivity.this, fileStoreDir + fileName);
                });

    }

    /**
     * 下载并安装新版Apk
     *
     * @param checkResponse
     */
    private void loadAndInstall(CheckResponse checkResponse) {
        String apkUrl = checkResponse.getVersionInfo().getVersionUrl();
        String baseUrl = apkUrl.substring(0, apkUrl.lastIndexOf("/") + 1);
        String fileName = apkUrl.substring(apkUrl.lastIndexOf("/") + 1);
        String fileStoreDir = Hd_AppConfig.getDefaultFileDir();
        FileApi.getInstance(baseUrl).loadFileByName(fileName,
                new FileCallback(fileStoreDir, fileName) {
                    @Override
                    public void progress(long progress,
                                         long total) {
                        txtProgress.setText(String.format("正在下载(%s/%s)",
                                DataManager.getFormatSize(progress),
                                DataManager.getFormatSize(total)));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call,
                                          Throwable t) {
                        DialogCenter.hideDialog();
                    }

                    @Override
                    public void onSuccess(File file) {
                        DialogCenter.hideDialog();
                        AppUtil.installApk(CheckUpdateActivity.this, file.getAbsolutePath());
                    }
                });
    }

    /**
     * 显示下载Apk Dialog
     */
    private void showDownloadingDialog() {
        txtProgress = (TextView) View.inflate(CheckUpdateActivity.this, R.layout
                .dialog_custom_view_txt, null);
        txtProgress.setText("下载安装包...");

        DialogCenter.showDialog(CheckUpdateActivity.this, txtProgress, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
                FileApi.cancelLoading();
            }

            @Override
            public void n() {
            }
        }, new String[]{"下载更新", "取消"});
    }

    /**
     * 显示下载Apk Dialog
     */
    private void showDownloadingDialog(String s) {
        txtProgress = (TextView) View.inflate(CheckUpdateActivity.this, R.layout
                .dialog_custom_view_txt, null);
        txtProgress.setText(s);
        DialogCenter.showDialog(CheckUpdateActivity.this, txtProgress, new DialogClickListener() {
            @Override
            public void p() {
                if (apkDownloader != null && !apkDownloader.isUnsubscribed()) {
                    apkDownloader.unsubscribe();
                }
                if (resDownloader != null && !resDownloader.isUnsubscribed()) {
                    resDownloader.unsubscribe();
                }
                DialogCenter.hideDialog();
            }

            @Override
            public void n() {

            }
        }, new String[]{"下载更新", "取消"});
    }

    /**
     * Dialog-显示当前版本信息
     */
    public void showVersionInfoDialog() {

        DialogCenter.showDialog(CheckUpdateActivity.this, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
            }
        }, new String[]{"版本更新", "当前已是最新版：" + AppUtil.getVersionName(CheckUpdateActivity.this), "取消"});
    }


    /**
     * 资源更新弹框
     *
     * @param resData
     */
    public void showHasNewResVersionDialog(ResUpdate.DataBean resData) {
        ScrollView scrollView = (ScrollView) View.inflate(CheckUpdateActivity.this, R.layout
                .dialog_custom_view_scroll_txt, null);
        txtUpdateLog = ViewUtil.getView(scrollView, R.id.tvUpdateLog);
        if (resData.getIs_update() == 1)
            txtUpdateLog.setText("资源将会更新到版本 " + resData.getVersion_id());
        DialogCenter.showDialog(CheckUpdateActivity.this, scrollView, new DialogClickListener() {
            @Override
            public void p() {
                showDownloadingDialog("下载资源包...");
                downloadRes(resData);
            }

            @Override
            public void n() {
                DialogCenter.hideDialog();
            }
        }, new String[]{"资源更新", "更新", "取消"});
    }

    /**
     * 有新版本时显示Dialog
     *
     * @param checkResponse
     */
    public void showHasNewVersionDialog(final CheckResponse checkResponse) {
        ScrollView scrollView = (ScrollView) View.inflate(CheckUpdateActivity.this, R.layout
                .dialog_custom_view_scroll_txt, null);
        txtUpdateLog = ViewUtil.getView(scrollView, R.id.tvUpdateLog);
        txtUpdateLog.setText("检查到新版本：" + checkResponse.getVersionInfo().getVersionName() + "\n更新日志：\n"
                + checkResponse.getVersionInfo().getVersionLog());

        DialogCenter.showDialog(CheckUpdateActivity.this, scrollView, new DialogClickListener() {
            @Override
            public void p() {
                super.p();
                showDownloadingDialog();
                loadAndInstall(checkResponse);
            }

            @Override
            public void n() {
                super.n();
                DialogCenter.hideDialog();
            }
        }, new String[]{"版本更新", "更新", "取消"});

    }

    /**
     * 检查资源版本
     */
    public void checkRes() {
        if (NetUtil.isConnected(CheckUpdateActivity.this)) {
            String fileName = Hd_AppConfig.getDefaultFileDir() + "resId.txt";
            String resId;
            if (FileUtils.readStringFromFile(fileName, "utf-8") != null) {
                resId = FileUtils.readStringFromFile(fileName, "utf-8").toString();
            } else {
                resId = "0";
            }

            DialogCenter.showProgressDialog(CheckUpdateActivity.this, "检查资源版本", false);
            EasyHttp.get("index.php?g=mapi&m=Resource&a=update_zip")
                    .readTimeOut(30 * 1000)//局部定义读超时
                    .writeTimeOut(30 * 1000)
                    .connectTimeout(8 * 1000)
                    .params("version", resId)
                    .timeStamp(true)
                    .execute(new SimpleCallBack<ResUpdate.DataBean>() {
                        @Override
                        public void onError(ApiException e) {
                            DialogCenter.hideProgressDialog();
                            Toast.makeText(CheckUpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(ResUpdate.DataBean response) {
                            DialogCenter.hideProgressDialog();
                            switch (response.getIs_update()) {
                                case 1:
                                    showHasNewResVersionDialog(response);
                                    break;
                                case 0:
                                    showResInfoDialog(resId);
                                    break;
                            }

                        }
                    });

        }
    }

    /**
     * 检查APK新版本@Field("appKey") String appKey,
     *
     * @Field("appSecret") String appSecret,
     * @Field("appKind") int appKind,
     * @Field("versionCode") int versionCode,
     * @Field("deviceId") String deviceId
     */
    public void checkNewVersion(final CheckCallback callback) {
        if (NetUtil.isConnected(CheckUpdateActivity.this)) {
            EasyHttp.post("http://101.200.234.14/APPCloud/index.php?g=&m=Api&a=checkVersion")
                    .readTimeOut(30 * 1000)//局部定义读超时
                    .writeTimeOut(30 * 1000)
                    .connectTimeout(30 * 1000)
                    .params("appKey", HdConstant.APP_KEY)
                    .params("appSecret", HdConstant.APP_SECRET)
                    .params("appKind", "3")
                    .params("versionCode", String.valueOf(AppUtil.getVersionCode(HdApplication.mContext)))
                    .params("deviceId", Hd_AppConfig.getDeviceNo())
                    .execute(new SimpleCallBack<String>() {
                        @Override
                        public void onError(ApiException e) {
                        }

                        @Override
                        public void onSuccess(String response) {
                            CheckResponse checkResponse = new Gson().fromJson(response.toString(), CheckResponse.class);
                            switch (checkResponse.getStatus()) {
                                case "2001":
                                    callback.isAlreadyLatestVersion();
                                    break;
                                case "2002":
                                    callback.hasNewVersion(checkResponse);
                                    break;
                                case "4041":
                                    break;
                            }
                        }
                    });
        }
    }

    /**
     * 资源下载
     *
     * @param resData
     */
    public void downloadRes(ResUpdate.DataBean resData) {
        GlideCacheUtil.getInstance().clearImageAllCache(this);
        String resUrl = resData.getDown_url();
        String fileName = resUrl.substring(resUrl.lastIndexOf("/") + 1);
        String zipPath = Hd_AppConfig.getDefaultFileDir();
        File file = new File(zipPath + fileName);
        resDownloader = RxDownload.getInstance().maxRetryCount(18).download(resUrl, fileName, zipPath)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<DownloadStatus>() {
                    @Override
                    public void call(DownloadStatus downloadStatus) {
                        if (downloadStatus.getDownloadSize() == downloadStatus.getTotalSize()) {
                            unzipFile(file);
                        } else {
                            if (NetUtil.isConnected(CheckUpdateActivity.this)) {
                                Message message = mHandler.obtainMessage();
                                message.obj = String.format("正在下载(%s/%s)",
                                        DataManager.getFormatSize(downloadStatus.getDownloadSize()),
                                        DataManager.getFormatSize(downloadStatus.getTotalSize()));
                                message.what = update_res_loading;
                                mHandler.sendMessageAtTime(message, 1000);
                            } else {
                                CheckUpdateActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CheckUpdateActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                        mHandler.removeCallbacksAndMessages(null);
                                    }
                                });
                            }
                        }

                    }
                }, throwable -> {
                    resDownloader.unsubscribe();
                    DialogCenter.hideDialog();
                }, () -> {
                    Logger.e("下载成功");
                    DialogCenter.hideDialog();
                    String resIdFile = Hd_AppConfig.getDefaultFileDir() + "resId.txt";
                    FileUtils.writeStringToFile(resIdFile, resData.getVersion_id() + "", false);
                    mHandler.sendEmptyMessage(update_res_success);

                });
    }
    /**
     * 从接口中获取数据写入到数据库
     */
    public void getDBDataAndInsertToDB() {
//        DataManager.cleanCustomCache(Hd_AppConfig.getDefaultFileDir()+HdConstant.DB_FILE_NAME);
//        DataManager.cleanExternalCache(this);
        DataManager.cleanDatabaseByName(this, HdConstant.DB_FILE_NAME);
        DataManager.cleanFiles(this);
        DialogCenter.showProgressDialog(CheckUpdateActivity.this, "更新数据库文件", false);
        EasyHttp.get("index.php?g=mapi&m=Resource&a=datas_info")
                .readTimeOut(30 * 1000)//局部定义读超时
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                .cacheMode(CacheMode.NO_CACHE)
                .timeStamp(true)
                .execute(new SimpleCallBack<DataBaseBean>() {
                    @Override
                    public void onError(ApiException e) {
                        DialogCenter.hideProgressDialog();
                    }
                    @Override
                    public void onSuccess(DataBaseBean response) {
                        DialogCenter.hideProgressDialog();
                        Observable.just(response)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Action1<DataBaseBean>() {
                                    @Override
                                    public void call(DataBaseBean dataBaseBean) {
                                        resetDb(dataBaseBean);
                                    }
                                });
                    }
                });
    }
    private void resetDb(DataBaseBean data) {
        db = Connector.getDatabase();
        HResDdUtil.deleteTable(db, ExhibitInfo.class.getSimpleName());
        HResDdUtil.deleteTable(db, Exhibition.class.getSimpleName());
        HResDdUtil.deleteTable(db, MapBean.class.getSimpleName());

        for (ExhibitInfo exhibitInfo : data.getExhibit_info()) {
            exhibitInfo.save();
        }
        for (Exhibition exhibition : data.getExhibition()) {
            exhibition.save();
        }
        for (MapBean mapBean : data.getMap()) {
            mapBean.save();
        }
        FileUtils.deleteFile(Hd_AppConfig.getDbFilePath());
        boolean b = FileUtils.copyFile(db.getPath(), Hd_AppConfig.getDbFilePath());
        if (b) mHandler.sendEmptyMessage(update_db_success);
    }


    /**
     * @param file
     */
    private void unzipFile(File file) {
        try {
            ZipUtil.unzipFolder(file.getAbsolutePath(), Hd_AppConfig.getDefaultFileDir(), new ZipUtil.IUnzipCallback() {
                @Override
                public void completed() {
                    Logger.e("解压完成");
                    Toast.makeText(CheckUpdateActivity.this, "解压完成", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file.exists()) {
            file.delete();

        }
    }
}
