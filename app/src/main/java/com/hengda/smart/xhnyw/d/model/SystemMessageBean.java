package com.hengda.smart.xhnyw.d.model;

import java.util.ArrayList;
import java.util.List;

/**
 * author : HaoYuZhang.
 * e-mail : zhy920726@163.com
 * time   : 2017/6/20.
 * desc   : 这是一个object的子类
 * version: 1.0
 */
public class SystemMessageBean implements Mapper<SystemMessageBean.SystemMessageVO> {

    /**
     * title : 123123123
     * url : http://192.168.10.158/xhnyw/index.php?g=Mapi&m=Resource&a=info_html&id=177
     */

    private String title;
    private String url;

    @Override
    public SystemMessageVO transform() {
        SystemMessageVO result = new SystemMessageVO();
        result.setTitle(title);
        result.setContent(url);
        return result;
    }

    public static List<SystemMessageVO> transformList(List<SystemMessageBean> dataList) {
        List<SystemMessageVO> result = new ArrayList<>();
        for (SystemMessageBean systemMessageBean : dataList) {
            result.add(systemMessageBean.transform());
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class SystemMessageVO {

        private String title;
        private String content;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }

}
