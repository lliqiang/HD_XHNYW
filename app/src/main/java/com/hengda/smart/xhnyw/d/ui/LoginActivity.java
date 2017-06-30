package com.hengda.smart.xhnyw.d.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hengda.smart.xhnyw.d.R;
import com.hengda.smart.xhnyw.d.app.HdConstant;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * @explain 管理员登录界面
 * @author lenovo.
 * @time 2017/6/17 13:45.
 */
public class LoginActivity extends BaseActivity {


    String pwd = "";
    String fromAction;
    @Bind(R.id.input_admin)
    TextView inputAdmin;
    @Bind(R.id.one_admin)
    ImageView oneAdmin;
    @Bind(R.id.two_admin)
    ImageView twoAdmin;
    @Bind(R.id.three_admin)
    ImageView threeAdmin;
    @Bind(R.id.four_admin)
    ImageView fourAdmin;
    @Bind(R.id.five_admin)
    ImageView fiveAdmin;
    @Bind(R.id.backspace_admin)
    ImageView backspaceAdmin;
    @Bind(R.id.six_admin)
    ImageView sixAdmin;
    @Bind(R.id.seven_admin)
    ImageView sevenAdmin;
    @Bind(R.id.eight_admin)
    ImageView eightAdmin;
    @Bind(R.id.nine_admin)
    ImageView nineAdmin;
    @Bind(R.id.zero_admin)
    ImageView zeroAdmin;
    @Bind(R.id.sure_yes_admin)
    ImageView sureYesAdmin;
    @Bind(R.id.enter_digital_admin)
    ImageView enterDigitalAdmin;
    @Bind(R.id.activity_digital)
    LinearLayout activityDigital;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);
        initUiDataEvent();
    }

    public void initUiDataEvent() {
        fromAction = getIntent().getStringExtra("fromAction");
        if (TextUtils.equals(fromAction, "ADMIN")) {
            inputAdmin.setText("请输入密码");
        } else if (TextUtils.equals(fromAction, "POWER")) {
            inputAdmin.setText("请输入密码");
        }
    }

    private void clickNum(int num) {
        if (pwd.length() < 8) {
            pwd += num;
            inputAdmin.setText(pwd);
        } else {
            clearInput();
        }
    }

    private void clickClear() {
        if (pwd.length() > 1) {
            pwd = pwd.substring(0, pwd.length() - 1);
            inputAdmin.setText(pwd);
        } else {
            clearInput();
        }
    }

    private void clearInput() {
        pwd = "";
        inputAdmin.setText("请输入密码");
    }

    private void acquireSetPermission() {
        if (TextUtils.equals(pwd, Hd_AppConfig.getPassword()) || TextUtils.equals(pwd, HdConstant.LOGIN_PWD)) {
            startActivity(new Intent(LoginActivity.this, SettingActivity.class));
            finish();
        } else {
            Toast.makeText(this, "输入密码错误", Toast.LENGTH_SHORT).show();
            clearInput();
        }
    }

    @OnClick({R.id.one_admin, R.id.two_admin, R.id.three_admin, R.id.four_admin, R.id.five_admin, R.id.backspace_admin, R.id.six_admin, R.id.seven_admin, R.id.eight_admin, R.id.nine_admin, R.id.zero_admin, R.id.sure_yes_admin, R.id.enter_digital_admin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one_admin:
                clickNum(1);
                break;
            case R.id.two_admin:
                clickNum(2);
                break;
            case R.id.three_admin:
                clickNum(3);
                break;
            case R.id.four_admin:
                clickNum(4);
                break;
            case R.id.five_admin:
                clickNum(5);
                break;
            case R.id.backspace_admin:
                clickClear();
                break;
            case R.id.six_admin:
                clickNum(6);
                break;
            case R.id.seven_admin:
                clickNum(7);
                break;
            case R.id.eight_admin:
                clickNum(8);
                break;
            case R.id.nine_admin:
                clickNum(9);
                break;
            case R.id.zero_admin:
                clickNum(0);
                break;
            case R.id.sure_yes_admin:
                clickOk();
                break;
            case R.id.enter_digital_admin:
                finish();
                break;
        }
    }

    private void clickOk() {
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "输入密码为空", Toast.LENGTH_SHORT).show();

        } else {
            switch (fromAction) {
                case "ADMIN":
                    acquireSetPermission();
                    break;
                case "POWER":
                    acquirePowerPermission();
                    break;
            }
        }
    }

    private void acquirePowerPermission() {
        if (TextUtils.equals(pwd, Hd_AppConfig.getPassword()) || TextUtils.equals(pwd, HdConstant.LOGIN_PWD)) {
            Hd_AppConfig.setPowerMode(1);
            Toast.makeText(this,"已获取关机权限，请按关机键进行关机", Toast.LENGTH_SHORT).show();

            finish();
        } else {
            Hd_AppConfig.setPowerMode(0);
            Toast.makeText(this,"密码错误", Toast.LENGTH_SHORT).show();

            clearInput();
        }
    }


}
