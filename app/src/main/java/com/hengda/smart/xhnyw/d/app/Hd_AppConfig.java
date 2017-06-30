package com.hengda.smart.xhnyw.d.app;/**
 * Created by lenovo on 2017/6/16.
 */

import android.text.TextUtils;

import com.hengda.smart.xhnyw.d.tools.FileUtils;
import com.hengda.smart.xhnyw.d.tools.SDCardUtil;
import com.hengda.smart.xhnyw.d.tools.SharedPrefUtil;

import java.io.File;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/16 16:39
 * 类描述：
 */
public class Hd_AppConfig {
    private static SharedPrefUtil appConfigShare = new SharedPrefUtil(HdApplication.mContext,
            HdConstant.SHARED_PREF_NAME);

    private static final String DEVICE = "DEVICE";
    public static final String PASSWORD = "PASSWORD";//管理员密码
    public static final String AUTO_FLAG = "AUTO_FLAG";//自动讲解：0关闭，1开启
    public static final String AUTO_MODE = "AUTO_MODE";//讲解方式：0隔一，1连续
    public static final String RECEIVE_NO_MODE = "RECEIVE_NO_MODE";//收号方式：0蓝牙，1RFID，2混合
    public static final String STC_MODE = "STC_MODE";//报警方式：0直接报警，1间接报警
    public static final String SCREEN_MODE = "SCREEN_MODE";//节能模式：0关闭，1开启
    public static final String POWER_MODE = "POWER_MODE";//关机权限：0禁止，1允许
    public static final String POWER_PERMI = "POWER_PERMI";//禁止关机下是否获取到关机权限：0无，1有
    public static final String SMART_SERVICE = "SMART_SERVICE";//智慧服务：0关闭，1开启
    public static final String RSSI = "RSSI";//RSSI门限
    public static final String IP_PORT = "IP_PORT";//服务器IP和端口
    public static final String LANGUAGE = "LANGUAGE";//语种

    public static final String KEY_IS_IN_GROUP = "KEY_IS_IN_GROUP";//是否在群组中
    public static final String KEY_LAST_RECEIVE_NUM = "KEY_LAST_RECEIVE_NUM";//上次收到的信号

    /**
     * 是否再群组之中
     */
    public static void setIsInGroup(Boolean isInGroup) {
        appConfigShare.setPrefBoolean(KEY_IS_IN_GROUP, isInGroup);
    }

    public static Boolean getIsInGroup() {
        return appConfigShare.getPrefBoolean(KEY_IS_IN_GROUP, false);
    }

    /**
     * 设备号
     */
    public static void setDeviceNo(String deviceNo) {
        FileUtils.writeStringToFile(SDCardUtil.getSDCardPath() + "DeviceNo.txt", deviceNo, false);
    }

    public static String getDeviceNo() {
        StringBuilder deviceNo = FileUtils.readStringFromFile(SDCardUtil.getSDCardPath() + "DeviceNo.txt", "UTF-8");
        return TextUtils.isEmpty(deviceNo) ? HdConstant.DEFAULT_DEVICE_NO : deviceNo.toString();
    }

    /**
     * 管理员密码
     */
    public static void setPassword(String password) {
        appConfigShare.setPrefString(PASSWORD, password);
    }

    public static String getPassword() {
        return appConfigShare.getPrefString(PASSWORD, HdConstant.DEFAULT_PWD);
    }

    /**
     * 管理员界面自动讲解开关
     *
     * @param autoFlag
     */
    public static void setAutoFlag(int autoFlag) {
        appConfigShare.setPrefInt(AUTO_FLAG, autoFlag);
    }

    public static int getAutoFlag() {
        return appConfigShare.getPrefInt(AUTO_FLAG, 1);
    }

    /**
     * 自动讲解模式
     *
     * @param autoMode
     */
    public static void setAutoMode(int autoMode) {
        appConfigShare.setPrefInt(AUTO_MODE, autoMode);
    }

    public static int getAutoMode() {
        return appConfigShare.getPrefInt(AUTO_MODE, 0);
    }

    /**
     * 收号模式
     *
     * @param receiveNoMode
     */
    public static void setReceiveNoMode(int receiveNoMode) {
        appConfigShare.setPrefInt(RECEIVE_NO_MODE, receiveNoMode);
    }

    public static int getReceiveNoMode() {
        return appConfigShare.getPrefInt(RECEIVE_NO_MODE, 1);
    }

    /**
     * 报警模式
     */
    public static void setSTCMode(int flag) {
        appConfigShare.setPrefInt(STC_MODE, flag);
    }

    public static int getSTCMode() {
        return appConfigShare.getPrefInt(STC_MODE, 1);
    }

    /**
     * 屏幕模式
     *
     * @return
     */
    public static void setScreenMode(int flag) {
        appConfigShare.setPrefInt(SCREEN_MODE, flag);
    }

    public static int getScreenMode() {
        return appConfigShare.getPrefInt(SCREEN_MODE, 1);
    }

    /**
     * 节能模式
     *
     * @param flag
     */
    public static void setPowerMode(int flag) {
        appConfigShare.setPrefInt(POWER_MODE, flag);
    }

    public static int getPowerMode() {
        return appConfigShare.getPrefInt(POWER_MODE, 1);
    }

    /**
     * 关机权限
     *
     * @param flag
     */
    public static void setPowerPermi(int flag) {
        appConfigShare.setPrefInt(POWER_PERMI, flag);
    }

    public static int getPowerPermi() {
        return appConfigShare.getPrefInt(POWER_PERMI, 0);
    }

    public static void setSmartService(int smartService) {
        appConfigShare.setPrefInt(SMART_SERVICE, smartService);
    }

    public static int getSmartService() {
        return appConfigShare.getPrefInt(SMART_SERVICE, 1);
    }

    /**
     * RSSI
     *
     * @param rssi
     */

    public static void setRssi(int rssi) {
        appConfigShare.setPrefInt(RSSI, rssi);
    }

    public static int getRssi() {
        return appConfigShare.getPrefInt(RSSI, HdConstant.BLE_RSSI_THRESHOLD);
    }

    public static void setDefaultIpPort(String ipPort) {
        appConfigShare.setPrefString(IP_PORT, ipPort);
    }

    public static String getDefaultIpPort() {
        return appConfigShare.getPrefString(IP_PORT, HdConstant.DEFAULT_IP_PORT);
    }

    public static void setLanguage(String language) {
        appConfigShare.setPrefString(LANGUAGE, language);
    }

    public static String getLanguage() {
        return appConfigShare.getPrefString(LANGUAGE, HdConstant.LANG_DEFAULT);
    }

    public static String getLanguageCode() {
        if (TextUtils.equals("CHINESE", getLanguage())) {
            return "1";
        } else {
            return "2";
        }
    }


    //    获取默认文件存储目录
    public static String getDefaultFileDir() {
        return SDCardUtil.getSDCardPath() + "Xhnyw_Res/";
    }

    //获取地图的路径
    public static String getMapPath(int floor) {
        return getDefaultFileDir() + "map/" + getLanguage() + "/" + floor;

    }

    //获取展品图片资源路径
    public static String getExhibitImgPath(String fileNo, String type) {
        return getDefaultFileDir() + "exhibit/" + fileNo + "/" + "image/" + fileNo + "_" + type + ".png";
    }

    //获取展品其他资源路径
    public static String getExhibitResPath(String fileNo){
        return getDefaultFileDir()+"exhibit/"+fileNo+"/"+getLanguage()+"/"+fileNo;
    }


    //    获取录音文件存储目录
    public static String getRecordFileDir() {
        return getDefaultFileDir() + "chatRecord/";
    }

    //    获取数据库文件路径
    public static String getDbFilePath() {

        return getDefaultFileDir() + HdConstant.DB_FILE_NAME;
    }

    //判断数据库是否存在
    public static boolean isDbExist() {
        File file = new File(getDefaultFileDir() + HdConstant.DB_FILE_NAME);
        return file.exists();
    }

    public static void setLastReceiveAutoNum(String autoNum) {
        appConfigShare.setPrefString(KEY_LAST_RECEIVE_NUM, autoNum);
    }

    public static String getLastReceiveAutoNum() {
       return appConfigShare.getPrefString(KEY_LAST_RECEIVE_NUM,"");
    }
}
