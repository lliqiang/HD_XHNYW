package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/23.
 */

import android.database.Cursor;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/23 11:03
 * 类描述：
 */
public class Exhibition extends DataSupport implements Serializable {

    /**
     * name : 一楼展厅
     * en_name : 111
     * class_id : 7
     */

    private String name;
    private String en_name;
    private String class_id;

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

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    @Override
    public String toString() {
        return "Exhibition{" +
                "name='" + name + '\'' +
                ", en_name='" + en_name + '\'' +
                ", class_id='" + class_id + '\'' +
                '}';
    }

    public static Exhibition CursorToModel(Cursor cursor) {
        Exhibition exhibition = new Exhibition();
        exhibition.setName(cursor.getString(cursor.getColumnIndex("name")));
        exhibition.setEn_name(cursor.getString(cursor.getColumnIndex("en_name")));
        exhibition.setClass_id(cursor.getString(cursor.getColumnIndex("class_id")));
        return exhibition;
    }
}
