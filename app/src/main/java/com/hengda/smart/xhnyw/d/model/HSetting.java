package com.hengda.smart.xhnyw.d.model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.LCommonAdapter;
import com.hengda.smart.xhnyw.d.adapter.ViewHolder;
import com.hengda.smart.xhnyw.d.app.HdConstant;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.tools.RxBus;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者：Tailyou
 * 时间：2016/1/14 16:45
 * 邮箱：tailyou@163.com
 * 描述：设置Dialog中显示的CustomView，根据设置的类型返回对应的ListView
 */
public class HSetting {
    public static final int AUTO_FLAG = 1;
    public static final int AUTO_MODE = 2;
    public static final int RECEIVE_NO_MODE = 3;
    public static final int STC_MODE = 4;
    public static final int SCREEN_MODE = 5;
    public static final int POWER_MODE = 6;
    public static final int SMART_SERVICE = 7;
    public LCommonAdapter<SetItem> adapter = null;

    private static volatile HSetting instance = null;

    public static HSetting getInstance() {
        if (instance == null) {
            synchronized (HSetting.class) {
                if (instance == null) {
                    instance = new HSetting();
                }
            }
        }
        return instance;
    }

    private HSetting() {
    }

    /**
     * 根据设置类型返回对应的ListView
     *
     * @param context
     * @param setType
     * @return
     * @return
     */
    public ListView getSetListView(Context context, final int setType) {
        ListView listView = new ListView(context);
        List<SetItem> datas = initSetDatas(setType);
        adapter = new LCommonAdapter<SetItem>(context, R.layout.item_setting_i, datas) {
            @Override
            public void convert(ViewHolder holder, SetItem setItem) {
                holder.setText(R.id.txtName, setItem.getName());
                switch (setType) {
                    case AUTO_FLAG:
                        renderImageView(holder, setItem, Hd_AppConfig.getAutoFlag());
                        break;
                    case AUTO_MODE:
                        renderImageView(holder, setItem, Hd_AppConfig.getAutoMode());
                        break;
                    case RECEIVE_NO_MODE:
                        renderImageView(holder, setItem, Hd_AppConfig.getReceiveNoMode());
                        break;
                    case STC_MODE:
                        renderImageView(holder, setItem, Hd_AppConfig.getSTCMode());
                        break;
                    case SCREEN_MODE:
                        renderImageView(holder, setItem, Hd_AppConfig.getScreenMode());
                        break;
                    case POWER_MODE:
                        renderImageView(holder, setItem, Hd_AppConfig.getPowerMode());
                        break;
                    case SMART_SERVICE:
                        renderImageView(holder, setItem, Hd_AppConfig.getSmartService());
                        break;
                }
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SetItem setItem = (SetItem) parent.getItemAtPosition(position);
                switch (setType) {
                    case AUTO_FLAG:
                        Hd_AppConfig.setAutoFlag(setItem.getCode());
                        RxBus.getDefault().post(setItem);
                        break;
                    case AUTO_MODE:
                        Hd_AppConfig.setAutoMode(setItem.getCode());
                        break;
                    case RECEIVE_NO_MODE:
                        Hd_AppConfig.setReceiveNoMode(setItem.getCode());
                        break;
                    case STC_MODE:
                        Hd_AppConfig.setSTCMode(setItem.getCode());
                        break;
                    case SCREEN_MODE:
                        Hd_AppConfig.setScreenMode(setItem.getCode());
                        break;
                    case POWER_MODE:
                        Hd_AppConfig.setPowerMode(setItem.getCode());
                        Hd_AppConfig.setPowerPermi(0);
                        break;
                    case SMART_SERVICE:
                        Hd_AppConfig.setSmartService(setItem.getCode());
                        break;
                }
                adapter.notifyDataSetChanged();
            }
        });
        return listView;
    }

    public void destroyAdapter() {
        if (adapter != null) {
            adapter = null;
        }
    }


    /**
     * 初始化设置数据
     *
     * @param setType
     * @return
     */
    private List<SetItem> initSetDatas(int setType) {
        List<SetItem> datas = new ArrayList<>();
        switch (setType) {
            case AUTO_FLAG:
                datas.add(new SetItem("关闭", 0));
                datas.add(new SetItem("开启", 1));
                break;
            case AUTO_MODE:
                datas.add(new SetItem("隔一讲解", 0));
                datas.add(new SetItem("连续讲解", 1));
                break;
            case RECEIVE_NO_MODE:
                datas.add(new SetItem("蓝牙感应", 0));
                datas.add(new SetItem("RFID感应", 1));
                datas.add(new SetItem("混合感应", 2));
                break;
            case STC_MODE:
                datas.add(new SetItem("间接报警", 0));
                datas.add(new SetItem("直接报警", 1));
                break;
            case SCREEN_MODE:
                datas.add(new SetItem("关闭", 0));
                datas.add(new SetItem("开启", 1));
                break;
            case POWER_MODE:
                datas.add(new SetItem("禁止关机", 0));
                datas.add(new SetItem("允许关机", 1));
                break;
            case SMART_SERVICE:
                datas.add(new SetItem("关闭", 0));
                datas.add(new SetItem("开启", 1));
                break;
        }
        return datas;
    }

    /**
     * 渲染设置ListView中的ImageView
     *
     * @param helper
     * @param item
     * @param mode
     */
    private void renderImageView(ViewHolder helper, SetItem item, int mode) {
        if (item.getCode() == mode) {
            helper.setImageResource(R.id.imgSelectStatus, R.mipmap.selected);

        } else {
            helper.setImageResource(R.id.imgSelectStatus, R.mipmap.unselected);

        }
    }

    /**
     * 设置实体
     */
    public class SetItem {
        private String name;
        private int code;

        public SetItem(String name, int code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }


        public int getCode() {
            return code;
        }

    }

}
