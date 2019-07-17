package com.canbot.u05.sdk.clientdemo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;



import java.util.ArrayList;
import java.util.Iterator;

/**
 * Wifi 状态改变 receiver
 * Created by xh on 2017/8/3.
 */

public class WifiReceiver extends BroadcastReceiver {

        public static final String TAG = "WifiReceiver";

        private static ArrayList<OnWifiConnectStateChangedListener> mOnWifiConnectStateChangedListenerLists = new ArrayList<>();

        /**
         * Wifi 连接状态改变监听器
         */
        public static abstract class OnWifiConnectStateChangedListener {

                /**
                 * Wifi 连接断开
                 */
                public void disconnected() {
                }

                /**
                 * Wifi 连接中
                 */
                public void connecting(String ssid) {
                }

                /**
                 * Wifi 连接成功
                 */
                public void connected(String ssid) {
                }

                /**
                 * Wifi 连接失败
                 */
                public void connectedFailed() {
                }

                /**
                 * 成功连接 U05 Wifi
                 */
                public void connectedU05(String ssid) {

                }

                /**
                 * 成功连接非 U05 Wifi
                 */
                public void connectedNotU05(String ssid) {

                }
        }

//        private static OnWifiConnectStateChangedListener sCurWifiListener;

        // 是否正在遍历
        private static boolean isTraversal = false;

        private static Iterator<OnWifiConnectStateChangedListener> sListenerIterator = mOnWifiConnectStateChangedListenerLists.iterator();

        /**
         * 添加 Wifi 连接状态发生改变的监听
         *
         * @param listener 需要添加的监听器
         */
        public static void addOnWifiConnectStateChangedListener(OnWifiConnectStateChangedListener listener) {
                for (OnWifiConnectStateChangedListener mListener : mOnWifiConnectStateChangedListenerLists) {
                        if (mListener == listener) {
                                return;
                        }
                }
                mOnWifiConnectStateChangedListenerLists.add(listener);
                sListenerIterator = mOnWifiConnectStateChangedListenerLists.iterator();
        }

        /**
         * 移出 Wifi 连接状态发生改变的监听
         *
         * @param listener 需要移出的监听器
         * @return 成功移出为 true，失败为 false
         */
        public static boolean removeOnWifiConnectStateChangedListener(OnWifiConnectStateChangedListener listener) {

                if (isTraversal) {
                        sListenerIterator.remove();
                        return true;
                }

                sListenerIterator = mOnWifiConnectStateChangedListenerLists.iterator();
                while (sListenerIterator.hasNext()) {
                        OnWifiConnectStateChangedListener mListener = sListenerIterator.next();
                        if (mListener == listener) {
                                sListenerIterator.remove();
                                return true;
                        }
                }
                return false;
        }

        //记录网络断开的状态
        private static boolean isDisConnected = false;

        //记录正在连接的状态
        private static boolean isConnecting = false;

        @Override
        public void onReceive(Context context, Intent intent) {
                WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = mWifiManager.getConnectionInfo();

                sListenerIterator = mOnWifiConnectStateChangedListenerLists.iterator();

                if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {// wifi连接上与否
                        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                        Logger.d(TAG, "网络已经改变----------> " + info.getState());
                        if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                                if (isConnecting){
                                        while (sListenerIterator.hasNext()) {
                                                isTraversal = true;
                                                sListenerIterator.next().connectedFailed();
                                        }
                                        isConnecting = false;
                                        isTraversal = false;
                                }

                                if (!isDisConnected) {
                                        isDisConnected = true;
                                        while (sListenerIterator.hasNext()) {
                                                isTraversal = true;
                                                sListenerIterator.next().disconnected();
                                        }
                                        Logger.e(TAG, "wifi已经断开 " + wifiInfo.getSSID());
                                        String ssid = wifiInfo.getSSID();
                                        if (ssid.startsWith("U05-") || ssid.startsWith("\"U05-") || ssid.startsWith("0x") || ssid.startsWith("\"0x") || ssid.contains("unknown ssid")) {
//                                                if (MyCameraService.getInstance() == null){
//                                                        Logger.e(TAG,"打开后台预览");
//                                                        //打开后台预览
//                                                        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(ConstUtils.ACTION_START_MYCAMERASERVICE));
//                                                }
                                        }
                                }
                        }
                        else if (info.getState().equals(NetworkInfo.State.CONNECTING)) {
                                if (!isConnecting) {
                                        isConnecting = true;
                                        while (sListenerIterator.hasNext()) {
                                                isTraversal = true;
                                                sListenerIterator.next().connecting(wifiInfo.getSSID());
                                        }
                                        Logger.e(TAG, "正在连接：" + wifiInfo.getSSID());
                                }
                        }
                        else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                                String stringExtra = intent.getStringExtra(WifiManager.EXTRA_BSSID);
                                boolean isConnect = TextUtils.isEmpty(stringExtra) ? false : true;
                                if (isConnect) {
                                        isConnecting = false;
                                        isDisConnected = false;
                                        while (sListenerIterator.hasNext()) {
                                                isTraversal = true;
                                                OnWifiConnectStateChangedListener listener = sListenerIterator.next();
                                                if ((wifiInfo.getSSID().startsWith("U05-") || wifiInfo.getSSID().startsWith("\"U05-"))) {
                                                        listener.connectedU05(wifiInfo.getSSID());
                                                }
                                                else {
                                                        listener.connectedNotU05(wifiInfo.getSSID());
                                                        Logger.d(TAG, "onReceive:  connect connectedNotU05ssid=");
                                                }
                                                listener.connected(wifiInfo.getSSID());
                                        }
                                        Logger.d(TAG, "onReceive:  connect success...........ssid="+wifiInfo.getSSID());
                                }
                        }
                }
                else if (intent.getAction().equals(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)) {
                        int error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0);
                        switch (error) {
                                case WifiManager.ERROR_AUTHENTICATING:
                                        for (OnWifiConnectStateChangedListener wifiListener : mOnWifiConnectStateChangedListenerLists) {
                                                isTraversal = true;
                                                wifiListener.connectedFailed();
                                        }
                                        mWifiManager.removeNetwork(wifiInfo.getNetworkId());
                                        Logger.e(TAG, wifiInfo.getSSID() + "密码错误");
                                        break;
                                default:
                                        break;
                        }
                }
                isTraversal = false;
        }

        /**
         * 清除被移出的监听
         */
        private static void clearInvalidListener() {
                Iterator<OnWifiConnectStateChangedListener> listenerIterator = mOnWifiConnectStateChangedListenerLists.iterator();
                while (listenerIterator.hasNext()) {
                        OnWifiConnectStateChangedListener listener = listenerIterator.next();
                        if (listener == null) {
                                listenerIterator.remove();
                        }
                }
        }

}
