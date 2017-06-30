package com.hengda.smart.xhnyw.d.layoutmanager;/**
 * Created by lenovo on 2017/6/27.
 */

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelib.multiitem.adapter.holder.BaseViewHolder;
import com.freelib.multiitem.adapter.holder.BaseViewHolderManager;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.RouteBean;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/27 18:52
 * 类描述：
 */
public class TitleManager extends BaseViewHolderManager<RouteBean.MsgBean.ListBean> {
    private ImageView dotImg;
    private ImageView expandImg;
    private boolean flag;

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, @NonNull RouteBean.MsgBean.ListBean listBean) {
        ((TextView) getView(holder, R.id.tv_expand_floor)).setText(listBean.getMap_name());
        dotImg = getView(holder, R.id.iv_expand_dot);
        expandImg = getView(holder, R.id.iv_expand_route);

        expandImg.setOnClickListener(v -> {
            if (flag) {
                expandImg.setImageResource(R.mipmap.img_down_expand);
                dotImg.setImageResource(R.mipmap.img_dot_weight);
            } else {
                expandImg.setImageResource(R.mipmap.img_up_fold);
                dotImg.setImageResource(R.mipmap.img_dot_light);
            }
            flag = !flag;
        });
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.title_route_layout;
    }
}
