package com.hengda.smart.xhnyw.d.adapter;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.FootprintBean;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/21.
 * desc   : 我的足迹列表适配器
 * version: 1.0
 */
public class FootprintListAdapter extends BaseQuickAdapter<FootprintBean.FootprintBeanVO, BaseViewHolder> {

    public FootprintListAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FootprintBean.FootprintBeanVO item) {
        ImageView ivPhoto = helper.getView(R.id.iv_photo);
        Glide.with(mContext).load(item.getImgPath()).into(ivPhoto);
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_date, item.getDate())
                .setText(R.id.tv_time, item.getTime());
    }

}
