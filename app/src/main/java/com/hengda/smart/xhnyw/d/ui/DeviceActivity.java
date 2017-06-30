package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hengda.frame.tileview.HDTileView;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.hengda.smart.xhnyw.d.model.LocBean;
import com.hengda.smart.xhnyw.d.model.MapBean;
import com.hengda.smart.xhnyw.d.model.MapConfigBean;
import com.hengda.smart.xhnyw.d.tools.BitmapProviderFile;
import com.hengda.smart.xhnyw.d.tools.NetUtil;
import com.hengda.smart.xhnyw.d.tools.RxBus;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DeviceActivity extends BaseActivity {

    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.los_common)
    ImageView losCommon;
    @Bind(R.id.container_device)
    FrameLayout containerDevice;
    private HDTileView tileView;
    private ExhibitInfo exhibitInfo;
    MapBean mapBean;
    String path;
    View view;
    List<double[]> positionses;
    private double[] buffer;
    private Paint paint;
    private int lastNum = -1;
    private ImageView bleImg;
    private ImageView hotImg;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        exhibitInfo = (ExhibitInfo) getIntent().getSerializableExtra("ExhibitionBean");
        if (!TextUtils.isEmpty(exhibitInfo.getMap_id())) {
            if (exhibitInfo.getMap_id() != null) {

                mapBean = getMapInfo(Integer.parseInt(exhibitInfo.getMap_id()));
            }
        }
//        Log.i("exhibitInfo",exhibitInfo.toString()+"mapBean: "+mapBean.toString());
        initView();
        positionses = new ArrayList<>();
        tvTilteCommon.setVisibility(View.GONE);
        imgBackCommon.setOnClickListener(v -> finish());
        initTileView();
        addmark();
        makeLine();

    }

    private void addmark() {
        if (exhibitInfo != null&&!TextUtils.isEmpty(exhibitInfo.getType())) {
            if (exhibitInfo.getType().equals("1")) {
                tileView.addMarker(view, Integer.parseInt(exhibitInfo.getAxis_x()), Integer.parseInt(exhibitInfo.getAxis_y()), -0.5f, -1.0f);
            } else {
                tileView.addMarker(hotImg, Integer.parseInt(exhibitInfo.getAxis_x()), Integer.parseInt(exhibitInfo.getAxis_y()), -0.5f, -1.0f);
            }
        }
    }

    private void makeLine() {
        if (flag) {
            RxBus.getDefault().toObservable(AutoNum.class)
                    .subscribe(new Subscriber<AutoNum>() {
                        @Override
                        public void onCompleted() {
                        }
                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(DeviceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNext(AutoNum autoNum) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flag = false;
                                    if (isReplay(autoNum.getAutoNum())) {
                                        ExhibitInfo startExhibitInfo = getFloorInfo(autoNum.getAutoNum(), 2);
                                        if (bleImg.getParent() != null) {
                                            ((ViewGroup) bleImg.getParent()).removeView(bleImg);
                                        }
                                        if (!TextUtils.isEmpty(startExhibitInfo.getAxis_x())) {
                                            tileView.addMarker(bleImg, Integer.parseInt(startExhibitInfo.getAxis_x()), Integer.parseInt(startExhibitInfo.getAxis_y()), -0.5f, -1.0f);
                                        }
                                        if (!TextUtils.isEmpty(startExhibitInfo.getMap_id()) && NetUtil.isConnected(DeviceActivity.this)) {
                                            postPosition(startExhibitInfo, autoNum);
                                        }
                                    }
                                }
                            });
                        }
                    });
        }

    }


    private void initTileView() {
        if (exhibitInfo.getMap_id() != null) {

            path = Hd_AppConfig.getMapPath(Integer.parseInt(exhibitInfo.getMap_id()));
        }
        tileView.setBitmapProvider(new BitmapProviderFile());
        if (mapBean != null && mapBean.getWidth() != null) {

            tileView.init(2, Integer.parseInt(mapBean.getWidth()), Integer.parseInt(mapBean.getHeight()), path);
            tileView.setMinimumScaleFullScreen();
        }
    }

    private void initView() {
        tileView = new HDTileView(this);
        bleImg = new ImageView(DeviceActivity.this);
        bleImg.setImageResource(R.mipmap.img_los_mark);
        hotImg = new ImageView(DeviceActivity.this);
        hotImg.setImageResource(R.mipmap.img_los_hot_mark);
        view = LayoutInflater.from(this).inflate(R.layout.big_mark_layout, null);
        ((TextView) view.findViewById(R.id.tv_mark_big)).setText(exhibitInfo.getAutonum() + "");
        Glide.with(this).load(Hd_AppConfig.getExhibitImgPath(exhibitInfo.getExhibit_id(), "map_icon")).into((ImageView) view.findViewById(R.id.iv_mark_big));

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorBrouse));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        containerDevice.addView(tileView);
    }

    /**
     * 获取路线信息
     *
     * @param startExhibitInfo
     * @param autoNum
     */
    private void postPosition(ExhibitInfo startExhibitInfo, AutoNum autoNum) {
        EasyHttp.post("index.php?g=mapi&m=Apinav&a=lx_daohang")
                .params("autonum", String.valueOf(autoNum.getAutoNum()))
                .params("star_map_id", startExhibitInfo.getMap_id())
                .params("end_x", exhibitInfo.getAxis_x())
                .params("end_y", exhibitInfo.getAxis_y())
                .params("end_map_id", exhibitInfo.getMap_id())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String response) {
                        LocBean locBean = new Gson().fromJson(response, LocBean.class);
                        buffer = new double[2 * locBean.getData().size()];

                        if (locBean.getData().size() > 1) {
                            for (int i = 0; i < locBean.getData().size() - 1; i++) {
                                positionses.add(new double[]{Double.parseDouble(locBean.getData().get(i).getX()), Double.parseDouble(locBean.getData().get(i).getY())});
                            }
                        }
                        tileView.drawPath(positionses, paint);


                    }
                });
    }

    public MapBean getMapInfo(int floor) {
        MapBean configBean = new MapBean();
        Cursor cursor = HResDdUtil.getInstance().getMapInfo(floor);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                configBean = MapBean.CursorToModel(cursor);
            }
            cursor.close();
        }
        return configBean;
    }

    public ExhibitInfo getFloorInfo(int autoNum, int type) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        Cursor cursor = HResDdUtil.getInstance().loadDeviceByAutoNo(autoNum, type);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                exhibitInfo = ExhibitInfo.CursorToModel(cursor);
            }
            cursor.close();
        }
        return exhibitInfo;
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
}
