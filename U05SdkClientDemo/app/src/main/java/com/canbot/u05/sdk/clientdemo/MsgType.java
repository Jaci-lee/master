package com.canbot.u05.sdk.clientdemo;

/**
 * 和平板通信的消息类型
 *
 * @author xiaowei
 */
public class MsgType {

        /**
         * 消息类型----接受语音识别的结果--同时是结束录音的消息
         */
        public static final int RECEIVER_MSG_VOICE_RECOGNIZER_RESULT = 1;

        /**
         * 消息类型---接受rk3288音频播放结束
         */
        public static final int RECEIVER_MSG_PLAY_SOUND_END = 2;

        /**
         * 消息----收到唤醒的消息，
         */
        public static final int RECEIVER_MSG_WAKE_UP = 6;

        /**
         * 退出识别，进入待唤醒时--pad跳转到唤醒界面
         */
        public static final int RECEIVER_MSG_SUSPEND = 9;

        /**
         * 感应器信息
         */
        public static final int RECEIVER_MSG_SENSOR = 12;

        /**
         * 获取当前头部 wifi 状态及对应的 ssid
         */
        public static final int RECEIVER_MSG_WIFI_STATUS_SSID = 17;

        /**
         * 获取头下发的时间
         */
        public static final int RECEIVE_MSG_CURRENT_TIME = 19;

        /**
         * 返回电量查询结果给客户端（接收查询的指令为8435）
         */
        public static final int RECEIVE_POWER_INFO = 37;

        /**
         * 头部开机成功指令
         */
        public static final int ON_SYSTEM_BOOT = 39;

        /**
         * 返回机器人自身热点SSID给客户端（接收查询的指令为8436）
         */
        public static final int U05_ROBOT_WIFI_SSID = 40;

        /**
         * 头部发送当前连接的 wifi 的 ssid 和 password 给胸口.（对应指令为8438）.
         */
        public static final int RECEIVE_MSG_WIFI_SSID_AND_PWD = 42;

        /**
         * 收到头部发送的音频时长
         */
        public static final int RECEIVER_VOICE_DURATION = 46;


        /**
         * 头部没有外网 wifi ssid 和 密码（对应指令为8438）.
         */
        public static final int HEAD_NO_WIFI_INFO = 47;


        /**
         * 消息类型---播放音频
         */
        public static final int PLAY_SOUND = 8400;

        /**
         * 消息类型---播放TTS
         */
        public static final int PLAY_TTS = 8401;

        /**
         * 随机播放SD卡目录下一个音频
         */
        public static final int PLAY_DIR_RANDOM_SOUND = 8402;

        /**
         * 消息类型--开始声控
         */
        public static final int START_RECOGNIZER = 8403;

        /**
         * 消息类型--取消声控
         */
        public static final int STOP_RECOGNIZER = 8404;

        /**
         * 消息类型--设置wifi密码
         */
        public static final int WIFI_PASSWORD = 8405;

        /**
         * 消息类型---播放拼接语音 (tts + 音频)
         */
        public static final int PLAY_LINK_SOUND = 8406;


        /**
         * 消息类型--停止识别的指令
         */
        public static final int RECEIVER_STOP_RECOGNIZER = 8410;

        /**
         * 发送停止动作指令
         */
        public static final int STOP_ACTION = 8414;

        /**
         * 暂停音频播放
         */
        public static final int PAUSE_AUDIO = 8417;

        /**
         * 继续音频播放
         */
        public static final int RESUME_AUDIO = 8418;


        /**
         * 发送动作指令
         */
        public static final int ACTION = 8420;

        /**
         * 播放网络歌曲
         */
        public static final int PLAY_URL_MUSIC = 8421;

        /**
         * 发送眼睛指令
         * 例：MsgSendUtils.sendStringMsg(MsgType.EYE_MOTION, "28");
         *    注："28"为eyeId
         */
        public static final int EYE_MOTION = 8422;

        /**
         * 播放音频-执行随机动作（随机动作是指头部程序内置的一些动作，客户不能指定随机动作）
         */
        public static final int PLAY_SOUND_WITH_RANDOM_ACTION = 8424;

        /**
         * 播放TTS-执行随机动作（随机动作是指头部程序内置的一些动作，客户不能指定随机动作）
         */
        public static final int PLAY_TTS_WITH_RANDOM_ACTION = 8425;

        /**
         * 拼接播放音频和tts--执行随机动作
         * 例：List<String[]> arrayList = new ArrayList<>();
         *     arrayList.add(new String[]{type + "", content});
         *     MsgSendUtils.sendStringMsg(MsgType.PLAY_LINK_SOUND_WITH_RANDOM_ACTION, JSON.toJSONString(arrayList));
         *     type：1表示tts，2表示音频
         */
        public static final int PLAY_LINK_SOUND_WITH_RANDOM_ACTION = 8426;

        /**
         * 打开人脸追踪
         */
        public static final int SEND_OPEN_GPU_FACE_TRACK = 8428;

        /**
         * 关闭人脸追踪
         */
        public static final int SEND_CLOSE_GPU_FACE_TRACK = 8429;

        /**
         * 消息类型--发送停止音频播放
         */
        public static final int STOP_SOUND = 8430;

        /**
         * 暂停 tts 播放
         */
        public static final int PAUSE_TTS = 8431;

        /**
         * 继续 tts 播放
         */
        public static final int RESUME_TTS = 8432;

        /**
         * 停止 tss 播放
         */
        public static final int STOP_TTS = 8433;

        /**
         * 客户端向机器人端发送电量查询消息
         * 消息内容：null
         */
        public static final int SEND_QUERY_POWER_INFO = 8435;

        /**
         * 接受demo客户端发送过来的指令-查询机器人自身热点SSID
         */
        public static final int QUERY_U05_ROBOT_WIFI_SSID = 8436;

        /**
         * 获取音频时长
         */
        public static final int SEND_CHECK_VOICE_DURATION = 8437;

        /**
         * 胸口请求头部下发当前连接 wifi 的 ssid 和 password.(应答指令为42或47).
         */
        public static final int REQUEST_HEAD_WIFI_INFO = 8438;

        /**
         * 通知头部发送 Ip 给胸口（对应指令为43）.
         */
        public static final int SEND_MSG_GET_HEAD_IP = 8440;


        /**
         * 消息类型--向胸口发送具体的动作指令
         */
        public static final int SEND_CONCRETE_ACTION = 8442;
        /**
         * 消息类型--控制底盘旋转角度
         */
        public static final int ROTATE = 8444;

        /**
         * 消息类型--关机
         */
        public static final int SHUTDOWN = 8445;


        /**
         * 停止地图任务播放
         */
        public static final int STOP_MAP_SOUND = 8624;

        /**
         * 地图播放TTS---用于支持导览任务TTS暂停继续
         */
        public static final int SEND_PLAY_MAP_TTS = 8625;

        /**
         * 地图播放音频---用于支持导览任务音频暂停继续
         */
        public static final int SEND_PLAY_MAP_VOICE = 8626;

        /**
         * 接受导览任务  暂停指令
         */
        public static final int SEND_PAD_MAP_SOUND_PAUSE = 8635;

        /**
         * 接受导览任务  继续指令
         */
        public static final int SEND_PAD_MAP_SOUND_CONTINUE = 8636;

        /**
         * 通知头部下发当前 wifi 状态及对应的 ssid
         */
        public static final int SEND_MSG_WIFI_STATUS_SSID = 8881;

        /**
         * 消息类型--接受胸口声源定位
         */
        public static final int RECEIVER_WAKEUP_SOURCE_ROTATE = 9301;

}
