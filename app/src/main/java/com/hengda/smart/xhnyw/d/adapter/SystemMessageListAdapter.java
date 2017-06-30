package com.hengda.smart.xhnyw.d.adapter;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.SystemMessageBean;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class SystemMessageListAdapter extends BaseQuickAdapter<SystemMessageBean.SystemMessageVO,BaseViewHolder> {

    public SystemMessageListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemMessageBean.SystemMessageVO item) {
        helper.setText(R.id.tv_title,item.getTitle());
    }

}
