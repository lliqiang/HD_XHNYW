package com.hengda.smart.xhnyw.d.model;/**
 * Created by lenovo on 2017/6/25.
 */

import java.io.Serializable;
import java.util.List;

/**
 * 创建人：lenovo
 * 创建时间：2017/6/25 8:38
 * 类描述：
 */
public class RouteBean implements Serializable {


    /**
     * status : 1
     * data : 1
     * msg : [{"road_title":"推荐路线","road_type":"7","road_description":"哈哈哈","road_time":"2小时","list":[{"exhibit_arr":[{"exhibit_id":"0003","byname":"12312","map_name":"主体楼一层"},{"exhibit_id":"0012","byname":"453453453","map_name":"主体楼一层"},{"exhibit_id":"0013","byname":"231123","map_name":"主体楼一层"},{"exhibit_id":"0014","byname":"weffsdf","map_name":"主体楼一层"},{"exhibit_id":"0015","byname":"fghfghfgfgh","map_name":"主体楼一层"}]},{"exhibit_arr":[{"exhibit_id":"0008","byname":"电饭锅电饭锅是大法官","map_name":"主体楼二层"}]}],"total_num":6},{"road_title":"精品路线","road_type":"8","road_description":"精品路线精品路线精品路线精品路线","road_time":"3小时","list":[{"exhibit_arr":[{"exhibit_id":"0004","byname":"123","map_name":"主体楼一层"},{"exhibit_id":"0005","byname":"3423123","map_name":"主体楼一层"},{"exhibit_id":"0007","byname":"而特瑞特让他","map_name":"主体楼一层"},{"exhibit_id":"0012","byname":"453453453","map_name":"主体楼一层"},{"exhibit_id":"0014","byname":"weffsdf","map_name":"主体楼一层"}]}],"total_num":5}]
     */

    private int status;
    private int data;
    /**
     * road_title : 推荐路线
     * road_type : 7
     * road_description : 哈哈哈
     * road_time : 2小时
     * list : [{"exhibit_arr":[{"exhibit_id":"0003","byname":"12312","map_name":"主体楼一层"},{"exhibit_id":"0012","byname":"453453453","map_name":"主体楼一层"},{"exhibit_id":"0013","byname":"231123","map_name":"主体楼一层"},{"exhibit_id":"0014","byname":"weffsdf","map_name":"主体楼一层"},{"exhibit_id":"0015","byname":"fghfghfgfgh","map_name":"主体楼一层"}]},{"exhibit_arr":[{"exhibit_id":"0008","byname":"电饭锅电饭锅是大法官","map_name":"主体楼二层"}]}]
     * total_num : 6
     */

    private List<MsgBean> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean implements Serializable {
        private String road_title;
        private String road_type;
        private String road_description;
        private String road_time;
        private String map_id;
        private String total_num;
        private List<ListBean> list;

        public String getMap_id() {
            return map_id;
        }

        public void setMap_id(String map_id) {
            this.map_id = map_id;
        }

        public String getRoad_title() {
            return road_title;
        }

        public void setRoad_title(String road_title) {
            this.road_title = road_title;
        }

        public String getRoad_type() {
            return road_type;
        }

        public void setRoad_type(String road_type) {
            this.road_type = road_type;
        }

        public String getRoad_description() {
            return road_description;
        }

        public void setRoad_description(String road_description) {
            this.road_description = road_description;
        }

        public String getRoad_time() {
            return road_time;
        }

        public void setRoad_time(String road_time) {
            this.road_time = road_time;
        }

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            public String getMap_name() {
                return map_name;
            }

            public void setMap_name(String map_name) {
                this.map_name = map_name;
            }

            /**
             * exhibit_id : 0003
             * byname : 12312
             * map_name : 主体楼一层
             */

            private String map_name;
            private List<ExhibitArrBean> exhibit_arr;

            public List<ExhibitArrBean> getExhibit_arr() {
                return exhibit_arr;
            }

            public void setExhibit_arr(List<ExhibitArrBean> exhibit_arr) {
                this.exhibit_arr = exhibit_arr;
            }

            public static class ExhibitArrBean implements Serializable {
                private String exhibit_id;
                private String byname;
                private String map_name;
                private int mark;

                public int getMark() {
                    return mark;
                }

                public void setMark(int mark) {
                    this.mark = mark;
                }

                public String getExhibit_id() {
                    return exhibit_id;
                }

                public void setExhibit_id(String exhibit_id) {
                    this.exhibit_id = exhibit_id;
                }

                public String getByname() {
                    return byname;
                }

                public void setByname(String byname) {
                    this.byname = byname;
                }

                public String getMap_name() {
                    return map_name;
                }

                public void setMap_name(String map_name) {
                    this.map_name = map_name;
                }
            }
        }

        @Override
        public String toString() {
            return "MsgBean{" +
                    "road_title='" + road_title + '\'' +
                    ", road_type='" + road_type + '\'' +
                    ", road_description='" + road_description + '\'' +
                    ", road_time='" + road_time + '\'' +
                    ", map_id='" + map_id + '\'' +
                    ", total_num='" + total_num + '\'' +
                    ", list=" + list +
                    '}';
        }
    }
}
