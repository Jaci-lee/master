package com.canbot.u05.sdk.clientdemo;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;

import com.canbot.u05.sdk.clientdemo.face.FaceDbUtils;
import com.canbot.u05.sdk.clientdemo.face.RegisterClient;
import com.canbot.u05.sdk.clientdemo.face.SenseTimeEngine;

/**
 * Created by 110 on 2018/1/16.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        //初始化数据库相关
        FaceDbUtils.getInstance().init(this);

        HandlerThread ht = new HandlerThread("back");
        ht.start();
        Looper myLooper = ht.getLooper();

        Handler mHandler = new Handler(myLooper) {

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 4:
                        boolean init = SenseTimeEngine.getInstance().isInit();
                        if (!init) {
                            SenseTimeEngine.getInstance().init(onSenseTimeInitListener);
                        }
                    default:
                        break;
                }
            }
        };
        mHandler.sendEmptyMessageDelayed(4, 1000);
    }

    SenseTimeEngine.OnSenseTimeInitListener onSenseTimeInitListener = new SenseTimeEngine.OnSenseTimeInitListener() {
        @Override
        public void onSuccess() {
            Log.e("test", "SenseTimeEngine-->onSuccess");
            //初始化人脸注册相关
            RegisterClient.getInstance().init(getApplicationContext());
        }

        @Override
        public void onFail(String message) {
            Log.e("test", "SenseTimeEngine-->" + message);
            long l = System.currentTimeMillis();
            CharSequence format = DateFormat.format("yyyy-MM-dd,HH：mm：ss", l);
            Log.e("test", "SenseTimeEngine-->" + format);
//                        throw new RuntimeException(message);
//            handler.postDelayed(runnable,2*1000);
        }
    };

    public static Context getContext() {
        return mContext;
    }
}
