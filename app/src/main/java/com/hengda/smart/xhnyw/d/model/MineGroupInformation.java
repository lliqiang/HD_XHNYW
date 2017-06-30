package com.hengda.smart.xhnyw.d.model;

import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/23.
 * desc   : 我的群组信息
 * version: 1.0
 */
public class MineGroupInformation {

    /**
     * member_list : [{"member_id":"AG10000000002","member_name":"游客5453"}]
     * chat_list : [{"is_self":1,"chat_id":"AG10000000001","content":"55","time":"14:34:59"},{"is_self":0,"chat_id":"AG10000000002","content":"55","time":"14:34:31"},{"is_self":0,"chat_id":"AG10000000002","content":"啦啦","time":"2017-06-22 14:17:19"},{"is_self":0,"chat_id":"AG10000000002","content":"啦啦","time":"2017-06-22 14:17:14"}]
     * self_info : {"nick_name":"哈哈","is_show_name":"1","is_admin":1}
     * group_info : {"title":"111","desc":"1312312312","is_need_check":"1","id":"20"}
     */

    private SelfInfoBean self_info;
    private GroupInfoBean group_info;
    private List<MemberListBean> member_list;
    private List<ChatListBean> chat_list;

    public SelfInfoBean getSelf_info() {
        return self_info;
    }

    public void setSelf_info(SelfInfoBean self_info) {
        this.self_info = self_info;
    }

    public GroupInfoBean getGroup_info() {
        return group_info;
    }

    public void setGroup_info(GroupInfoBean group_info) {
        this.group_info = group_info;
    }

    public List<MemberListBean> getMember_list() {
        return member_list;
    }

    public void setMember_list(List<MemberListBean> member_list) {
        this.member_list = member_list;
    }

    public List<ChatListBean> getChat_list() {
        return chat_list;
    }

    public void setChat_list(List<ChatListBean> chat_list) {
        this.chat_list = chat_list;
    }

    public static class SelfInfoBean {
        /**
         * nick_name : 哈哈
         * is_show_name : 1
         * is_admin : 1
         */

        private String nick_name;
        private int is_show_name;
        private int is_admin;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getIs_show_name() {
            return is_show_name;
        }

        public void setIs_show_name(int is_show_name) {
            this.is_show_name = is_show_name;
        }

        public int getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(int is_admin) {
            this.is_admin = is_admin;
        }
    }

    public static class GroupInfoBean {
        /**
         * title : 111
         * desc : 1312312312
         * is_need_check : 1
         * "fake_group_id": "90260",
         * id : 20
         */

        private String title;
        private String desc;
        private String is_need_check;
        private String id;
        private String fake_group_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIs_need_check() {
            return is_need_check;
        }

        public void setIs_need_check(String is_need_check) {
            this.is_need_check = is_need_check;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFake_group_id() {
            return fake_group_id;
        }

        public void setFake_group_id(String fake_group_id) {
            this.fake_group_id = fake_group_id;
        }
    }

    public static class MemberListBean {
        /**
         * member_id : AG10000000002
         * member_name : 游客5453
         */

        private String member_id;
        private String member_name;

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
    }

    public static class ChatListBean {
        /**
         "is_self":1,
         "chat_id":"AG10000000006",
         "audio_duration":"0",
         "content":"kkk",
         "type":"1",消息类型1 文本 2 语音
         "time":"14:17:29"
         */


        private int is_self;
        private String chat_id;
        private String content;
        private String time;
        private String type;
        private String audio_duration;

        public ChatListBean() {
        }

        public int getIs_self() {
            return is_self;
        }

        public void setIs_self(int is_self) {
            this.is_self = is_self;
        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAudio_duration() {
            return audio_duration;
        }

        public void setAudio_duration(String audio_duration) {
            this.audio_duration = audio_duration;
        }
    }
}
