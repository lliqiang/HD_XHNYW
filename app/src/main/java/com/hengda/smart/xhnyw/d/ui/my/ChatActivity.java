package com.hengda.smart.xhnyw.d.ui.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.ChatGroupMemberListAdapter;
import com.hengda.smart.xhnyw.d.adapter.ChatUIListAdapter;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.http.UploadApi;
import com.hengda.smart.xhnyw.d.model.ChatEvent;
import com.hengda.smart.xhnyw.d.model.ChatGroupSettingBean;
import com.hengda.smart.xhnyw.d.model.ChatHistoryJoinRequestBean;
import com.hengda.smart.xhnyw.d.model.ChatUiBean;
import com.hengda.smart.xhnyw.d.model.MineChatCommonResult;
import com.hengda.smart.xhnyw.d.model.MineGroupInformation;
import com.hengda.smart.xhnyw.d.model.UploadRecordBean;
import com.hengda.smart.xhnyw.d.tools.TimeUtils;
import com.lqr.audio.AudioPlayManager;
import com.lqr.audio.AudioRecordManager;
import com.lqr.audio.IAudioPlayListener;
import com.lqr.audio.IAudioRecordListener;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.CustomRequest;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;


/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 聊天页面
 * version: 1.0
 */
public class ChatActivity extends MineBaseActivity {

    @Bind(R.id.root)
    LinearLayout llRoot;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_user_num)
    TextView tvUserNum;
    @Bind(R.id.tv_group_num)
    TextView tvGroupNum;
    @Bind(R.id.recycler_view_user)
    RecyclerView recyclerViewUser;
    @Bind(R.id.recycler_view_chat)
    RecyclerView recyclerViewChat;
    @Bind(R.id.btn_record)
    Button btnRecord;
    @Bind(R.id.edt_send_content)
    EditText edtSendContent;

    private ChatGroupSettingBean bean = new ChatGroupSettingBean();
    private ChatGroupMemberListAdapter groupMemberListAdapter;
    private ChatUIListAdapter chatListAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        groupMemberListAdapter = new ChatGroupMemberListAdapter(R.layout.item_recycler_chat_group_member);
        chatListAdapter = new ChatUIListAdapter(null);
        recyclerViewUser.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUser.setAdapter(groupMemberListAdapter);
        recyclerViewUser.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MineGroupInformation.MemberListBean bean = (MineGroupInformation.MemberListBean) adapter.getData().get(position);
                String memberId = bean.getMember_id();
                FriendLocationActivity.open(ChatActivity.this, memberId);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerViewChat.setLayoutManager(manager);
        recyclerViewChat.setAdapter(chatListAdapter);
        btnRecord.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    AudioRecordManager.getInstance(ChatActivity.this).startRecord();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isCancelled(v, event)) {
                        AudioRecordManager.getInstance(ChatActivity.this).willCancelRecord();
                    } else {
                        AudioRecordManager.getInstance(ChatActivity.this).continueRecord();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    AudioRecordManager.getInstance(ChatActivity.this).stopRecord();
                    AudioRecordManager.getInstance(ChatActivity.this).destroyRecord();
                    break;
            }
            return false;
        });
        recyclerViewChat.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ChatUiBean chatUiBean = (ChatUiBean) adapter.getData().get(position);
                if (chatUiBean.getItemType() == ChatUiBean.MY_RECORD || chatUiBean.getItemType() == ChatUiBean.OTHER_RECORD) {
                    String url = chatUiBean.getContent();
                    EasyHttp.downLoad(url)
                            .savePath(Hd_AppConfig.getDefaultFileDir())
                            .saveName(getFileName(url))//不设置默认名字是时间戳生成的
                            .execute(new DownloadProgressCallBack<String>() {
                                @Override
                                public void update(long bytesRead, long contentLength, boolean done) {
                                }

                                @Override
                                public void onStart() {
                                }

                                @Override
                                public void onComplete(String path) {
                                    AudioPlayManager.getInstance().stopPlay();
                                    File item = new File(path);
                                    Uri audioUri = Uri.fromFile(item);
                                    AudioPlayManager.getInstance().startPlay(ChatActivity.this, audioUri, new IAudioPlayListener() {
                                        @Override
                                        public void onStart(Uri var1) {
                                        }

                                        @Override
                                        public void onStop(Uri var1) {
                                        }

                                        @Override
                                        public void onComplete(Uri var1) {
                                        }
                                    });
                                }

                                @Override
                                public void onError(ApiException e) {
                                }
                            });
                }
            }
        });
        File mAudioDir = new File(Hd_AppConfig.getDefaultFileDir(), "chatRecord");
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }
        AudioRecordManager.getInstance(this).setAudioSavePath(mAudioDir.getAbsolutePath());
        AudioRecordManager.getInstance(this).setAudioRecordListener(new IAudioRecordListener() {

            private PopupWindow mRecordWindow;

            @Override
            public void initTipView() {
                View view = View.inflate(ChatActivity.this, R.layout.popup_chat_record, null);
                mRecordWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                mRecordWindow.showAtLocation(llRoot, 17, 0, 0);
                mRecordWindow.setFocusable(true);
                mRecordWindow.setOutsideTouchable(false);
                mRecordWindow.setTouchable(false);
            }

            @Override
            public void setTimeoutTipView(int counter) {
                //doNothing
            }

            @Override
            public void setRecordingTipView() {
                //doNothing
            }

            @Override
            public void setAudioShortTipView() {
                Toast.makeText(ChatActivity.this, R.string.mine_chat_record_time_short, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setCancelTipView() {
                Toast.makeText(ChatActivity.this, R.string.mine_chat_record_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void destroyTipView() {
                if (this.mRecordWindow != null) {
                    this.mRecordWindow.dismiss();
                    this.mRecordWindow = null;
                }
            }

            @Override
            public void onStartRecord() {
                //开始录制
            }

            @Override
            public void onFinish(Uri audioPath, int duration) {
                //发送文件
                File file = new File(audioPath.getPath());
                if (file.exists()) {
                    Toast.makeText(getApplicationContext(), "录制成功" + duration, Toast.LENGTH_SHORT).show();
                    CustomRequest request = EasyHttp.custom()
                            .baseUrl("http://" + Hd_AppConfig.getDefaultIpPort() + "/xhnyw/")
                            .addConverterFactory(GsonConverterFactory.create(new Gson()))
                            .build();
                    UploadApi uploadService = request.create(UploadApi.class);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    Observable<CustomApiResult<UploadRecordBean>> observable = request.call(uploadService.uploadAvatar(requestBody, Hd_AppConfig.getDeviceNo(), Hd_AppConfig.getLanguageCode(), String.valueOf(duration)));
                    observable.subscribe(stringCustomApiResult -> {
                        if (stringCustomApiResult.getCode() == 1 && stringCustomApiResult.getData().getIsSuccess() == 1) {
                            EventBus.getDefault().post(new ChatEvent(ChatUiBean.MY_RECORD, "http://" + Hd_AppConfig.getDefaultIpPort() + "/xhnyw/" + stringCustomApiResult.getData().getUrl(), String.valueOf(duration), TimeUtils.getNowTimeString("HH:mm")));
                        }
                    }, Throwable::printStackTrace);
                }

            }

            @Override
            public void onAudioDBChanged(int db) {
                //doNothing
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=group_info")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineGroupInformation>, MineGroupInformation>(new SimpleCallBack<MineGroupInformation>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineGroupInformation response) {
                        proceedGroupData(response);
                        proceedChatData(response.getChat_list());
                        getOfflineRequest();
                    }
                }) {
                }));
    }

    /**
     * 处理返回的群组信息数据
     */
    public void proceedGroupData(MineGroupInformation response) {
        MineGroupInformation.SelfInfoBean selfData = response.getSelf_info();
        MineGroupInformation.GroupInfoBean groupInfo = response.getGroup_info();
        bean.setOwner(selfData.getIs_admin() == 1);
        bean.setMyNickName(selfData.getNick_name());
        bean.setShowNickName(selfData.getIs_show_name() == 1);
        bean.setGroupId(groupInfo.getId());
        bean.setGroupFakeId(groupInfo.getFake_group_id());
        bean.setGroupIntroduce(groupInfo.getDesc());
        bean.setGroupName(groupInfo.getTitle());
        bean.setNeedCertification(TextUtils.equals(groupInfo.getIs_need_check(), "1"));
        if (selfData.getIs_show_name() == 1) {
            tvUserName.setText(selfData.getNick_name());
        } else {
            tvUserName.setText("----");
        }
        tvUserNum.setText(String.format("%s" + Hd_AppConfig.getDeviceNo(), "ID:"));
        tvGroupNum.setText(String.format("%s" + groupInfo.getId(), getString(R.string.mine_chat_label_group_num)));
        groupMemberListAdapter.setNewData(response.getMember_list());
    }

    private void proceedChatData(List<MineGroupInformation.ChatListBean> chatList) {
        if (chatListAdapter.getData().isEmpty() && chatList != null && !chatList.isEmpty()) {
            chatListAdapter.setNewData(ChatUiBean.transform(chatList));
            recyclerViewChat.scrollToPosition(chatListAdapter.getData().size() - 1);
        }
    }

    @OnClick({R.id.iv_edit, R.id.btn_log_out, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_edit:
                ChatSettingActivity.open(this, bean, 6);
                break;
            case R.id.btn_log_out:
                LogoutGroup();
                break;
            case R.id.btn_send:
                String content = edtSendContent.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=send_chat")
                            .params("device_id", Hd_AppConfig.getDeviceNo())
                            .params("language", Hd_AppConfig.getLanguageCode())
                            .params("content", content)
                            .execute(new CallBackProxy<CustomApiResult<MineGroupInformation>, MineGroupInformation>(new SimpleCallBack<MineGroupInformation>() {
                                @Override
                                public void onError(ApiException e) {
                                    proceedException(e);
                                }

                                @Override
                                public void onSuccess(MineGroupInformation response) {
                                    edtSendContent.setText("");
                                    EventBus.getDefault().post(new ChatEvent(ChatUiBean.MY_TEXT, content, TimeUtils.getNowTimeString("HH:mm")));
                                }
                            }) {
                            })
                    );
                }
                break;
        }
    }

    /**
     * 弹出请求加入对话框
     */
    private void showRequestJoinDialog(String deviceId) {
        View customView = View.inflate(this, R.layout.dialog_mine_chat_approval_join, null);
        TextView tvContent = (TextView) customView.findViewById(R.id.tv_content);
        tvContent.setText(String.format("%s" + deviceId + "\n" + "%s", getString(R.string.mine_chat_label_user), getString(R.string.mine_chat_label_want_to_join_group)));
        Button btnAgree = (Button) customView.findViewById(R.id.btn_agree);
        Button btnDisAgree = (Button) customView.findViewById(R.id.btn_dis_agree);
        ImageView ivClose = (ImageView) customView.findViewById(R.id.iv_close);
        AlertDialog mJoinDialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(customView)
                .create();
        ivClose.setOnClickListener(v -> {
            disagreeJoinGroup(deviceId);
            mJoinDialog.dismiss();
        });
        btnAgree.setOnClickListener(v -> {
            agreeJoinGroup(deviceId);
            mJoinDialog.dismiss();
        });
        btnDisAgree.setOnClickListener(v -> {
            disagreeJoinGroup(deviceId);
            mJoinDialog.dismiss();
        });
        mJoinDialog.setCancelable(false);
        mJoinDialog.show();
    }

    private void disagreeJoinGroup(String deviceId) {
        EasyHttp.get("index.php?g=mapi&m=Friend&a=deny_join_group")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .params("group_id", bean.getGroupId())
                .params("dst_id", deviceId)
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                    }
                }) {
                });
    }

    private void agreeJoinGroup(String deviceId) {
        EasyHttp.get("index.php?g=mapi&m=Friend&a=agree_join_group")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .params("group_id", bean.getGroupId())
                .params("dst_id", deviceId)
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                    }
                }) {
                });
    }

    private void getOfflineRequest() {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=unprocess_application")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<List<ChatHistoryJoinRequestBean>>, List<ChatHistoryJoinRequestBean>>(new SimpleCallBack<List<ChatHistoryJoinRequestBean>>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(List<ChatHistoryJoinRequestBean> response) {
                        if (response != null && !response.isEmpty()) {
                            for (ChatHistoryJoinRequestBean bean : response) {
                                showRequestJoinDialog(bean.getUser_id());
                            }
                        }
                    }
                }) {
                }));
    }


    /**
     * 退出群组
     */
    private void LogoutGroup() {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Friend&a=del_group")
                .params("device_id", Hd_AppConfig.getDeviceNo())
                .params("language", Hd_AppConfig.getLanguageCode())
                .execute(new CallBackProxy<CustomApiResult<MineChatCommonResult>, MineChatCommonResult>(new SimpleCallBack<MineChatCommonResult>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(MineChatCommonResult response) {
                        Hd_AppConfig.setIsInGroup(false);
                        finish();
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
//        Toast.makeText(ChatActivity.this, "类型:" + event.getTypeName() + "\n" + "内容:" + event.getContent(), Toast.LENGTH_SHORT).show();
        switch (event.getType()) {
            case ChatEvent.TYPE_REFRESH:
                initData(null);
                break;
            case ChatEvent.TYPE_REQUEST_JOIN:
                showRequestJoinDialog(event.getContent());
                break;
            default:
                ChatUiBean chatUiBean = new ChatUiBean(event.getType());
                chatUiBean.setRecordTime(event.getDuration());
                chatUiBean.setContent(event.getContent());
                chatUiBean.setTime(event.getTime());
                chatListAdapter.addData(chatUiBean);
                recyclerViewChat.scrollToPosition(chatListAdapter.getData().size() - 1);
                break;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlayManager.getInstance().stopPlay();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 && resultCode == RESULT_OK && data != null) {
            bean = data.getParcelableExtra(ChatSettingActivity.KEY_DATA);
            if (bean.isShowNickName()) {
                tvUserName.setText(bean.getMyNickName());
            } else {
                tvUserName.setText("----");
            }
        }
    }

    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth() || event.getRawY() < location[1] - 40;
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    public String getFileName(String path) {
        int start = path.lastIndexOf("/");
        if (start != -1) {
            return path.substring(start + 1);
        } else {
            return path;
        }
    }
}
