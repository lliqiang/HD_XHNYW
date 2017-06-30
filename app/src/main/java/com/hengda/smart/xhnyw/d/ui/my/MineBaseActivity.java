package com.hengda.smart.xhnyw.d.ui.my;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 我的页面的BaseActivity
 * version: 1.0
 */
public abstract class MineBaseActivity extends AppCompatActivity {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBundle(getIntent().getExtras());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initWidget(savedInstanceState);
        initData(savedInstanceState);
    }

    protected void initBundle(Bundle extras) {
    }

    protected abstract int getContentView();

    protected void initWidget(Bundle savedInstanceState) {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    protected void addSubscription(Subscription subscription){
        compositeSubscription.add(subscription);
    }

    protected void clearHttp(){
        compositeSubscription.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearHttp();
    }

    protected void proceedException(com.zhouyou.http.exception.ApiException e){
        if (TextUtils.equals(Hd_AppConfig.getLanguage(), "ENGLISH") && TextUtils.equals("连接失败", e.getMessage())) {
            Toast.makeText(MineBaseActivity.this, R.string.add_connect_error, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MineBaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}


