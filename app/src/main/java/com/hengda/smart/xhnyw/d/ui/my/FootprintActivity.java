package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.FootprintListAdapter;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.ExhibitInfo;
import com.hengda.smart.xhnyw.d.model.FootprintBean;
import com.hengda.smart.xhnyw.d.ui.PlayActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

import butterknife.Bind;

public class FootprintActivity extends MineBaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private FootprintListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        adapter = new FootprintListAdapter(R.layout.item_recycler_footprint);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ExhibitInfo bean = ((FootprintBean.FootprintBeanVO) adapter.getData().get(position)).getDataBean();
                PlayActivity.open(FootprintActivity.this, bean);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Resource&a=my_looked")
                .params("deviceno", Hd_AppConfig.getDeviceNo())
                .params("limit1", "0")
                .params("limit2", "99999")
                .execute(new CallBackProxy<CustomApiResult<List<FootprintBean>>, List<FootprintBean>>(new SimpleCallBack<List<FootprintBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(List<FootprintBean> dataList) {
                        if (dataList != null && !dataList.isEmpty()) {
                            adapter.setNewData(FootprintBean.transformList(dataList));
                        }
                    }
                }) {
                })
        );
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, FootprintActivity.class);
        context.startActivity(intent);
    }

}
