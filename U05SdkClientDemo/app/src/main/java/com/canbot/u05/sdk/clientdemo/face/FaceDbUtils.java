package com.canbot.u05.sdk.clientdemo.face;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.canbot.u05.sdk.clientdemo.bean.PersonData;
import com.canbot.u05.sdk.clientdemo.util.Logger;

import java.util.List;

/**
 * 人脸数据库操作 必须先初始化--增 删 查
 */

public class FaceDbUtils {

        private static final String TAG = "FaceDbUtils";

        private static FaceDbUtils mFaceDbUtils;

        private Context mContext;

        private final String TABLE_NAME = "person_data";

        private ContentResolver resolver;
        private Cursor cursor;

        private FaceDbUtils() {
        }

        public static FaceDbUtils getInstance() {
                if (mFaceDbUtils == null) {
                        mFaceDbUtils = new FaceDbUtils();
                }
                return mFaceDbUtils;
        }

        public  final String AUTHORITY = "com.uurobot.face_verify.provider.face";

        public  final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/face_info");

        public void init(Context context) {
                this.mContext = context;
//                resolver = context.getContentResolver();
                Logger.e("初始化FaceDbUtils");
        }

        /**
         * 添加数据
         *
         * @param person
         */
        public void add(PersonData person) {
                FaceDbProviderUtils.add(mContext,person);
        }

        public boolean deleteByName(String name) {
                return FaceDbProviderUtils.deleteByName(mContext,name);
        }


        public List<PersonData> findAll() {
                return FaceDbProviderUtils.queryAll(mContext);
        }


        /**
         * 查找数据库中最大的identification
         * @return
         */
        public String findMaxIdentification() {

                String identification = FaceDbProviderUtils.queryMaxID(mContext);
                                Logger.e(TAG,"findMaxIdentification");
                return identification;
        }

}
