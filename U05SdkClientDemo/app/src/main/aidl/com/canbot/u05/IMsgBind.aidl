// IMsgBind.aidl
package com.canbot.u05;
import com.canbot.u05.IMsgCallBack ;

interface IMsgBind {
    void send(in String dataJson);
    void registerCallBack(IMsgCallBack callback);
    void unRegisterCallBack(IMsgCallBack callback);
}