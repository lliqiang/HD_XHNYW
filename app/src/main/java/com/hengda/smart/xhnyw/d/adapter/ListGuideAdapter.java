package com.hengda.smart.xhnyw.d.adapter;/**
 * Created by lenovo on 2017/6/17.
 */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.Exhibition;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/17 15:06
 * 类描述：
 */
public class ListGuideAdapter extends BaseQuickAdapter<Exhibition,BaseViewHolder> {
    private Context mContext;
    public ListGuideAdapter(int layoutResId, List<Exhibition> data, Context context) {
        super(layoutResId, data);
        this.mContext=context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, Exhibition exhibitionBean) {
        String path=Hd_AppConfig.getDefaultFileDir()+"exhibition/"+exhibitionBean.getClass_id()+".png";
        Glide.with(mContext).load(path).into((ImageView) baseViewHolder.getView(R.id.iv_item_list_guide));
        if (Hd_AppConfig.getLanguage().equals("CHINESE")){
            baseViewHolder.setText(R.id.name_item_list_guide, exhibitionBean.getName());
        }else {
            baseViewHolder.setText(R.id.name_item_list_guide,exhibitionBean.getEn_name());
        }
    }
}
