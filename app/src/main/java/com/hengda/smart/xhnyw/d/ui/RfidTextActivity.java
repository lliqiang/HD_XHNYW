package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.AutoNum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @explain 管理员工程测试界面
 * @author lenovo.
 * @time 2017/6/17 13:46.
 */
public class RfidTextActivity extends BaseActivity {

    @Bind(R.id.rfid_text)
    TextView rfidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rfid_text);
        ButterKnife.bind(this);
//        startService(new Intent(this, RfidNoService.class));
        EventBus.getDefault().register(this);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(AutoNum event) {

        rfidText.setText("当前FRID为：   "+event.getAutoNum());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);

    }
}
