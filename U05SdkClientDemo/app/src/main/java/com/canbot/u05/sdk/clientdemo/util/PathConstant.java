package com.canbot.u05.sdk.clientdemo.util;

import android.os.Environment;

import com.canbot.u05.sdk.clientdemo.bean.PersonData;

import java.util.ArrayList;

public class PathConstant {

        public static final int FEATURE_SIZE = 1385;// 特征值长度

        public static final String POINT = "21 point";

        public static final int PREVIEW_WIDTH = 640;

        public static final int PREVIEW_HEIGHT = 480;

        public static final int PICTRUE_WIDTH = 640;

        public static final int PICTRUE_HEIGHT = 480;

        public static final int PREVIEW_RATE = 15;

        public static boolean hadInit = false;

        /**
         * 图片方向 mOrientation == 0 IMAGE_ORIENTATION = 1;
         * mOrientation == 90. IMAGE_ORIENTATION = 2;
         * mOrientation == 180 IMAGE_ORIENTATION = 4;
         * mOrientation == 270 IMAGE_ORIENTATION = 8;
         */

        public static final int IMAGE_ORIENTATION = 8;

        public static final String ROOT_PATH = Environment
                .getExternalStorageDirectory().getAbsolutePath();

        public static final String ANI_PATH_LEFT = ROOT_PATH
                + "/ai/ai05res/a1/res/animation/eye/left/";

        public static final String ANI_PATH_RIGHT = ROOT_PATH
                + "/ai/ai05res/a1/res/animation/eye/right/";

        public static final String ANI_PATH_LEFT_DHS = ROOT_PATH
                + "/ai/ai05res/a1/res/animation/eyedhs/left/";

        public static final String ANI_PATH_RIGHT_DHS = ROOT_PATH
                + "/ai/ai05res/a1/res/animation/eyedhs/right/";

        public static final String FACEREC_APP_PATH = ROOT_PATH
                + "/ai/ai05res/a1/res/audio/face/";

        public static final int PLAY_NORMAL_ANIMA = 4;

        public static final int COUNT = 3;

        public static final int INIT_SCENSETIMEENGINE = 5;

        public static final String EYE_ANIMA_NORMAL = "eye4.mp4";

        /**
         * 获取人脸角度的时间间隔（ms）.
         */
        public static final int PERIOD_GET_ANGLE = 50;

        public static final int PERIOD_POST_RECOGNIZE = 100;

        public static final int MESSAGE_INSERT_REGISTER_INFO = 0;

        public static final int INIT_ERROR = 1;

        public static final String PICTURE_PATH = PathConstant.ROOT_PATH + "/uurobotPicture";

        public static final String PHOTO_PATH = PathConstant.ROOT_PATH + "/uurobotPhotos";

        public static String currentFilePath;

        public static boolean EMOTION_ISOPEN = false;

        /**
         * 娱乐模式.
         */
        public static final String MODE_ENTERTAINMENT_TAKE_PHOTO = "com.jsg.photograph";//我要拍照

        public static final String MODE_ENTERTAINMENT_GUESS = "com.uurobot.u05iplayuguess";//我比你猜

        public static final String MODE_ENTERTAINMENT_ACT = "com.canbot.mooddiscern";//演技大考验

        public static final String MODE_ENTERTAINMENT_FINGER_PALY = "com.example.finger_guessinggame";//剪刀石头布

        public static final String MODE_ENTERTAINMENT_PARROT = "";//优友学舌

        public static final String MODE_ENTERTAINMENT_REMIND = "";//智能提醒

        public static final String MODE_ENTERTAINMENT_RADIO = "com.myroobt.broadcasting";//UU电台

        public static final String MODE_ENTERTAINMENT_HEALTHY = "com.unisrobot.jtjkzx2";//家庭健康

        public static final int BUFFER_SIZE = 1024 * 10;

        /**
         * socket pc人脸注册端口.
         */
        public static final int SOCKET_PORT = 56168;

        /**
         * socket pc 注册结果端口.
         */
        public static final int SOCKET_PORT2 = 56169;

        /**
         * socket 人脸批量注册文件目录.
         */
        public static final String FILE_DIR = ROOT_PATH + "/temp";

        /**
         * 人脸注册相对地址.
         */
        public static final String FACES_ABS_DIR = "/temp/faces/";

        /**
         * 人脸注册音频相对地址.
         */
        public static final String FACES_VOICE_ABS_DIR = "/temp/faces/voice/";

        /**
         * 相人脸临时文件相对地址.
         */
        public static final String FACES_ABS_TEMP = "/temp/temp/";

        /**
         * faces dir.
         */
        public static final String FACES_DIR = ROOT_PATH + FACES_ABS_DIR;

        /**
         * faces voice dir.
         */
        public static final String FACES_VOICE_DIR = ROOT_PATH + FACES_VOICE_ABS_DIR;

        /**
         * MEETING voice dir.
         */
        public static final String MEETING_VOICE_DIR = ROOT_PATH + "/meeting/video/";

        /**
         * faces 陌生临时图片.
         */
        public static final String FACES_TEMP = ROOT_PATH + FACES_ABS_TEMP;

        public static final String EMOTION_DIR = "/temp/emotion/";

        /**
         * socket 人脸批量注册压缩文件.
         */
        public static final String FILE_PATH = FILE_DIR + "/faces.zip";

        /**
         * socket ���� byte[] size.
         */
        public static final int SOCKET_BUFFER_SIZE = 1024 * 5;

        /**
         * 开始解压.
         */
        public static final int START_UIP_FILE = 0;

        /**
         * 解压完成.
         */
        public static final int FINISH_UIP_FILE = 1;

        public static final String KEY_VOICE = "voice";

        public static final String KEY_VOICECONTENT = "voicecontent";

        public static final String KEY_SMARTHOME_CMD = "smarthome_cmd";

        //提问答案音频文件 类型 TTS还是音频文件
        public static final int QS_TTS_TYPE = 1;

        public static final int QS_FILE_TYPE = 0;

        /**
         * 列表定义的最大值  与网页上同步.
         */
        public static final int QS_MAX_NUM = 20 + 1;

        public static final int QS_ON_MAX_NUM = 50 + 1;

        /**
         * 消失二维码.
         */
        public static final int CODE_DISMISS = 7;

        public static final String FILE_DIR_CMD_DATA_ROOT_PATH = ROOT_PATH + "/temp/cmd/";

        public static final String FILE_DIR_CMD_DATA_PATH = FILE_DIR_CMD_DATA_ROOT_PATH + "asqs.db3";

        public static ArrayList<PersonData> mPersonData = new ArrayList<PersonData>();
}
