package com.hengda.smart.xhnyw.d.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.MineGroupInformation;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/23.
 * desc   : 聊天群组列表adapter
 * version: 1.0
 */
public class ChatGroupMemberListAdapter extends BaseQuickAdapter<MineGroupInformation.MemberListBean, BaseViewHolder> {

    public ChatGroupMemberListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineGroupInformation.MemberListBean item) {
        helper.setText(R.id.tv_name, item.getMember_name())
                .setText(R.id.tv_id,String.format("%s"+ item.getMember_id(),"ID:"));
        helper.addOnClickListener(R.id.iv_location);
    }

}
