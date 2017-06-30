package com.hengda.smart.xhnyw.d.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hengda.frame.tileview.HDTileView;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HBriteDatabase;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.model.AutoNum;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.hengda.smart.xhnyw.d.model.MapBean;
import com.hengda.smart.xhnyw.d.model.MapConfigBean;
import com.hengda.smart.xhnyw.d.model.RouteBean;
import com.hengda.smart.xhnyw.d.model.RouteDetailBean;
import com.hengda.smart.xhnyw.d.service.BleNoService;
import com.hengda.smart.xhnyw.d.ui.MapActivity;
import com.hengda.smart.xhnyw.d.ui.PlayActivity;
import com.hengda.zwf.autonolibrary.BleNumService;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.view.ViewGroup.LayoutParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.internal.util.LinkedArrayList;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportFragment {
    private HDTileView tileView;
    private MapBean mapBean;
    private String path;
    private int floor;

    private String imgUrl;
    private int lastNum;
    private List<ExhibitInfo> exhibitionBeanList;
    View view;
    View bigView;
    private Map<String, View> viewMap = new HashMap<>();
    private Map<String, View> bitViewMap = new HashMap<>();
    private Context mContext;
    private ImageView cardImg;
    private RouteDetailBean detailBean;
    private RouteBean routeBean;
    private int type;
    List<double[]> positionses;
    private double[] buffer;
    private Paint paint;
    private Subscription subscription;

    public MapFragment() {
        // Required empty public constructor
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        tileView = new HDTileView(getContext());
        exhibitionBeanList = new ArrayList<>();
        floor = getArguments().getInt("Floor");
        path = Hd_AppConfig.getMapPath(floor);
        positionses = new ArrayList<>();
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorBrouse));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        type = getActivity().getIntent().getIntExtra("type", -1);
        routeBean = (RouteBean) getActivity().getIntent().getSerializableExtra("routeBean");
        if (routeBean != null) {
            if (type != -1) {
                switch (type) {
                    case 0:
                        makeLine(0);
                        break;
                    case 1:
                        makeLine(1);
                        break;
                    case 2:
                        makeLine(2);
                        break;
                    case 3:
                        makeLine(3);
                        break;
                    case 4:
                        makeLine(4);
                        break;
                }
            }
        }
        getMapInfo(floor);
    }

    private void makeLine(int type) {
        EasyHttp.get("index.php?g=mapi&m=Apinav&a=road_info")
                .params("map_id", String.valueOf(floor))
                .params("road_type", routeBean.getMsg().get(type).getRoad_type())
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                    }

                    @Override
                    public void onSuccess(String response) {
                        positionses.clear();
                        detailBean = new Gson().fromJson(response, RouteDetailBean.class);
                        buffer = new double[2 * detailBean.getData().getGj_arr().size()];
                        if (detailBean.getData().getGj_arr().size() > 1) {
                            for (int i = 0; i < detailBean.getData().getGj_arr().size() - 1; i++) {
                                positionses.add(new double[]{Double.parseDouble(detailBean.getData().getGj_arr().get(i).getX()), Double.parseDouble(detailBean.getData().getGj_arr().get(i).getY())});
                            }
                        }
                        tileView.drawPath(positionses, paint);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initTileView();
        cardImg = new ImageView(getActivity());
        cardImg.setImageResource(R.mipmap.img_los_mark);
        addMark();
        return tileView;
    }

    private void addMark() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ").append("exhibitinfo")
                .append(" WHERE map_id = " + floor)
                .append(" AND type=1")
                .append(" ORDER BY autonum");
        subscription = HBriteDatabase.getInstance()
                .getDb()
                .createQuery("exhibitinfo", sb.toString(), new String[]{})
                .mapToList(new Func1<Cursor, ExhibitInfo>() {
                    @Override
                    public ExhibitInfo call(Cursor cursor) {
                        return ExhibitInfo.CursorToModel(cursor);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<ExhibitInfo>, Observable<? extends ExhibitInfo>>() {
                    @Override
                    public Observable<? extends ExhibitInfo> call(List<ExhibitInfo> exhibitionBeen) {
                        exhibitionBeanList.clear();
                        viewMap.clear();
                        bitViewMap.clear();
                        exhibitionBeanList = exhibitionBeen;
                        return Observable.from(exhibitionBeen);
                    }
                })
                .subscribe(exhibitionBean -> {
                    view = LayoutInflater.from(mContext).inflate(R.layout.mark_layout, null);
                    bigView = LayoutInflater.from(mContext).inflate(R.layout.big_mark_layout, null);
                    imgUrl = Hd_AppConfig.getExhibitImgPath(exhibitionBean.getExhibit_id(), "map_icon");
                    Glide.with(mContext).load(imgUrl).into(((ImageView) view.findViewById(R.id.iv_mark)));
                    Glide.with(mContext).load(imgUrl).into(((ImageView) bigView.findViewById(R.id.iv_mark_big)));
                    ((TextView) bigView.findViewById(R.id.tv_mark_big)).setText(exhibitionBean.getAutonum());
                    viewMap.put(exhibitionBean.getExhibit_id(), view);
                    bitViewMap.put(exhibitionBean.getExhibit_id(), bigView);
                    tileView.addMarker(viewMap.get(exhibitionBean.getExhibit_id()), Integer.parseInt(exhibitionBean.getAxis_x()), Integer.parseInt(exhibitionBean.getAxis_y()), -0.5f, -1.0f);
                    view.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        intent.putExtra("ExhibitionBean", exhibitionBean);
                        startActivity(intent);
                    });
                    bigView.setOnClickListener(v -> {
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        intent.putExtra("ExhibitionBean", exhibitionBean);
                        startActivity(intent);
                    });
                });
    }

    private void initTileView() {
        mapBean = getMapInfo(floor);
        if (Integer.parseInt(mapBean.getHeight()) > 0) {
            tileView.init(2, Integer.parseInt(mapBean.getWidth()), Integer.parseInt(mapBean.getHeight()), path);
            tileView.loadMapFromDisk();
            tileView.setMinimumScaleFullScreen();
            tileView.addSample(path + "/img.png", false);
        }
    }

    public static MapFragment newInstance(int floor) {
        MapFragment fragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Floor", floor);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(AutoNum event) {
        ExhibitInfo exhibitInfo = getFloorInfo(event.getAutoNum());
        ExhibitInfo exhibitInfo1 = getCardInfo(event.getAutoNum(), 2);
        if (!TextUtils.isEmpty(exhibitInfo.getMap_id())) {
            if (Integer.parseInt(exhibitInfo.getMap_id()) == floor) {
                for (int i = 0; i < exhibitionBeanList.size(); i++) {
                    if (Integer.parseInt(exhibitionBeanList.get(i).getAutonum()) == event.getAutoNum() && !isReplay(event.getAutoNum())) {

                        if (exhibitInfo1.getType().equals("2")) {

                            if (cardImg.getParent() != null) {
                                ((ViewGroup) cardImg.getParent()).removeView(cardImg);
                            }
                            tileView.addMarker(cardImg, Integer.parseInt(exhibitInfo1.getAxis_x()), Integer.parseInt(exhibitInfo1.getAxis_y()), -0.5f, -0.1f);
                        }

                        if (bitViewMap.get(exhibitionBeanList.get(i).getExhibit_id()).getParent() != null) {
                            ((ViewGroup) bitViewMap.get(exhibitionBeanList.get(i).getExhibit_id()).getParent()).removeView(bitViewMap.get(exhibitionBeanList.get(i).getExhibit_id()));
                        }
                        tileView.addMarker(bitViewMap.get(exhibitionBeanList.get(i).getExhibit_id()), Integer.parseInt(exhibitionBeanList.get(i).getAxis_x()), Integer.parseInt(exhibitionBeanList.get(i).getAxis_y()), -0.5f, -1.0f);
                    } else {
                        tileView.removeMarker(bitViewMap.get(exhibitionBeanList.get(i).getExhibit_id()));
                    }
                }

            }
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        subscription.unsubscribe();
        if (tileView != null) {
            tileView.remmoveAllMarkers();
            tileView.destroy();
        }
    }

    public MapBean getMapInfo(int floor) {
        MapBean mapBean = new MapBean();

        Cursor cursor = HResDdUtil.getInstance().getMapInfo(floor);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                mapBean = MapBean.CursorToModel(cursor);

            }
            cursor.close();
        }
        return mapBean;
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

    public ExhibitInfo getCardInfo(int autoNum, int type) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        Cursor cursor = HResDdUtil.getInstance().getCardPositon(autoNum, type);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                exhibitInfo = ExhibitInfo.CursorToModel(cursor);
            }
            cursor.close();
        }
        return exhibitInfo;
    }

}
