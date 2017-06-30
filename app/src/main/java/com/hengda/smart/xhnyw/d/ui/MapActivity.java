package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.fragment.MapFragment;
import com.hengda.smart.xhnyw.d.fragment.RouteFragment;
import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.hengda.smart.xhnyw.d.service.BleNoService;
import com.hengda.smart.xhnyw.d.tools.FragmentUtil;
import com.hengda.smart.xhnyw.d.tools.ViewUtil;
import com.hengda.smart.xhnyw.d.view.FloorDialog;
import com.hengda.smart.xhnyw.d.view.HDialogDevice;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author lenovo.
 * @explain 地图导览界面
 * @time 2017/6/17 13:45.
 */
public class MapActivity extends SupportActivity implements View.OnClickListener {

    @Bind(R.id.img_back_map)
    ImageView imgBackMap;
    @Bind(R.id.et_map)
    TextView etMap;
    @Bind(R.id.container_map)
    FrameLayout containerMap;
    @Bind(R.id.img_route)
    ImageView imgRoute;
    @Bind(R.id.img_device)
    ImageView imgDevice;
    @Bind(R.id.activity_map)
    RelativeLayout activityMap;
    @Bind(R.id.btn_one_map)
    RadioButton btnOneMap;
    @Bind(R.id.btn_two_map)
    RadioButton btnTwoMap;
    @Bind(R.id.btn_three_map)
    RadioButton btnThreeMap;
    @Bind(R.id.btn_four_map)
    RadioButton btnFourMap;
    @Bind(R.id.rp_map)
    RadioGroup rpMap;
    private HDialogDevice dialogDevice;
    private int floor = 1;
    private int lastNum = -1;
    private Intent intent;
    private FloorDialog floorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        hideSoftInput();
        initListner();
        EventBus.getDefault().register(this);
        floorDialog = new FloorDialog(this);
        dialogDevice = new HDialogDevice(this);

//        etMap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    hideSoftInput();
//                }
//            }
//        });
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(AutoNum event) {
        ExhibitInfo exhibitInfo = getFloorInfo(event.getAutoNum());
        if (!TextUtils.isEmpty(exhibitInfo.getMap_id())) {
            if (Integer.parseInt(exhibitInfo.getMap_id()) != floor && isReplay(event.getAutoNum())) {
                if (!floorDialog.isShowing()) {
                    floorDialog.pBtnText(getString(R.string.sure))
                            .message(getString(R.string.switch_info))
                            .nBtnText(getString(R.string.cancel))
                            .outsideCancelable(true)
                            .pBtnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switchFloor(exhibitInfo);
                                    floorDialog.dismiss();
                                }
                            }).nBtnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            floorDialog.dismiss();
                        }
                    }).show();
                }
            }
        }
    }
    private void switchFloor(ExhibitInfo exhibitInfo) {
        floor = Integer.parseInt(exhibitInfo.getMap_id());
        switch (floor) {
            case 1:
                btnOneMap.setChecked(true);
                break;
            case 2:
                btnTwoMap.setChecked(true);
                break;
            case 3:
                btnThreeMap.setChecked(true);
                break;
            case 4:
                btnFourMap.setChecked(true);
                break;
        }
        FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_map,
                MapFragment.newInstance(floor), true);
//        replaceLoadRootFragment(R.id.container_map, MapFragment.newInstance(floor), false);
    }
    private void initListner() {
        imgBackMap.setOnClickListener(this);
        etMap.setOnClickListener(this);
        imgRoute.setOnClickListener(this);
        imgDevice.setOnClickListener(this);

        rpMap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                ViewUtil.showDownloadProgressDialog(MapActivity.this, getString(R.string.going));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch (checkedId) {
                            case R.id.btn_one_map:
                                floor = 1;
                                FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_map,
                                        MapFragment.newInstance(floor), true);
                                break;
                            case R.id.btn_two_map:
                                floor = 2;
                                FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_map,
                                        MapFragment.newInstance(floor), true);
                                break;
                            case R.id.btn_three_map:
                                floor = 3;
                                FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_map,
                                        MapFragment.newInstance(floor), true);
                                break;
                            case R.id.btn_four_map:
                                floor = 4;
                                FragmentUtil.replaceFragment(getSupportFragmentManager(), R.id.container_map,
                                        MapFragment.newInstance(floor), true);
                                break;
                        }
//                        ViewUtil.hideDownloadProgressDialog();
                    }
                }, 10);


            }
        });
        btnOneMap.setChecked(true);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back_map:
                finish();
                break;
            case R.id.et_map:
                Intent intent = new Intent(MapActivity.this, SearchActivity.class);
                intent.putExtra("floor", floor);
                startActivity(intent);
                break;
            case R.id.img_route:
                startActivity(new Intent(MapActivity.this, DialogActivity.class));

                break;
            case R.id.img_device:
                showDeviceDialog();
                break;

        }
    }
    /**
     * 显示路线弹框
     */
    private void showDeviceDialog() {
        dialogDevice.ptnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExhibitInfo exhibitInfo = new ExhibitInfo();
                intent = new Intent(MapActivity.this, DeviceActivity.class);
                switch (v.getId()) {
                    case R.id.ll_bathroom_dialog:
                        exhibitInfo = getTypeData(floor, 3);

                        break;
                    case R.id.ll_elect_floor_dialog:
                        exhibitInfo = getTypeData(floor, 4);
                        break;
                    case R.id.ll_floor_dialog:
                        exhibitInfo = getTypeData(floor, 5);
                        break;
                    case R.id.ll_stairs_dialog:
                        exhibitInfo = getTypeData(floor, 6);
                        break;
                    case R.id.ll_shop_dialog:
                        exhibitInfo = getTypeData(floor, 7);
                        break;

                }
                if (exhibitInfo != null&&!TextUtils.isEmpty(exhibitInfo.getExhibit_id())) {

                    intent.putExtra("ExhibitionBean", exhibitInfo);
                    startActivity(intent);
                }else {
                    Toast.makeText(MapActivity.this, getString(R.string.no_device), Toast.LENGTH_SHORT).show();
                }
            }
        }).ntnClickListner(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDevice.dismiss();
            }
        }).cancelable(false).outsideCancelable(false).show();
    }

    public ExhibitInfo getFloorInfo(int autoNum) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        Cursor cursor = HResDdUtil.getInstance().loadExhibitByAutoNo(autoNum);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                exhibitInfo = ExhibitInfo.CursorToModel(cursor);
            }
            cursor.close();

        }
        return exhibitInfo;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 隔一复收
     *
     * @param num
     * @return
     */
    private boolean isReplay(int num) {
        boolean temp_flag = false;
        if (num != 0 && num != lastNum) {
            lastNum = num;
            temp_flag = true;
        }
        return temp_flag;
    }

    /**
     * 获取展厅设施
     *
     * @param floor
     */
    private ExhibitInfo getTypeData(int floor, int type) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        Cursor cursor = HResDdUtil.getInstance().getMapExhibit(floor, type);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                exhibitInfo = ExhibitInfo.CursorToModel(cursor);
            }
            cursor.close();
        }
        return exhibitInfo;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (null != this.getCurrentFocus()) {
//            /**
//             * 点击空白位置 隐藏软键盘
//             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);

    }
}
