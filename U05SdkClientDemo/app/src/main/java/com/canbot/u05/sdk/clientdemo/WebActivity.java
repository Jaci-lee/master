package com.canbot.u05.sdk.clientdemo;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.canbot.u05.sdk.clientdemo.util.WifiReceiver;
import com.uurobot.sdkclientdemo.R;


public class WebActivity extends Activity {

        public static final String TAG = "WebViewActivity";
        private  WebView mWebView;
        private WifiReceiver wifiReceiver;
        private IntentFilter wifiIntentFilter;

        /**
         * Wifi 连接状态改变广播
         */
        private WifiReceiver.OnWifiConnectStateChangedListener mWifiListener = new WifiReceiver.OnWifiConnectStateChangedListener() {
                @Override
                public void connectedNotU05(String ssid) {
                        super.connectedNotU05(ssid);
                        Toast.makeText(WebActivity.this,"外网连接成功：" + ssid,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void connecting(String ssid) {
                        super.connecting(ssid);
                        Log.e("WebActivity", "connecting" + ssid);
                }

                @Override
                public void connectedU05(String ssid) {
                        super.connectedU05(ssid);
                        Toast.makeText(WebActivity.this,"U05内网连接成功：" + ssid,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void connectedFailed() {
                        super.connectedFailed();
                        Log.e("WebActivity", "connectedFailed");
                        Toast.makeText(WebActivity.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void disconnected() {
                        super.disconnected();
                        Toast.makeText(WebActivity.this,"网络已断开",Toast.LENGTH_SHORT).show();
                }
        };



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_web);
        }

        @Override
        protected void onResume() {
                super.onResume();
                initWebView();
                wifiReceiver = new WifiReceiver();
                wifiIntentFilter = new IntentFilter();
                wifiIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
                wifiIntentFilter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
                registerReceiver(wifiReceiver, wifiIntentFilter);
                WifiReceiver.addOnWifiConnectStateChangedListener(mWifiListener);
        }

        @Override
        protected void onPause() {
                super.onPause();
                WifiReceiver.removeOnWifiConnectStateChangedListener(mWifiListener);
        }

        /**
         * 初始化webView
         */
        private void initWebView() {
                mWebView = (WebView) findViewById(R.id.webview);
                //设置编码
                mWebView.getSettings().setDefaultTextEncodingName("utf-8");
                //设置背景颜色 透明
                setWebViewParams();
                //  mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
                String url = "https://www.baidu.com/";

                mWebView.loadUrl(url);
        }

        /**
         * 设置webView 相关的参数
         */
        private void setWebViewParams() {
                //支持javascript
                mWebView.getSettings().setJavaScriptEnabled(true);
                mWebView.setInitialScale(25);//为25%，最小缩放等级
                //扩大比例的缩放
                mWebView.getSettings().setUseWideViewPort(true);
                //自适应屏幕
                mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                //支持缩放
                mWebView.getSettings().setSupportZoom(true);
                // 缩放按钮
                mWebView.getSettings().setBuiltInZoomControls(true);// 设置缩放
                mWebView.getSettings().setDisplayZoomControls(true);
        }



        @Override
        protected void onDestroy() {
                super.onDestroy();
        }

}
