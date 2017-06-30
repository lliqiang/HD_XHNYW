package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/26.
 */

import java.io.Serializable;
import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/26 17:09
 * 类描述：
 */
public class RouteDetailBean implements Serializable{

    /**
     * status : 1
     * data : {"exhibit_arr":["0007","0001","0014","0015"],"gj_arr":[{"x":"417","y":"889"},{"x":"416","y":"835"},{"x":"416","y":"765"},{"x":"416","y":"712"},{"x":"416","y":"675"},{"x":"416","y":"622"},{"x":"454","y":"622"},{"x":"454","y":"565"},{"x":"512","y":"565"},{"x":"553","y":"565"},{"x":"553","y":"519"},{"x":"553","y":"485"},{"x":"553","y":"427"},{"x":"553","y":"427"},{"x":"553","y":"485"},{"x":"553","y":"519"},{"x":"553","y":"565"},{"x":"512","y":"565"},{"x":"454","y":"565"},{"x":"416","y":"565"},{"x":"416","y":"622"},{"x":"416","y":"675"},{"x":"416","y":"712"},{"x":"416","y":"765"},{"x":"346","y":"765"},{"x":"346","y":"692"},{"x":"346","y":"624"},{"x":"249","y":"624"},{"x":"130","y":"624"},{"x":"130","y":"624"},{"x":"249","y":"624"},{"x":"346","y":"624"},{"x":"346","y":"692"},{"x":"346","y":"765"},{"x":"416","y":"765"},{"x":"416","y":"712"},{"x":"416","y":"675"},{"x":"416","y":"622"},{"x":"454","y":"622"},{"x":"454","y":"565"},{"x":"512","y":"565"},{"x":"553","y":"565"},{"x":"553","y":"519"},{"x":"553","y":"485"},{"x":"553","y":"427"},{"x":"665","y":"427"},{"x":"748","y":"427"},{"x":"823","y":"427"},{"x":"823","y":"353"},{"x":"823","y":"300"},{"x":"764","y":"300"},{"x":"764","y":"300"},{"x":"823","y":"300"},{"x":"823","y":"353"},{"x":"823","y":"427"},{"x":"900","y":"427"},{"x":"981","y":"427"},{"x":"1057","y":"427"},{"x":"1129","y":"427"},{"x":"1173","y":"427"},{"x":"1173","y":"313"},{"x":"1173","y":"246"},{"x":"1245","y":"246"},{"x":"1349","y":"246"},{"x":"1412","y":"246"},{"x":"1412","y":"328"},{"x":"1412","y":"328"},{"x":"1412","y":"246"},{"x":"1349","y":"246"},{"x":"1245","y":"246"},{"x":"1173","y":"246"},{"x":"1173","y":"313"},{"x":"1173","y":"427"},{"x":"1173","y":"537"},{"x":"1173","y":"627"},{"x":"1237","y":"627"},{"x":"1237","y":"728"},{"x":"1188","y":"728"}]}
     * msg : 查询成功
     */

    private int status;
    private DataBean data;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<String> exhibit_arr;
        /**
         * x : 417
         * y : 889
         */

        private List<GjArrBean> gj_arr;

        public List<String> getExhibit_arr() {
            return exhibit_arr;
        }

        public void setExhibit_arr(List<String> exhibit_arr) {
            this.exhibit_arr = exhibit_arr;
        }

        public List<GjArrBean> getGj_arr() {
            return gj_arr;
        }

        public void setGj_arr(List<GjArrBean> gj_arr) {
            this.gj_arr = gj_arr;
        }

        public static class GjArrBean {
            private String x;
            private String y;

            public String getX() {
                return x;
            }

            public void setX(String x) {
                this.x = x;
            }

            public String getY() {
                return y;
            }

            public void setY(String y) {
                this.y = y;
            }
        }
    }
}
