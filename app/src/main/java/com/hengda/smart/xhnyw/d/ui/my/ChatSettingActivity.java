package com.hengda.smart.xhnyw.d.ui.my;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.ChatGroupSettingBean;
import com.hengda.smart.xhnyw.d.model.MineChatCommonResult;
import com.kyleduo.switchbutton.SwitchButton;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.Bind;
import butterknife.OnClick;

public class ChatSettingActivity extends MineBaseActivity {

    public static final String KEY_DATA = "KEY_DATA";

    @Bind(R.id.spacer)
    Space spacer;
    @Bind(R.id.ll_group_setting)
    LinearLayout llGroupSetting;
    @Bind(R.id.tv_group_name)
    TextView tvGroupName;
    @Bind(R.id.tv_group_id)
    TextView tvGroupId;
    @Bind(R.id.tv_group_introduction)
    TextView tvGroupIntroduction;
    @Bind(R.id.switch_button_need_certification)
    SwitchButton switchButtonNeedCertification;
    @Bind(R.id.tv_my_nick_name)
    TextView tvMyNickName;
    @Bind(R.id.switch_button_nick_name)
    SwitchButton switchButtonNickName;

    private ChatGroupSettingBean mData;

    private AlertDialog mEditDialog;
    private AlertDialog mWaitingDialog;
    private EditText edtContent;
    private Button btnConfirm;

    @Override
    protected void initBundle(Bundle bundle) {
        mData = bundle.getParcelable(KEY_DATA);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_chat_setting;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        View customView = View.inflate(this, R.layout.dialog_mine_chat_setting, null);
        edtContent = (EditText) customView.findViewById(R.id.edt_content);
        btnConfirm = (Button) customView.findViewById(R.id.btn_confirm);
        ImageView ivClose = (ImageView) customView.findViewById(R.id.iv_close);
        ivClose.setOnClickListener(v -> mEditDialog.dismiss());
        mEditDialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(customView)
                .create();


        View waitingView = View.inflate(this, R.layout.dialog_mine_chat_waiting, null);
        ImageView ivWaitingClose = (ImageView) waitingView.findViewById(R.id.iv_close);
        ivWaitingClose.setOnClickListener(v -> mWaitingDialog.dismiss());
        mWaitingDialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(waitingView)
                .create();
        mWaitingDialog.setCancelable(false);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (mData.isOwner()) {
            spacer.setVisibility(View.VISIBLE);
            llGroupSetting.setVisibility(View.VISIBLE);
            tvGroupName.setText(mData.getGroupName());
            tvGroupId.setText(mData.getGroupFakeId());
            tvGroupIntroduction.setText(mData.getGroupIntroduce());
            switchButtonNeedCertification.setChecked(mData.isNeedCertification());
        } else {
            spacer.setVisibility(View.GONE);
            llGroupSetting.setVisibility(View.GONE);
        }
        tvMyNickName.setText(mData.getMyNickName());
        switchButtonNickName.setChecked(mData.isShowNickName());
        switchButtonNeedCertification.setClickable(false);
        switchButtonNickName.setClickable(false);
    }

    /**
     * 弹出输入对话框
     */
    private void showInputDialog(int hintStringId, View.OnClickListener listener) {
        edtContent.setText("");
        edtContent.setHint(hintStringId);
        btnConfirm.setOnClickListener(listener);
        mEditDialog.show();
    }

    private void setIsHideUserName() {
        mWaitingDialog.show();
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_show_nick")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("group_id", mData.getGroupId())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                        mWaitingDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                        mWaitingDialog.dismiss();
                        switchButtonNickName.toggle();
                    }
                }) {
                }));
        mData.setShowNickName(!mData.isShowNickName());
        setResult();
    }

    private void setIsNeedCertification() {
        mWaitingDialog.show();
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_is_check")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("group_id", mData.getGroupId())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        mWaitingDialog.dismiss();
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                        switchButtonNeedCertification.toggle();
                        mWaitingDialog.dismiss();
                    }
                }) {
                }));
        mData.setNeedCertification(!mData.isNeedCertification());
        setResult();
    }

    @OnClick({R.id.ll_group_name, R.id.ll_group_id, R.id.ll_group_introduction, R.id.ll_add_group_need_certification, R.id.ll_my_nick_name, R.id.ll_my_need_show_nick_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_group_name:
                showInputDialog(R.string.mine_chat_setting_dialog_hint_group_name, v -> {
                    String groupName = edtContent.getText().toString();
                    addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_title")
                            .params("device_id", Hd_AppConfig.getDeviceNo())
                            .params("group_id", mData.getGroupId())
                            .params("language", Hd_AppConfig.getLanguageCode())
                            .params("title", groupName)
                            .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                                @Override
                                public void onError(ApiException e) {
                                    proceedException(e);
                                    mEditDialog.dismiss();
                                }

                                @Override
                                public void onSuccess(MineChatCommonResult response) {
                                    tvGroupName.setText(groupName);
                                    mData.setGroupName(groupName);
                                    setResult();
                                    mEditDialog.dismiss();
                                }
                            }) {
                            }));
                });
                break;
            case R.id.ll_group_id:
                showInputDialog(R.string.mine_chat_setting_dialog_hint_group_id, v -> {
                    String groupId = edtContent.getText().toString();
                    addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_group_id")
                            .params("device_id", Hd_AppConfig.getDeviceNo())
                            .params("group_id", groupId)
                            .params("language", Hd_AppConfig.getLanguageCode())
                            .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                                @Override
                                public void onError(ApiException e) {
                                    proceedException(e);
                                    mEditDialog.dismiss();
                                }

                                @Override
                                public void onSuccess(MineChatCommonResult response) {
                                    tvGroupId.setText(groupId);
                                    mData.setGroupFakeId(groupId);
                                    setResult();
                                    mEditDialog.dismiss();
                                }
                            }) {
                            }));
                });
                break;
            case R.id.ll_group_introduction:
                showInputDialog(R.string.mine_chat_setting_dialog_hint_group_desc, v -> {
                    String groupDesc = edtContent.getText().toString();
                    addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_desc")
                            .params("device_id", Hd_AppConfig.getDeviceNo())
                            .params("group_id", mData.getGroupId())
                            .params("language", Hd_AppConfig.getLanguageCode())
                            .params("desc", groupDesc)
                            .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                                @Override
                                public void onError(ApiException e) {
                                    proceedException(e);
                                    mEditDialog.dismiss();
                                }

                                @Override
                                public void onSuccess(MineChatCommonResult response) {
                                    tvGroupIntroduction.setText(groupDesc);
                                    mData.setGroupIntroduce(groupDesc);
                                    setResult();
                                    mEditDialog.dismiss();
                                }
                            }) {
                            }));
                });
                break;
            case R.id.ll_add_group_need_certification:
                setIsNeedCertification();
                break;
            case R.id.ll_my_nick_name:
                showInputDialog(R.string.mine_chat_setting_dialog_hint_my_nick_name, v -> {
                    String nickName = edtContent.getText().toString();
                    addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=modify_nick_name")
                            .params("device_id", Hd_AppConfig.getDeviceNo())
                            .params("group_id", mData.getGroupId())
                            .params("language", Hd_AppConfig.getLanguageCode())
                            .params("nick_name", nickName)
                            .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                                @Override
                                public void onError(ApiException e) {
                                    proceedException(e);
                                    mEditDialog.dismiss();
                                }

                                @Override
                                public void onSuccess(MineChatCommonResult response) {
                                    tvMyNickName.setText(nickName);
                                    mData.setMyNickName(nickName);
                                    setResult();
                                    mEditDialog.dismiss();
                                }
                            }) {
                            }));
                });
                break;
            case R.id.ll_my_need_show_nick_name:
                setIsHideUserName();
                break;
        }
    }

    private void setResult() {
        Intent intent = new Intent();
        intent.putExtra(KEY_DATA, mData);
        setResult(RESULT_OK, intent);
    }

    public static void open(Activity context, ChatGroupSettingBean bean, int requsetCode) {
        Intent intent = new Intent(context, ChatSettingActivity.class);
        intent.putExtra(KEY_DATA, bean);
        context.startActivityForResult(intent, requsetCode);
    }

}
