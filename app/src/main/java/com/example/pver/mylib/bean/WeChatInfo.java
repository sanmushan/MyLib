package com.example.pver.mylib.bean;

import java.util.List;

/**
 * Created by PVer on 2017/8/25.
 */

public class WeChatInfo {

    /**
     * reason : 请求成功
     * result : {"list":[{"id":"wechat_20170825024650","title":"杭州一大姐，接了个电话就去旅馆开了房！看到警察，她突然哭了！","source":"FM93交通之声","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-36871272.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825024650"},{"id":"wechat_20170825024715","title":"心痛！浙江13岁女孩倒在血泊中，下狠手的竟是邻居家男孩！就因为这点事，竟下此狠手！","source":"FM93交通之声","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-37123731.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825024715"},{"id":"wechat_20170825034160","title":"中华道学百问丨历史上注释《道德经》最有影响的人是谁？","source":"腾讯道学","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-37141555.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825034160"}],"totalPage":44288,"ps":3,"pno":1}
     * error_code : 0
     */

    private String reason;
    /**
     * list : [{"id":"wechat_20170825024650","title":"杭州一大姐，接了个电话就去旅馆开了房！看到警察，她突然哭了！","source":"FM93交通之声","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-36871272.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825024650"},{"id":"wechat_20170825024715","title":"心痛！浙江13岁女孩倒在血泊中，下狠手的竟是邻居家男孩！就因为这点事，竟下此狠手！","source":"FM93交通之声","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-37123731.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825024715"},{"id":"wechat_20170825034160","title":"中华道学百问丨历史上注释《道德经》最有影响的人是谁？","source":"腾讯道学","firstImg":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-37141555.jpg/640","mark":"","url":"http://v.juhe.cn/weixin/redirect?wid=wechat_20170825034160"}]
     * totalPage : 44288
     * ps : 3
     * pno : 1
     */

    private ResultEntity result;
    private int error_code;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getError_code() {
        return error_code;
    }

    public static class ResultEntity {
        private int totalPage;
        private int ps;
        private int pno;
        /**
         * id : wechat_20170825024650
         * title : 杭州一大姐，接了个电话就去旅馆开了房！看到警察，她突然哭了！
         * source : FM93交通之声
         * firstImg : http://zxpic.gtimg.com/infonew/0/wechat_pics_-36871272.jpg/640
         * mark :
         * url : http://v.juhe.cn/weixin/redirect?wid=wechat_20170825024650
         */

        private List<ListEntity> list;

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public void setPs(int ps) {
            this.ps = ps;
        }

        public void setPno(int pno) {
            this.pno = pno;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public int getPs() {
            return ps;
        }

        public int getPno() {
            return pno;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String id;
            private String title;
            private String source;
            private String firstImg;
            private String mark;
            private String url;

            public void setId(String id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public void setFirstImg(String firstImg) {
                this.firstImg = firstImg;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getSource() {
                return source;
            }

            public String getFirstImg() {
                return firstImg;
            }

            public String getMark() {
                return mark;
            }

            public String getUrl() {
                return url;
            }
        }
    }
}
