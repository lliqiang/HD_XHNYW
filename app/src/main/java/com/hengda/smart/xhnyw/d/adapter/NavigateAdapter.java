package com.hengda.smart.xhnyw.d.adapter;/**
 * Created by lenovo on 2017/6/26.
 */

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/26 13:39
 * 类描述：
 */
public class NavigateAdapter extends BaseQuickAdapter<ExhibitInfo, BaseViewHolder> {
    private Context mContext;
    private ImageView imageView;
    private String path;

    public NavigateAdapter(@LayoutRes int layoutResId, @Nullable List<ExhibitInfo> data, Context context) {
        super(layoutResId, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitInfo item) {
        imageView = ((ImageView) helper.getView(R.id.iv_item_navigate));
        path = Hd_AppConfig.getExhibitImgPath(item.getExhibit_id(), "litimg");
        Glide.with(mContext).load(path).into(imageView);
    }
}
