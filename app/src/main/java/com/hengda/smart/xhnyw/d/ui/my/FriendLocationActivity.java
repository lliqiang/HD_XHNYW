package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.FriendLocationBean;
import com.hengda.smart.xhnyw.d.model.MapBean;
import com.hengda.smart.xhnyw.d.tools.BitmapProviderFile;
import com.hengda.smart.xhnyw.d.tools.SizeUtils;
import com.qozix.tileview.TileView;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 一键求助页面
 * version: 1.0
 */
public class FriendLocationActivity extends MineBaseActivity {

    private static final String KEY_FRIEND_ID = "KEY_FRIEND_ID";
    private static final int TYPE_ADD_ROUTE = 2;

    @Bind(R.id.tile_view)
    TileView tileView;

    private String friendId;

    @Override
    protected void initBundle(Bundle extras) {
        super.initBundle(extras);
        friendId = extras.getString(KEY_FRIEND_ID);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_friend_location;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        String myLocation = Hd_AppConfig.getLastReceiveAutoNum();
        if (TextUtils.isEmpty(myLocation)) {
            Toast.makeText(FriendLocationActivity.this, R.string.map_friend_location_no_my_location, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            addSubscription(EasyHttp.post("index.php?g=mapi&m=Apinav&a=get_friend_lx")
                    .params("autonum", myLocation)
                    .params("friend_deviceno", friendId)
                    .execute(new CallBackProxy<CustomApiResult<FriendLocationBean>, FriendLocationBean>(new SimpleCallBack<FriendLocationBean>() {
                        @Override
                        public void onError(ApiException e) {
                            proceedException(e);
                            finish();
                        }

                        @Override
                        public void onSuccess(FriendLocationBean response) {
                            proceedData(response);
                        }
                    }) {
                    })
            );
        }
    }

    private void proceedData(FriendLocationBean response) {
        //初始化地图
        int mapId = Integer.parseInt(response.getFirend_info().getMap_id());
        String mapPath = Hd_AppConfig.getMapPath(mapId);
        MapBean mapBean = getMapInfo(mapId);
        int mMapWidth = Integer.parseInt(mapBean.getWidth());
        int mMapHeight = Integer.parseInt(mapBean.getHeight());
        tileView.setSize(mMapWidth, mMapHeight);
        tileView.defineBounds(0, 0, mMapWidth, mMapHeight);
        tileView.setMarkerAnchorPoints(-0.5f, -1.0f);
        tileView.setScale(0);
        tileView.setScaleLimits(0, 2);
        tileView.setBitmapProvider(new BitmapProviderFile());
        tileView.addDetailLevel(0.0125f, mapPath + "/125/%d_%d.png");
        tileView.addDetailLevel(0.2500f, mapPath + "/250/%d_%d.png");
        tileView.addDetailLevel(0.5000f, mapPath + "/500/%d_%d.png");
        tileView.addDetailLevel(1.0000f, mapPath + "/1000/%d_%d.png");
        ImageView downSample = new ImageView(this);
        Glide.with(this).load(mapPath + "/img.png").into(downSample);
        tileView.addView(downSample, 0);
        //添加点
        ImageView redPoint = new ImageView(this);
        Glide.with(this).load(R.mipmap.img_los_hot_mark).override(SizeUtils.dp2px(48), SizeUtils.dp2px(48)).into(redPoint);
        tileView.addMarker(redPoint, Double.parseDouble(response.getFirend_info().getX()), Double.parseDouble(response.getFirend_info().getY()), null, null);
        //添加路线
        if (response.getType() == TYPE_ADD_ROUTE) {
            List<FriendLocationBean.RoadBean> roadList = response.getRoad();
            if (roadList == null || roadList.isEmpty()) return;
            ImageView bluePoint = new ImageView(this);
            Glide.with(this).load(R.mipmap.img_los_mark).override(SizeUtils.dp2px(48), SizeUtils.dp2px(48)).into(bluePoint);
            FriendLocationBean.RoadBean myPoint = roadList.get(0);
            tileView.addMarker(bluePoint, Double.parseDouble(myPoint.getX()), Double.parseDouble(myPoint.getY()), null, null);
            ArrayList<double[]> points = new ArrayList<>();
            for (FriendLocationBean.RoadBean roadBean : roadList) {
                double[] doubles = new double[2];
                doubles[0] = Double.parseDouble(roadBean.getX());
                doubles[1] = Double.parseDouble(roadBean.getY());
                points.add(doubles);
            }
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#F1A53B"));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            tileView.drawPath(points,paint);
        }
    }

    public static void open(Context context, String friendDeviceId) {
        Intent intent = new Intent(context, FriendLocationActivity.class);
        intent.putExtra(KEY_FRIEND_ID, friendDeviceId);
        context.startActivity(intent);
    }
    /*=========================================================================================================*/

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

}
