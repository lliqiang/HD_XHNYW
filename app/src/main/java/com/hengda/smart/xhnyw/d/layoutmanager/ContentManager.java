package com.hengda.smart.xhnyw.d.layoutmanager;/**
 * Created by lenovo on 2017/6/27.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.RouteBean;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/27 18:52
 * 类描述：
 */
public class ContentManager extends BaseViewHolderManager<RouteBean.MsgBean.ListBean.ExhibitArrBean> {
    private Context mContext;

    public ContentManager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull RouteBean.MsgBean.ListBean.ExhibitArrBean arrBean) {
        ((TextView) getView(holder, R.id.tv_lv_route)).setText(arrBean.getByname());
        Glide.with(mContext).load(Hd_AppConfig.getExhibitImgPath(arrBean.getExhibit_id(), "litimg")).into((ImageView) getView(holder, R.id.iv_item_lv));
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_route_content;
    }
}
