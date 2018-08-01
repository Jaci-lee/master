package com.canbot.u05.sdk.clientdemo.util;

public class ErrorCode {

        public static final int CV_OK = 0;

        public static final int CV_E_INVALIDARG = -1;

        public static final int CV_E_HANDLE = -2;

        public static final int CV_E_OUTOFMEMORY = -3;

        public static final int CV_E_FAIL = -4;

        public static final int CV_E_DELNOTFOUND = -5;

        public static final int CV_E_INVALID_PIXEL_FORMAT = -6;

        public static final int CV_E_FILE_NOT_FOUND = -10;

        public static final int CV_E_INVALID_FILE_FORMAT = -11;

        public static final int CV_E_INVALID_APPID = 12;

        public static final int CV_E_INVALID_AUTH = -13;

        public static final int CV_E_AUTH_EXPIRE = -14;

        public static final int CV_E_FILE_EXPIRE = -15;

        public static final int CV_E_DONGLE_EXPIRE = -16;

        public static final int CV_E_ONLINE_AUTH_FAIL = -17;

        public static final int CV_E_ONLINE_AUTH_TIMEOUT = -18;

        public static String getMessage(int code) {
                if (code == CV_OK) {
                        return "正常运行";
                }
                else if (code == CV_E_INVALIDARG) {
                        return "无效参数";
                }
                else if (code == CV_E_HANDLE) {
                        return "句柄错误";
                }
                else if (code == CV_E_OUTOFMEMORY) {
                        return "内存不足";
                }
                else if (code == CV_E_FAIL) {
                        return "内部错误";
                }
                else if (code == CV_E_DELNOTFOUND) {
                        return "定义缺失";
                }
                else if (code == CV_E_INVALID_PIXEL_FORMAT) {
                        return "不支持的图像格式";
                }
                else if (code == CV_E_FILE_NOT_FOUND) {
                        return "模型文件不存在";
                }
                else if (code == CV_E_INVALID_FILE_FORMAT) {
                        return "模型格式不正确，导致加载失败";
                }
                else if (code == CV_E_INVALID_APPID) {
                        return "包名错误";
                }
                else if (code == CV_E_INVALID_AUTH) {
                        return "加密狗功能不支持";
                }
                else if (code == CV_E_AUTH_EXPIRE) {
                        return "SDK过期";
                }
                else if (code == CV_E_FILE_EXPIRE) {
                        return "模型文件过期";
                }
                else if (code == CV_E_DONGLE_EXPIRE) {
                        return "加密狗过期";
                }
                else if (code == CV_E_ONLINE_AUTH_FAIL) {
                        return "在线验证失败";
                }
                else if (code == CV_E_ONLINE_AUTH_TIMEOUT) {
                        return "在线验证超时";
                }

                return null;
        }
}
