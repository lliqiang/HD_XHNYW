package com.hengda.smart.xhnyw.d.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author lenovo.
 * @explain 博物馆简介界面
 * @time 2017/6/17 13:47.
 */
public class IntroActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.wv_intro)
    WebView wvIntro;
    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.los_common)
    ImageView losCommon;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        tvTilteCommon.setText(getString(R.string.nanyue_meseum));
        imgBackCommon.setOnClickListener(this);
        if (Hd_AppConfig.getLanguage().equals("CHINESE")) {
            path = Hd_AppConfig.getDefaultFileDir() + "introduction/1/" + "ch_introduction.html";
        } else {
            path = Hd_AppConfig.getDefaultFileDir() + "introduction/1/" + "en_introduction.html";
        }
        wvIntro.setBackgroundColor(0);
        wvIntro.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        wvIntro.getSettings().setLoadWithOverviewMode(true);
        wvIntro.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        wvIntro.loadUrl("file:///" + path);

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
