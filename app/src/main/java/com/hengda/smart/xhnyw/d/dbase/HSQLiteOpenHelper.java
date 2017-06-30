package com.hengda.smart.xhnyw.d.dbase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.hengda.smart.xhnyw.d.app.HdApplication;
import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

public class HSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;

    public HSQLiteOpenHelper() {
        super(HdApplication.mContext, Hd_AppConfig.getDbFilePath(), null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
