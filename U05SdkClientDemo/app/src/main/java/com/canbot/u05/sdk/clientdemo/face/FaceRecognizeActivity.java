package com.canbot.u05.sdk.clientdemo.face;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.canbot.u05.IMsgBind;
import com.canbot.u05.IMsgCallBack;
import com.canbot.u05.sdk.clientdemo.StringMsgBean;
import com.canbot.u05.sdk.clientdemo.bean.PersonData;
import com.canbot.u05.sdk.clientdemo.bean.PersonVerifyData;
import com.canbot.u05.sdk.clientdemo.bean.VerifyFaceResult;
import com.canbot.u05.sdk.clientdemo.util.BitmapUtils;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.canbot.u05.sdk.clientdemo.util.PathConstant;
import com.cv.faceapi.CvAttributeResult;
import com.google.gson.Gson;
import com.uurobot.sdkclientdemo.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static com.canbot.u05.sdk.clientdemo.util.PathConstant.PREVIEW_HEIGHT;
import static com.canbot.u05.sdk.clientdemo.util.PathConstant.PREVIEW_WIDTH;

/**
 *  人脸识别界面
 */

public class FaceRecognizeActivity extends CameraBaseActivity implements SurfaceHolder.Callback  {

    public static final String TAG = "FaceRecognizeActivity";

    private SurfaceView mIvFace;

    private HandlerThread mHandlerThread;

    private Handler mRegistHandler;

    private List<PersonData> personDatas;

    private String currentResult = "";

    private long currentTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_recognize);
        FaceDbUtils.getInstance().init(this);
        initFaceUtils();
        initView();
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

    private void initView() {
        mIvFace = (SurfaceView) findViewById(R.id.face_sv);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = mIvFace.getLayoutParams();
        layoutParams.width = displayMetrics.heightPixels * 3 / 4;
        layoutParams.height = displayMetrics.heightPixels;
        mIvFace.setLayoutParams(layoutParams);
        mIvFace.getHolder().addCallback(this);
    }

    /**
     * 初始化人脸证书
     */
    private void initFaceUtils() {
        boolean isInit = SenseTimeEngine.getInstance().isInit();
        if (!isInit) {
            Toast.makeText(FaceRecognizeActivity.this,"人脸证书初始化失败",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
            //获取机器本体数据库所有注册的人脸信息
        personDatas = FaceDbUtils.getInstance().findAll();
        Logger.e(personDatas.toString());

        initHandler();
    }

    private void initHandler() {
        mHandlerThread = new HandlerThread(TAG);
        mHandlerThread.start();
        mRegistHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        verifyFace((byte[]) msg.obj);
                        break;
                }
            }
        };
    }


    /**
     * 人脸信息对比
     * @param data
     */
    private void verifyFace(byte[] data) {
        YuvImage image = new YuvImage(data, ImageFormat.NV21, PREVIEW_WIDTH, PREVIEW_HEIGHT, null);
        ByteArrayOutputStream os = new ByteArrayOutputStream(data.length);
        if (!image.compressToJpeg(new Rect(0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT), 100, os)) {
            return;
        }
        byte[] tmp = os.toByteArray();
        Bitmap mBitmap2 = BitmapFactory.decodeByteArray(tmp, 0, tmp.length);
        Bitmap mBitmap = BitmapUtils.adjustPhotoRotation(mBitmap2, 270);
        mBitmap2.recycle();
        CvAttributeResult result;
        result = SenseTimeEngine.getInstance().attributeFace(mBitmap);
        if (result.isHasFace()) {
            byte[] feature = SenseTimeEngine.getInstance().getFaceFeature(mBitmap);
            Log.e(TAG, "正在对比:");
            if (feature != null && feature.length > 0) {

                for (int i = 0; i < personDatas.size(); i++) {
                    PersonData one = personDatas.get(i);
                    float score = SenseTimeEngine.getInstance().compareFeature(one.getFeatures(), feature);
                    Log.e(TAG, "正在对比:" + one.getName() + "---当前匹配度：" + score);
                    if (score > SenseTimeEngine.SCORE) {
                        if (currentResult.equals(one.getIdentification())) {
                            if (System.currentTimeMillis() - currentTimeMillis < 5 * 1000) {
                                mBitmap.recycle();
                                return;
                            }
                        }
                        currentResult = one.getIdentification();
                        currentTimeMillis = System.currentTimeMillis();
                        Log.e("t", "你好!" + one.getName());
                        BitmapUtils.saveAsFile(mBitmap, "snapshot.jpg", PathConstant.FACES_TEMP);
                        mBitmap.recycle();
                        String json = new Gson().toJson(new VerifyFaceResult(0, "人脸识别成功", new PersonVerifyData(one)));
                        //识别人脸成功
                        Log.e("t", "识别人脸成功=" + json);
                        return;
                    }
                }

                if (getString(R.string.face_fail).equals(currentResult)) {
                    Log.e(TAG, "currentResult = " + currentResult);
                    if (System.currentTimeMillis() - currentTimeMillis < 10 * 1000) {
                        Log.e(TAG, "陌生人重复识别");
                        mBitmap.recycle();
                        return;
                    }
                }
                BitmapUtils.saveAsFile(mBitmap, "snapshot.jpg", PathConstant.FACES_TEMP);
                currentResult = getString(R.string.face_fail);
                currentTimeMillis = System.currentTimeMillis();
                String json = new Gson().toJson(new VerifyFaceResult(-2, getString(R.string.face_fail),
                        new PersonVerifyData("陌生人", result.isMale() > 50 ? "男" : "女", "", 0, "", "", "", "", 0)));
                 //识别为陌生人
                Log.e("t", "识别为陌生人=" + json);
                return;
            }
        }
        else {
            mBitmap.recycle();
            if (getString(R.string.no_face).equals(currentResult)) {
                if (System.currentTimeMillis() - currentTimeMillis < 10 * 1000) {
                    return;
                }
            }
            currentResult = getString(R.string.no_face);
            currentTimeMillis = System.currentTimeMillis();
            //没有发现人脸
            Log.e("t", "没有发现人脸");
        }
    }

 public void back(View v){
        finish();
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

    //-----------摄像头回调--------------


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
              initCamera(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                 openCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        recycleCamera();
    }

    @Override
    protected void onPreviewCallback(byte[] data) {
        mRegistHandler.removeMessages(0);
        mRegistHandler.obtainMessage(0, data).sendToTarget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandlerThread != null && mHandlerThread.isAlive()) {
            mHandlerThread.quit();
            mRegistHandler.removeMessages(0);
        }
    }
}
