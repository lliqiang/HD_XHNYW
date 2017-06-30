package com.hengda.smart.xhnyw.d.model;

import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/27.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class FriendLocationBean {

    /**
     * road : [{"x":"185","y":"593"},{"x":"185","y":"624"},{"x":"249","y":"624"},{"x":"346","y":"624"},{"x":"346","y":"692"},{"x":"346","y":"765"},{"x":"416","y":"765"},{"x":"416","y":"712"},{"x":"416","y":"675"},{"x":"416","y":"622"},{"x":"454","y":"622"},{"x":"454","y":"565"},{"x":"512","y":"565"},{"x":"553","y":"565"},{"x":"553","y":"519"},{"x":"553","y":"485"},{"x":"553","y":"427"},{"x":"665","y":"427"},{"x":"748","y":"427"},{"x":"823","y":"427"},{"x":"900","y":"427"},{"x":"981","y":"427"},{"x":"1057","y":"427"},{"x":"1129","y":"427"},{"x":"1173","y":"427"},{"x":"1173","y":"338"},{"x":"1165","y":"338"}]
     * type : 2
     * firend_info : {"map_id":"1","x":"1165","y":"338"}
     */

    private int type;
    private FirendInfoBean firend_info;
    private List<RoadBean> road;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public FirendInfoBean getFirend_info() {
        return firend_info;
    }

    public void setFirend_info(FirendInfoBean firend_info) {
        this.firend_info = firend_info;
    }

    public List<RoadBean> getRoad() {
        return road;
    }

    public void setRoad(List<RoadBean> road) {
        this.road = road;
    }

    public static class FirendInfoBean {
        /**
         * map_id : 1
         * x : 1165
         * y : 338
         */

        private String map_id;
        private String x;
        private String y;

        public String getMap_id() {
            return map_id;
        }

        public void setMap_id(String map_id) {
            this.map_id = map_id;
        }

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

    public static class RoadBean {
        /**
         * x : 185
         * y : 593
         */

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
