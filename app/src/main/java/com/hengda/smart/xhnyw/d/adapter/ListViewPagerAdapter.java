package com.hengda.smart.xhnyw.d.adapter;/**
 * Created by lenovo on 2017/6/19.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/19 8:46
 * 类描述：
 */
public class ListViewPagerAdapter extends PagerAdapter {
    private List<ExhibitionBean> beanList;
    private Context mContext;

    public ListViewPagerAdapter(List<ExhibitionBean> beanList, Context mContext) {
        this.beanList = beanList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(mContext);
        String path=Hd_AppConfig.getDefaultFileDir()+Hd_AppConfig.getLanguage()+"/"+beanList.get(position).getFileNo()+"/"+beanList.get(position).getFileNo()+".png";
        Glide.with(mContext).load(path).placeholder(R.mipmap.listdef).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
