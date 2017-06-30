package com.hengda.smart.xhnyw.d.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.ChatUiBean;

import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/24.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class ChatUIListAdapter extends BaseMultiItemQuickAdapter<ChatUiBean, BaseViewHolder> {

    public ChatUIListAdapter(List<ChatUiBean> data) {
        super(data);
        addItemType(ChatUiBean.MY_TEXT, R.layout.item_recycler_chat_ui_my_text);
        addItemType(ChatUiBean.OTHER_TEXT, R.layout.item_recycler_chat_ui_other_text);
        addItemType(ChatUiBean.MY_RECORD, R.layout.item_recycler_chat_ui_my_record);
        addItemType(ChatUiBean.OTHER_RECORD, R.layout.item_recycler_chat_ui_other_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChatUiBean item) {
        switch (helper.getItemViewType()) {
            case ChatUiBean.MY_TEXT:
                helper.setText(R.id.tv_content, item.getContent())
                        .setText(R.id.tv_time, item.getTime());
                break;
            case ChatUiBean.OTHER_TEXT:
                helper.setText(R.id.tv_content, item.getContent())
                        .setText(R.id.tv_time, item.getTime());
                break;
            case ChatUiBean.MY_RECORD:
                helper.setText(R.id.tv_content, String.format("%s\"",item.getRecordTime()))
                        .setText(R.id.tv_time, item.getTime());
                break;
            case ChatUiBean.OTHER_RECORD:
                helper.setText(R.id.tv_content, String.format("%s\"",item.getRecordTime()))
                        .setText(R.id.tv_time, item.getTime());
                break;
        }
    }

}
