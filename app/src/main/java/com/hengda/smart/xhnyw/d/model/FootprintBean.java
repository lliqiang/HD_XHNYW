package com.hengda.smart.xhnyw.d.model;

import android.database.Cursor;

import com.hengda.smart.xhnyw.d.app.Hd_AppConfig;
import com.hengda.smart.xhnyw.d.dbase.HResDdUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/21.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class FootprintBean implements Mapper<FootprintBean.FootprintBeanVO> {

    /**
     * exhibit_id : 0015
     * date : 2017-06-24
     * time : 14:07
     */

    private String exhibit_id;
    private String date;
    private String time;

    @Override
    public FootprintBeanVO transform() {
        FootprintBeanVO result = new FootprintBeanVO();
        result.setTime(time);
        result.setDate(date);
        result.setUniqueId(exhibit_id);
        result.setImgPath(Hd_AppConfig.getExhibitImgPath(exhibit_id, "map_icon"));
        try {
            Cursor cursor = HResDdUtil.getInstance().loadExhibitionById(exhibit_id);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    ExhibitInfo exhibitInfo = ExhibitInfo.CursorToModel(cursor);
                    result.setName(exhibitInfo.getName());
                    result.setDataBean(exhibitInfo);
                }
                cursor.close();
            }
        } catch (Exception exception) {
            return null;
        }
        return result;
    }

    public static List<FootprintBeanVO> transformList(List<FootprintBean> dataList) {
        List<FootprintBeanVO> result = new ArrayList<>();
        for (FootprintBean footprintBean : dataList) {
            FootprintBeanVO temp = footprintBean.transform();
            if (temp != null) {
                result.add(temp);
            }
        }
        return result;
    }

    public String getExhibit_id() {
        return exhibit_id;
    }

    public void setExhibit_id(String exhibit_id) {
        this.exhibit_id = exhibit_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static class FootprintBeanVO {

        //唯一标示
        private String uniqueId;
        //图片路径
        private String imgPath;
        //展品名称
        private String name;
        //日期 2017.06.06
        private String date;
        //时间 10:22
        private String time;
        //实体类用于页面跳转
        private ExhibitInfo dataBean;


        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String id) {
            this.uniqueId = id;
        }

        public ExhibitInfo getDataBean() {
            return dataBean;
        }

        public void setDataBean(ExhibitInfo dataBean) {
            this.dataBean = dataBean;
        }
    }

}
