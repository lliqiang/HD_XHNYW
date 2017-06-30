package com.hengda.smart.xhnyw.d.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.NavigateAdapter;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.fragment.MapFragment;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.RouteBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public class NavigateActivity extends SupportActivity {

    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.los_common)
    ImageView losCommon;
    @Bind(R.id.container_navigate)
    RelativeLayout containerNavigate;
    @Bind(R.id.vr_navigate)
    RecyclerView vrNavigate;
    @Bind(R.id.back_navigate)
    ImageView backNavigate;
    private List<ExhibitInfo> exhibitInfos;
    private int floor = 1;
    private NavigateAdapter navigateAdapter;
    private RouteBean routeBean;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate);
        HdApplication.addActivity(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        vrNavigate.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        tvTilteCommon.setVisibility(View.GONE);
        losCommon.setVisibility(View.GONE);
        imgBackCommon.setOnClickListener(v -> finish());
        backNavigate.setOnClickListener(v -> {
            HdApplication.clearActivity();

        });
        exhibitInfos = new ArrayList<>();
        routeBean = (RouteBean) getIntent().getSerializableExtra("routeBean");
        type = getIntent().getIntExtra("type", -1);
        if (routeBean!=null){

            switch (type) {
                case 0:
                    floor = Integer.valueOf(routeBean.getMsg().get(0).getMap_id());
                    break;
                case 1:
                    floor = Integer.valueOf(routeBean.getMsg().get(1).getMap_id());
                    break;
                case 2:
                    floor = Integer.valueOf(routeBean.getMsg().get(2).getMap_id());
                    break;
                case 3:
                    floor = Integer.valueOf(routeBean.getMsg().get(3).getMap_id());
                    break;
                case 4:
                    floor = Integer.valueOf(routeBean.getMsg().get(4).getMap_id());
                    break;
            }
        }
        getData(floor);

        navigateAdapter = new NavigateAdapter(R.layout.item_img_layout, exhibitInfos, this);
        vrNavigate.setAdapter(navigateAdapter);
        navigateAdapter.notifyDataSetChanged();
        replaceLoadRootFragment(R.id.container_navigate, MapFragment.newInstance(floor), false);
    }

    /**
     * 获取展厅列表展品
     *
     * @param floor
     */
    private void getData(int floor) {
        List<ExhibitInfo> list = new ArrayList<>();
        Cursor cursor = HResDdUtil.getInstance().getMapExhibit(floor, 1);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ExhibitInfo exhibitInfo = ExhibitInfo.CursorToModel(cursor);
                Log.i("exhibitInfo", exhibitInfo.toString());
                list.add(exhibitInfo);
            }
            exhibitInfos.clear();
            exhibitInfos.addAll(list);
            cursor.close();
        }

    }
}
