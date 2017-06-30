package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.MineChatCommonResult;
import com.hengda.smart.xhnyw.d.view.MineMenuItem;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.Bind;


/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 一键求助页面
 * version: 1.0
 */
public class AskHelpActivity extends MineBaseActivity {

    @Bind(R.id.menu_help_get_lost)
    MineMenuItem menuHelpGetLost;
    @Bind(R.id.menu_help_injured)
    MineMenuItem menuHelpInjured;
    @Bind(R.id.menu_help_other)
    MineMenuItem menuHelpOther;

    @Override
    protected int getContentView() {
        return R.layout.activity_ask_help;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        menuHelpGetLost.setOnClickListener(v -> postHelp("1"));
        menuHelpInjured.setOnClickListener(v -> postHelp("2"));
        menuHelpOther.setOnClickListener(v -> postHelp("3"));
    }

    private void postHelp(String type) {
        addSubscription(EasyHttp.post("index.php?g=mapi&m=Interaction&a=call_help")
                .params("type", type)
                .params("user_login", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                        showComfortDialog();
                    }
                }) {
                }));
    }

    private void showComfortDialog() {
        View customView = View.inflate(this, R.layout.dialog_mine_help_comfort, null);
        ImageView ivClose = (ImageView) customView.findViewById(R.id.iv_close);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(customView)
                .create();
        ivClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, AskHelpActivity.class);
        context.startActivity(intent);
    }

}
