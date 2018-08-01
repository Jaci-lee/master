package com.canbot.u05.sdk.clientdemo.face;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;

import com.canbot.u05.sdk.clientdemo.util.ErrorCode;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.canbot.u05.sdk.clientdemo.util.PathConstant;
import com.cv.faceapi.CvAttributeResult;
import com.cv.faceapi.CvFace;
import com.cv.faceapi.CvFaceApiBridge;
import com.cv.faceapi.CvFaceAttribute;
import com.cv.faceapi.CvFaceDetector;
import com.cv.faceapi.CvFaceTrack;
import com.cv.faceapi.CvFaceVerify;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SenseTimeEngine {

        public static final float SCORE = 0.7f;

        private static final String TAG = "SenseTimeEngine";

        public static final int FEATURE_SIZE = 1385;// 特征值长度

        public static final String POINT = "21 point";

        //        public static final String POINT = "106 point";
        private boolean isInit = false;

        private static SenseTimeEngine instance;

        private CvFaceTrack mTracker;

        private CvFaceAttribute mAttribute;

        private CvFaceDetector mDetector;

        private CvFaceVerify mVerify;

        private OnSenseTimeInitListener mListener;

        private static Object lock = new Object();

        public static SenseTimeEngine getInstance() {
                if (instance == null) {
                        synchronized (lock) {
                                if (instance == null) {
                                        instance = new SenseTimeEngine();
                                }
                        }

                }
                return instance;
        }

        public synchronized boolean init(OnSenseTimeInitListener listener) {
                if (listener != null)
                        this.mListener = listener;
                if (isInit) {
                        if (listener != null)
                                listener.onSuccess();
                        return true;
                }
                String license = readFileLicense();
                try {
                        int rst = CvFaceApiBridge.FACESDK_INSTANCE
                                .cv_face_init_license_config(license);
                        Logger.e(TAG, "人脸ResultCode==" + rst);
                        if (rst != 0) {
                                if (this.mListener != null)
                                        this.mListener.onFail("Calling cv_face_init_license_config method failed! ResultCode=" + rst + "--->" + ErrorCode.getMessage(rst));
                                isInit = false;
                                return false;
                        }
                        //TODO
//                        mTracker = new CvFaceTrack(POINT);
//                        mTracker.setMaxDetectableFaces(-1);
//                        mDetector = new CvFaceDetector(POINT);
//                        mAttribute = CvFaceAttribute.getInstance();
//                        mVerify = new CvFaceVerify();
                }
                catch (RuntimeException e) {
                        if (this.mListener != null)
                                this.mListener.onFail(e.getMessage());
                        isInit = false;
                        return false;
                }
                if (this.mListener != null)
                        this.mListener.onSuccess();
                isInit = true;
                return true;
        }

        public synchronized boolean isInit() {
                return isInit;
        }

        public synchronized CvFaceTrack getTracker() {
                if (mTracker == null) {
                        mTracker = new CvFaceTrack(POINT);
                        mTracker.setMaxDetectableFaces(-1);
                }
                return mTracker;
        }

        public synchronized CvFaceAttribute getAttribute() {
                if (mAttribute == null)
                        mAttribute = CvFaceAttribute.getInstance();
                return mAttribute;
        }

        public synchronized CvFaceDetector getDetector() {
                if (mDetector == null)
                        mDetector = new CvFaceDetector(POINT);
                return mDetector;
        }


        public synchronized CvFaceVerify getVerify() {
                if (mVerify == null)
                        mVerify = new CvFaceVerify();
                return mVerify;
        }

        public void recycle() {
                mTracker = null;
                mDetector = null;
                mVerify = null;
                mAttribute = null;
                isInit = false;
        }

        public static String readFileLicense() {
                String res = "";
                try {
                        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                        InputStream in = new FileInputStream(rootPath + "/license.lic");
                        int length = in.available();
                        byte[] buffer = new byte[length];
                        in.read(buffer);
                        in.close();
                        res = new String(buffer, "UTF-8");
                }
                catch (IOException e) {
                        e.printStackTrace();
                }
                return res;
        }

        public interface OnSenseTimeInitListener {

                void onSuccess();

                void onFail(String message);

        }

        /**
         * 获取人脸特征值
         *
         * @param bitmap
         * @return
         */
        public synchronized byte[] getFaceFeature(Bitmap bitmap) {
                CvFace[] face = getDetector().detect(bitmap);
                if (face != null && face.length != 0) {
                        byte[] mFeature = getVerify().getFeature(bitmap, getMaxFace(face));
                        return mFeature;
                }
                return null;
        }

        /**
         *获取最大的人脸
         * @param faces
         * @return
         */
        private CvFace getMaxFace(CvFace[] faces) {
                if (faces != null && faces.length > 0) {
                        CvFace cvFace = faces[0];
                        Rect rect = cvFace.getRect();
                        int area = rect.height() * rect.width();
                        for (CvFace face : faces) {
                                Rect rect2 = face.getRect();
                                int area2 = rect2.height() * rect2.width();
                                if (area2 > area) {
                                        cvFace = face;
                                        area = area2;
                                }
                        }
                        return cvFace;
                }
                return null;
        }

        /**
         * 比较人脸相似度
         *
         * @param one
         * @param two
         * @return
         */
        public synchronized float compareFeature(byte[] one, byte[] two) {
                return getVerify().compareFeature(one, two);
        }

        /**
         * 扫描人脸位置
         *
         * @param data
         * @param orientation
         * @return
         */
        public synchronized CvFace[] trackFace(byte[] data, int orientation) {
                return getTracker().track(data, orientation, PathConstant.PREVIEW_WIDTH,
                        PathConstant.PREVIEW_HEIGHT);
        }

        /**
         * 扫描人脸特征信息
         *
         * @param image
         * @return
         */
        public CvAttributeResult attributeFace(Bitmap image) {
                return getAttribute().attribute(image, 0);
        }

}
