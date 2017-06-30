package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/23.
 */

import android.database.Cursor;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/23 10:57
 * 类描述：
 */
public class ExhibitInfo extends DataSupport implements Serializable {

    /**
     * exhibit_id : 0003
     * autonum : 103
     * class_id : 7
     * map_id : 1
     * axis_x : 666
     * axis_y : 499
     * road1 : 1
     * road2 : 1
     * type : 1
     * name : 12312
     * en_name :
     */

    private String exhibit_id;
    private String autonum;
    private String class_id;
    private String map_id;
    private String axis_x;
    private String axis_y;
    private String road1;
    private String road2;
    private String type;
    private String name;
    private String en_name;


    public String getExhibit_id() {
        return exhibit_id;
    }

    public void setExhibit_id(String exhibit_id) {
        this.exhibit_id = exhibit_id;
    }

    public String getAutonum() {
        return autonum;
    }

    public void setAutonum(String autonum) {
        this.autonum = autonum;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getMap_id() {
        return map_id;
    }

    public void setMap_id(String map_id) {
        this.map_id = map_id;
    }

    public String getAxis_x() {
        return axis_x;
    }

    public void setAxis_x(String axis_x) {
        this.axis_x = axis_x;
    }

    public String getAxis_y() {
        return axis_y;
    }

    public void setAxis_y(String axis_y) {
        this.axis_y = axis_y;
    }

    public String getRoad1() {
        return road1;
    }

    public void setRoad1(String road1) {
        this.road1 = road1;
    }

    public String getRoad2() {
        return road2;
    }

    public void setRoad2(String road2) {
        this.road2 = road2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public static ExhibitInfo CursorToModel(Cursor cursor) {
        ExhibitInfo exhibitInfo = new ExhibitInfo();
        exhibitInfo.setAutonum(cursor.getString(cursor.getColumnIndex("autonum")));
        exhibitInfo.setAxis_x(cursor.getString(cursor.getColumnIndex("axis_x")));
        exhibitInfo.setAxis_y(cursor.getString(cursor.getColumnIndex("axis_y")));
        exhibitInfo.setClass_id(cursor.getString(cursor.getColumnIndex("class_id")));
        exhibitInfo.setEn_name(cursor.getString(cursor.getColumnIndex("en_name")));
        exhibitInfo.setExhibit_id(cursor.getString(cursor.getColumnIndex("exhibit_id")));
        exhibitInfo.setMap_id(cursor.getString(cursor.getColumnIndex("map_id")));
        exhibitInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
        exhibitInfo.setRoad1(cursor.getString(cursor.getColumnIndex("road1")));
        exhibitInfo.setRoad2(cursor.getString(cursor.getColumnIndex("road2")));
        exhibitInfo.setType(cursor.getString(cursor.getColumnIndex("type")));
        return exhibitInfo;
    }

    @Override
    public String toString() {
        return "ExhibitInfo{" +
                "exhibit_id='" + exhibit_id + '\'' +
                ", autonum='" + autonum + '\'' +
                ", class_id='" + class_id + '\'' +
                ", map_id='" + map_id + '\'' +
                ", axis_x='" + axis_x + '\'' +
                ", axis_y='" + axis_y + '\'' +
                ", road1='" + road1 + '\'' +
                ", road2='" + road2 + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", en_name='" + en_name + '\'' +
                '}';
    }
}
