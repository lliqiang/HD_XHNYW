package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.LCommonAdapter;
import com.hengda.smart.xhnyw.d.adapter.ViewHolder;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HBriteDatabase;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.ExhibitionBean;
import com.hengda.smart.xhnyw.d.tools.RxBusUtil;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.back_search)
    ImageView backSearch;
    @Bind(R.id.edit_search)
    EditText editSearch;
    @Bind(R.id.rl_search)
    RelativeLayout rlSearch;
    @Bind(R.id.ll_bathroom_search)
    LinearLayout llBathroomSearch;
    @Bind(R.id.ll_elevator_search)
    LinearLayout llElevatorSearch;
    @Bind(R.id.ll_floor_search)
    LinearLayout llFloorSearch;
    @Bind(R.id.ll_stairs_search)
    LinearLayout llStairsSearch;
    @Bind(R.id.ll_shop_search)
    LinearLayout llShopSearch;
    @Bind(R.id.activity_search)
    RelativeLayout activitySearch;
    Subscription subscription;
    @Bind(R.id.ll_device_search)
    LinearLayout llDeviceSearch;
    @Bind(R.id.lv_search)
    ListView lvSearch;
    private LCommonAdapter<ExhibitInfo> adapter;
    String path;
    private Intent intent;
    private int floor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        floor = getIntent().getIntExtra("floor", floor);
        searchLitner();
        initListner();
        showView();

    }

    private void showView() {
        adapter = new LCommonAdapter<ExhibitInfo>(this, R.layout.item_search_layout) {
            @Override
            public void convert(ViewHolder holder, ExhibitInfo exhibitionBean) {
                if (Hd_AppConfig.getLanguage().equals("CHINESE")) {

                    holder.setText(R.id.tv_search, exhibitionBean.getName());
                } else {
                    if (!TextUtils.isEmpty(exhibitionBean.getEn_name())){
                        holder.setText(R.id.tv_search, exhibitionBean.getEn_name());
                    }
                }
                holder.getView(R.id.iv_loc_search).setOnClickListener(v -> {
                    Intent intent = new Intent(SearchActivity.this, DeviceActivity.class);
                    intent.putExtra("ExhibitionBean", exhibitionBean);
                    startActivity(intent);
                });
                path = Hd_AppConfig.getExhibitImgPath(exhibitionBean.getExhibit_id(), "map_icon2");
                Glide.with(SearchActivity.this).load(path).into(((ImageView) holder.getView(R.id.iv_search)));
            }
        };
        lvSearch.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initListner() {
        backSearch.setOnClickListener(v -> {
            //此方法只是关闭软键盘
            hintKbTwo();
            finish();
        });
        llBathroomSearch.setOnClickListener(this);
        llElevatorSearch.setOnClickListener(this);
        llFloorSearch.setOnClickListener(this);
        llShopSearch.setOnClickListener(this);
        llStairsSearch.setOnClickListener(this);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, PlayActivity.class);
                intent.putExtra("ExhibitionBean", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    /**
     * EditText的监听
     */
    private void searchLitner() {
        RxTextView.textChangeEvents(editSearch)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TextViewTextChangeEvent>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        String changedMessage = textViewTextChangeEvent.text().toString().trim();
                        if (TextUtils.isEmpty(changedMessage)) {
                            llDeviceSearch.setVisibility(View.VISIBLE);
                            lvSearch.setVisibility(View.GONE);
                        } else {
                            llDeviceSearch.setVisibility(View.GONE);
                            lvSearch.setVisibility(View.VISIBLE);
                            loadExhibitByInput();
                        }
                    }
                });
    }

    /**
     * 根据输入加载展品
     */
    private void loadExhibitByInput() {
        String table = "exhibitinfo";
        String sql;
        if (Hd_AppConfig.getLanguage().equals("CHINESE")) {
            sql = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append("exhibitinfo")
                    .append(" WHERE type=1 AND name LIKE '%")
                    .append(editSearch.getText())
                    .append("%'").toString();
        } else {
            sql = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append("exhibitinfo")
                    .append(" WHERE type=1 AND en_name LIKE '%")
                    .append(editSearch.getText())
                    .append("%'").toString();
        }
        doSubscribe(table, sql);
    }

    /**
     * adapter订阅查询操作
     *
     * @param table
     * @param sql
     */
    private void doSubscribe(String table, String sql) {
        unsubscribe();
        subscription = HBriteDatabase.getInstance()
                .getDb()
                .createQuery(table, sql, new String[]{})
                .mapToList(new Func1<Cursor, ExhibitInfo>() {
                    @Override
                    public ExhibitInfo call(Cursor cursor) {
                        return ExhibitInfo.CursorToModel(cursor);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ExhibitInfo>>() {
                    @Override
                    public void call(List<ExhibitInfo> exhibitInfos) {

                        adapter.call(exhibitInfos);
                    }
                });
    }

    /**
     * 取消订阅，避免内存泄漏
     */
    private void unsubscribe() {
        RxBusUtil.unsubscribe(subscription);
    }

    @Override
    public void onClick(View v) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        intent = new Intent(SearchActivity.this, DeviceActivity.class);
        switch (v.getId()) {
            case R.id.ll_bathroom_search:
                exhibitInfo = getTypeData(floor, 3);
                break;
            case R.id.ll_elevator_search:
                exhibitInfo = getTypeData(floor, 4);
                break;
            case R.id.ll_floor_search:
                exhibitInfo = getTypeData(floor, 5);
                break;
            case R.id.ll_stairs_search:
                exhibitInfo = getTypeData(floor, 6);
                break;
            case R.id.ll_shop_search:
                exhibitInfo = getTypeData(floor, 7);
                break;
        }
        if (exhibitInfo != null && !TextUtils.isEmpty(exhibitInfo.getExhibit_id())) {

            intent.putExtra("ExhibitionBean", exhibitInfo);
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.no_device, Toast.LENGTH_SHORT).show();
        }
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

    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(SearchActivity.this.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
