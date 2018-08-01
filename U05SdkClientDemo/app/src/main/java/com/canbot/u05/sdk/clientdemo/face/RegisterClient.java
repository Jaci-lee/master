package com.canbot.u05.sdk.clientdemo.face;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.canbot.u05.sdk.clientdemo.bean.PersonVerifyData;
import com.canbot.u05.sdk.clientdemo.util.Logger;

/**
 * 人脸注册类，必须先初始化 ；必须先初始化FaceDbUtils
 */
public class RegisterClient {

        protected static final String TAG = "VerifyClient";

        private Context mContext;

        private static RegisterClient instance;

        private IVerifyListener listener;

        private VerifyUtil mVerifyUtil;

        private RegisterClient() {
        }

        public static RegisterClient getInstance() {
                if (instance == null) {
                        instance = new RegisterClient();
                }

                return instance;
        }

        public void init(Context context) {
                if (this.mContext == null) {
                        this.mContext = context;
                        mVerifyUtil = VerifyUtil.getInstace(context);
                }
        }

        public void registe(String json, IVerifyListener listener) {
                this.registe(json, listener, false);
        }


        /**
         * 发送信息给服务端 注意必须加 msg.replyTo = sendMessenger ，不然收不到回复的信息.
         * 通过json 信息注册
         *
         * @param isDelete 注册成功后是否删除原有图片
         * @param json
         */
        public void registe(String json, IVerifyListener listener, boolean isDelete) {
                this.listener = listener;
                PersonVerifyData facemsg = JSON.parseObject(json, PersonVerifyData.class);
                String identification = facemsg.getIdentification();
                Log.e(TAG,"identification=" +identification);
//                boolean isInsert = FaceDbUtils.getInstance().isInsert(identification);
                boolean isInsert = FaceDbProviderUtils.query(mContext,identification);
                // true 已经注册过 false 没有注册过
                boolean isSuccess;
                if (isInsert) {// 已经注册过
                        Logger.e("test", "已经注册过  ，返回失败给客户端");
                        isSuccess = false;
                }
                else {
                        isSuccess = mVerifyUtil.verify(BitmapFactory.decodeFile(facemsg.getPath()), facemsg, isDelete);
                        if (isSuccess) {
                                Logger.e("test", "注册成功 ");
                        }
                        else {
                                Logger.e("test", "注册失败 ");
                        }
                }
                if (listener != null) {
                        listener.onVerify(isSuccess, isInsert);
                }
        }




        /**
         * 注册结果回调
         *
         * @author zmp
         */
        public interface IVerifyListener {

                /**
                 * @param success  注册是否成功
                 * @param isInsert 此ID是否已经存在  存在有不会再注册
                 */
                void onVerify(boolean success, boolean isInsert);

        }
}
