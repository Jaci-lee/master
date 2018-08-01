package com.canbot.u05.sdk.clientdemo.map;

/**
 * Created by Administrator on 2017/11/23.
 */

public class MapMsgType {

        public static final String MAP_SDK_CODE = "map_sdk_code";

        public static final String MAP_SDK_RESULT = "map_sdk_result";

        public static final String MAP_SDK_DATA = "map_sdk_data";

        public static final String MAP_SDK_RESULT_ACTION = "map_sdk_action";

        public static final String MAP_SDK_SERVICE_ACTION = "map_sdk_service_action";

        public static final String MAP_SDK_PACKAGE_NAME = "com.canbot.u05";

        /**
         * 当前地图
         */
        public static final int MAP_SDK_TYPE_CURRENT_MAP = 0;

        /**
         * 当前地图列表
         */
        public static final int MAP_SDK_TYPE_MAP_LIST = 1;

        /**
         * 加载地图
         */
        public static final int MAP_SDK_TYPE_LOAD_MAP = 2;

        /**
         * 复位地图
         */
        public static final int MAP_SDK_TYPE_RESTORE_MAP = 3;

        /**
         * 位置点
         */
        public static final int MAP_SDK_TYPE_MAP_POINTS = 4;

        /**
         * 人物点
         */
        public static final int MAP_SDK_TYPE_MAP_PERSONS = 5;

        /**
         * moveTO位置点
         */
        public static final int MAP_SDK_TYPE_MOVE_TO_LOCAL_P = 6;

        /**
         * moveTO人物点
         */
        public static final int MAP_SDK_TYPE_MOVE_TO_PERSON_P = 7;

        /**
         * 回出生点
         */
        public static final int MAP_SDK_TYPE_MOVE_TO_INIT= 8;

        /**
         * 停止导览
         */
        public static final int MAP_SDK_TYPE_STOP_MOVE = 10;
}
