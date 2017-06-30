package com.hengda.smart.xhnyw.d.service;


import android.util.Log;

import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.RfidNum;
import com.hengda.zwf.autonolibrary.SerialPortService;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

/**
 * 作者：祝文飞（Tailyou）
 * 邮箱：tailyou@163.com
 * 时间：2016/11/9 14:54
 * 描述：Rfid收号服务，相关的依赖
 */
public class RfidNoService extends SerialPortService {

    @Override
    public void onDataReceived(byte[] buffer, int size) {
        for (int m = 0; m < size; m++) {
            if (buffer[m] == (byte) 0xAA && buffer[m + 1] == (byte) 0x55) {
                if ((buffer[m + 2] == (byte) 0x05)
                        && buffer[m + 6] == (buffer[m] ^ buffer[m + 1]
                        ^ buffer[m + 2] ^ buffer[m + 3]
                        ^ buffer[m + 4] ^ buffer[m + 5])) {
                    int autoNo = (buffer[m + 4] & 0xFF) * 256 + (buffer[m + 5] & 0xFF);
                    EventBus.getDefault().post(new RfidNum(autoNo));
                    Logger.e("RfidNo:" + autoNo);

                }
            }
        }
    }

}
