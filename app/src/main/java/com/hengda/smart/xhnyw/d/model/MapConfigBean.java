package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/20.
 */

import android.database.Cursor;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/20 10:28
 * 类描述：
 */
public class MapConfigBean {
    private int width;
    private int height;
    private int scale;
    private int unitType;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public static MapConfigBean CursorToModel(Cursor cursor) {
        MapConfigBean mapConfigBean = new MapConfigBean();
        mapConfigBean.setWidth(cursor.getInt(cursor.getColumnIndex("Width")));
        mapConfigBean.setHeight(cursor.getInt(cursor.getColumnIndex("Height")));
        mapConfigBean.setScale(cursor.getInt(cursor.getColumnIndex("Scale")));
        mapConfigBean.setScale(cursor.getInt(cursor.getColumnIndex("UnitType")));
        return mapConfigBean;
    }

    @Override
    public String toString() {
        return "MapConfigBean{" +
                "width=" + width +
                ", height=" + height +
                ", scale=" + scale +
                ", unitType=" + unitType +
                '}';
    }
}
