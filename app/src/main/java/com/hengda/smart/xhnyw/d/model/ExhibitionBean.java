package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/17.
 */

import android.database.Cursor;

import java.io.Serializable;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/17 14:35
 * 类描述：
 */
public class ExhibitionBean implements Serializable{
    private int AutoNum;
    private String FileNo;
    private String ByName;
    private int Floor;
    private int x;
    private int y;
    private String ExhibitName;
    private int type=0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAutoNum() {
        return AutoNum;
    }

    public void setAutoNum(int autoNum) {
        AutoNum = autoNum;
    }

    public String getFileNo() {
        return FileNo;
    }

    public void setFileNo(String fileNo) {
        FileNo = fileNo;
    }

    public String getByName() {
        return ByName;
    }

    public void setByName(String byName) {
        ByName = byName;
    }

    public int getFloor() {
        return Floor;
    }

    public void setFloor(int floor) {
        Floor = floor;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getExhibitName() {
        return ExhibitName;
    }

    public void setExhibitName(String exhibitName) {
        ExhibitName = exhibitName;
    }

    public static ExhibitionBean CursorToModel(Cursor cursor) {
        ExhibitionBean exhibitionBean = new ExhibitionBean();
        exhibitionBean.setAutoNum(cursor.getInt(cursor.getColumnIndex("AutoNum")));
        exhibitionBean.setFileNo(cursor.getString(cursor.getColumnIndex("FileNo")));
        exhibitionBean.setByName(cursor.getString(cursor.getColumnIndex("ByName")));
        exhibitionBean.setFloor(cursor.getInt(cursor.getColumnIndex("Floor")));
        exhibitionBean.setX(cursor.getInt(cursor.getColumnIndex("X")));
        exhibitionBean.setY(cursor.getInt(cursor.getColumnIndex("Y")));
        exhibitionBean.setExhibitName(cursor.getString(cursor.getColumnIndex("Exhibition")));
        return exhibitionBean;
    }

    @Override
    public String toString() {
        return "ExhibitionBean{" +
                "AutoNum=" + AutoNum +
                ", FileNo='" + FileNo + '\'' +
                ", ByName='" + ByName + '\'' +
                ", Floor=" + Floor +
                ", x=" + x +
                ", y=" + y +
                ", ExhibitName='" + ExhibitName + '\'' +
                '}';
    }
}
