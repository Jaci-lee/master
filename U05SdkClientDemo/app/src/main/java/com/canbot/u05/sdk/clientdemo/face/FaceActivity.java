package com.canbot.u05.sdk.clientdemo.face;

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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.canbot.u05.IMsgBind;
import com.canbot.u05.IMsgCallBack;
import com.canbot.u05.sdk.clientdemo.StringMsgBean;
import com.canbot.u05.sdk.clientdemo.bean.PersonData;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.uurobot.sdkclientdemo.R;

import java.io.File;

/**
 * 人脸注册 删除 （使用人脸相关时 必须初始化FaceDbUtils和RegisterClient，已经在MyApplication中初始化）
 */

public class FaceActivity extends Activity {

    public static final String TAG = "FaceActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);

    }



    /**
     * 注：必须在在onResume方法中绑定远程service，否则可能会出现远程服务无法绑定的情况
     *     在需要接受机器人端消息的界面绑定远程服务
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        bindService();
    }


    /**
     *注：必须在onPause方法中解绑远程服务回调接口，解绑远程服务，否则后续可能出现无法接收远程服务消息的情况
     */
    @Override
    protected void onPause() {
        super.onPause();
        unRegisterAIDL();
        unBindMsgService();
    }

    /**
     * 注册一条人脸信息
     * @param view
     */
    public void register(View view){
        //先获取数据库最大identification,然后注册
        File file = new File("/mnt/internal_sd/a.jpg");
            if (!file.exists()){
                    Toast.makeText(this, "图片不存在,请将要注册的图片命名为a.jpg并放入sdcard下", Toast.LENGTH_SHORT).show();
                   return;
            }
            String maxId = findMax();
        if (TextUtils.isEmpty(maxId)){
            Log.e(TAG,"获取最大值为null");
            maxId = "0";
        }
        Logger.e(TAG,"获取的max ifen=" + maxId);
        PersonData personData=new PersonData();
        personData.setIdentification(Integer.parseInt(maxId) + 1 +"");
        personData.setName("小花");
        personData.setPath("/mnt/internal_sd/a.jpg");
        personData.setSex("女");
        //回复语音  1表示tts  2表示音频，0 表示打开机器人自有人脸识别功能时播放默认语音
//      personData.setVoicetype(1);
        personData.setVoicetype(2);
        //音频文件路径或tts内容
//      personData.setContent("很高兴认识你啊");
        //音频路径不需要从SD卡根目录
        //   /mnt/internal_sd/ai/ai05res/a1/res/audio/chat/sk_01.wav
        personData.setContent("/ai/ai05res/a1/res/audio/chat/sk_01.wav");

        String data = JSON.toJSONString(personData);

        RegisterClient.getInstance().registe(data, new RegisterClient.IVerifyListener() {
            @Override
            public void onVerify(boolean success, boolean isInsert) {
                if (success){
                    Log.e(TAG,"注册结果=" + success);
                }else{
                    if(isInsert){
                        Log.e(TAG,"ID重复了，注册失败" );

                    }else{
                        Log.e(TAG,"未能找到人脸，注册失败" );

                    }
                }
            }
        });
    }

    /**
     * 删除人脸信息
     * @param view
     */
    public void delete(View view){
       FaceDbUtils.getInstance().deleteByName("小花");
    }

    /**
     * 跳转界面
     * @param view
     */
    public void recognizeFace(View view){
            startActivity(new Intent(FaceActivity.this, FaceRecognizeActivity.class));
    }


    /**
     * 处理收到的结果
     *
     * @param msgType   消息类型
     * @param msgData   消息内容
     * @return          true 客户端需要处理,可以根据识别结果自定义处理(访问自己的语义服务器，或者本地处理).
     *                  false 客户端不处理,返回给机器人使用机器人默认的语义服务器.
     */
    private boolean handleResult(int msgType, String msgData) {
        Log.e(TAG, "收到MsgType，msgType: " + msgType +"msgdata = " + msgData);
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
        } catch (Exception e) {
            Log.e(TAG, "bindService-----Exception--" + e.toString());
            e.printStackTrace();
        }
    }


    /**
     * 解绑远程服务
     */
    protected  void unBindMsgService(){
        unbindService(connection);
    }


    /**
     * 绑定远程接口
     */
    private void registerAIDL() {
        if (mService != null) {
            try {
                mService.registerCallBack(iMsgCallback);
                Log.e(TAG,"registerAIDL---------");
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException-------" + e.toString());
            }
        }
    }


    /**
     * 解绑远程接口
     */
    protected void unRegisterAIDL() {
        if (mService!=null){
            try {
                mService.unRegisterCallBack(iMsgCallback);
                mService = null ;
                Log.e(TAG,"unRegisterAIDL---------");
            } catch (RemoteException e) {
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
     *
     */
    private IMsgCallBack iMsgCallback = new IMsgCallBack.Stub() {
        @Override
        public boolean interceptRobotAction(final String dataJson) {
            Log.e(TAG, "onReceiver-------data=" + dataJson);
            StringMsgBean stringMsgBean = JSON.parseObject(dataJson, StringMsgBean.class);
            final int msgType = stringMsgBean.getMsgType();
            final String msgData = stringMsgBean.getMsgData();

            return handleResult(msgType,msgData);
        }

        //========以下是人脸注册 删除等相关=======

    };

    /**
     * 查询数据库目前最大的Identification值
     * @return
     */
    public String findMax(){
        return FaceDbUtils.getInstance().findMaxIdentification();
    }


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
            } catch (RemoteException e) {
                e.printStackTrace();
                Log.e(TAG, "RemoteException=" + e.toString());
            }
        }
    }

}
