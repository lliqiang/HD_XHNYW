package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.RfidNum;
import com.hengda.smart.xhnyw.d.service.RfidNoService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author lenovo.
 * @explain 启动界面
 * @time 2017/6/17 13:46.
 */
public class LaucherActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_scan_laucher)
    ImageView imgScanLaucher;
    @Bind(R.id.img_off_laucher)
    ImageView imgOffLaucher;
    @Bind(R.id.img_admin_laucher)
    ImageView imgAdminLaucher;
    @Bind(R.id.btn_starGuide)
    Button btnStarGuide;
    private Intent intent;
    private String fromAction;
    private Intent serviceIntent;
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        setContentView(R.layout.activity_laucher);
        ButterKnife.bind(this);
        serviceIntent = new Intent(LaucherActivity.this, RfidNoService.class);
        startService(serviceIntent);
        EventBus.getDefault().register(this);
        initListner();
        Hd_AppConfig.setPowerMode(0);
        if (Hd_AppConfig.getPowerMode() == 0) {
            imgOffLaucher.setVisibility(View.INVISIBLE);
        } else {
            imgOffLaucher.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPowerMode();
    }

    private void initListner() {
        imgScanLaucher.setOnClickListener(this);
        imgOffLaucher.setOnClickListener(this);
        imgAdminLaucher.setOnClickListener(this);
        btnStarGuide.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_scan_laucher:
                openActivity(LaucherActivity.this, BarCodeActivity.class);
                break;
            case R.id.img_off_laucher:
                intent = new Intent(LaucherActivity.this, LoginActivity.class);
                fromAction = "POWER";
                intent.putExtra("fromAction", fromAction);
                startActivity(intent);
                break;
            case R.id.img_admin_laucher:
                intent = new Intent(LaucherActivity.this, LoginActivity.class);
                fromAction = "ADMIN";
                intent.putExtra("fromAction", fromAction);
                startActivity(intent);
                break;
            case R.id.btn_starGuide:
                if (Hd_AppConfig.isDbExist()) {
                    openActivity(LaucherActivity.this, LanguageActivity.class);
                } else {
                    Toast.makeText(this, "数据库资源不存在，请到管理员界面下载", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 初始化关机权限
     */
    private void initPowerMode() {
        if (Hd_AppConfig.getPowerMode() == 1) {
            imgOffLaucher.setVisibility(View.GONE);
            sendBroadcast(new Intent("android.intent.action.ALLOW_POWERDOWN"));
        } else {
            imgOffLaucher.setVisibility(View.VISIBLE);
            sendBroadcast(new Intent("android.intent.action.LIMIT_POWERDOWN"));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(RfidNum event) {
        switch (event.getRfid()) {
            case 2001:
                if (AlarmActivity.mInstance == null) {
                    startActivity(new Intent(LaucherActivity.this, AlarmActivity.class));
                }
                break;
            case 2002:
                if (AlarmActivity.mInstance == null) {
                    startActivity(new Intent(LaucherActivity.this, AlarmActivity.class));
                }
                break;
            case 2003:
                if (AlarmActivity.mInstance != null) {
                    AlarmActivity.mInstance.finish();
                    AlarmActivity.mInstance = null;
                }
                break;
        }

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            return false;
        }
        else if (keyCode==event.KEYCODE_HOME){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(serviceIntent);
        EventBus.getDefault().unregister(this);
    }
}
