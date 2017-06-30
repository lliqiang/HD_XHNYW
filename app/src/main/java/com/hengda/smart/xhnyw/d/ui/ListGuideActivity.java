package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.ListGuideAdapter;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HBriteDatabase;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.Exhibition;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author lenovo.
 * @explain 列表导览界面
 * @time 2017/6/17 13:46.
 */
public class ListGuideActivity extends BaseActivity {

    @Bind(R.id.img_back_common)
    ImageView iv_BackCommon;
    @Bind(R.id.tv_tilte_common)
    TextView txt_TilteCommon;
    @Bind(R.id.transpager_list)
    DiscreteScrollView transpagerList;
    @Bind(R.id.btn_enter_list)
    Button btn_EnterList;
    private List<Exhibition> beanList;
    private ListGuideAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_guide);
        ButterKnife.bind(this);
        beanList = new ArrayList<>();
        iv_BackCommon.setOnClickListener(v -> finish());
        StringBuilder sb = new StringBuilder();
        String table = "exhibition";
        sb.append("SELECT * FROM ").append(table);
        HBriteDatabase.getInstance()
                .getDb()
                .createQuery(table, sb.toString(), new String[]{})
                .mapToList(cursor -> Exhibition.CursorToModel(cursor))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Exhibition>>() {
                    @Override
                    public void call(List<Exhibition> exhibitionBeen) {
                        beanList.clear();
                        beanList.addAll(exhibitionBeen);
                        adapter = new ListGuideAdapter(R.layout.item_list_guide, beanList, ListGuideActivity.this);
                        transpagerList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
        transpagerList.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.95f)
                .build());
        transpagerList.setCurrentItemChangeListener(new DiscreteScrollView.CurrentItemChangeListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                btn_EnterList.setOnClickListener(v -> {
                    Intent intent = new Intent(ListGuideActivity.this, ListDetailActivity.class);
                    intent.putExtra("Class_id", beanList.get(adapterPosition));
                    startActivity(intent);
                });

            }
        });

    }
}
