package com.hengda.smart.xhnyw.d.adapter;/**
 * Created by lenovo on 2017/6/26.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.ui.ListDetailActivity;
import com.hengda.smart.xhnyw.d.ui.PlayActivity;

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/26 13:39
 * 类描述：
 */
public class ListDetailAdapter extends BaseQuickAdapter<ExhibitInfo, BaseViewHolder> {
    private Context mContext;
    private ImageView imageView;
    private String path;
    private ImageView getImageView;
    public ListDetailAdapter(@LayoutRes int layoutResId, @Nullable List<ExhibitInfo> data, Context context,ImageView img) {
        super(layoutResId, data);
        this.mContext = context;
        this.getImageView=img;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExhibitInfo item) {
        imageView = ((ImageView) helper.getView(R.id.iv_item_navigate));
        ((ImageView) helper.getView(R.id.iv_line_navigate)).setVisibility(View.GONE);
        path = Hd_AppConfig.getExhibitImgPath(item.getExhibit_id(), "litimg");
        Glide.with(mContext).load(path).into(imageView);
//        getImageView.setOnClickListener(v -> {
//            Intent intent=new Intent(mContext,PlayActivity.class);
//            intent.putExtra("ExhibitionBean",item);
//            mContext.startActivity(intent);
//        });
    }
}
