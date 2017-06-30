package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.view.TitleBar;

import butterknife.Bind;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 普通的web页面
 * version: 1.0
 */
public class CommonWebActivity extends MineBaseActivity {

    private static final String KEY_WEB_URL = "KEY_WEB_URL";
    private static final String KEY_TITLE_RES = "KEY_TITLE_RES";

    private String mUrl;
    private int mTitleRes;

    @Bind(R.id.root)
    LinearLayout root;
    @Bind(R.id.title_bar)
    TitleBar titleBar;
    @Bind(R.id.web_view)
    WebView webView;

    @Override
    protected void initBundle(Bundle extras) {
        mUrl = extras.getString(KEY_WEB_URL);
        mTitleRes = extras.getInt(KEY_TITLE_RES);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        titleBar.setTitle(mTitleRes);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Toast.makeText(CommonWebActivity.this, R.string.add_connect_error, Toast.LENGTH_SHORT).show();
                CommonWebActivity.this.finish();
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl(mUrl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        root.removeView(webView);
        webView.destroy();
    }

    public static void open(Context context, int titleRes, String webUrl) {
        Intent intent = new Intent(context, CommonWebActivity.class);
        intent.putExtra(KEY_WEB_URL, webUrl);
        intent.putExtra(KEY_TITLE_RES, titleRes);
        context.startActivity(intent);
    }

}
