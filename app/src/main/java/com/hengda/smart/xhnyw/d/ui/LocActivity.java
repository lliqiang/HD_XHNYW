package com.hengda.smart.xhnyw.d.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengda.frame.tileview.HDTileView;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author lenovo.
 * @explain 定位界面
 * @time 2017/6/19 18:57.
 */
public class LocActivity extends BaseActivity {

    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.los_common)
    ImageView losCommon;
    @Bind(R.id.container_loc)
    FrameLayout containerLoc;
    private HDTileView tileView;
    private ExhibitionBean exhibitionBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);
        ButterKnife.bind(this);
        tvTilteCommon.setVisibility(View.GONE);
        exhibitionBean= (ExhibitionBean) getIntent().getSerializableExtra("ExhibitionBean");

        tileView = new HDTileView(this);
        initTileView();
    }

    private void initTileView() {

    }
}
