package com.canbot.u05.sdk.clientdemo;

/**
 * 通过socket发送过来的Stringbean
 *
 * @author xiaowei
 */
public class StringMsgBean {

        /**
         * 消息类型，详见MsgType
         */
        private int msgType;

        /**
         * 数据
         */
        private String msgData;

        public StringMsgBean(int msgType, String msgData) {
                super();
                this.msgType = msgType;
                this.msgData = msgData;
        }

        public StringMsgBean() {
                super();
        }

        public int getMsgType() {
                return msgType;
        }

        public void setMsgType(int msgType) {
                this.msgType = msgType;
        }

        public String getMsgData() {
                return msgData;
        }

        public void setMsgData(String msgData) {
                this.msgData = msgData;
        }

        @Override
        public String toString() {
                return "StringMsgBean [msgType=" + msgType + ", msgData=" + msgData + "]";
        }

}
