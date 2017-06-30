package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/25.
 */

import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/25 15:53
 * 类描述：
 */
public class LocBean {

    /**
     * status : 1
     * data : [{"x":"599","y":"399"},{"x":"599","y":"427"},{"x":"665","y":"427"},{"x":"665","y":"499"},{"x":"666","y":"499"}]
     * msg : 成功
     */

    private int status;
    private String msg;
    /**
     * x : 599
     * y : 399
     */

    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
