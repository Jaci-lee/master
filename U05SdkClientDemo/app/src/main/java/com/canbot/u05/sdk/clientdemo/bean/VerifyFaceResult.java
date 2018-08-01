package com.canbot.u05.sdk.clientdemo.bean;


public class VerifyFaceResult {

        private int errorCode;

        private String errorMsg;

        private PersonVerifyData data;

        public VerifyFaceResult() {
                super();
        }

        public VerifyFaceResult(int errorCode, String errorMsg, PersonVerifyData data) {
                super();
                this.errorCode = errorCode;
                this.errorMsg = errorMsg;
                this.data = data;
        }


        public int getErrorCode() {
                return errorCode;
        }

        public void setErrorCode(int errorCode) {
                this.errorCode = errorCode;
        }

        public String getErrorMsg() {
                return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
                this.errorMsg = errorMsg;
        }

        public PersonVerifyData getData() {
                return data;
        }

        public void setData(PersonVerifyData data) {
                this.data = data;
        }

        @Override
        public String toString() {
                return "VerifyFaceResult [errorMsg=" + errorCode + ", errorMsg=" + errorMsg + ", data=" + data + "]";
        }
}
