package com.hengda.smart.xhnyw.d.model;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/25.
 * desc   : 聊天相关的Event
 * version: 1.0
 */
public class ChatEvent {

    public static final int TYPE_REFRESH = 600;
    public static final int TYPE_REQUEST_JOIN = 601;
    public static final int TYPE_RESULT_JOIN = 602;
    public static final int TYPE_RESULT_REFUSE = 603;

    private String content;
    private String time;
    private String duration;
    private int type;


    public ChatEvent(int type) {
        this.type = type;
    }

    public ChatEvent(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public ChatEvent(int type, String content, String time) {
        this.type = type;
        this.content = content;
        this.time = time;
    }

    public ChatEvent(int type, String url, String duration, String time) {
        this.type = type;
        this.content = url;
        this.duration = duration;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTypeName() {
        switch (type) {
            case TYPE_REFRESH:
                return "人员出入刷新";
            case TYPE_REQUEST_JOIN:
                return "请求加入群组";
            case TYPE_RESULT_JOIN:
                return "同意加入";
            case TYPE_RESULT_REFUSE:
                return "拒绝加入";
            case ChatUiBean.MY_RECORD:
                return "我发的录音";
            case ChatUiBean.MY_TEXT:
                return "我发的文字";
            case ChatUiBean.OTHER_RECORD:
                return "别人发的录音";
            case ChatUiBean.OTHER_TEXT:
                return "别人发的文字";
        }
        return "未知类型";
    }
}
