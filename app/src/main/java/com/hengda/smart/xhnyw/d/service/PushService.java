package com.hengda.smart.xhnyw.d.service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hengda.smart.xhnyw.d.model.ChatEvent;
import com.hengda.smart.xhnyw.d.model.ChatUiBean;
import com.hengda.smart.xhnyw.d.model.MineBindDeviceResult;
import com.hengda.smart.xhnyw.d.tools.TimeUtils;
import com.hengda.zwf.hdpush.BasePushService;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/23.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class PushService extends BasePushService {

    Disposable mDisposable;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    @Override
    public void bindDevice(String clientId, String deviceNo) {
        EasyHttp.post("index.php?g=mapi&m=Friend&a=bind_deviceno")
                .params("device_id", deviceNo)
                .params("client_id", clientId)
                .execute(new SimpleCallBack<MineBindDeviceResult>() {
                    @Override
                    public void onError(ApiException e) {
                        Log.e("EasyHttp", "绑定设备号异常" + e.getMessage());
                    }

                    @Override
                    public void onSuccess(MineBindDeviceResult response) {
                        Log.e("EasyHttp", "绑定设备号回调成功" + response.getIsSuccess());
                    }
                });
    }

    @Override
    public void dealPush(String type, String content) {
        switch (type) {
            case "1":
                EventBus.getDefault().post(new ChatEvent(ChatEvent.TYPE_REFRESH));
                break;
            case "2":
                EventBus.getDefault().post(new ChatEvent(ChatUiBean.OTHER_TEXT, content, TimeUtils.getNowTimeString("HH:mm")));
                break;
            case "3":
                EventBus.getDefault().post(new ChatEvent(ChatEvent.TYPE_REQUEST_JOIN, content));
                break;
            case "4":
                String[] data = content.split("##");
                EventBus.getDefault().post(new ChatEvent(ChatUiBean.OTHER_RECORD, data[0], data[1], TimeUtils.getNowTimeString("HH:mm")));
                break;
            case "5":
                EventBus.getDefault().post(new ChatEvent(ChatEvent.TYPE_RESULT_JOIN));
                break;
            case "6":
                EventBus.getDefault().post(new ChatEvent(ChatEvent.TYPE_RESULT_REFUSE));
                break;
        }
        printPushMsg(String.format("消息类型：%s，消息内容：%s", type, content));
    }

    private void printPushMsg(String msg) {
        Log.e("Push", "msg:" + msg);
    }

}