package com.hengda.smart.xhnyw.d.dbase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;

/**
 * @author lenovo.
 * @explain
 * @time 2017/6/17 9:29.
 */
public class HResDdUtil {

    private SQLiteDatabase db = null;

    /**
     * 单例模式
     */
    private static volatile HResDdUtil instance = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static HResDdUtil getInstance() {
        if (instance == null) {
            synchronized (HResDdUtil.class) {
                if (instance == null) {
                    instance = new HResDdUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 私有构造方法
     */
    private HResDdUtil() {
    }


    /**
     * 打开指定文件路径对应的数据库
     *
     * @param dbFilePath
     */
    public void openDB(String dbFilePath) {
        closeDB();
        try {

            db = SQLiteDatabase.openDatabase(dbFilePath, null, SQLiteDatabase
                    .OPEN_READWRITE);
        } catch (Exception e) {
            e.printStackTrace();
            closeDB();
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDB() {
        if (db != null)
            db = null;
    }

    /**
     * 基础查询方法
     *
     * @param sql 输入SQL语句
     * @return 返回Cursor
     */
    private Cursor queryBase(String sql) {
        Cursor cursor = null;
        try {
            if (db == null)
                openDB(Hd_AppConfig.getDbFilePath());
            cursor = db.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        return cursor;
    }

    /**
     * 根据展厅编号查询展品
     *
     * @param class_id
     * @return
     */
    public Cursor getListExhibit(int class_id) {
        String sql = String.format("SELECT * FROM %s WHERE class_id =%d",
                "exhibitinfo", class_id);
        return queryBase(sql);
    }

    /**
     * 根据floor 查询展品
     *
     * @param map_id
     * @return
     */
    public Cursor getMapExhibit(int map_id, int type) {
        String sql = String.format("SELECT * FROM %s WHERE map_id =%d AND type=%d",
                "exhibitinfo", map_id, type);
        return queryBase(sql);
    }


    /**
     * 根据 exhibit_id 查询展品
     *
     * @param exhibit_id
     * @return
     */
    public Cursor getFootExhibit(String exhibit_id) {
        String sql = String.format("SELECT * FROM %s WHERE type=1 AND exhibit_id = '%s'",
                "exhibitinfo", exhibit_id);
        return queryBase(sql);
    }


    /**
     * 根据floor查询地图大小
     *
     * @param floor
     * @return
     */
    public Cursor getMapInfo(int floor) {
        String sql = "SELECT * FROM mapbean WHERE map_id =" + "'" + floor + "'";
        return queryBase(sql);
    }

    /**
     * 根据自动编号加载资源
     *
     * @param autoNo
     * @return
     */
    public Cursor loadExhibitByAutoNo(int autoNo) {
        String sql = String.format("SELECT * FROM %s WHERE autonum =%d", "exhibitinfo", autoNo);
        return queryBase(sql);
    }

    /**
     * 根据自动编号查找设备位置
     *
     * @param autoNo
     * @return
     */
    public Cursor loadDeviceByAutoNo(int autoNo, int type) {
        String sql = String.format("SELECT * FROM %s WHERE autonum =%d AND type =%d", "exhibitinfo", autoNo, type);
        return queryBase(sql);
    }


    public Cursor loadExhibitionById(String fileNum) {
        String sql = String.format("SELECT * FROM %s WHERE type = 1 AND exhibit_id ='%s'", "exhibitinfo", fileNum);
        return queryBase(sql);
    }

    //删除数据库资源
    public static void deleteTable(SQLiteDatabase db, String tableName) {
        db.delete(tableName, null, null);
        db.execSQL("update sqlite_sequence set seq='0' where name='" + tableName + "'");
    }


    /**
     * @param autonum
     * @param type
     * @return
     */
    public Cursor getCardPositon(int autonum, int type) {
        String sql = String.format("SELECT * FROM %s WHERE autonum = %d AND  type= %d",
                "exhibitinfo", autonum, type);
        return queryBase(sql);
    }

}
