package com.canbot.u05.sdk.clientdemo.face;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.canbot.u05.sdk.clientdemo.bean.PersonData;
import com.canbot.u05.sdk.clientdemo.bean.PersonVerifyData;
import com.canbot.u05.sdk.clientdemo.util.FileUtils;
import com.canbot.u05.sdk.clientdemo.util.Logger;
import com.canbot.u05.sdk.clientdemo.util.PathConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FaceDbProviderUtils {

        public static final String AUTHORITY = "com.uurobot.face_verify.provider.face";

        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/face_info");

        public static final String TABLE_NAME = "person_data";

        // 没有找到调用的地方，暂时注释.
        // public static boolean querry(Context context,String identification){
        // Cursor cursor =
        // context.getContentResolver().query(CONTENT_URI,
        // new String
        // []{Provider.PersonColumns.NAME,Provider.PersonColumns.IDENTIFICATION},
        // "identification=?" , new String []{identification}, null);
        // if(cursor.moveToNext())
        // return true;
        // return false;
        // }
        public static boolean delete(Context context, String identification) {
                PersonData personData = querryPersonData(context, identification);
                int result = context.getContentResolver().delete(CONTENT_URI, "identification=?",
                        new String[]{identification});
                deleteFaceFile(personData);
                return result != 0;

        }

        public static boolean deleteByName(Context context, String name) {
                PersonData personData = queryPersonDataByName(context, name);
                int result = context.getContentResolver().delete(CONTENT_URI, "name=?",
                        new String[]{name});
                if (personData != null){
                        deleteFaceFile(personData);
                }
                return result != 0;

        }

        /**
         * 删除人脸相关文件
         *
         * @param personData
         */
        private static void deleteFaceFile(PersonData personData) {
                String path = personData.getPath();
                File imagFile = new File(PathConstant.ROOT_PATH + path);
                if (!imagFile.exists()) {
                        imagFile = new File(PathConstant.ROOT_PATH + PathConstant.FACES_ABS_DIR + path);
                }
                if (imagFile.exists()) {
                        imagFile.delete();
                }
                if (personData.getVoicetype() == PersonVerifyData.VOICE_TYPE) {
                        String voicePath = personData.getContent();
                        File voiceFile = new File(PathConstant.ROOT_PATH + voicePath);
                        if (!voiceFile.exists()) {
                                voiceFile = new File(PathConstant.ROOT_PATH + PathConstant.FACES_VOICE_ABS_DIR + voicePath);
                        }
                        if (voiceFile.exists()) {
                                voiceFile.delete();
                        }
                }
        }

        public static boolean query(Context context, String identification) {
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"*"},
                        "identification=?", new String[]{identification}, null);
                if (cursor.moveToNext()) {
                        cursor.close();
                        return true;
                }
                cursor.close();
                return false;
        }

        public static PersonData querryPersonData(Context context, String identification) {
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"*"},
                        "identification=?", new String[]{identification}, null);
                if (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String path = cursor.getString(cursor.getColumnIndex("path"));
                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        int voicetype = cursor.getInt(cursor.getColumnIndex("voicetype"));
                        byte[] features = cursor.getBlob(cursor.getColumnIndex("features"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String dirType = cursor.getString(cursor.getColumnIndex("dirType"));
                        int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
                        // 日志打印输出
                        Logger.i("DatabaseUtils", "query-->" + name + "---->" + features);
                        cursor.close();
                        PersonData data = new PersonData(name, sex, content, voicetype, features, identification, path, email, phone, groupId);
                        data.setId(id);
                        data.setDirType(dirType);
                        return data;
                }
                cursor.close();
                return null;
        }

        public static PersonData queryPersonDataByName(Context context, String name) {
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, new String[]{"*"},
                        "name=?", new String[]{name}, null);
                if (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String path = cursor.getString(cursor.getColumnIndex("path"));
                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        int voicetype = cursor.getInt(cursor.getColumnIndex("voicetype"));
                        byte[] features = cursor.getBlob(cursor.getColumnIndex("features"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String dirType = cursor.getString(cursor.getColumnIndex("dirType"));
                        int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
                        // 日志打印输出
                        Logger.i("DatabaseUtils", "query-->" + name + "---->" + features);
                        cursor.close();
                        PersonData data = new PersonData(name, sex, content, voicetype, features, name, path, email, phone, groupId);
                        data.setId(id);
                        data.setDirType(dirType);
                        return data;
                }
                cursor.close();
                return null;
        }

        public static String queryMaxID(Context context) {
                String identification = null;
                Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                        new String[]{"identification"}, null, null, "ABS(identification) ASC");
                if (cursor.moveToLast()) {
                        identification = cursor.getString(cursor.getColumnIndex("identification"));
                }
                cursor.close();
                if (identification == null) {
                        identification = "-1";
                }
                return identification;
        }

        public static int updateByIdentification(Context context, ContentValues values, String identification) {
                int update = context.getContentResolver().update(CONTENT_URI, values,
                        "identification = ?", new String[]{identification});
                return update;
        }

        public static int updateByIdentification(Context context, String name, String sex, String identification) {
                ContentValues values = new ContentValues();
                // 像ContentValues中存放数据
                values.put("name", name);
                values.put("sex", sex);
                int update = context.getContentResolver().update(CONTENT_URI, values,
                        "identification = ?", new String[]{identification});
                return update;
        }

        public static int updateByIdentification(Context context, PersonData person, String identification) {
                ContentValues values = new ContentValues();
                // 像ContentValues中存放数据
                values.put("name", person.getName());
                values.put("email", person.getEmail());
                values.put("phone", person.getPhone());
                values.put("path", person.getPath());
                values.put("sex", person.getSex());
                values.put("voicetype", person.getVoicetype());
                values.put("content", person.getContent());
                values.put("features", person.getFeatures());
                values.put("dirType", person.getDirType());
                int update = context.getContentResolver().update(CONTENT_URI, values,
                        "identification = ?", new String[]{identification});
                return update;
        }

        public static int updateDirtype(Context context, String dirType, String identification) {
                ContentValues values = new ContentValues();
                // 像ContentValues中存放数据
                values.put("dirType", dirType);
                int update = context.getContentResolver().update(CONTENT_URI, values,
                        "identification = ?", new String[]{identification});
                return update;
        }

        public static int updatePathByIdentification(Context context, String path, String identification) {
                ContentValues values = new ContentValues();
                // 像ContentValues中存放数据
                values.put("path", path);
                int update = context.getContentResolver().update(CONTENT_URI, values,
                        "identification = ?", new String[]{identification});
                return update;
        }

        public static String queryMinID(Context context) {
                String identification = null;
                Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                        new String[]{"identification"}, null, null, "ABS(identification) DESC");
                if (cursor.moveToLast()) {
                        identification = cursor.getString(cursor.getColumnIndex("identification"));
                }
                cursor.close();
                return identification;
        }

        public static ArrayList<PersonData> queryAll(Context context) {
                Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
                ArrayList<PersonData> datas = new ArrayList<PersonData>();
                PersonData data;
                // 利用游标遍历所有数据对象
                while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex("_id"));
                        String path = cursor.getString(cursor.getColumnIndex("path"));
                        String email = cursor.getString(cursor.getColumnIndex("email"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        String content = cursor.getString(cursor.getColumnIndex("content"));
                        String identification = cursor.getString(cursor.getColumnIndex("identification"));
                        int voicetype = cursor.getInt(cursor.getColumnIndex("voicetype"));
                        byte[] features = cursor.getBlob(cursor.getColumnIndex("features"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String dirType = cursor.getString(cursor.getColumnIndex("dirType"));
                        int groupId = cursor.getInt(cursor.getColumnIndex("group_id"));
                        // 日志打印输出
                        Logger.i("DatabaseUtils", "query-->" + name + "---->" + features);
                        data = new PersonData(name, sex, content, voicetype, features, identification, path, email, phone, groupId);
                        data.setId(id);
                        data.setDirType(dirType);
                        datas.add(data);
                }
                cursor.close();
                return datas;
        }


        public static void add(Context context, String name, byte[] features, String sex, String content, int voicetype,
                               String identification, String path, String email, String phone) {
                ContentValues values = new ContentValues();
                // 像ContentValues中存放数据
                values.put("name", name);
                values.put("email", email);
                values.put("phone", phone);
                values.put("path", path);
                values.put("features", features);
                values.put("sex", sex);
                values.put("voicetype", voicetype);
                values.put("content", content);
                values.put("identification", identification);
                // 数据库执行插入命令
                ContentResolver resolver = context.getContentResolver();
                resolver.insert(CONTENT_URI, values);
        }

        public static boolean deleteAll(Context context) {
                int result = context.getContentResolver().delete(CONTENT_URI, null, null);
                FileUtils.deleteDir(PathConstant.FACES_DIR);
                return result != 0;
        }

        public static void add(Context context, List<PersonData> datas) {
                for (PersonData personData : datas) {
                        add(context, personData);
                }
        }

        public static void add(Context context, PersonData data) {
                boolean querry2 = false;
                if (data.getIdentification() != null) {
                        querry2 = query(context, data.getIdentification());
                }
                if (querry2) {
                        Logger.e("test", "此Id已存在：" + data.getIdentification());
                        return;
                }
                ContentValues values = new ContentValues();
                values.put("name", data.getName());
                values.put("phone", data.getPhone());
                values.put("email", data.getEmail());
                values.put("path", data.getPath());
                values.put("features", data.getFeatures());
                values.put("sex", data.getSex());
                values.put("voicetype", data.getVoicetype());
                values.put("content", data.getContent());
                values.put("identification", data.getIdentification());
                values.put("dirType", data.getDirType());
                ContentResolver resolver = context.getContentResolver();
                resolver.insert(CONTENT_URI, values);
        }

        public static void add(Context context, byte[] features, PersonVerifyData data) {
                boolean querry2 = query(context, data.getIdentification());
                if (querry2) {
                        Logger.e("test", "此Id已存在：" + data.getIdentification());
                        return;
                }
                ContentValues values = new ContentValues();
                values.put("name", data.getName());
                values.put("phone", data.getPhone());
                values.put("email", data.getEmail());
                values.put("path", data.getPath());
                values.put("features", features);
                values.put("sex", data.getSex());
                values.put("voicetype", data.getVoicetype());
                values.put("content", data.getContent());
                values.put("identification", data.getIdentification());
                ContentResolver resolver = context.getContentResolver();
                resolver.insert(CONTENT_URI, values);
        }
}
