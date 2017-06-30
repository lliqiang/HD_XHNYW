package com.hengda.smart.xhnyw.d.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.tools.CommonUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LanguageActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.img_chinese)
    ImageView imgChinese;
    @Bind(R.id.img_english)
    ImageView imgEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        initListner();
    }

    private void initListner() {
        imgChinese.setOnClickListener(this);
        imgEnglish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_chinese:
                CommonUtil.configLanguage(LanguageActivity.this, "CHINESE");
                openActivity(LanguageActivity.this, MainActivity.class);
                Hd_AppConfig.setLanguage("CHINESE");
                break;
            case R.id.img_english:
                CommonUtil.configLanguage(LanguageActivity.this, "ENGLISH");
                openActivity(LanguageActivity.this, MainActivity.class);
                Hd_AppConfig.setLanguage("ENGLISH");
                break;
        }
    }
}
