package com.hengda.smart.xhnyw.d.model;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/24.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class ChatUiBean implements MultiItemEntity {

    public static final int MY_TEXT = 1;
    public static final int MY_RECORD = 2;
    public static final int OTHER_TEXT = 3;
    public static final int OTHER_RECORD = 4;

    private int itemType;

    private String time;
    private String content;
    private String recordTime;

    public ChatUiBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static ArrayList<ChatUiBean> transform(List<MineGroupInformation.ChatListBean> chatList) {
        ArrayList<ChatUiBean> resultList = new ArrayList<>();
        for (MineGroupInformation.ChatListBean chatListBean : chatList) {
            ChatUiBean chatUiBean;
            if (TextUtils.equals(chatListBean.getType(), "1")) {
                if (chatListBean.getIs_self() == 1) {
                    chatUiBean = new ChatUiBean(MY_TEXT);
                } else {
                    chatUiBean = new ChatUiBean(OTHER_TEXT);
                }
                chatUiBean.setTime(chatListBean.getTime());
                chatUiBean.setContent(chatListBean.getContent());
                resultList.add(chatUiBean);
            }else if(TextUtils.equals(chatListBean.getType(), "2")){
                if (chatListBean.getIs_self() == 1) {
                    chatUiBean = new ChatUiBean(MY_RECORD);
                } else {
                    chatUiBean = new ChatUiBean(OTHER_RECORD);
                }
                chatUiBean.setTime(chatListBean.getTime());
                chatUiBean.setContent(chatListBean.getContent());
                chatUiBean.setRecordTime(chatListBean.getAudio_duration());
                resultList.add(chatUiBean);
            }
        }
        return resultList;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
