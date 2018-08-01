package com.canbot.u05.sdk.clientdemo.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.canbot.u05.sdk.clientdemo.MainActivity;
import com.canbot.u05.sdk.clientdemo.MyApplication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by xh on 2017/6/14.
 */

public class WifiUtils {

        // 定义一个WifiManager对象
        private WifiManager mWifiManager;

        // 定义一个WifiInfo对象
        private WifiInfo mWifiInfo;

        // 扫描出的网络连接列表
        private List<ScanResult> mScanWifiList;

        // 网络连接列表
        private List<WifiConfiguration> mWifiConfigurations;

        private android.net.wifi.WifiManager.WifiLock mWifiLock;

        public static final String TAG = "WifiUtils";

        public static final int WIFICIPHER_WEP = 1;

        public static final int WIFICIPHER_NOPASS = 2;

        public static final int WIFICIPHER_WPA = 3;
        public static  WifiUtils wifiUtils ;
        public static WifiUtils getInstance() {
                if (wifiUtils == null) {
                        synchronized (WifiUtils.class){
                                if (wifiUtils == null) {
                                        wifiUtils = new WifiUtils();
                                }
                        }
                }
                return wifiUtils;
        }

        private WifiUtils() {
                init(MyApplication.getContext());
        }

        private void init(Context context) {
                //取得WifiManager对象
                mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                //取得WifiInfo对象
                mWifiInfo = mWifiManager.getConnectionInfo();
        }

        /**
         * Function:关闭wifi<br>
         *
         * @return<br>
         */
        public boolean closeWifi() {
                if (mWifiManager.isWifiEnabled()) {
                        return mWifiManager.setWifiEnabled(false);
                }
                return false;
        }

        /**
         * Gets the Wi-Fi enabled state.检查当前wifi状态
         */
        public int checkState() {
                return mWifiManager.getWifiState();
        }

        // 锁定wifiLock
        public void acquireWifiLock() {
                mWifiLock.acquire();
        }

        // 解锁wifiLock
        public void releaseWifiLock() {
                // 判断是否锁定
                if (mWifiLock.isHeld()) {
                        mWifiLock.acquire();
                }
        }

        // 创建一个wifiLock
        public void createWifiLock() {
                mWifiLock = mWifiManager.createWifiLock("test");
        }

        // 得到配置好的网络
        public List<WifiConfiguration> getConfiguration() {
                return mWifiConfigurations;
        }

        // 指定配置好的网络进行连接
        public void connetionConfiguration(int index) {
                if (index > mWifiConfigurations.size()) {
                        return;
                }
                // 连接配置好指定ID的网络
                mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
        }

        public void startScan() {
                // 开启wifi
                openWifi();
                // 开始扫描
                mWifiManager.startScan();
                // 得到扫描结果
                mScanWifiList = mWifiManager.getScanResults();
                // 得到配置好的网络连接
                mWifiConfigurations = mWifiManager.getConfiguredNetworks();
        }

        // 得到网络列表
        public List<ScanResult> getWifiList() {
                return mScanWifiList;
        }

        // 查看扫描结果
        public StringBuffer lookUpScan() {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < mScanWifiList.size(); i++) {
                        sb.append("Index_" + new Integer(i + 1).toString() + ":");
                        // 将ScanResult信息转换成一个字符串包
                        // 其中把包括：BSSID、SSID、capabilities、frequency、level
                        sb.append((mScanWifiList.get(i)).toString()).append("\n");
                }
                return sb;
        }

        public String getSSID() {
                return mWifiInfo == null ? null : mWifiInfo.getSSID();
        }

        public String getMacAddress() {
                return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
        }

        /**
         * Return the basic service set identifier (BSSID) of the current access
         * point. The BSSID may be {@code null} if there is no network currently
         * connected.
         *
         * @return the BSSID, in the form of a six-byte MAC address:
         * {@code XX:XX:XX:XX:XX:XX}
         */
        public String getBSSID() {
                return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
        }

        public int getIpAddress() {
                return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
        }

        /**
         * the network ID, or -1 if there is no currently connected network
         */
        public int getNetWordId() {
                return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
        }

        /**
         * Function: 得到wifiInfo的所有信息
         */
        public String getWifiInfo() {
                return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
        }

        // 添加一个网络并连接
        public void addNetWork(WifiConfiguration configuration) {
                int wcgId = mWifiManager.addNetwork(configuration);
                mWifiManager.enableNetwork(wcgId, true);
        }

        // 断开指定ID的网络
        public void disConnectionWifi(int netId) {
                mWifiManager.disableNetwork(netId);
                mWifiManager.disconnect();
        }

        /**
         * Function: 打开wifi功能<br>
         *
         * @return true:打开成功；false:打开失败<br>
         */
        public boolean openWifi() {
                boolean bRet = true;
                if (!mWifiManager.isWifiEnabled()) {
                        bRet = mWifiManager.setWifiEnabled(true);
                }
                return bRet;
        }

        /**
         * 给外部提供一个借口，连接无线网络
         *
         * @param SSID     ssid
         * @param Password pwd
         * @return true：开始连接 false：无法连接<br>
         */
        public boolean connect(String SSID, String Password) {
                Logger.e(TAG, "SSID = " + SSID);
                int Type = getCipherType(mWifiManager, SSID);

                if (!this.openWifi()) {
                        return false;
                }
                // 状态变成WIFI_STATE_ENABLED的时候才能执行下面的语句
                while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                        try {
                                // 为了避免程序一直while循环，让它睡个100毫秒在检测……
                                Thread.currentThread();
                                Thread.sleep(100);
                        }
                        catch (InterruptedException ie) {
                        }
                }

                // 查看以前是否也配置过这个网络
                WifiConfiguration tempConfig = this.isExsits(SSID);
                if (tempConfig != null) {
                        mWifiManager.removeNetwork(tempConfig.networkId);
                }

                WifiConfiguration wifiConfig = createWifiInfo(SSID, Password, Type);

                // 添加一个新的网络描述为一组配置的网络。
                int netID = mWifiManager.addNetwork(wifiConfig);
                // 设置为true,使其他的连接断开
                boolean mConnectConfig = mWifiManager.enableNetwork(netID, true);
                mWifiManager.saveConfiguration();

                return mConnectConfig;
        }

        // 查看以前是否也配置过这个网络
        private WifiConfiguration isExsits(String SSID) {
                List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
                if (existingConfigs == null || existingConfigs.size() == 0) {
                        return null;
                }
                for (WifiConfiguration existingConfig : existingConfigs) {
                        if (existingConfig.SSID.equals(SSID) || existingConfig.SSID.equals("\"" + SSID + "\"")) {
                                return existingConfig;
                        }
                }
                return null;
        }

        private WifiConfiguration createWifiInfo(String SSID, String Password, int Type) {
                WifiConfiguration config = new WifiConfiguration();

                config.SSID = SSID;

                if (Type == WIFICIPHER_NOPASS) { // WIFICIPHER_NOPASS
                        config.preSharedKey = null;
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                }
                if (Type == WIFICIPHER_WEP) { // WIFICIPHER_WEP
                        config.preSharedKey = "\"" + Password + "\"";
                        config.hiddenSSID = true;
                        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        config.wepTxKeyIndex = 0;
                }
                if (Type == WIFICIPHER_WPA) { //WIFICIPHER_WPA
                        // 修改之后配置
                        config.preSharedKey = "\"" + Password + "\"";
                }
                return config;
        }

        /**
         * Function:判断扫描结果是否连接上<br>
         *
         * @param result
         * @return<br>
         */
        public boolean isConnect(ScanResult result) {
                if (result == null) {
                        return false;
                }

                mWifiInfo = mWifiManager.getConnectionInfo();
                String g2 = "\"" + result.SSID + "\"";
                return g2.equals(mWifiInfo.getSSID());
        }

        /**
         * Function: 将int类型的IP转换成字符串形式的IP<br>
         *
         * @param ip
         * @return<br>
         */
        public String ipIntToString(int ip) {
                try {
                        byte[] bytes = new byte[4];
                        bytes[0] = (byte) (0xff & ip);
                        bytes[1] = (byte) ((0xff00 & ip) >> 8);
                        bytes[2] = (byte) ((0xff0000 & ip) >> 16);
                        bytes[3] = (byte) ((0xff000000 & ip) >> 24);
                        return Inet4Address.getByAddress(bytes).getHostAddress();
                }
                catch (Exception e) {
                        return "";
                }
        }

        public int getConnNetId() {
                // result.SSID;
                mWifiInfo = mWifiManager.getConnectionInfo();
                return mWifiInfo.getNetworkId();
        }

        /**
         * 忘记指定 ssid 的 wifi 配置
         *
         * @param ssid wifi 名称
         */
        public void forgetNet(String ssid) {
                WifiConfiguration configuration = isExsits(ssid);
                if (configuration != null) {
                        mWifiManager.removeNetwork(configuration.networkId);
                }
        }

        /**
         * Function:信号强度转换为字符串
         *
         * @param level
         * @author Xiho
         */
        public static String singlLevToStr(int level) {

                String resuString = "无信号";

                if (Math.abs(level) > 100) {
                }
                else if (Math.abs(level) > 80) {
                        resuString = "弱";
                }
                else if (Math.abs(level) > 70) {
                        resuString = "强";
                }
                else if (Math.abs(level) > 60) {
                        resuString = "强";
                }
                else if (Math.abs(level) > 50) {
                        resuString = "较强";
                }
                else {
                        resuString = "极强";
                }
                return resuString;
        }

        /**
         * 添加到网络
         *
         * @param wcg
         * @author Xiho
         */
        public boolean addNetwork(WifiConfiguration wcg) {
                if (wcg == null) {
                        return false;
                }
                // receiverDhcp = new ReceiverDhcp(ctx, mWifiManager, this,
                // wlanHandler);
                // ctx.registerReceiver(receiverDhcp, new
                // IntentFilter(WifiUtils.NETWORK_STATE_CHANGED_ACTION));
                int wcgID = mWifiManager.addNetwork(wcg);
                boolean b = mWifiManager.enableNetwork(wcgID, true);
                mWifiManager.saveConfiguration();
                System.out.println(b);
                return b;
        }

        public boolean connectSpecificAP(String ssid) {
                boolean networkInSupplicant = false;
                boolean connectResult = false;
                // 重新连接指定AP
                mWifiManager.disconnect();
                WifiConfiguration tempCofig = isExsits(ssid);
                if (tempCofig != null) {
                        connectResult = mWifiManager.enableNetwork(tempCofig.networkId, true);
                        networkInSupplicant = true;
                }

                if (!networkInSupplicant) {
                        WifiConfiguration config = new WifiConfiguration();
                        config.hiddenSSID = false;
                        config.status = WifiConfiguration.Status.ENABLED;
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        config.SSID = ssid;
                        config.preSharedKey = null;
                        connectResult = addNetwork(config);
                }

                return connectResult;
        }

        // 然后是一个实际应用方法，只验证过没有密码的情况：
        public WifiConfiguration CreateWifiInfo(ScanResult scan, String Password) {
                WifiConfiguration config = new WifiConfiguration();
                config.hiddenSSID = false;
                config.status = WifiConfiguration.Status.ENABLED;

                config.SSID = scan.SSID;

                if (scan.capabilities.contains("WEP")) {
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);

                        config.wepTxKeyIndex = 0;
                        config.wepKeys[0] = Password;
                        // config.preSharedKey = "\"" + SHARED_KEY + "\"";
                }
                else if (scan.capabilities.contains("PSK")) {
                        config.preSharedKey = "\"" + Password + "\"";
                }
                else if (scan.capabilities.contains("EAP")) {
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
                        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                        //            config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                        config.preSharedKey = "\"" + Password + "\"";
                }
                else {
                        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

                        config.SSID = "\"" + scan.SSID + "\"";
                        // config.BSSID = info.mac;
                        config.preSharedKey = null;
                        //
                }
                return config;
        }

        private int getCipherType(WifiManager mWifiManager, String ssid) {
                List<ScanResult> list = mWifiManager.getScanResults();
                for (ScanResult scResult : list) {
                        if (!TextUtils.isEmpty(scResult.SSID) && scResult.SSID.equals(ssid)) {
                                String capabilities = scResult.capabilities;
                                Log.e(TAG, "capabilities=" + capabilities);
                                if (!TextUtils.isEmpty(capabilities)) {
                                        if (capabilities.contains("WPA") || capabilities.contains("wpa")) {
                                                Log.e(TAG, "wpa");
                                                return WIFICIPHER_WPA;
                                        }
                                        else if (capabilities.contains("WEP") || capabilities.contains("wep")) {
                                                Log.e(TAG, "wep");
                                                return WIFICIPHER_WEP;
                                        }
                                        else {
                                                Log.e(TAG, "no");
                                                return WIFICIPHER_NOPASS;
                                        }
                                }
                                else {
                                        return WIFICIPHER_NOPASS;
                                }
                        }
                }
                return WIFICIPHER_NOPASS;
        }

        /**
         * 忘记所有保存的 wifi
         */
        public static void forgetAllWifi() {
                WifiUtils wifiUtils = getInstance();
                List<WifiConfiguration> configurationList = wifiUtils.mWifiManager.getConfiguredNetworks();
                if (configurationList != null && configurationList.size() > 0) {
                        for (WifiConfiguration configuration : configurationList) {
                                wifiUtils.mWifiManager.removeNetwork(configuration.networkId);
                        }
                }
        }

        /**
         * 忘记网络2
         *
         * @param context
         */
        public void forgetnet(Context context) {
                //获取当前连接的wifi信息
                WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                if (wifiInfo != null) {
                        String ssid = wifiInfo.getSSID();
                        ssid = ssid.replace("\"", "");
                        if (ssid.startsWith("U05-") || ssid.startsWith("\"U05-")) {
                                WifiConnect wifiConnect = new WifiConnect(wifiManager);
                                wifiConnect.forgetnet(ssid);
                        }
                        Logger.d("NetworkServlet", "忘记当前网络成功");

                }
                else {
                        Logger.d("NetworkServlet", "忘记网络2 当前没有可断开的网络");

                }

        }


        public void setTime(String time) {
                DataOutputStream os = null;
                try {
                        Process process = Runtime.getRuntime().exec("su");
                        os = new DataOutputStream(process.getOutputStream());
                        os.writeBytes("/system/bin/date -s" + time + "\n");
                        os.writeBytes("setprop persist.sys.timezone Asia/Shanghai\n");
                        os.writeBytes("clock -w\n");
                        os.writeBytes("exit\n");
                        os.flush();
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
                finally {
                        if (os != null) {
                                try {
                                        os.close();
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }

        public boolean isConnectU05() {
                String connectWifiSsId = getConnectWifiSsId();
                if (TextUtils.isEmpty(connectWifiSsId)) {
                        return false;
                }
                return connectWifiSsId.contains("U05-");
        }

        public String getConnectWifiSsId() {
                if (mWifiInfo == null) {
                        return null;
                }
                String ssId = mWifiInfo.getSSID();
                if (TextUtils.isEmpty(ssId)) {
                        return null;
                }
                return ssId;
        }
}
