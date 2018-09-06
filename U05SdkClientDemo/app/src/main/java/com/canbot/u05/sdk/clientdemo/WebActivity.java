package com.canbot.u05.sdk.clientdemo;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.uurobot.sdkclientdemo.R;


public class WebActivity extends Activity {

        public static final String TAG = "WebViewActivity";
        private  WebView mWebView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_web);
                initWebView();
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
