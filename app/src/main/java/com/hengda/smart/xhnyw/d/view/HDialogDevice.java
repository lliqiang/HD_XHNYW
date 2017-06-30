package com.hengda.smart.xhnyw.d.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.tools.ColorUtils;

import butterknife.Bind;


public class HDialogDevice extends Dialog {
    LinearLayout llBathroomDialog;
    LinearLayout llElectFloorDialog;
    LinearLayout llFloorDialog;
    LinearLayout llStairsDialog;
    LinearLayout llShopDialog;
    RelativeLayout rootPanel;
    private BaseEffects baseEffects;
    private ImageView cancelImg;

    public HDialogDevice(Context context) {
        super(context, R.style.hd_dialog_dim);
        init(context);
    }

    public HDialogDevice(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        View dialogContainer = View.inflate(context, R.layout.dialog_divice, null);
        rootPanel = (RelativeLayout) dialogContainer.findViewById(R.id.ll_device_dialog);
        llBathroomDialog = (LinearLayout) dialogContainer.findViewById(R.id.ll_bathroom_dialog);
        llElectFloorDialog = (LinearLayout) dialogContainer.findViewById(R.id.ll_elect_floor_dialog);
        llFloorDialog = (LinearLayout) dialogContainer.findViewById(R.id.ll_floor_dialog);
        llStairsDialog = (LinearLayout) dialogContainer.findViewById(R.id.ll_stairs_dialog);
        llShopDialog = (LinearLayout) dialogContainer.findViewById(R.id.ll_shop_dialog);
        cancelImg = (ImageView) dialogContainer.findViewById(R.id.cancel_dialog);
        setContentView(dialogContainer);
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                if (baseEffects != null) {
                    baseEffects.setDuration(500);
                    baseEffects.start(rootPanel);
                }
            }
        });
    }

    /**
     * 确定按钮监听
     *
     * @param click
     * @return
     */
    public HDialogDevice ptnClickListener(View.OnClickListener click) {
        llBathroomDialog.setOnClickListener(click);
        llElectFloorDialog.setOnClickListener(click);
        llFloorDialog.setOnClickListener(click);
        llStairsDialog.setOnClickListener(click);
        llShopDialog.setOnClickListener(click);
        return this;
    }

    public HDialogDevice ntnClickListner(View.OnClickListener click) {
        cancelImg.setOnClickListener(click);
        return this;
    }

    /**
     * 设置Dlg出现动画
     *
     * @param baseEffects
     * @return
     */
    public HDialogDevice baseEffects(BaseEffects baseEffects) {
        this.baseEffects = baseEffects;
        return this;
    }


    /**
     * 设置是否可以取消
     *
     * @param cancelable
     * @return
     */
    public HDialogDevice cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 设置是否可以点击周围取消
     *
     * @param outsideCancelable
     * @return
     */
    public HDialogDevice outsideCancelable(boolean outsideCancelable) {
        setCanceledOnTouchOutside(outsideCancelable);
        return this;
    }

//    /**
//     * 设置字体
//     *
//     * @param typeface
//     * @return
//     */
//    public HDialogDevice setTypeface(Typeface typeface) {
//        mTitle.setTypeface(typeface);
//        mMsg.setTypeface(typeface);
//        mBtnP.setTypeface(typeface);
//        mBtnN.setTypeface(typeface);
//        return this;
//    }

}
