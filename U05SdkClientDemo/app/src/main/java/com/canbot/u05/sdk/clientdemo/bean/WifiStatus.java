package com.canbot.u05.sdk.clientdemo.bean;

/**
 * Wifi 状态
 * Created by xh on 2017/7/17.
 */

public class WifiStatus {

        /**
         * 0：断开连接<br/>
         * 1：连接中<br/>
         * 2：连接成功
         * 3：密码错误
         */
        private String status;

        private String ssid;

        public WifiStatus(String status, String ssid) {
                this.status = status;
                this.ssid = ssid;
        }

        public String getStatus() {
                return status;
        }

        public String getSsid() {
                return ssid;
        }


        @Override
        public String toString() {
                return "WifiStatus{" +
                        "status='" + status + '\'' +
                        ", ssid='" + ssid + '\'' +
                        '}';
        }

}
