package com.canbot.u05.sdk.clientdemo.face;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.canbot.u05.sdk.clientdemo.bean.PersonData;
import com.canbot.u05.sdk.clientdemo.bean.PersonVerifyData;
import com.canbot.u05.sdk.clientdemo.util.FileUtils;
import com.canbot.u05.sdk.clientdemo.util.PathConstant;
import com.cv.faceapi.CvFace;
import com.cv.faceapi.CvFaceDetector;
import com.cv.faceapi.CvFaceVerify;

public class VerifyUtil {

        private static final String TAG = "VerifyUtil";

        private Context mContext;

        private CvFaceDetector detector;

        private CvFaceVerify verify;

        private static VerifyUtil mVerifyUtil;

        public static VerifyUtil getInstace(Context context) {
                if (mVerifyUtil == null) {
                        mVerifyUtil = new VerifyUtil(context);
                }
                return mVerifyUtil;
        }

        private VerifyUtil(Context context) {
                mContext = context;
                detector = SenseTimeEngine.getInstance().getDetector();
                verify = SenseTimeEngine.getInstance().getVerify();
                Log.d(TAG, "verifyutill init success");
        }

        private byte[] getFeature(Bitmap bitmap) {
                CvFace[] face = detector.detect(bitmap);
                if (face != null && face.length != 0) {
                        byte[] mFeature = verify.getFeature(bitmap, face[0]);
                        bitmap.recycle();
                        return mFeature;
                }
                return null;
        }

        public boolean verify(Bitmap decodeFile, PersonVerifyData data, boolean isDelete) {
                byte[] feature = getFeature(decodeFile);
                if (null != feature && feature.length != 0) {
                        String newpath = data.getIdentification() + "_" + data.getName() + ".jpg";
                        FileUtils.copyFile(data.getPath(), PathConstant.FACES_DIR + newpath, isDelete);
//                        int voicetype = data.getVoicetype();
//                        if (voicetype == PersonVerifyData.VOICE_TYPE) {
//                                String voicePath;
//                                if (data.getContent().endsWith(".wav")) {
//                                        voicePath = data.getIdentification() + "_" + data.getName() + ".wav";
//                                }
//                                else {
//                                        voicePath = data.getIdentification() + "_" + data.getName() + ".mp3";
//                                }
//                                data.setContent(voicePath);
//                                FileUtils.copyFile(data.getContent(), PathConstant.FACES_VOICE_DIR + voicePath, isDelete);
//                        }
                        data.setPath(newpath);
                        PersonData personData = new PersonData(feature, data);
                        FaceDbUtils.getInstance().add(personData);
                        return true;
                }
                else {
                        return false;
                }
        }

        public boolean verifyFaceDir(Bitmap decodeFile, PersonVerifyData data) {
                byte[] feature = getFeature(decodeFile);
                if (null != feature && feature.length != 0) {
                        PersonData personData = new PersonData(feature, data);
                        FaceDbUtils.getInstance().add(personData);
                        return true;
                }
                else {
                        return false;
                }
        }
}
