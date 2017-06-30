package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.ListDetailAdapter;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.Exhibition;
import com.hengda.smart.xhnyw.d.tools.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListDetailActivity extends BaseActivity {

    @Bind(R.id.img_back_common)
    ImageView imgBackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView tvTilteCommon;
    @Bind(R.id.iv_listDetail)
    ImageView ivListDetail;
    @Bind(R.id.vr_listDetail)
    RecyclerView vrListDetail;
    private ListDetailAdapter adapter;
    Exhibition Class_id;
    private List<ExhibitInfo> beanList;
    private int poc=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        ButterKnife.bind(this);
        vrListDetail.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.HORIZONTAL, false));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.edit_text_m_h);
        vrListDetail.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
        beanList = new ArrayList<>();
        Class_id = (Exhibition) getIntent().getSerializableExtra("Class_id");
        adapter = new ListDetailAdapter(R.layout.item_img_layout, beanList, this, ivListDetail);
        vrListDetail.setAdapter(adapter);
        getData(Integer.parseInt(Class_id.getClass_id()));
        if (Hd_AppConfig.getLanguage().equals("CHINESE")) {
            tvTilteCommon.setText(Class_id.getName());
        } else {
            tvTilteCommon.setText(Class_id.getEn_name());
        }
        if (beanList.size() > 0) {
            String path = Hd_AppConfig.getExhibitImgPath(beanList.get(0).getExhibit_id(), "list_img");
            Glide.with(ListDetailActivity.this).load(path).into(ivListDetail);
        }
        ivListDetail.setOnClickListener(v -> {
            Intent intent = new Intent(ListDetailActivity.this, PlayActivity.class);
            intent.putExtra("ExhibitionBean", beanList.get(poc));
            startActivity(intent);
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ExhibitInfo exhibitInfo = (ExhibitInfo) adapter.getItem(position);
                Glide.with(ListDetailActivity.this).load(Hd_AppConfig.getExhibitImgPath(exhibitInfo.getExhibit_id(), "list_img")).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.def_listdetail).into(ivListDetail);
                vrListDetail.scrollToPosition(position);
                poc=position;

            }
        });
        adapter.notifyDataSetChanged();
        imgBackCommon.setOnClickListener(v -> finish());
    }
    /**
     * 获取展厅列表展品
     *
     * @param class_id
     */
    private void getData(int class_id) {
        List<ExhibitInfo> list = new ArrayList<>();

        Cursor cursor = HResDdUtil.getInstance().getListExhibit(class_id);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ExhibitInfo exhibitInfo = ExhibitInfo.CursorToModel(cursor);
                Log.i("exhibitInfo", exhibitInfo.toString());
                list.add(exhibitInfo);
            }
            beanList.clear();
            beanList.addAll(list);
            cursor.close();
        }
    }

}
