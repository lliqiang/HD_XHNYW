package com.hengda.smart.xhnyw.d.ui.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.SystemMessageListAdapter;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.http.CustomApiResult;
import com.hengda.smart.xhnyw.d.model.SystemMessageBean;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.List;

import butterknife.Bind;

public class SystemMessageActivity extends MineBaseActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    private SystemMessageListAdapter adapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        adapter = new SystemMessageListAdapter(R.layout.item_recycler_system_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                SystemMessageBean.SystemMessageVO dataBean = (SystemMessageBean.SystemMessageVO) adapter.getData().get(position);
                showContentDialog(dataBean.getTitle(), dataBean.getContent());
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        addSubscription(EasyHttp.get("index.php?g=mapi&m=Resource&a=info_list")
                .params("deviceno", Hd_AppConfig.getDeviceNo())
                .params("limit1", "0")
                .params("limit2", "999999")
                .execute(new CallBackProxy<CustomApiResult<List<SystemMessageBean>>, List<SystemMessageBean>>(new SimpleCallBack<List<SystemMessageBean>>() {
                    @Override
                    public void onError(ApiException e) {
                        proceedException(e);
                    }

                    @Override
                    public void onSuccess(List<SystemMessageBean> response) {
                        if (response != null && !response.isEmpty()) {
                            adapter.setNewData(SystemMessageBean.transformList(response));
                        }
                    }
                }) {
                })

        );
    }

    /**
     * 弹出对话框
     * @param title    标题(现已无用)
     * @param content  内容现为网页url
     */
    private void showContentDialog(String title, String content) {
        View customView = View.inflate(this, R.layout.dialog_mine_system_message, null);
        WebView webContent = (WebView) customView.findViewById(R.id.web_view);
        webContent.setBackgroundColor(Color.TRANSPARENT);
        webContent.setOverScrollMode(View.OVER_SCROLL_NEVER);
        webContent.loadUrl(content);
        ImageView ivClose = (ImageView) customView.findViewById(R.id.iv_close);
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.mine_common_dialog)
                .setView(customView)
                .create();
        ivClose.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(960, 480);
        }
    }

    public static void open(Context context) {
        Intent intent = new Intent(context, SystemMessageActivity.class);
        context.startActivity(intent);
    }

}
