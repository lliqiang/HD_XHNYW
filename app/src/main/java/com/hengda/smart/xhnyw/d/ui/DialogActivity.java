package com.hengda.smart.xhnyw.d.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.fragment.RouteFragment;
import com.hengda.smart.xhnyw.d.model.RouteBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public class DialogActivity extends SupportActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    @Bind(R.id.cancel_route)
    ImageView cancelRoute;
    @Bind(R.id.starGuide_route)
    TextView starGuideRoute;
    @Bind(R.id.rbtn_route_fullview)
    RadioButton rbtnRouteFullview;
    @Bind(R.id.rbtn_hot_route)
    RadioButton rbtnHotRoute;

    @Bind(R.id.rbtn_precious_route)
    RadioButton rbtnPreciousRoute;
    @Bind(R.id.rbtn_fast_route)
    RadioButton rbtnFastRoute;
    @Bind(R.id.rbtn_child_route)
    RadioButton rbtnChildRoute;


    @Bind(R.id.rg_dialog_route)
    RadioGroup rgDialogRoute;
    @Bind(R.id.container_route)
    FrameLayout containerRoute;
    @Bind(R.id.rl_route)
    RelativeLayout rlRoute;
    private String lan;
    private RouteBean routeBean;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_route);
        ButterKnife.bind(this);
        HdApplication.addActivity(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initLisnter();
        if (Hd_AppConfig.getLanguage().equals("CHINESE")) {
            lan = "1";
        } else {
            lan = "2";
        }
        getRouteInfo();
    }

    private void getRouteInfo() {
        EasyHttp.get("index.php?g=mapi&m=Apinav&a=road_type_list")
                .params("language", lan)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        Toast.makeText(DialogActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String response) {
                        routeBean = new Gson().fromJson(response, RouteBean.class);
                        count = routeBean.getMsg().size();
//                        if (routeBean.getMsg().size())
                        rbtnRouteFullview.setText(routeBean.getMsg().get(0).getRoad_title());
                        if (count >= 2) {
                            rbtnHotRoute.setText(routeBean.getMsg().get(1).getRoad_title());
                            rbtnPreciousRoute.setVisibility(View.GONE);
                            rbtnFastRoute.setVisibility(View.GONE);
                            rbtnChildRoute.setVisibility(View.GONE);
                        }
                        if (count >= 3) {
                            rbtnPreciousRoute.setVisibility(View.VISIBLE);
                            rbtnPreciousRoute.setText(routeBean.getMsg().get(2).getRoad_title());
                            rbtnFastRoute.setVisibility(View.GONE);
                            rbtnChildRoute.setVisibility(View.GONE);
                        }
                        if (count >= 4) {
                            rbtnFastRoute.setVisibility(View.VISIBLE);
                            rbtnFastRoute.setText(routeBean.getMsg().get(3).getRoad_title());
                            rbtnChildRoute.setVisibility(View.GONE);
                        }
                        if (count >= 5) {
                            rbtnChildRoute.setVisibility(View.VISIBLE);
                            rbtnChildRoute.setText(routeBean.getMsg().get(4).getRoad_title());
                        }
                        rbtnRouteFullview.setChecked(true);
//                        for (int i = 0; i < routeBean.getMsg().size(); i++) {
//                            RadioButton tempButton = new RadioButton(DialogActivity.this);
//                            tempButton.setBackgroundResource(R.color.colorBrouse);
//                            tempButton.setBackgroundResource(R.drawable.select_btn_map);
//                            // 设置RadioButton的背景图片
//                            tempButton.setTextSize(20);
//                            tempButton.setPadding(10, 30, 10, 30);
//                            tempButton.setTextColor(getResources().getColor(R.color.colorWhite));
//                            tempButton.setButtonDrawable(null);           // 设置按钮的样式
//                            // 设置文字距离按钮四周的距离
//                            tempButton.setText(routeBean.getMsg().get(i).getRoad_title());
//                            rgDialogRoute.addView(tempButton);
//                        }

                    }
                });
    }

    private void initLisnter() {
        cancelRoute.setOnClickListener(this);
        starGuideRoute.setOnClickListener(this);
        rgDialogRoute.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_route:
                finish();
                break;
            case R.id.starGuide_route:
                Intent intent = new Intent(DialogActivity.this, NavigateActivity.class);
                intent.putExtra("routeBean", routeBean);
                if (rbtnRouteFullview.isChecked()) {
                    intent.putExtra("type", 0);
                } else if (rbtnHotRoute.isChecked()) {
                    intent.putExtra("type", 1);
                } else if (rbtnPreciousRoute.isChecked()) {
                    intent.putExtra("type", 2);
                } else if (rbtnFastRoute.isChecked()) {
                    intent.putExtra("type", 3);
                } else {
                    intent.putExtra("type", 4);
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.rbtn_route_fullview:
                if (routeBean != null && routeBean.getMsg().get(0) != null) {
                    replaceLoadRootFragment(R.id.container_route, RouteFragment.newInstance(routeBean.getMsg().get(0)), false);
                }
                break;
            case R.id.rbtn_hot_route:
                if (count > 1) {
                    if (routeBean != null && routeBean.getMsg().get(1) != null) {
                        replaceLoadRootFragment(R.id.container_route, RouteFragment.newInstance(routeBean.getMsg().get(1)), false);
                    }
                }
                break;
            case R.id.rbtn_precious_route:
                if (count > 2) {
                    if (routeBean != null && routeBean.getMsg().get(2) != null) {
                        replaceLoadRootFragment(R.id.container_route, RouteFragment.newInstance(routeBean.getMsg().get(2)), false);
                    }
                }
                break;
            case R.id.rbtn_fast_route:
                if (count > 3) {
                    if (routeBean != null && routeBean.getMsg().get(3) != null) {
                        replaceLoadRootFragment(R.id.container_route, RouteFragment.newInstance(routeBean.getMsg().get(3)), false);
                    }
                }
                break;
            case R.id.rbtn_child_route:
                if (count > 4) {
                    if (routeBean != null && routeBean.getMsg().get(4) != null) {
                        replaceLoadRootFragment(R.id.container_route, RouteFragment.newInstance(routeBean.getMsg().get(4)), false);
                    }
                }
                break;
        }
    }
}
