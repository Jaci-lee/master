package com.canbot.u05.sdk.clientdemo.bean;

/**
 * Created by Administrator on 2017/5/18.
 */

public class JsonResponse {


        private String msg;

        private int rc;

        private Object result;

        public JsonResponse() {
        }

        public JsonResponse(String msg, int rc, Object result) {
                this.msg = msg;
                this.rc = rc;
                this.result = result;
        }

        @Override
        public String toString() {
                return "JsonResponse{" +
                        "msg='" + msg + '\'' +
                        ", rc=" + rc +
                        ", result=" + result +
                        '}';
        }

        public void setMsg(String msg) {
                this.msg = msg;
        }

        public String getMsg() {
                return msg;
        }

        public int getRc() {
                return rc;
        }

        public void setRc(int rc) {
                this.rc = rc;
        }

        public Object getResult() {
                return result;
        }

        public void setResult(Object result) {
                this.result = result;
        }
}
