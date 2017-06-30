package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/23.
 */

import android.database.Cursor;

import org.litepal.crud.DataSupport;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/23 11:22
 * 类描述：
 */
public class MapBean extends DataSupport {
    private String map_id;
    private String name;
    private String en_name;
    private String width;
    private String height;

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public static MapBean CursorToModel(Cursor cursor) {
        MapBean mapBean = new MapBean();
        mapBean.setEn_name(cursor.getString(cursor.getColumnIndex("en_name")));
        mapBean.setName(cursor.getString(cursor.getColumnIndex("name")));
        mapBean.setWidth(cursor.getString(cursor.getColumnIndex("width")));
        mapBean.setHeight(cursor.getString(cursor.getColumnIndex("height")));
        mapBean.setMap_id(cursor.getString(cursor.getColumnIndex("map_id")));
        return mapBean;
    }
}
