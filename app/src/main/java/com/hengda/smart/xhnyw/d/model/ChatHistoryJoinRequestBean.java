package com.hengda.smart.xhnyw.d.model;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/26.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class ChatHistoryJoinRequestBean {

    /**
     * user_id : AG10000000002
     * group_id : 64
     * tips : 申请加入群组
     */

    private String user_id;
    private String group_id;
    private String tips;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }
}
