// IMyAidlInterface.aidl
package com.canbot.u05;
// Declare any non-default types here with import statements
import com.canbot.u05.IMapAidlCallBack;
interface IMapAidlInterface {
	 void initMapCallBack(IMapAidlCallBack back);
	 void sendMapMsg(int msgType,in String msgData);
}

