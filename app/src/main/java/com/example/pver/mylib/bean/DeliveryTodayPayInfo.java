package com.example.pver.mylib.bean;

import java.util.List;

/**
 * 今日送货收入排名
 * Created by PVer on 2017/7/6.
 */

public class DeliveryTodayPayInfo {
    /**
     * msg : 请求成功
     * code : 0
     * response : {"datas":[{"deliveryTodayPay":2351836200,"name":"彭栓","id":1,"passportId":1909725},{"deliveryTodayPay":400697460,"name":"陈冠平","id":2,"passportId":1919361},{"deliveryTodayPay":194940000,"name":"黄楚鹏","id":3,"passportId":1912297},{"deliveryTodayPay":148948800,"name":"陈俊","id":4,"passportId":1914722},{"deliveryTodayPay":69973500,"name":"谢远大","id":5,"passportId":1917775},{"deliveryTodayPay":69589200,"name":"张培安","id":6,"passportId":1912196},{"deliveryTodayPay":57234000,"name":"蒋贺添","id":7,"passportId":1915636},{"deliveryTodayPay":56586000,"name":"段伟","id":8,"passportId":1914912},{"deliveryTodayPay":52764000,"name":"尹永清","id":9,"passportId":1918505},{"deliveryTodayPay":42653400,"name":"方斌","id":10,"passportId":1911786},{"deliveryTodayPay":36162000,"name":"刘石庚","id":11,"passportId":1923001},{"deliveryTodayPay":34412400,"name":"郑光和","id":12,"passportId":1922052},{"deliveryTodayPay":32862000,"name":"黄明果","id":13,"passportId":1918977},{"deliveryTodayPay":32076000,"name":"刘小丹","id":14,"passportId":1916103},{"deliveryTodayPay":28625850,"name":"王玲羲","id":15,"passportId":1921820},{"deliveryTodayPay":3816000,"name":"邓国强","id":16,"passportId":1923681},{"deliveryTodayPay":0,"name":"测试大佬李毅宾","id":17,"passportId":609457}]}
     */

    private String msg;
    private int code;
    private ResponseEntity response;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public ResponseEntity getResponse() {
        return response;
    }

    public static class ResponseEntity {
        /**
         * deliveryTodayPay : 2351836200
         * name : 彭栓
         * id : 1
         * passportId : 1909725
         */

        private List<DatasEntity> datas;

        public void setDatas(List<DatasEntity> datas) {
            this.datas = datas;
        }

        public List<DatasEntity> getDatas() {
            return datas;
        }

        public static class DatasEntity {
            private String deliveryTodayPay;
            private String name;
            private int id;
            private int passportId;

            public void setDeliveryTodayPay(String deliveryTodayPay) {
                this.deliveryTodayPay = deliveryTodayPay;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setPassportId(int passportId) {
                this.passportId = passportId;
            }

            public String getDeliveryTodayPay() {
                return deliveryTodayPay;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }

            public int getPassportId() {
                return passportId;
            }
        }
    }
   /* private int deliveryTodayPay;
    private String name;
    private String id;
    private int passportId;

    public int getDeliveryTodayPay() {
        return deliveryTodayPay;
    }

    public void setDeliveryTodayPay(int deliveryTodayPay) {
        this.deliveryTodayPay = deliveryTodayPay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPassportId() {
        return passportId;
    }

    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }*/

}
