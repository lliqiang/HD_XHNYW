package com.hengda.smart.xhnyw.d.ui.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.ChatEvent;
import com.hengda.smart.xhnyw.d.model.MineChatCommonResult;
import com.hengda.smart.xhnyw.d.view.MineMenuItem;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 我的伙伴菜单页面
 * version: 1.0
 */
public class PartnerActivity extends MineBaseActivity {

    @Bind(R.id.menu_partner_build_group)
    MineMenuItem menuPartnerBuildGroup;
    @Bind(R.id.menu_partner_join_group)
    MineMenuItem menuPartnerJoinGroup;

    private AlertDialog mJoinDialog;
    private EditText edtGroupId;
    private TextView tvWaitingLabel;
    private Button btnConfirm;
    private ImageView ivClose;

    @Override
    protected int getContentView() {
        return R.layout.activity_partner;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        initJoinDialog();
        menuPartnerJoinGroup.setOnClickListener(v -> showInputDialog());
        menuPartnerBuildGroup.setOnClickListener(v -> buildGroup());
    }

    private void initJoinDialog() {
        View customView = View.inflate(this, R.layout.dialog_mine_join_group, null);
        edtGroupId = (EditText) customView.findViewById(R.id.edt_group_id);
        tvWaitingLabel = (TextView) customView.findViewById(R.id.tv_label_waiting);
        btnConfirm = (Button) customView.findViewById(R.id.btn_confirm);
        ivClose = (ImageView) customView.findViewById(R.id.iv_close);
        mJoinDialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(customView)
                .create();
        mJoinDialog.setCancelable(false);
    }

    /**
     * 创建群组
     */
    private void buildGroup() {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=request_group")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(
                        new SimpleCallBack<MineChatCommonResult>() {
                            @Override
                            public void onError(ApiException e) {
                                proceedException(e);
                                if (e.getCode() == CustomApiResult.HTTP_ERROR_CODE) {
                                    Hd_AppConfig.setIsInGroup(true);
                                    finish();
                                }
                            }

                            @Override
                            public void onSuccess(MineChatCommonResult response) {
                                Hd_AppConfig.setIsInGroup(true);
                                ChatActivity.open(PartnerActivity.this);
                                finish();
                            }
                        }) {
                }));
    }

    /**
     * 弹出输入群组号对话框
     */
    private void showInputDialog() {
        ivClose.setOnClickListener(v -> mJoinDialog.dismiss());
        edtGroupId.setVisibility(View.VISIBLE);
        tvWaitingLabel.setVisibility(View.GONE);
        btnConfirm.setText(R.string.common_ok);
        btnConfirm.setOnClickListener(v -> {
            addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=join_group")
                    .params("device_id", Hd_AppConfig.getDeviceNo())
                    .params("group_id", edtGroupId.getText().toString())
                    .params("language", Hd_AppConfig.getLanguageCode())
                    .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                        @Override
                        public void onError(ApiException e) {
                            proceedException(e);
                            if (mJoinDialog != null && mJoinDialog.isShowing()) {
                                mJoinDialog.dismiss();
                            }
                        }

                        @Override
                        public void onSuccess(MineChatCommonResult response) {
                            if (response.getSuccess() == 1) {
                                try{
                                    mJoinDialog.dismiss();
                                }catch (Exception ignore){}
                                Hd_AppConfig.setIsInGroup(true);
                                ChatActivity.open(PartnerActivity.this);
                                finish();
                            }
                        }
                    }) {
                    }));
            showWaitingDialog();
        });
        if (mJoinDialog != null && !mJoinDialog.isShowing()) {
            mJoinDialog.show();
        }
    }

    /**
     * 弹出输入等待对话框
     */
    private void showWaitingDialog() {
        ivClose.setOnClickListener(v -> cancelApplication(true));
        tvWaitingLabel.setVisibility(View.VISIBLE);
        edtGroupId.setVisibility(View.GONE);
        btnConfirm.setText(R.string.mine_partner_cancel_application);
        btnConfirm.setOnClickListener(v -> cancelApplication(false));
        if (mJoinDialog != null && !mJoinDialog.isShowing()) {
            mJoinDialog.show();
        }
    }

    private void cancelApplication(boolean closeFirst) {
        if (closeFirst) {
            if (mJoinDialog != null) {
                mJoinDialog.dismiss();
            }
        }
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=cancel_join_group")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("group_id", edtGroupId.getText().toString())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                        if (mJoinDialog != null && mJoinDialog.isShowing()) {
                            mJoinDialog.dismiss();
                        }
                    }
                }) {
                }));
    }

    /**
     * 收到推送事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveChatEvent(ChatEvent event) {
        if (event == null) return;
        if (event.getType() == ChatEvent.TYPE_RESULT_JOIN) {
            Hd_AppConfig.setIsInGroup(true);
            ChatActivity.open(PartnerActivity.this);
            finish();
        } else if (event.getType() == ChatEvent.TYPE_RESULT_REFUSE) {
            Toast.makeText(this, R.string.mine_chat_join_request_refuse, Toast.LENGTH_SHORT).show();
            if (mJoinDialog != null && mJoinDialog.isShowing()) {
                mJoinDialog.dismiss();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, PartnerActivity.class);
        context.startActivity(intent);
    }

}
