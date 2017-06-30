package com.hengda.smart.xhnyw.d.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;


public class FloorDialog extends Dialog {

    private BaseEffects baseEffects;
    private RelativeLayout mRlSwitchDialog;
    private TextView mTvTipSwitch;
    private Button mBtnSureSwitchDialog;
    private Button mBtnCancelSwitchDialog;
    WindowManager.LayoutParams params;
    public FloorDialog(Context context) {
        super(context, R.style.hd_dialog_dim);
        init(context);
    }
    public FloorDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        View dialogContainer = View.inflate(context, R.layout.dialog_switch_floor, null);
        mRlSwitchDialog = (RelativeLayout) dialogContainer.findViewById(R.id.rl_switch_dialog);
        mTvTipSwitch = (TextView)  dialogContainer.findViewById(R.id.tv_tip_switch);
        mBtnSureSwitchDialog = (Button)  dialogContainer.findViewById(R.id.btn_sure_switch_dialog);
        mBtnCancelSwitchDialog = (Button)  dialogContainer.findViewById(R.id.btn_cancel_switch_dialog);

        setContentView(dialogContainer);
        setOnShowListener(dialogInterface -> {
            if (baseEffects != null) {
                baseEffects.setDuration(500);
                baseEffects.start(mRlSwitchDialog);
            }
        });
    }

    /**
     * 设置消息
     *
     * @param msg
     * @return
     */
    public FloorDialog message(CharSequence msg) {
        mTvTipSwitch.setVisibility(View.VISIBLE);
        mTvTipSwitch.setText(msg);
        return this;
    }

    /**
     * 设置消息
     *
     * @param msg
     * @return
     */
    public FloorDialog message(int msg) {
        mTvTipSwitch.setVisibility(View.VISIBLE);
        mTvTipSwitch.setText(msg);
        return this;
    }

    /**
     * 确定按钮文字
     *
     * @param text
     * @return
     */
    public FloorDialog pBtnText(CharSequence text) {
        mBtnSureSwitchDialog.setVisibility(View.VISIBLE);
        mBtnSureSwitchDialog.setText(text);
        return this;
    }

    /**
     * 取消按钮文字
     *
     * @param text
     * @return
     */
    public FloorDialog nBtnText(CharSequence text) {
        mBtnCancelSwitchDialog.setVisibility(View.VISIBLE);
        mBtnCancelSwitchDialog.setText(text);
        return this;
    }

    /**
     * 确定按钮监听
     *
     * @param click
     * @return
     */
    public FloorDialog pBtnClickListener(View.OnClickListener click) {
        mBtnSureSwitchDialog.setOnClickListener(click);
        return this;
    }
    /**
     * 取消按钮监听
     *
     * @param click
     * @return
     */
    public FloorDialog nBtnClickListener(View.OnClickListener click) {
        mBtnCancelSwitchDialog.setOnClickListener(click);
        return this;
    }
    /**
     * 设置Dlg出现动画
     * <p>
     * //     * @param baseEffects
     *
     * @return
     */
    public FloorDialog baseEffects(BaseEffects baseEffects) {
        this.baseEffects = baseEffects;
        return this;
    }

    /**
     * 设置是否可以取消
     *
     * @param cancelable
     * @return
     */
    public FloorDialog cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    /**
     * 设置是否可以点击周围取消
     *
     * @param outsideCancelable
     * @return
     */
    public FloorDialog outsideCancelable(boolean outsideCancelable) {
        setCanceledOnTouchOutside(outsideCancelable);
        return this;
    }


}
