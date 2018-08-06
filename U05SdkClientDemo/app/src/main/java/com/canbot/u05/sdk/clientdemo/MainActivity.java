package com.canbot.u05.sdk.clientdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.canbot.u05.IMsgBind;
import com.canbot.u05.IMsgCallBack;
import com.canbot.u05.sdk.clientdemo.bean.IndustryDatas;
import com.canbot.u05.sdk.clientdemo.bean.WifiStatus;
import com.canbot.u05.sdk.clientdemo.face.FaceActivity;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.canbot.u05.sdk.clientdemo.util.WifiUtils;
import com.uurobot.sdkclientdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * U05机器人远程通信协议客户端SDK demo
 * <p>
 * 版本号:1.0.3
 * <p>
 * <p>
 * 此类定义了客户端和机器人本体相互通信的接口
 * 用户可以通过此接口接收机器人端发送过来的信息，比如：语音唤醒 语音识别信息,感应器信息...等;
 * 也可以通过此接口向机器人端发送指令，比如：播放tts，播放音频，执行动作指令...等。
 * <p>
 * <p>
 * 客户端实现流程:
 * 1, 用户在需要接受机器人端消息的地方绑定服务 bindService();
 * 2, 远程服务绑定成功后绑定远程接口 registerAIDL();
 * 3, 接收远程消息，通过IMsgCallBack接口中的interceptRobotAction()方法
 *    若return ture,则表示该信息用户自己处理了，机器人不再处理。若return false,则表示该信息机器人处理。
 * 4, 定义StringMsgBean, 其中msgType为数据类型（详细类型参见MsgType类），msgData为内容
 * 5, 向机器人端发送指令,通过sendData(StringMsgBean msgBean)方法，传入已经好的StringMsgBean;
 * 6, 用户在不需要接受机器人端消息时解绑远程服务回调接口和解绑远程服务
 * <p>
 * <p>
 * 客户可以通过此sdk实现的功能：
 * 1, 知道机器人被唤醒的时机，并且做出相应的处理(注：对机器人说：你好，优友，即可唤醒)
 * 2, 能获取到自己谈话的内容，访问自己的语义服务器做相应的扩展
 * 3, 能感知机器人的感应器何时被触摸，做出相应的回应或者其他扩展
 * 4, 控制机器人做出，比如前进，后退，左转，右转，敬礼，舞蹈等等一系列的动作
 * 5, 控制机器人播放tts，和音频
 * <p>
 * <p>
 * 此文档适用于U05和U05E系列产品
 */

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

        public static final String TAG = "MainActivity";

        private List<IndustryDatas>  mbrowList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                IntentFilter wifiIntentFilter = new IntentFilter();
                wifiIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                wifiIntentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);

                GridView gridView = (GridView) findViewById(R.id.grid_view);
                mbrowList = getDate();
                MainAdapter mainAdapter = new MainAdapter(MainActivity.this,mbrowList);
                gridView.setAdapter(mainAdapter);
                gridView.setOnItemClickListener(this);
        }

        private List<IndustryDatas> getDate() {

                mbrowList = new ArrayList<>();
                int j = 0;

                IndustryDatas industryDatas1 = new IndustryDatas(j, "播放tts", new Runnable() {
                        @Override
                        public void run() {
                                sendPlayTTS();
                        }
                });

                IndustryDatas industryDatas2 = new IndustryDatas(j++, "播放音频", new Runnable() {
                        @Override
                        public void run() {
                                sendPlaySound();
                        }
                });
                IndustryDatas industryDatas3 = new IndustryDatas(j++, "停止音频播放", new Runnable() {
                        @Override
                        public void run() {
                                sendStopSound();
                        }
                });
                IndustryDatas industryDatas4 = new IndustryDatas(j++, "向后退", new Runnable() {
                        @Override
                        public void run() {
                                move_backward();
                        }
                });
                IndustryDatas industryDatas5 = new IndustryDatas(j++, "向前进", new Runnable() {
                        @Override
                        public void run() {
                                move_forward();
                        }
                });
                IndustryDatas industryDatas6 = new IndustryDatas(j++, "向左转", new Runnable() {
                        @Override
                        public void run() {
                                turn_left();
                        }
                });
                IndustryDatas industryDatas7 = new IndustryDatas(j++, "向右转", new Runnable() {
                        @Override
                        public void run() {
                                turn_right();
                        }
                });
                IndustryDatas industryDatas8 = new IndustryDatas(j++, "向右看", new Runnable() {
                        @Override
                        public void run() {
                                look_right();
                        }
                });
                IndustryDatas industryDatas9 = new IndustryDatas(j++, "向左看", new Runnable() {
                        @Override
                        public void run() {
                                look_left();
                        }
                });
                IndustryDatas industryDatas10 = new IndustryDatas(j++, "再摇头", new Runnable() {
                        @Override
                        public void run() {
                                shake_head_again();
                        }
                });
                IndustryDatas industryDatas11 = new IndustryDatas(j++, "摇头", new Runnable() {
                        @Override
                        public void run() {
                                shake_head();
                        }
                });
                IndustryDatas industryDatas12 = new IndustryDatas(j++, "停止动作", new Runnable() {
                        @Override
                        public void run() {
                                sendStopAction();
                        }
                });
                IndustryDatas industryDatas13 = new IndustryDatas(j++, "敬礼", new Runnable() {
                        @Override
                        public void run() {
                                salute();
                        }
                });
                IndustryDatas industryDatas14 = new IndustryDatas(j++, "转过身来", new Runnable() {
                        @Override
                        public void run() {
                                turn_bakc();
                        }
                });
                IndustryDatas industryDatas15 = new IndustryDatas(j++, "转圈", new Runnable() {
                        @Override
                        public void run() {
                                circle();
                        }
                });
                IndustryDatas industryDatas16 = new IndustryDatas(j++, "开启声控", new Runnable() {
                        @Override
                        public void run() {
                                sendOpenSound();
                        }
                });
                IndustryDatas industryDatas17 = new IndustryDatas(j++, "关闭声控", new Runnable() {
                        @Override
                        public void run() {
                                sendCloseSound();
                        }
                });
                IndustryDatas industryDatas18 = new IndustryDatas(j++, "握手", new Runnable() {
                        @Override
                        public void run() {
                                shake_hand();
                        }
                });
                IndustryDatas industryDatas19 = new IndustryDatas(j++, "眼睛动画", new Runnable() {
                        @Override
                        public void run() {
                                sendEyeId();
                        }
                });
                IndustryDatas industryDatas20 = new IndustryDatas(j++, "获取wifi信息", new Runnable() {
                        @Override
                        public void run() {
                                sendPadConnetHeadWifi();
                        }
                });
                IndustryDatas industryDatas21 = new IndustryDatas(j++, "打开人脸追踪", new Runnable() {
                        @Override
                        public void run() {
                                sendOpenFaceTrack();
                        }
                });
                IndustryDatas industryDatas22 = new IndustryDatas(j++, "关闭人脸追踪", new Runnable() {
                        @Override
                        public void run() {
                                sendCloseFaceTrack();
                        }
                });
                IndustryDatas industryDatas23 = new IndustryDatas(j++, "查询机器ssid", new Runnable() {
                        @Override
                        public void run() {
                                sendQueryU05RobotWifiSSID();
                        }
                });

                IndustryDatas industryDatas24 = new IndustryDatas(j++, "走2步", new Runnable() {
                        @Override
                        public void run() {
                                sendValueMotion();
                        }
                });

                IndustryDatas industryDatas25 = new IndustryDatas(j++, "上传文件", new Runnable() {
                        @Override
                        public void run() {
                                startActivity(new Intent(MainActivity.this, UploadFileActivity.class));
                        }
                });
                IndustryDatas industryDatas26 = new IndustryDatas(j++, "人脸识别", new Runnable() {
                        @Override
                        public void run() {
                                startActivity(new Intent(MainActivity.this, FaceActivity.class));
                        }
                });

                mbrowList.add(industryDatas1);
                mbrowList.add(industryDatas2);
                mbrowList.add(industryDatas3);
                mbrowList.add(industryDatas4);
                mbrowList.add(industryDatas5);
                mbrowList.add(industryDatas6);
                mbrowList.add(industryDatas7);
                mbrowList.add(industryDatas8);
                mbrowList.add(industryDatas9);
                mbrowList.add(industryDatas10);
                mbrowList.add(industryDatas11);
                mbrowList.add(industryDatas12);
                mbrowList.add(industryDatas13);
                mbrowList.add(industryDatas14);
                mbrowList.add(industryDatas15);
                mbrowList.add(industryDatas16);
                mbrowList.add(industryDatas17);
                mbrowList.add(industryDatas18);
                mbrowList.add(industryDatas19);
                mbrowList.add(industryDatas20);
                mbrowList.add(industryDatas21);
                mbrowList.add(industryDatas22);
                mbrowList.add(industryDatas23);
                mbrowList.add(industryDatas24);
                mbrowList.add(industryDatas25);
                mbrowList.add(industryDatas26);
                return mbrowList;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mbrowList.get(i).getRunnable().run();
        }

        /**
         * 请求胸口连接头部Wifi指令：
         * 发送指令后,头部会发送当前外网的ssId和密码到胸口,胸口主应用会自动连接对应的网络
         * 客户端可通过注册广播wifiReceiver来监听胸口网络的连接状态
         * 如果头上没有WIfi信息,会返回 handleResult()  ====》type = MsgType.HEAD_NO_WIFI_INFO
         * @param
         */
        public void sendPadConnetHeadWifi() {
                sendToRobot(MsgType.REQUEST_HEAD_WIFI_INFO, "geWifiInfo");
        }

        @Override
        protected void onDestroy() {
                super.onDestroy();
        }

        /**
         * 发送播放网络音频的指令到机器人
         *
         * 注：data为网址，例如：https://www.ip.com/123
         */
        public void sendPlayUrlSound(String data) {
                sendToRobot(MsgType.PLAY_URL_MUSIC, data);
        }

        /**
         * 发送tts到机器人
         */
        public void sendPlayTTS() {
                sendToRobot(MsgType.PLAY_TTS, "我是北京康力优蓝的机器人优友" + "&&recoder");
        }

        /**
         * 发送播放音频指令到机器人
         */
        public void sendPlaySound() {
                sendToRobot(MsgType.PLAY_SOUND, "/ai/ai05res/a1/res/audio/dance/self_introduction_my.wav&&recoder");
        }

        /**
         * 发送停止音频播放到机器人
         */
        public void sendStopSound() {
                sendToRobot(MsgType.STOP_SOUND, "stop_sound");
        }

        /**
         * 发送关闭声控指令到机器人
         * <p>
         * 注：发送关闭声控指令之后，机器人不接受任何声控命令
         */
        public void sendCloseSound() {
                sendToRobot(MsgType.STOP_RECOGNIZER, "stop");
        }

        /**
         * 发送开启声控指令到机器人
         * <p>
         * 注：发送开启声控指令之后，机器人正常接受声控命令
         */
        public void sendOpenSound() {
                sendToRobot(MsgType.START_RECOGNIZER, "start");
        }

        /**
         * 发送停止动作指令到机器人
         */
        public void sendStopAction() {
                sendToRobot(MsgType.STOP_ACTION, "stop_action");
        }

        /**
         * 发送动作指令到机器人.
		 * U05动作库（外发）.xlsx 里面有目前支持的动作ID列表.
         *
         * @param actionId 对应的动作Id
         */
        public void sendAction(String actionId) {
                sendToRobot(MsgType.ACTION, actionId);
        }

        /**
         * 发送表情指令到机器人.
		 * U05眼睛库（确认版）.xlsx 列出了目前支持的眼睛表情ID.
         *
         * @param emotionId 对应的表情Id
         */
        public void sendEmotion(String emotionId) {
                sendToRobot(MsgType.EYE_MOTION, emotionId);
        }

        /**
         * 发送查询电量信息指令.
         *
         * @param
         */
        public void sendQueryPower() {
                sendToRobot(MsgType.SEND_QUERY_POWER_INFO, "");
        }

        /**
         * 发送查询头部当前WiFi状态、 ssid 指令
         *
         * @param
         */
        public void sendQueryWifiInfo() {
                sendToRobot(MsgType.SEND_MSG_WIFI_STATUS_SSID, "wifi");
        }

        /**
         * 查询机器人自身热点wifi ssid
         */
        public void sendQueryU05RobotWifiSSID() {
                sendToRobot(MsgType.QUERY_U05_ROBOT_WIFI_SSID, "wifi");
        }

        /**
         * 发送头部连接wifi指令
         *
         * @param
         */
        public void sendConnetWifi(String ssid, String password) {
                sendToRobot(MsgType.WIFI_PASSWORD, ssid + "&&" + password);
        }

        /**
         * 打开人脸追踪
         * 注：打开人脸追踪之后，尽量不要调动作，因为会有冲突
         */
        private void sendOpenFaceTrack() {
                sendToRobot(MsgType.SEND_OPEN_GPU_FACE_TRACK, "打开人脸追踪");
        }

        /**
         * 关闭人脸追踪
         *
         */
        private void sendCloseFaceTrack() {
                sendToRobot(MsgType.SEND_CLOSE_GPU_FACE_TRACK, "关闭人脸追踪");
        }
        /**
         * 发送眼睛动画
         */
        public void sendEyeId() {
                sendToRobot(MsgType.EYE_MOTION, "14");
        }

        public void sendToRobot(int msgType, String data) {
                StringMsgBean msgBean = new StringMsgBean();
                msgBean.setMsgType(msgType);
                msgBean.setMsgData(data);
                sendData(msgBean);
        }

        /**
         * 发送具体数值动作指令
         * 例如： 100     表示前进100厘米
         *        -100    表示后退100厘米
         *
         *        距离单位：厘米，数值必须为阿拉伯数字，正数表示前进，负数表示后退
         */
        public void sendValueMotion() {
                sendToRobot(MsgType.SEND_CONCRETE_ACTION, "50");
        }

        /**
         * 向前进
         *
         */
        public void move_forward() {
                sendAction(ActionId.MOVE_FORWARD);
        }

        /**
         * 向后退
         *
         */
        public void move_backward() {
                sendAction(ActionId.MOVE_BACKWARD);
        }

        /**
         * 向左转
         *
         */
        public void turn_left() {
                sendAction(ActionId.TURN_LEFT);
        }

        /**
         * 向右转
         *
         */
        public void turn_right() {
                sendAction(ActionId.TURN_RIGHT);
        }

        /**
         * 向左看
         *
         */
        public void look_left() {
                sendAction(ActionId.LOOK_LEFT);
        }

        /**
         * 向右看
         *
         */
        public void look_right() {
                sendAction(ActionId.LOOK_RIGHT);
        }

        /**
         * 再摇头
         *
         */
        public void shake_head_again() {
                sendAction(ActionId.SHAKE_HEAD_AGAIN);
        }

        /**
         * 摇头
         *
         */
        public void shake_head() {
                sendAction(ActionId.SHAKE_HEAD);
        }

        /**
         * 敬礼
         *
         */
        public void salute() {
                sendAction(ActionId.SALUTE);
        }

        /**
         * 转过身来
         *
         */
        public void turn_bakc() {
                sendAction(ActionId.TURN_BACK);
        }

        /**
         * 转圈
         *
         */
        public void circle() {
                sendAction(ActionId.CIRCLE);
        }

        /**
         * 握手
         *
         */
        public void shake_hand() {
                sendAction(ActionId.SHAKE_HAND);
        }


        /**
         * 注：必须在在onResume方法中绑定远程service，否则可能会出现远程服务无法绑定的情况
         * 在需要接受机器人端消息的界面绑定远程服务
         */
        @Override
        protected void onResume() {
                super.onResume();
                bindService();
        }


        /**
         * 注：必须在onPause方法中解绑远程服务回调接口，解绑远程服务，否则后续可能出现无法接收远程服务消息的情况
         */
        @Override
        protected void onPause() {
                super.onPause();
                unRegisterAIDL();
                unBindMsgService();
        }


        /**
         * 处理收到的结果
         *
         * @param msgType 消息类型
         * @param msgData 消息内容
         * @return true 客户端需要处理,可以根据识别结果自定义处理(访问自己的语义服务器，或者本地处理).
         * false 客户端不处理,返回给机器人使用机器人默认的语义服务器.
         */
        private boolean handleResult(int msgType, String msgData) {
                switch (msgType) {
                        case MsgType.RECEIVER_MSG_VOICE_RECOGNIZER_RESULT:
                                if (msgData.equals("今天天气怎么样")) {     //本地处理

                                        Log.e(TAG, "收到识别结果，本地需要处理: " + msgData);

                                        sendToRobot(MsgType.PLAY_TTS, "今天天气不错" + "&&recoder");

                                        return true;

                                }
                                else {    //本地不处理

                                        Log.e(TAG, "收到识别结果,本地不处理: " + msgData);
                                        return false;

                                }

                        case MsgType.RECEIVER_MSG_SENSOR:   //感应器信息

                                int which = Integer.parseInt(msgData);

                                if (which == Sensor.Belly) {

                                        Log.e(TAG, "肚子感应器被触摸: ");

                                }
                                else if (which == Sensor.Buns) {

                                        Log.e(TAG, "屁股感应器被触摸: ");

                                }
                                else if (which == Sensor.Right_ear) {

                                        Log.e(TAG, "右耳感应器被触摸: ");

                                }
                                else if (which == Sensor.Mouth) {

                                        Log.e(TAG, "下巴感应器被触摸: ");

                                }
                                else if (which == Sensor.Head_top) {

                                        Log.e(TAG, "头顶感应器被触摸: ");

                                }
                                else if (which == Sensor.Left_ear) {

                                        Log.e(TAG, "左耳感应器被触摸: ");

                                }
                                else if (which == Sensor.HUMAN_BODY_FRONT) {

                                        Log.e(TAG, "人体感应器(前面)被触发: ");

                                }else if (which == Sensor.HUMAN_BODY_BACK) {

                                        Log.e(TAG, "人体感应器(后面)被触发: ");

                                }

                                return true;

                        case MsgType.RECEIVER_MSG_WAKE_UP:   //头部唤醒信息

                                Log.e(TAG, "收到头部唤醒: " + msgData);

                                sendToRobot(MsgType.PLAY_TTS, "我在呢" + "&&recoder");
                                sendToRobot(MsgType.RECEIVER_WAKEUP_SOURCE_ROTATE, msgData);

                                return true;

                        case MsgType.RECEIVER_MSG_PLAY_SOUND_END: //语音播放结束

                                Log.e(TAG, "语音播放结束: " + msgData);

                                return true;

                        case MsgType.RECEIVER_MSG_SUSPEND:  //机器人进入休眠（待唤醒）状态

                                Log.e(TAG, "收到机器人进入休眠状态: " + msgData);

                                return true;

                        case MsgType.RECEIVE_POWER_INFO:
                                Log.e(TAG, "收到机器人电量信息: " + msgData); //收到电量信息（需要先发送查询电量指令）
                                return true;

                        case MsgType.RECEIVER_MSG_WIFI_STATUS_SSID:
                                Log.e(TAG, "收到机器人头部WIFI状态信息: " + msgData); //收到机器人头部WIFI状态信息（需要先发送查询wifi    SEND_MSG_WIFI_STATUS_SSID 指令）
                                String[] strings = msgData.split(",");
                                WifiStatus wifiStatus = new WifiStatus(strings[0], strings[1]);
                                Log.e(TAG, "收到机器人头部WIFI状态信息: " + wifiStatus.toString());
                                return true;

                        case MsgType.U05_ROBOT_WIFI_SSID: //收到机器人自身热点SSID（需要先发送查询指令 QUERY_U05_ROBOT_WIFI_SSID , 机器人热点密码是1223334444）
                                if (!TextUtils.isEmpty(msgData)) {
                                        Log.e(TAG, "收到机器人自身热点ssid: " + msgData);
                                }
                                return true;
                        case MsgType.HEAD_NO_WIFI_INFO://头部没有wifi信息时的回调
                                runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                                Toast.makeText(MainActivity.this, "头部没有连接WIFI", Toast.LENGTH_SHORT).show();
                                        }
                                });
                                Log.e(TAG, "头部没有WIFI");
                                break;
                        case MsgType.RECEIVE_MSG_WIFI_SSID_AND_PWD://接收到发送当前连接的 wifi ssid 及 password
                                if (msgData.contains(":")) {
                                        String[] datas = msgData.split(":");
                                        final String ssid = datas[0];
                                        if (datas.length > 1) {
                                                final String pwd = datas[1];
                                                runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                                Toast.makeText(MainActivity.this, "头部WIFI,ssid是:"+ssid+"pwd是:"+pwd, Toast.LENGTH_SHORT).show();
                                                        }
                                                });
                                                boolean b = WifiUtils.getInstance().connect(ssid, pwd);
                                                if (b) {
                                                        Logger.e(TAG, "网络连接成功");
                                                }
                                                else {
                                                        Logger.e(TAG, "网络连接失败");
                                                }
                                        }
                                        else {
                                                Logger.e(TAG, "头部发送的 wifi 不正确");
                                        }
                                }
                                return true;
                }
                return false;
        }



        /*-----------------------         请客户不要随意修改以下代码内容              ---------------------/



        /**
         * 绑定远程服务
         * 请客户不要随意修改此处代码。
         */
        protected void bindService() {
                try {
                        Intent intent = new Intent("com.canbot.u05.service.MsgService");
                        intent.setClassName("com.canbot.u05", "com.canbot.u05.service.MsgService");
                        bindService(intent, connection, Context.BIND_AUTO_CREATE);
                }
                catch (Exception e) {
                        Log.e(TAG, "bindService-----Exception--" + e.toString());
                        e.printStackTrace();
                }
        }


        /**
         * 解绑远程服务
         */
        protected void unBindMsgService() {
                unbindService(connection);
        }


        /**
         * 绑定远程接口
         */
        private void registerAIDL() {
                if (mService != null) {
                        try {
                                mService.registerCallBack(iMsgCallback);
                                Log.e(TAG, "registerAIDL---------");
                        }
                        catch (RemoteException e) {
                                Log.e(TAG, "RemoteException-------" + e.toString());
                        }
                }
        }


        /**
         * 解绑远程接口
         */
        protected void unRegisterAIDL() {
                if (mService != null) {
                        try {
                                mService.unRegisterCallBack(iMsgCallback);
                                mService = null;
                                Log.e(TAG, "unRegisterAIDL---------");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }


        private IMsgBind mService;

        protected ServiceConnection connection = new ServiceConnection() {
                @Override
                public void onServiceDisconnected(ComponentName name) {
                        Log.e(TAG, "onServiceDisconnected-------");

                }

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                        Log.e(TAG, "onServiceConnected-------");
                        mService = IMsgBind.Stub.asInterface(service);
                        registerAIDL();
                }
        };


        /**
         * 远程服务回调接口，用于接收远程服务发送的消息
         * 请客户不要随意修改此处代码
         */
        private IMsgCallBack iMsgCallback = new IMsgCallBack.Stub() {
                @Override
                public boolean interceptRobotAction(final String dataJson) {
                        Log.e(TAG, "onReceiver-------data=" + dataJson);
                        StringMsgBean stringMsgBean = JSON.parseObject(dataJson, StringMsgBean.class);
                        final int msgType = stringMsgBean.getMsgType();
                        final String msgData = stringMsgBean.getMsgData();

                        return handleResult(msgType, msgData);
                }
        };


        /**
         * 通过aidl发送数据
         *
         * @param msgBean
         */
        protected void sendData(StringMsgBean msgBean) {
                if (mService != null) {

                        String jsonString = JSON.toJSONString(msgBean);

                        try {
                                Log.e(TAG, "sendData=" + jsonString);
                                mService.send(jsonString);
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                                Log.e(TAG, "RemoteException=" + e.toString());
                        }
                }
        }

}
