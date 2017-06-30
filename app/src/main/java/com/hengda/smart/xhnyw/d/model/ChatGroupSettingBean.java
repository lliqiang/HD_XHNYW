package com.hengda.smart.xhnyw.d.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/24.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class ChatGroupSettingBean implements Parcelable {

    private boolean isOwner;
    private String groupName;
    private String groupId;
    private String groupFakeId;
    private String groupIntroduce;
    private boolean needCertification;
    private String myNickName;
    private boolean isShowNickName;

    public ChatGroupSettingBean(){

    }

    protected ChatGroupSettingBean(Parcel in) {
        isOwner = in.readByte() != 0;
        groupName = in.readString();
        groupId = in.readString();
        groupFakeId = in.readString();
        groupIntroduce = in.readString();
        needCertification = in.readByte() != 0;
        myNickName = in.readString();
        isShowNickName = in.readByte() != 0;
    }

    public static final Creator<ChatGroupSettingBean> CREATOR = new Creator<ChatGroupSettingBean>() {
        @Override
        public ChatGroupSettingBean createFromParcel(Parcel in) {
            return new ChatGroupSettingBean(in);
        }

        @Override
        public ChatGroupSettingBean[] newArray(int size) {
            return new ChatGroupSettingBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isOwner ? 1 : 0));
        dest.writeString(groupName);
        dest.writeString(groupId);
        dest.writeString(groupFakeId);
        dest.writeString(groupIntroduce);
        dest.writeByte((byte) (needCertification ? 1 : 0));
        dest.writeString(myNickName);
        dest.writeByte((byte) (isShowNickName ? 1 : 0));
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupIntroduce() {
        return groupIntroduce;
    }

    public void setGroupIntroduce(String groupIntroduce) {
        this.groupIntroduce = groupIntroduce;
    }

    public boolean isNeedCertification() {
        return needCertification;
    }

    public void setNeedCertification(boolean needCertification) {
        this.needCertification = needCertification;
    }

    public String getMyNickName() {
        return myNickName;
    }

    public void setMyNickName(String myNickName) {
        this.myNickName = myNickName;
    }

    public boolean isShowNickName() {
        return isShowNickName;
    }

    public void setShowNickName(boolean showNickName) {
        isShowNickName = showNickName;
    }

    public String getGroupFakeId() {
        return groupFakeId;
    }

    public void setGroupFakeId(String groupFakeId) {
        this.groupFakeId = groupFakeId;
    }
}
