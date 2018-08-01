package com.canbot.u05.sdk.clientdemo.map;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.canbot.u05.IMapAidlCallBack;
import com.canbot.u05.IMapAidlInterface;
import com.uurobot.sdkclientdemo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MapActivity extends Activity {

        private String TAG = "MapActivity";

        private ServiceConnection connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                        iMapAidlInterface = IMapAidlInterface.Stub.asInterface(service);
                        try {
                                iMapAidlInterface.initMapCallBack(new IMapAidlCallBack.Stub() {
                                        @Override
                                        public void onMapAidlBack(int type, boolean isSuccess, String data) throws RemoteException {
                                                handleMapResult(type, isSuccess, data);
                                        }
                                });
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                                textView.append("aidl异常\r\n");
                        }

                        textView.append("连接成功\r\n");
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                        textView.append("断开连接\r\n");
                }
        };


        private IMapAidlInterface iMapAidlInterface;

        private Toast toast;

        private TextView textView;

        private EditText editText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_map);
                textView = (TextView) findViewById(R.id.tv);
                editText = (EditText) findViewById(R.id.et);
                toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
                bindMapService();
        }

        protected void bindMapService() {
                try {
                        Intent intent = new Intent("map_sdk_service_action");
                        intent.setClassName("com.canbot.u05", "com.canbot.u05.service.map.MapSdkService");
                        bindService(intent, connection, Context.BIND_AUTO_CREATE);
                }
                catch (Exception e) {
                        Log.e(TAG, "bindService-----Exception--" + e.toString());
                        e.printStackTrace();
                }
        }

        @Override
        protected void onDestroy() {
                unbindService(connection);
                super.onDestroy();
        }


        /**
         * 处理地图相关广播
         */
        private void handleMapResult(int type, boolean isSuccess, String data) {
                Log.d(TAG, "handleMapResult: code:" + type + ",isSuccess:" + isSuccess + ",data:" + data);
                switch (type) {
                        case MapMsgType.MAP_SDK_TYPE_CURRENT_MAP:
                                if (isSuccess) {
                                        debugText("地图名:" + data);
                                }
                                else {//没有加载地图
                                        debugText("获取地图名失败");
                                }
                                break;
                        case MapMsgType.MAP_SDK_TYPE_MAP_LIST:
                                if (isSuccess) {
                                        ArrayList<String> mapList = new Gson().fromJson(data, new TypeToken<ArrayList<String>>() {
                                        }.getType());
                                        debugText(data);
                                }
                                else {
                                        debugText("获取地图列表失败");
                                }
                                break;
                        case MapMsgType.MAP_SDK_TYPE_LOAD_MAP:
                                if (isSuccess) {
                                        debugText("地图加载并复位成功");
                                }
                                else if ("success".equals(data)) {
                                        debugText("地图加载成功");
                                }
                                else if ("failed".equals(data)) {
                                        debugText("地图加载失败");
                                }
                                break;
                        case MapMsgType.MAP_SDK_TYPE_RESTORE_MAP:
                                if (isSuccess) {
                                        debugText("地图复位成功");
                                }
                                else {
                                        debugText("地图复位成功");
                                }
                                break;
                        case MapMsgType.MAP_SDK_TYPE_MAP_POINTS:
                                if (isSuccess) {
                                        debugText(data);
                                }
                                else {
                                        debugText("获取位置点失败");
                                }
                                break;
                        case MapMsgType.MAP_SDK_TYPE_MAP_PERSONS:
                                if (isSuccess) {
                                        debugText(data);
                                }
                                else {
                                        debugText("获取人物点失败");
                                }
                                break;
                        /**
                         * 注意位置点 和人物点返回结果 code 是一样的
                         */
                        case MapMsgType.MAP_SDK_TYPE_MOVE_TO_LOCAL_P:
                                if (isSuccess) {
                                        debugText("到达指定点");
                                }
                                else {
                                        debugText("导览失败");
                                }
                                break;
                }
        }

        private void debugText(final String s) {
                if (toast != null) {
                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        toast.setText(s);
                                        toast.show();
                                        textView.append(s + "\r\n");
                                }
                        });
                }
        }

        /**
         * *******************-- map Sdk --***************
         */
        /**
         * 获取当前地图名
         */
        public void getCurrentMapName() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_CURRENT_MAP, "current_map");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 获取地图列表
         */
        public void getMapList() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MAP_LIST, "mapList");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 加载地图
         */
        public void loadMapByName(String mapName) {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_LOAD_MAP, mapName);
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 复位当前地图
         */
        public void restoreMapClient() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_RESTORE_MAP, "restore_map");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 获取当前地图位置点
         */
        public void getLocalPointers() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MAP_POINTS, "map_points");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 获取当前地图人物点
         */
        public void getPersonsPointers() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MAP_PERSONS, "map_persons");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 去目标点
         */
        public void moveToLocalPointer(String pointName) {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MOVE_TO_LOCAL_P, pointName);
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 去找谁
         */
        public void moveToPersonPointer(String pointName) {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MOVE_TO_PERSON_P, pointName);
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 去初始化
         */
        public void moveToInitPointer() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_MOVE_TO_INIT, "init_point");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        /**
         * 停止导览
         */
        public void stopMove() {
                if (iMapAidlInterface != null) {
                        try {
                                iMapAidlInterface.sendMapMsg(MapMsgType.MAP_SDK_TYPE_STOP_MOVE, "stop_move");
                        }
                        catch (RemoteException e) {
                                e.printStackTrace();
                        }
                }
        }

        public void stopMove(View view) {
                stopMove();
        }

        public void moveToInit(View view) {
                moveToInitPointer();
        }

        public void moveToPerson(View view) {
                String trim = editText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                        toast.setText("内容不能为空哦！");
                        return;
                }
                moveToPersonPointer(trim);
        }

        public void moveToLocal(View view) {
                String trim = editText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                        toast.setText("内容不能为空哦！");
                        return;
                }
                moveToLocalPointer(trim);
        }

        public void getPersonsPointers(View view) {
                getPersonsPointers();
        }

        public void getLocalPointers(View view) {
                getLocalPointers();
        }

        public void restoreMap(View view) {
                restoreMapClient();
        }

        public void loadMap(View view) {
                String trim = editText.getText().toString().trim();
                if (TextUtils.isEmpty(trim)) {
                        toast.setText("内容不能为空哦！");
                        return;
                }
                loadMapByName(trim);
        }

        public void getMapList(View view) {
                getMapList();
        }

        public void getCurrentMap(View view) {
                getCurrentMapName();
        }
}
