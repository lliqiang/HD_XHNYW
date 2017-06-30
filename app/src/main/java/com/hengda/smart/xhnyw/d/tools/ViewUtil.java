package com.hengda.smart.xhnyw.d.tools;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.view.HProgressDialog;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.AdapterViewItemClickEvent;
import com.jakewharton.rxbinding.widget.RxAdapterView;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/7/19 10:37
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class ViewUtil {
    static HProgressDialog progressDialog;
    /**
     * 防抖点击
     *
     * @param tvDestPlace
     * @param action1
     */
    public static void clickView(View tvDestPlace, Action1 action1) {
        RxView.clicks(tvDestPlace)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

    /**
     * ListView Item 防抖点击
     *
     * @param listView
     * @param action1
     */
    public static void clickListItem(ListView listView,
                                     Action1<AdapterViewItemClickEvent> action1) {
        RxAdapterView.itemClickEvents(listView)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(action1);
    }

    /**
     * 获取View控件
     *
     * @param view
     * @param id
     * @param <E>
     * @return
     */
    public static final <E extends View> E getView(View view, int id) {
        try {
            return (E) view.findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }
    /**
     * 显示加载 ProgressDialog，圆形进度
     *
     * @param context
     */
    public static void showDownloadProgressDialog(Activity context, String msg) {
        hideDownloadProgressDialog();
        progressDialog = new HProgressDialog(context);
        progressDialog
                .message(msg)
                .tweenAnim(R.mipmap.progress_roate, R.anim.progress_rotate)
                .outsideCancelable(false)
                .cancelable(false)
                .show();
    }
    /**
     * 隐藏 ProgressDialog
     */
    public static void hideDownloadProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
