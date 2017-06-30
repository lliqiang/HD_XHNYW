package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 意见反馈页面
 * version: 1.0
 */
public class FeedbackActivity extends MineBaseActivity {

    @Bind(R.id.edt_contact)
    EditText edtContact;
    @Bind(R.id.edt_content)
    EditText edtContent;

    @Override
    protected int getContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        Toast.makeText(this,"send",Toast.LENGTH_SHORT).show();
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

}
