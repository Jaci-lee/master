// IMyAidlInterface.aidl
package com.canbot.u05;
// Declare any non-default types here with import statements
//注册结果回调
interface IMapAidlCallBack {
	 void onMapAidlBack(int type,boolean isSuccess,in String data);
}

