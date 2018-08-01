package com.canbot.u05.sdk.clientdemo.face;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivityNoTitle extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                hideBottomUIMenu();
        }

        @Override
        protected void onResume() {
                // TODO Auto-generated method stub
                super.onResume();
                hideBottomUIMenu();
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
                // TODO Auto-generated method stub
//	        switch(ev.getAction()){
//	            case MotionEvent.ACTION_DOWN:
//	                Logger.v("BaseActivityNoTitle", "dispatchTouchEvent");
//	                synchronized (MainApplication.LOCK) {
//	                        ((MainApplication)getApplication()).touchTime = System.currentTimeMillis();
//	                    }
//	                break;
//	            }
                return super.dispatchTouchEvent(ev);
        }


        /**
         * 隐藏虚拟按键，并且全屏
         */
        protected void hideBottomUIMenu() {

                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                        //布局位于状态栏下方
                                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                        //全屏
                                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                                        //隐藏导航栏
                                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                                if (Build.VERSION.SDK_INT >= 19) {
                                        uiOptions |= 0x00001000;
                                }
                                else {
                                        uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                                }
                                getWindow().getDecorView().setSystemUiVisibility(uiOptions);
                        }
                });


//	        //隐藏虚拟按键，并且全屏
//	        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
//	            View v = this.getWindow().getDecorView();
//	            v.setSystemUiVisibility(View.GONE);
//	        } else if (Build.VERSION.SDK_INT >= 19) {
//	            //for new api versions.
//	            View decorView = getWindow().getDecorView();
//	            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//	                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
//	            decorView.setSystemUiVisibility(uiOptions);
//	        }
        }

}
