package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.adapter.LCommonAdapter;
import com.hengda.smart.xhnyw.d.adapter.ViewHolder;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.app.HdConstant;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.callback.CheckCallback;
import com.hengda.smart.xhnyw.d.model.CheckResponse;
import com.hengda.smart.xhnyw.d.model.DeviceNoEvent;
import com.hengda.smart.xhnyw.d.model.HSetting;
import com.hengda.smart.xhnyw.d.model.SettingItem;
import com.hengda.smart.xhnyw.d.tools.AppUtil;
import com.hengda.smart.xhnyw.d.tools.DataManager;
import com.hengda.smart.xhnyw.d.tools.FileUtils;
import com.hengda.smart.xhnyw.d.tools.RxBus;
import com.hengda.smart.xhnyw.d.view.DialogCenter;
import com.hengda.smart.xhnyw.d.view.DialogClickListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * @explain 管理员设置界面
 * @author lenovo.
 * @time 2017/6/17 13:48.
 */
public class SettingActivity extends CheckUpdateActivity {
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.listView)
    ListView listView;
    String[] settingNames;
    List<SettingItem> settingItems = new ArrayList<>();
    LCommonAdapter<SettingItem> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        initData();
        initUI();

    }

    /**
     * 初始化设置数据
     */
    private void initData() {
        settingNames = getResources().getStringArray(R.array.settings);
        int[] iconId = {R.mipmap.icon_set_1, R.mipmap.icon_set_2,
                R.mipmap.icon_set_3, R.mipmap.icon_set_4, R.mipmap.icon_set_5,
                R.mipmap.icon_set_6, R.mipmap.icon_set_7, R.mipmap.icon_set_8,
                R.mipmap.icon_set_9, R.mipmap.icon_set_10, R.mipmap.icon_set_11,
                R.mipmap.icon_set_12, R.mipmap.icon_set_13, R.mipmap.icon_set_14,
                R.mipmap.icon_set_15, R.mipmap.icon_set_16, R.mipmap.icon_set_17,
                R.mipmap.icon_set_17, R.mipmap.icon_set_17};
        for (int i = 0; i < iconId.length; i++) {
            settingItems.add(new SettingItem(settingNames[i], iconId[i]));
        }
    }


    private void initUI() {

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new LCommonAdapter<SettingItem>(SettingActivity.this, R.layout.item_setting_o,
                settingItems) {
            @Override
            public void convert(ViewHolder holder, SettingItem settingItem) {
                holder.setText(R.id.txtName, settingItem.getName());
                holder.setImageResource(R.id.imgIcon, settingItem.getImgId());
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingItem settingItem = (SettingItem) parent.getItemAtPosition(position);
                switch (position) {
                    case 0://自动讲解
                        SettingActivity.this.showDialogBySetType(HSetting.AUTO_FLAG, settingItem.getName());
                        break;
                    case 1://智慧服务
                        SettingActivity.this.showDialogBySetType(HSetting.SMART_SERVICE, settingItem.getName());
                        break;
                    case 2://节能模式
                        SettingActivity.this.showDialogBySetType(HSetting.SCREEN_MODE, settingItem.getName());
                        break;
                    case 3://公众关机
                        SettingActivity.this.showDialogBySetType(HSetting.POWER_MODE, settingItem.getName());
                        break;
                    case 4://自动模式
                        SettingActivity.this.showDialogBySetType(HSetting.AUTO_MODE, settingItem.getName());
                        break;
                    case 5://感应模式
                        SettingActivity.this.showDialogBySetType(HSetting.RECEIVE_NO_MODE, settingItem.getName());
                        break;
                    case 6://预警模式
                        showDialogBySetType(HSetting.STC_MODE, settingItem.getName());
                        break;
                    case 7://网络设置
                        SettingActivity.this.startActivity(new Intent(Settings.ACTION_SETTINGS));
                        break;
                    case 8://服务器设置
                        SettingActivity.this.editIpPort(settingItem);
                        break;
                    case 9://RSSI设置
                        SettingActivity.this.editDefaultRssi(settingItem);
                        break;
                    case 10://工程模式
                        //TODO 工程模式
                        openActivity(SettingActivity.this, RfidTextActivity.class);
                        break;
                    case 11://测试平台
                        SettingActivity.this.openTestPlatform();
                        break;
                    case 12://应用更新
                        checkNewVersion(new CheckCallback() {
                            @Override
                            public void hasNewVersion(CheckResponse checkResponse) {
                                showHasNewVersionDialog(checkResponse);
                            }

                            @Override
                            public void isAlreadyLatestVersion() {
                                showVersionInfoDialog();
                            }
                        });
                        break;
                    case 13://清除资源
                        SettingActivity.this.clearRes();
                        break;
                    case 14://管理员密码
                        SettingActivity.this.editPwd(settingItem);
                        break;
                    case 15://设备编号
                        SettingActivity.this.editDeviceNo(settingItem);
                        break;
                    case 16://版本信息
                        SettingActivity.this.showVersionInfoDialog();
                        break;

                    case 17://数据库更新

                        getDBDataAndInsertToDB();
                        break;
                    case 18://资源更新
                        checkRes();
                        break;
                    case 19://地图资源更新
                        checkRes();
                        break;
                }
            }
        });
    }


    /**
     * 编辑默认Rssi门限值
     *
     * @param settingItem
     */
    private void editDefaultRssi(SettingItem settingItem) {
        View rootRssi = View.inflate(SettingActivity.this, R.layout
                .dialog_custom_view_edt, null);
        final EditText edtRssi = (EditText) rootRssi.findViewById(R.id.editText);

        edtRssi.setHint("请输入RSSI强度值，30-100");
        Selection.setSelection(edtRssi.getText(), edtRssi.getText().length());
        DialogCenter.showDialog(SettingActivity.this, rootRssi, new
                DialogClickListener() {
                    @Override
                    public void p() {
                        String rssi = edtRssi.getText().toString();
                        if (isValidRssi(rssi)) {
                            Hd_AppConfig.setRssi(Integer.valueOf(rssi));
                        } else {
                            Toast.makeText(SettingActivity.this, getString(R.string.fromat), Toast.LENGTH_SHORT).show();

                        }
                        DialogCenter.hideDialog();
                    }

                    @Override
                    public void n() {
                    }
                }, new String[]{settingItem.getName(), "关闭"});
    }

    /**
     * 修改服务器IP + PORT
     *
     * @param settingItem
     */
    private void editIpPort(SettingItem settingItem) {
        View rootIp = View.inflate(SettingActivity.this, R.layout
                .dialog_custom_view_edt, null);
        final EditText edtIp = (EditText) rootIp.findViewById(R.id.editText);
        edtIp.setText(Hd_AppConfig.getDefaultIpPort());
        Selection.setSelection(edtIp.getText(), edtIp.getText().length());
        DialogCenter.showDialog(SettingActivity.this, rootIp, new
                DialogClickListener() {
                    @Override
                    public void p() {
                        String ipPort = edtIp.getText().toString();
                        if (TextUtils.isEmpty(ipPort)) {
//                            Toast.makeText(SettingActivity.this, getString(R.string.ip_nospace), Toast.LENGTH_SHORT).show();
                        } else {
                            DialogCenter.hideDialog();
                            if (!TextUtils.equals(Hd_AppConfig.getDefaultIpPort(), ipPort)) {
                                Hd_AppConfig.setDefaultIpPort(ipPort);
                                Log.i("DefIp", "DefIp----------------" + Hd_AppConfig.getDefaultIpPort());
                                // TODO: 2017/6/16
//                                RxBus.getDefault().post(new DeviceNoEvent(0));
                            }
                        }
                    }
                    @Override
                    public void n() {
                        DialogCenter.hideDialog();
                    }
                }, new String[]{settingItem.getName(), "确定",
                "关闭"});
    }

    /**
     * 根据设置类型显示Dialog
     *
     * @param setType
     * @param title
     */
    private void showDialogBySetType(int setType, String title) {
        DialogCenter.showDialog(SettingActivity.this, HSetting.getInstance().getSetListView
                (SettingActivity.this, setType), new
                DialogClickListener() {
                    @Override
                    public void p() {
                        HSetting.getInstance().destroyAdapter();
                        DialogCenter.hideDialog();
                    }

                    @Override
                    public void n() {

                    }
                }, new String[]{title, "确定"});
    }

    /**
     * 判断是否是有效的Rssi（强度值）
     *
     * @param str
     * @return
     */
    private boolean isValidRssi(String str) {
        boolean is = false;
        try {
            int i = Integer.parseInt(str);
            if (i > 100 || i < 30) {
                is = false;
            } else {
                is = true;
            }
        } catch (Exception e) {
        }

        return is;
    }

    /**
     * 打开测试平台
     */
    private void openTestPlatform() {
        if (AppUtil.checkInstallByPackageName(SettingActivity.this, HdConstant
                .TEST_APP_PACKAGE_NAME)) {
            Intent intent = new Intent();
            intent.setClassName(HdConstant.TEST_APP_PACKAGE_NAME,
                    HdConstant.TEST_APP_LAUNCHER_ACT);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.install_text), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 清除资源
     */
    private void clearRes() {
        DialogCenter.showDialog(SettingActivity.this, new DialogClickListener() {
            @Override
            public void p() {
                DialogCenter.hideDialog();
                DialogCenter.showProgressDialog(SettingActivity.this, "请稍后...", false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FileUtils.deleteFile(Hd_AppConfig.getDefaultFileDir());
                        DataManager.clearAllCache(SettingActivity.this);
                        DialogCenter.hideProgressDialog();
                        Toast.makeText(SettingActivity.this, getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                    }
                }, 2000L);
            }

            @Override
            public void n() {
                DialogCenter.hideDialog();
            }
        }, new String[]{"温馨提示", "确定要删除导览资源吗？", "删除", "取消"});
    }

    /**
     * 修改管理员密码
     *
     * @param settingItem
     */
    private void editPwd(SettingItem settingItem) {
        View rootPwd = View.inflate(SettingActivity.this, R.layout
                .dialog_custom_view_edt, null);
        final EditText edtPwd = (EditText) rootPwd.findViewById(R.id.editText);
        edtPwd.setText(Hd_AppConfig.getPassword());
        Selection.setSelection(edtPwd.getText(), edtPwd.getText().length());
        DialogCenter.showDialog(SettingActivity.this, rootPwd, new
                DialogClickListener() {
                    @Override
                    public void p() {
                        String password = edtPwd.getText().toString();
                        if (isPassword(password)) {
                            Hd_AppConfig.setPassword(password);
                            DialogCenter.hideDialog();
                        } else {
                            Toast.makeText(SettingActivity.this, "格式错误，请输入1-8位数字！", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void n() {
                    }
                }, new String[]{settingItem.getName(), "确定"});
    }

    /**
     * 判断是否是有效密码（格式）
     *
     * @param str
     * @return
     */
    private boolean isPassword(String str) {
        Pattern pattern = Pattern.compile("^\\d{1,8}$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 修改设备号
     *
     * @param settingItem
     */
    private void editDeviceNo(SettingItem settingItem) {
        View rootDeviceNo = View.inflate(SettingActivity.this, R.layout
                .dialog_custom_view_edt, null);
        final EditText edtDeviceNo = (EditText) rootDeviceNo.findViewById(R.id.editText);
        edtDeviceNo.setText(Hd_AppConfig.getDeviceNo());
        Selection.setSelection(edtDeviceNo.getText(), edtDeviceNo.getText().length());
        DialogCenter.showDialog(SettingActivity.this, rootDeviceNo, new
                DialogClickListener() {
                    @Override
                    public void p() {
                        String deviceNo = edtDeviceNo.getText().toString();
                        if (TextUtils.isEmpty(deviceNo)) {
                            Toast.makeText(SettingActivity.this, "请输入设备编号！", Toast.LENGTH_SHORT).show();

                        } else {
                            if (!TextUtils.equals(Hd_AppConfig.getDeviceNo(),
                                    deviceNo)) {
                                Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                Hd_AppConfig.setDeviceNo(deviceNo);
                                RxBus.getDefault().post(new DeviceNoEvent(0));
                            }
                            DialogCenter.hideDialog();
                        }
                    }

                    @Override
                    public void n() {
                        DialogCenter.hideDialog();
                    }
                }, new String[]{settingItem.getName(), "确定",
                "关闭"});
    }


}
