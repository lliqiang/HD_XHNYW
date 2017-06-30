package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/23.
 */

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/23 10:54
 * 类描述：
 */
public class DataBaseBean extends DataSupport {

    /**
     * exhibit_id : 0001
     * autonum : 101
     * class_id : 7
     * map_id : 1
     * road1 : 1
     * road2 : 0
     * name : erfwe
     * en_name : 345
     */

    private List<ExhibitInfo> exhibit_info;
    /**
     * name : 一楼展厅
     * en_name : 111
     * class_id : 7
     */

    private List<Exhibition> exhibition;
    /**
     * map_id : 1
     * name : 主体楼一层
     * en_name : 1
     * width : 1663
     * height : 1043
     */

    private List<MapBean> map;

    public List<ExhibitInfo> getExhibit_info() {
        return exhibit_info;
    }

    public void setExhibit_info(List<ExhibitInfo> exhibit_info) {
        this.exhibit_info = exhibit_info;
    }

    public List<Exhibition> getExhibition() {
        return exhibition;
    }

    public void setExhibition(List<Exhibition> exhibition) {
        this.exhibition = exhibition;
    }

    public List<MapBean> getMap() {
        return map;
    }

    public void setMap(List<MapBean> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "DataBaseBean{" +
                "exhibit_info=" + exhibit_info +
                ", exhibition=" + exhibition +
                ", map=" + map +
                '}';
    }
}
