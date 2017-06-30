package com.hengda.smart.xhnyw.d.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.zxing.WriterException;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.uuzuche.lib_zxing.encoding.EncodingHandler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @explain 条形码界面
 * @author lenovo.
 * @time 2017/6/17 13:47.
 */
public class BarCodeActivity extends BaseActivity {


    @Bind(R.id.ivPhoto)
    ImageView mIvPhoto;
    @Bind(R.id.tvDeviceNum)
    TextView mTvDeviceNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_code_avtivity);

        ButterKnife.bind(this);

        performBarCode();
    }

    private void performBarCode() {
        String content = Hd_AppConfig.getDeviceNo();
        mTvDeviceNum.setText(getString(R.string.device_no) + content);
        Bitmap bitmap = null;
        try {
            bitmap = EncodingHandler.createOneDCode(content);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mIvPhoto.setImageBitmap(bitmap);
    }

    @OnClick(R.id.btnPre)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
