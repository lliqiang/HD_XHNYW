package com.hengda.smart.xhnyw.d.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
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
 * desc   : 我的模块顶部标题栏,包含返回按钮,以及标题
 * version: 1.0
 */
public class TitleBar extends RelativeLayout {

    private TextView tvTitle;

    public TitleBar(Context context) {
        super(context);
        init(null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        String mTitleText = "";
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TitleBar);
            mTitleText = ta.getString(R.styleable.TitleBar_title_text);
            ta.recycle();
        }
        //添加左侧返回按钮
        ImageView ivBack = new ImageView(getContext());
        ivBack.setId(R.id.iv_mine_title_bar_back);
        ivBack.setImageResource(R.drawable.select_back);
        LayoutParams iconParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ivBack.setPadding(SizeUtils.dp2px(30), 0, SizeUtils.dp2px(30), 0);
        ivBack.setOnClickListener(v -> {
            if (getContext() instanceof Activity) {
                ((Activity) getContext()).finish();
            } else {
                Log.e("wtf", "TitleBar获取Activity失败");
            }
        });
        iconParams.addRule(CENTER_VERTICAL);
        iconParams.addRule(ALIGN_PARENT_START);
        addView(ivBack, iconParams);
        //添加标题
        tvTitle = new TextView(getContext());
        tvTitle.setId(R.id.tv_mine_title_bar_title);
        tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.colorBrouse));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        tvTitle.setText(mTitleText);
        LayoutParams tvTitleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvTitleParams.addRule(CENTER_IN_PARENT);
        addView(tvTitle, tvTitleParams);
        //添加底部横线
        View dividerView = new View(getContext());
        dividerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBrouse));
        LayoutParams dividerViewParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(1));
        dividerViewParams.addRule(BELOW, R.id.iv_mine_title_bar_back);
        addView(dividerView, dividerViewParams);
    }

    public void setTitle(int titleRes) {
        if (tvTitle != null) {
            tvTitle.setText(titleRes);
        }
    }

}
