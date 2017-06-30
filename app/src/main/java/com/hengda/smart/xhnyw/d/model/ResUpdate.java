package com.hengda.smart.xhnyw.d.model;

/**
 * Created by lenovo on 2016/11/12.
 */

public class ResUpdate {


    /**
     * status : 1
     * data : {"is_update":1,"version_id":"20","down_url":"http://192.168.10.158/xhnyw/resource/resource.zip"}
     * msg : 查询成功
     */

    private int status;
    /**
     * is_update : 1
     * version_id : 20
     * down_url : http://192.168.10.158/xhnyw/resource/resource.zip
     */

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
        private int is_update;
        private String version_id;
        private String down_url;

        public int getIs_update() {
            return is_update;
        }

        public void setIs_update(int is_update) {
            this.is_update = is_update;
        }

        public String getVersion_id() {
            return version_id;
        }

        public void setVersion_id(String version_id) {
            this.version_id = version_id;
        }

        public String getDown_url() {
            return down_url;
        }

        public void setDown_url(String down_url) {
            this.down_url = down_url;
        }
    }
}
