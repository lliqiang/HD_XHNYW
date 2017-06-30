package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.service.PushService;
import com.hengda.smart.xhnyw.d.view.MineMenuItem;
import com.hengda.zwf.hdpush.common.Intents;

import butterknife.Bind;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 我的页面主页
 * version: 1.0
 */
public class MineHomeActivity extends MineBaseActivity {

    @Bind(R.id.menu_my_footprint)
    MineMenuItem menuMyFootprint;
    @Bind(R.id.menu_my_partner)
    MineMenuItem menuMyPartner;
    @Bind(R.id.menu_my_help)
    MineMenuItem menuMyHelp;
    @Bind(R.id.menu_my_message)
    MineMenuItem menuMyMessage;
    @Bind(R.id.menu_my_feedback)
    MineMenuItem menuMyFeedback;
    @Bind(R.id.menu_my_questionnaire)
    MineMenuItem menuMyQuestionnaire;


    @Override
    protected int getContentView() {
        return R.layout.activity_mine_home;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        menuMyFootprint.setOnClickListener(v -> FootprintActivity.open(MineHomeActivity.this));
        menuMyHelp.setOnClickListener(v -> AskHelpActivity.open(MineHomeActivity.this));
        menuMyMessage.setOnClickListener(v -> SystemMessageActivity.open(MineHomeActivity.this));
        menuMyFeedback.setOnClickListener(v -> CommonWebActivity.open(MineHomeActivity.this,R.string.mine_label_menu_feedback,
                "http://"+Hd_AppConfig.getDefaultIpPort()+"/xhnyw/"+"index.php?g=mapi&m=Interaction&a=liuyan&type=2&language="+Hd_AppConfig.getLanguageCode()+"&user_login="+Hd_AppConfig.getDeviceNo()));
        menuMyQuestionnaire.setOnClickListener(v -> CommonWebActivity.open(MineHomeActivity.this,R.string.mine_label_menu_questionnaire,
                "http://"+Hd_AppConfig.getDefaultIpPort()+"/xhnyw/"+"index.php?g=mapi&m=Interaction&a=quesinfo&type=2&language="+Hd_AppConfig.getLanguageCode()+"&user_login="+Hd_AppConfig.getDeviceNo()));
        menuMyPartner.setOnClickListener(v -> {
            if (Hd_AppConfig.getIsInGroup()) {
                ChatActivity.open(MineHomeActivity.this);
            } else {
                PartnerActivity.open(MineHomeActivity.this);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = new Intent(MineHomeActivity.this, PushService.class);
        intent.putExtra(Intents.Extra.DEVICE_NO, Hd_AppConfig.getDeviceNo());
        intent.putExtra(Intents.Extra.SERVER_IP, Hd_AppConfig.getDefaultIpPort());
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(MineHomeActivity.this, PushService.class));
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, MineHomeActivity.class);
        context.startActivity(intent);
    }

}
