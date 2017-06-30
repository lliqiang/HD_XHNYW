package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.HSetting;
import com.hengda.smart.xhnyw.d.tools.AppUtil;
import com.hengda.smart.xhnyw.d.tools.CommonUtil;
import com.hengda.smart.xhnyw.d.tools.RxBus;
import com.hengda.smart.xhnyw.d.ui.my.MineHomeActivity;
import com.hengda.smart.xhnyw.d.service.BleNoService;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * @author lenovo.
 * @explain 主界面
 * @time 2017/6/17 13:46.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_map_guide_main)
    LinearLayout llMapGuideMain;
    @Bind(R.id.ll_listGuide_main)
    LinearLayout llListGuideMain;
    @Bind(R.id.ll_intro_main)
    LinearLayout llIntroMain;
    @Bind(R.id.ll_mine_main)
    LinearLayout llMineMain;
    @Bind(R.id.ll_ar_main)
    LinearLayout llArMain;
    private Intent intent;
    private PackageInfo packageInfo;
    private String packageNameStr = "com.hengdawenbo.xihan";
    //    com.hengdawenbo.xihan.UnityPlayerActivity
    private String firstActivity = " com.hengdawenbo.xihan.UnityPlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initServiceState();
        initListner();
    }

    private void initServiceState() {
        intent = new Intent(this, BleNoService.class);
        if (Hd_AppConfig.getAutoFlag() == 1) {
            startService(intent);
        } else {
            stopService(intent);
        }
        RxBus.getDefault().toObservable(HSetting.SetItem.class).subscribe(new Action1<HSetting.SetItem>() {
            @Override
            public void call(HSetting.SetItem setItem) {
                if (setItem.getCode() == 1) {
                    startService(intent);
                } else {
                    stopService(intent);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void initListner() {
        llMapGuideMain.setOnClickListener(this);
        llListGuideMain.setOnClickListener(this);
        llIntroMain.setOnClickListener(this);
        llMineMain.setOnClickListener(this);
        llArMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_map_guide_main:
                openActivity(MainActivity.this, MapActivity.class);
                break;
            case R.id.ll_listGuide_main:
                openActivity(MainActivity.this, ListGuideActivity.class);
                break;
            case R.id.ll_intro_main:
                openActivity(MainActivity.this, IntroActivity.class);
                break;
            case R.id.ll_mine_main:
                MineHomeActivity.open(this);
                break;
            case R.id.ll_ar_main:

                if (AppUtil.checkInstallByPackageName(MainActivity.this, packageNameStr)) {
                    AppUtil.startAppWithPackageName(MainActivity.this, "com.hengdawenbo.xihan");
                } else {
                    AppUtil.installApk(MainActivity.this, Hd_AppConfig.getDefaultFileDir() + "xihan.apk");
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Hd_AppConfig.getLanguage().equals("CHINESE")){
            CommonUtil.configLanguage(MainActivity.this,"CHINESE");
        }else {
            CommonUtil.configLanguage(MainActivity.this,"ENGLISH");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);

    }
}
