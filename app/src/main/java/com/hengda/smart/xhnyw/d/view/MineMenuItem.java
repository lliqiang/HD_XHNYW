package com.hengda.smart.xhnyw.d.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.tools.SizeUtils;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 我的模块菜单按钮
 * version: 1.0
 */
public class MineMenuItem extends RelativeLayout {

    private ImageView ivBackground;

    public MineMenuItem(Context context) {
        super(context);
        init(null);
    }

    public MineMenuItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MineMenuItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int iconRes = R.mipmap.ic_launcher;
        String labelText = "";
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MineMenuItem);
            iconRes = ta.getResourceId(R.styleable.MineMenuItem_icon_src, R.mipmap.ic_launcher);
            labelText = ta.getString(R.styleable.MineMenuItem_label_text);
            ta.recycle();
        }
        //背景图片
        ivBackground = new ImageView(getContext());
        ivBackground.setId(R.id.iv_mine_menu_item_background);
        ivBackground.setImageResource(R.drawable.select_bg_mine);
        LayoutParams ivBackgroundParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivBackgroundParams.addRule(CENTER_IN_PARENT);
        addView(ivBackground, ivBackgroundParams);
        //参照View
        View referenceView = new View(getContext());
        referenceView.setId(R.id.v_mine_menu_item_reference);
        LayoutParams referenceViewParams = new LayoutParams(0, 0);
        referenceViewParams.addRule(CENTER_IN_PARENT);
        addView(referenceView, referenceViewParams);
        //图片Icon
        ImageView ivIcon = new ImageView(getContext());
        ivIcon.setImageResource(iconRes);
        LayoutParams ivIconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivIconParams.addRule(CENTER_HORIZONTAL);
        ivIconParams.addRule(ABOVE, R.id.v_mine_menu_item_reference);
        addView(ivIcon, ivIconParams);
        //文字Label
        TextView tvLabel = new TextView(getContext());
        tvLabel.setText(labelText);
        tvLabel.setTextColor(Color.WHITE);
        tvLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tvLabel.setPadding(0, SizeUtils.dp2px(24), 0, 0);
        LayoutParams tvLabelParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLabelParams.addRule(CENTER_HORIZONTAL);
        tvLabelParams.addRule(BELOW, R.id.v_mine_menu_item_reference);
        addView(tvLabel, tvLabelParams);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        ivBackground.setOnClickListener(l);
    }

}
