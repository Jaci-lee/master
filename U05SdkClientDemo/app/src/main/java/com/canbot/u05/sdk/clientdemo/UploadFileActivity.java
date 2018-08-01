package com.canbot.u05.sdk.clientdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.canbot.u05.IMsgBind;
import com.canbot.u05.IMsgCallBack;
import com.canbot.u05.sdk.clientdemo.bean.JsonResponse;
import com.canbot.u05.sdk.clientdemo.util.ConstUtils;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.canbot.u05.sdk.clientdemo.util.PathConstant;
import com.canbot.u05.sdk.clientdemo.util.WifiUtils;
import com.uurobot.sdkclientdemo.R;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 客户端实现流程:
 * 1. 通过设置界面，给头部连接外网
 * 2. 点击该界面的连接网络按钮,去获取头部当前连接外网的ssId和密码
 * 3. 在 handleResult方法，接收到ssid和密码并让胸口连接该网络
 * 4. 网络连接成功后，胸口与头部在同一局域网内，即可收到头部通过组播发送的消息："I am 01"，进而获取到头部的ip地址。
 * 5. 点击该界面的上传文件按钮，上传文件
 * 6. 点击该界面的播放音频按钮，播放音频
 * <p>
 * 客户可以通过此sdk实现的功能：文件上传到头部指定路径下
 * <p>
 */

public class UploadFileActivity extends Activity implements View.OnClickListener {

        public static final String TAG = "UploadFileActivity";

        private MulticastSocket ds;

        private String multicastHost = "224.0.0.1";

        private InetAddress receiveAddress;

        private OkHttpClient client;

        private String path;

        /**
         * 头部URL
         */
        private String hostUrl = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_upload_file);
                getServerIp();
                initView();

        }

        private void initView() {
                client = new OkHttpClient();

                findViewById(R.id.connectWifi).setOnClickListener(this);
                findViewById(R.id.upload).setOnClickListener(this);
                findViewById(R.id.play).setOnClickListener(this);
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

        @Override
        public void onClick(View v) {
                switch (v.getId()){
                        case R.id.connectWifi:
                                sendToRobot(MsgType.REQUEST_HEAD_WIFI_INFO, "geWifiInfo");
                                break;
                        case R.id.upload:
                                upload();
                                break;
                        case R.id.play:
                                sendToRobot(MsgType.PLAY_SOUND, path);
                                break;
                        default:
                                break;
                }

        }

        /**
         *  上传文件
         *
         *  filePath：用户指定的 头部存放上传文件的路径
         *  fileName：文件名称
         *  audiofile：要上传的文件（文件必须存在）
         *
         *  成功后返回  path = filePath + fileName
        * */
        public void upload(){

                if(TextUtils.isEmpty(hostUrl)){
                        Logger.e(TAG, "hostUrl is null");
                        return;
                }
                HashMap<String, Object> hasMap = new HashMap<>();

                final File file = new File(PathConstant.ROOT_PATH +"/ai/sound/" ,"rt_dj_01.wav");

                hasMap.put("filePath", "/sound/");
                hasMap.put("audiofile", file);
                hasMap.put("fileName", "rt_dj_01.wav");


                upLoadFile(hostUrl + "uploadFile", hasMap, new OkHttpCallBack(){

                        @Override
                        public void onSuccess(String result) {
                                final JsonResponse strMsgBean = JSON.parseObject(result, JsonResponse.class);
                                if (strMsgBean.getRc() == 0) {
                                        path = strMsgBean.getResult().toString();
                                        Logger.e(TAG, path);
                                }

                        }

                        @Override
                        public void onFailure() {
                                Logger.e(TAG, "key" +  "onFailure: ");
                        }
                });
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
                        case MsgType.RECEIVE_MSG_WIFI_SSID_AND_PWD://接收到发送当前连接的 wifi ssid 及 password
                                String ssid = "";
                                String pwd = "";
                                if (msgData.contains(":")) {
                                        String[] datas = msgData.split(":");
                                        ssid = datas[0];
                                        if (datas.length > 1) {
                                                pwd = datas[1];
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

        /**
         * 上传文件
         *
         * @param actionUrl 接口地址
         * @param paramsMap 参数
         * @param callBack  回调
         */
        private void upLoadFile(String actionUrl, HashMap<String, ? extends Object> paramsMap, final OkHttpCallBack callBack) {
                try {
                        //补全请求地址
                        MultipartBody.Builder builder = new MultipartBody.Builder();
                        //设置类型
                        builder.setType(MultipartBody.FORM);
                        //追加参数
                        for (String key : paramsMap.keySet()) {
                                Object object = paramsMap.get(key);
                                Logger.e(TAG, "file: " + object);
                                if (null == object) {
                                        continue;
                                }
                                if (!(object instanceof File)) {
                                        builder.addFormDataPart(key, object.toString());
                                }
                                else {
                                        File file = (File) object;
                                        Logger.v(TAG, "key" + key + "file: " + file.getName());
                                        String uploadType = "application/*";//二进制
                                        if (key.equals("audiofile")) {
                                                uploadType = "audio/*";
                                        }
                                        else if (key.equals("imgfile")) {
                                                uploadType = "image/*";
                                        }
                                        builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(uploadType), file));
                                }
                        }
                        //创建RequestBody
                        RequestBody body = builder.build();
                        //创建Request
                        final Request request = new Request.Builder().url(actionUrl).post(body).build();
                        //单独设置参数 比如读取超时时间
                        final Call call = client.newBuilder().writeTimeout(500, TimeUnit.SECONDS).connectTimeout(500, TimeUnit.SECONDS).readTimeout(500, TimeUnit.SECONDS).build().newCall(request);
                        call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                        Log.e(TAG, "response ----->" + e.toString());
                                        if (callBack != null) {
                                                callBack.onFailure();
                                        }
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                        if (response.isSuccessful()) {
                                                String string = response.body().string();
                                                Log.e(TAG, "response ----->" + string);
                                                if (callBack != null) {
                                                        callBack.onSuccess(string);
                                                }
                                        }
                                        else {
                                                if(callBack != null) {
                                                        callBack.onFailure();
                                                }
                                        }
                                }

                        });
                }
                catch (Exception e) {
                        if (callBack != null) {
                                callBack.onFailure();
                        }
                }
        }


        public interface OkHttpCallBack {

                public void onSuccess(String result);

                public void onFailure();
        }

        /**
         * 增加接受组播,获取头部服务器ip
         */
        private void getServerIp() {

                new Thread(new Runnable() {

                        @Override
                        public void run() {
                                Logger.e(TAG, "开始监听组播信息");
                                try {
                                        ds = new MulticastSocket(8003);
                                        receiveAddress = InetAddress.getByName(multicastHost);
                                        ds.joinGroup(receiveAddress);
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                }


                                byte buf[] = new byte[8192];
                                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                                while (true) {
                                        try {
                                                ds.receive(dp);
                                        }
                                        catch (IOException e) {
                                                e.printStackTrace();
                                        }

                                        String a = new String(dp.getData(), 0, dp.getLength());

                                        if (a.equals("I am 01")) {
                                                Logger.e(TAG, "收到 I am 01");
                                                hostUrl = "http://" + dp.getAddress().toString().substring(1) + ":7755/";
                                                Logger.e("I am 01", hostUrl);
                                        }
                                }
                        }
                }).start();

        }

        public void sendToRobot(int msgType, String data) {
                StringMsgBean msgBean = new StringMsgBean();
                msgBean.setMsgType(msgType);
                msgBean.setMsgData(data);
                sendData(msgBean);
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
